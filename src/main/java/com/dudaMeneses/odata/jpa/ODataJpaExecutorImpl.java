package com.dudaMeneses.odata.jpa;

import com.dudaMeneses.odata.filter.ODataFilter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ODataJpaExecutorImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements ODataJpaExecutor<T> {

    private final JpaEntityInformation entityInformation;
    private final EntityManager entityManager;

    public ODataJpaExecutorImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
    }

    @Override
    public List<T> findAll(@Nullable Specification<T> specification, ODataFilter filter){
        if(filter.hasSelectors() && CollectionUtils.isNotEmpty(Arrays.asList(filter.getSelectors()))){
            return getQuery(specification, Sort.unsorted(), filter).getResultList().stream()
                    .map(tuple -> {
                        try {
                            return createObjectFromTuple(filter, tuple);
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
        } else {
            return getQuery(specification, Sort.unsorted()).getResultList();
        }
    }

    @Override
    public Page<T> findAll(@Nullable Specification<T> specification, Pageable pageable, ODataFilter filter){
        if(filter.getSelectors() != null && CollectionUtils.isNotEmpty(Arrays.asList(filter.getSelectors()))){
            TypedQuery<Tuple> query = getQuery(specification, pageable, filter);
            return (Page)(pageable.isUnpaged() ?
                    new PageImpl(query.getResultList().stream()
                            .map(tuple -> {
                                try {
                                    return createObjectFromTuple(filter, tuple);
                                } catch (Exception e) {
                                    return null;
                                }
                            })
                            .collect(Collectors.toList())
                    ) :
                    this.readPageFromTuple(query, this.getDomainClass(), pageable, specification, filter));
        } else {
            TypedQuery<T> query = getQuery(specification, pageable);
            return (Page)(pageable.isUnpaged() ? new PageImpl(query.getResultList()) : this.readPage(query, this.getDomainClass(), pageable, specification));
        }
    }

    private TypedQuery<Tuple> getQuery(Specification<T> spec, Pageable pageable, ODataFilter filter) {
        Sort sort = pageable.isPaged() ? pageable.getSort() : Sort.unsorted();
        return getQuery(spec, sort, filter);
    }

    private TypedQuery<Tuple> getQuery(Specification<T> spec, Sort sort, ODataFilter filter) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<T> root = query.from(this.getDomainClass());

        Predicate predicate = spec.toPredicate(root, query, builder);

        if (predicate != null) {
            query.where(predicate);
        }

        List<Selection<?>> selections = getSelection(filter.getSelectors(), root);

        query.multiselect(selections.toArray(new Selection[selections.size()]));

        if (sort.isSorted()) {
            query.orderBy(QueryUtils.toOrders(sort, root, builder));
        }

        return this.entityManager.createQuery(query);
    }

    private List<Selection<?>> getSelection(String[] selectors, Root<T> root) {
        List<Selection<?>> selections = new ArrayList<>();

        if(selectors != null){
            for(String selector : selectors){
                try{
                    selections.add(root.get(selector));
                } catch(IllegalArgumentException e){
                    throw new InvalidPropertyException(getDomainClass(), selector, "Search could not be done.");
                }
            }
        }

        return selections;
    }

    @Override
    protected <S extends T> Page<S> readPage(TypedQuery<S> query, Class<S> domainClass, Pageable pageable, Specification<S> spec) {
        if (pageable.isPaged()) {
            query.setFirstResult((int)pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }

        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> executeCountQuery(this.getCountQuery(spec, domainClass)));
    }

    private Page<T> readPageFromTuple(TypedQuery<Tuple> query, Class<T> domainClass, Pageable pageable, Specification<T> specification, ODataFilter filter) {
        if (pageable.isPaged()) {
            query.setFirstResult((int)pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }

        return PageableExecutionUtils.getPage(
                query.getResultList().stream()
                        .map(tuple -> {
                            try {
                                return createObjectFromTuple(filter, tuple);
                            } catch (Exception e) {
                                return null;
                            }
                        })
                .collect(Collectors.toList()),
                pageable,
                () -> executeCountQuery(this.getCountQuery(specification, domainClass)));

    }

    private T createObjectFromTuple(ODataFilter filter, Tuple tuple) throws Exception {
        T instance = getDomainClass().getDeclaredConstructor().newInstance();

        for (int i = 0; i < filter.getSelectors().length; i++) {
            Field field = getDomainClass().getDeclaredField(filter.getSelectors()[i]);
            field.setAccessible(true);
            field.set(instance, tuple.get(i));
        }

        return instance;
    }

    private static Long executeCountQuery(TypedQuery<Long> query) {
        Assert.notNull(query, "TypedQuery must not be null!");
        List<Long> totals = query.getResultList();
        Long total = 0L;

        Long element;
        for(Iterator var3 = totals.iterator(); var3.hasNext(); total = total + (element == null ? 0L : element)) {
            element = (Long)var3.next();
        }

        return total;
    }
}

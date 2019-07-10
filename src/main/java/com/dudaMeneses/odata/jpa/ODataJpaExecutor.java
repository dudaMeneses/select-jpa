package com.dudaMeneses.odata.jpa;

import com.dudaMeneses.odata.filter.ODataFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.Nullable;

import java.util.List;

@NoRepositoryBean
public interface ODataJpaExecutor<T> {
    List<T> findAll(@Nullable Specification<T> specification, ODataFilter filter);
    Page<T> findAll(@Nullable Specification<T> specification, Pageable pageable, ODataFilter filter);
    long count(@Nullable Specification<T> specification);
}

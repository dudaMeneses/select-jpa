package config;

import org.springframework.data.jpa.domain.Specification;

public class TestSpecification {

    private TestSpecification(){}

    public static Specification<TestEntity> getAll(){
        return (root, criteriaQuery, criteriaBuilder) -> null;
    }

    public static Specification<TestEntity> getOne(){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), 1L);
    }

}

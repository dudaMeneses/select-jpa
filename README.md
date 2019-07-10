# OData JPA
This library integrate OData way of REST exposing with Spring and JPA.  

## Dependency
```xml
<dependency>
    <groupId>com.dudaMeneses</groupId>
    <artifactId>odata-jpa</artifactId>
    <version>${project.version}</version>
    <scope>compile</scope>
</dependency>
```

## How to use
- Add ``@EnableJpaRepositories(repositoryBaseClass = ODataJpaExecutorImpl.class)`` on Application class (Spring Boot)
- create your repository and extends ``ODataJpaExecutor``
```java
public interface DocumentRepository extends JpaRepository<Document,Integer>,ODataJpaExecutor<Document,Integer> {
    //...
}
```
- use it
```java
  @Test
  public void specificationWithProjection() {
      Specifications<Document> where = Specifications.where(DocumentSpecs.idEq(1L));
      Page<Document> all = documentRepository.findAll(where, new PageRequest(0,10), ODataFilter.builder().selectors("id").build());
      Assertions.assertThat(all).isNotEmpty();
  }
```

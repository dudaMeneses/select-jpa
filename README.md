# Select JPA
This library select sepcific fields from entity instead whole root when using `Specification` with Spring and JPA.  

## Dependency
```xml
<dependency>
    <groupId>com.dudaMeneses</groupId>
    <artifactId>select-jpa</artifactId>
    <version>${project.version}</version>
    <scope>compile</scope>
</dependency>
```

## How to use
- Add ``@EnableJpaRepositories(repositoryBaseClass = SelectJpaExecutorImpl.class)`` on Application class (Spring Boot)
- create your repository and extends ``SelectJpaExecutor``
```java
public interface DocumentRepository extends JpaRepository<Document,Integer>, SelectJpaExecutor<Document,Integer> {
    //...
}
```
- use it
```java
  @Test
  public void specificationWithProjection() {
      Specifications<Document> where = Specifications.where(DocumentSpecs.idEq(1L));
      Page<Document> all = documentRepository.findAll(where, new PageRequest(0,10), SelectFilter.builder().selectors("id").build());
      Assertions.assertThat(all).isNotEmpty();
  }
```

package com.duda.odata.jpa;

import com.duda.odata.filter.ODataFilter;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import config.TestApplication;
import config.TestRepository;
import config.TestSpecification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@DBRider
@DataJpaTest
@Transactional
@RunWith(SpringRunner.class)
@DataSet(value = "datasets/test.yml")
@ContextConfiguration(classes = TestApplication.class)
@TestPropertySource(locations = {"classpath:test.properties"})
public class ODataJpaExecutorTest {

    @Autowired
    private TestRepository testRepository;

    @Test
    public void findAll_whenNoSelector_shouldReturnAllFullObjects(){
        assertThat(testRepository.findAll(TestSpecification.getAll(), ODataFilter.builder().build()), hasItem(allOf(
                hasProperty("id", is(not(nullValue()))),
                hasProperty("name", is(not(nullValue())))
        )));
    }

    @Test
    public void findAll_whenHaveSelector_shouldReturnAllSelectedFields(){
        assertThat(testRepository.findAll(TestSpecification.getAll(), ODataFilter.builder().selectors(new String[]{"id"}).build()), hasItem(allOf(
                hasProperty("id", is(not(nullValue()))),
                hasProperty("name", is(nullValue()))
        )));
    }

    @Test
    public void findAll_whenGettingPageWithNoSelector_shouldReturnAllSelectedFields(){
        assertThat(testRepository.findAll(TestSpecification.getAll(), PageRequest.of(1, 1), ODataFilter.builder().build()),
                hasProperty("content", hasItem(allOf(
                    hasProperty("id", is(not(nullValue()))),
                    hasProperty("name", is(not(nullValue())))
                )
        )));
    }

    @Test
    public void findAll_whenGettingPageWithSelector_shouldReturnAllSelectedFields(){
        assertThat(testRepository.findAll(TestSpecification.getAll(), PageRequest.of(1, 1), ODataFilter.builder().selectors(new String[]{"id"}).build()),
                hasProperty("content", hasItem(allOf(
                    hasProperty("id", is(not(nullValue()))),
                    hasProperty("name", is(nullValue()))
                )
        )));
    }

    @Test
    public void findAll_whenUnpagedPageWithNoSelector_shouldReturnAllSelectedFields(){
        assertThat(testRepository.findAll(TestSpecification.getAll(), Pageable.unpaged(), ODataFilter.builder().build()),
                hasProperty("content", hasItem(allOf(
                        hasProperty("id", is(not(nullValue()))),
                        hasProperty("name", is(not(nullValue())))
                        )
                )));
    }

    @Test
    public void findAll_whenUnpagedPageWithSelector_shouldReturnAllSelectedFields(){
        assertThat(testRepository.findAll(TestSpecification.getAll(), Pageable.unpaged(), ODataFilter.builder().selectors(new String[]{"id"}).build()),
                hasProperty("content", hasItem(allOf(
                        hasProperty("id", is(not(nullValue()))),
                        hasProperty("name", is(nullValue()))
                        )
                )));
    }

    @Test(expected = InvalidPropertyException.class)
    public void findAll_whenEntityHasNoProperEntity_shouldThrowException(){
        testRepository.findAll(TestSpecification.getAll(), ODataFilter.builder().selectors(new String[]{"email"}).build());
    }

}
package com.duda.odata.interpreter;

import com.duda.odata.filter.LogicalOperatorEnum;
import com.duda.odata.filter.ODataFilter;
import com.duda.odata.filter.ODataOperator;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ODataInterpreterTest {

    @Test
    public void getFilter_whenQueryParamsAreEmpty_shouldReturnDefaultODataFilter(){
        assertThat(ODataInterpreter.getFilter(null, null, null),
                is(
                        ODataFilter.builder()
                                .selectors(new ArrayList<>())
                                .filters(new ArrayList<>())
                                .build()
                )
        );
    }

    @Test
    public void getFilter_whenHaveEqualFilterForString_shouldReturnODataFilterWithEqualOperator(){
        assertThat(ODataInterpreter.getFilter(null, null, "name eq 'Test OData'").getFilters(),
                hasItem(ODataOperator.builder()
                                .field("name")
                                .operator(LogicalOperatorEnum.EQUAL)
                                .value("Test OData")
                                .build()
                )
        );
    }

    @Test
    public void getFilter_whenHaveEqualFilterForNumber_shouldReturnODataFilterWithEqualOperator(){
        assertThat(ODataInterpreter.getFilter(null, null, "price eq 123").getFilters(),
                hasItem(ODataOperator.builder()
                        .field("price")
                        .operator(LogicalOperatorEnum.EQUAL)
                        .value("123")
                        .build()
                )
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFilter_whenMalformedEqualFilter_shouldReturnEmptyOperator(){
        ODataInterpreter.getFilter(null, null, "price eq ");
    }

    @Test
    public void getFilter_whenHaveNotEqualFilterForString_shouldReturnODataFilterWithEqualOperator(){
        assertThat(ODataInterpreter.getFilter(null, null, "name ne 'Test OData'").getFilters(),
                hasItem(ODataOperator.builder()
                        .field("name")
                        .operator(LogicalOperatorEnum.NOT_EQUAL)
                        .value("Test OData")
                        .build()
                )
        );
    }

    @Test
    public void getFilter_whenHaveNotEqualFilterForNumber_shouldReturnODataFilterWithEqualOperator(){
        assertThat(ODataInterpreter.getFilter(null, null, "price ne 123").getFilters(),
                hasItem(ODataOperator.builder()
                        .field("price")
                        .operator(LogicalOperatorEnum.NOT_EQUAL)
                        .value("123")
                        .build()
                )
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFilter_whenMalformedNotEqualFilter_shouldReturnEmptyOperator(){
        ODataInterpreter.getFilter(null, null, "price ne ");
    }
}
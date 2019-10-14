package com.duda.odata.interpreter;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ODataInterpreterTest {

    @Test
    public void getFilter_whenQueryHasThreeSelectors_shouldReturnODataFilterWithSelectors(){
        String query = "select=abc,def,123";

        assertThat(
                Arrays.asList(ODataInterpreter.getFilter(query).getSelectors()),
                containsInAnyOrder("abc", "def", "123")
        );
    }

    @Test
    public void getFilter_whenQueryHasOnlyOneSelector_shouldReturnODataFilterWithSelector(){
        String query = "select=abc";

        assertThat(
                Arrays.asList(ODataInterpreter.getFilter(query).getSelectors()),
                containsInAnyOrder("abc")
        );
    }

    @Test
    public void getFilter_whenQueryHasCounting_shouldReturnCountingNumber(){
        String query = "top=2";

        assertThat(
                ODataInterpreter.getFilter(query).getCount(),
                is(2)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFilter_whenQueryHasReceivingInvalidValueForCounting_shouldIllegalArgumentException(){
        ODataInterpreter.getFilter("top=abc");
    }

}
package com.duda.odata.interpreter;

import com.duda.odata.factory.OperatorFactory;
import com.duda.odata.filter.LogicalOperatorEnum;
import com.duda.odata.filter.ODataFilter;
import com.duda.odata.filter.ODataOperator;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class ODataInterpreter {

    private ODataInterpreter(){}

    public static ODataFilter getFilter(List<String> selectors, Integer top, String filter) {
        return ODataFilter.builder()
                .selectors(new ArrayList<>(CollectionUtils.emptyIfNull(selectors)))
                .count(top)
                .filters(getFilters(filter))
                .build();
    }

    private static List<ODataOperator> getFilters(String filter) {
        if(!StringUtils.isEmpty(filter)){
            return getOperators(filter);
        } else  return new ArrayList<>();
    }

    private static List<ODataOperator> getOperators(String filter) {
        if(!StringUtils.isEmpty(filter)){
            List<ODataOperator> operators = new ArrayList<>();

            operators.add(OperatorFactory.getInstance(filter).get());

            return operators;
        } else return new ArrayList<>();
    }

}

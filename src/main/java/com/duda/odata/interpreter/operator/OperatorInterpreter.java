package com.duda.odata.interpreter.operator;

import com.duda.odata.filter.LogicalOperatorEnum;
import com.duda.odata.filter.ODataOperator;
import lombok.AllArgsConstructor;

import java.util.regex.Matcher;

@AllArgsConstructor
public abstract class OperatorInterpreter {

    private String filter;

    abstract LogicalOperatorEnum getOperator();

    public ODataOperator get(){
        Matcher matcher = getOperator().getMatcher(filter);

        if(matcher.find()){
            return ODataOperator.builder()
                    .field(matcher.group("field"))
                    .operator(getOperator())
                    .value(removeQuotes(matcher.group("value")))
                    .build();
        } else throw new IllegalArgumentException("Malformed 'filter' request parameter");
    }

    private static Object removeQuotes(String value){
        if(value.startsWith("'") && value.endsWith("'")){
            value = value.substring(1, value.length() -1);
        }
        return value;
    }
}

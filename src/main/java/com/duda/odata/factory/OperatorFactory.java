package com.duda.odata.factory;

import com.duda.odata.filter.LogicalOperatorEnum;
import com.duda.odata.interpreter.operator.EqualsOperatorInterpreter;
import com.duda.odata.interpreter.operator.NotEqualsOperatorInterpreter;
import com.duda.odata.interpreter.operator.OperatorInterpreter;

public class OperatorFactory {

    private OperatorFactory(){}

    public static OperatorInterpreter getInstance(String filter) {
        if(LogicalOperatorEnum.EQUAL.getMatcher(filter).find()){
            return new EqualsOperatorInterpreter(filter);
        }

        if(LogicalOperatorEnum.NOT_EQUAL.getMatcher(filter).find()) {
            return new NotEqualsOperatorInterpreter(filter);
        }

        throw new IllegalArgumentException("Malformed 'filter' request parameter");
    }
}

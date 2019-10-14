package com.duda.odata.interpreter.operator;

import com.duda.odata.filter.LogicalOperatorEnum;

public class NotEqualsOperatorInterpreter extends OperatorInterpreter {

    public NotEqualsOperatorInterpreter(String filter) {
        super(filter);
    }

    @Override
    public LogicalOperatorEnum getOperator() {
        return LogicalOperatorEnum.NOT_EQUAL;
    }
}

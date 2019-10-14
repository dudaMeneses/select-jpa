package com.duda.odata.interpreter.operator;

import com.duda.odata.filter.LogicalOperatorEnum;

public class EqualsOperatorInterpreter extends OperatorInterpreter {

    public EqualsOperatorInterpreter(String filter) {
        super(filter);
    }

    public LogicalOperatorEnum getOperator() {
        return LogicalOperatorEnum.EQUAL;
    }
}

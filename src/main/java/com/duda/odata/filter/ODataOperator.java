package com.duda.odata.filter;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class ODataOperator {
    private String field;
    private LogicalOperatorEnum operator;
    private Object value;
}

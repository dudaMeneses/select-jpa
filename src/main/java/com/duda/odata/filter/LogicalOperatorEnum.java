package com.duda.odata.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@AllArgsConstructor
public enum LogicalOperatorEnum {
    EQUAL("(?<field>[a-zA-Z0-9_]*) eq (?<value>[0-9]+|'(.*)')"),
    NOT_EQUAL("(?<field>[a-zA-Z0-9_]*) ne (?<value>[0-9]+|'(.*)')"),
    GREATER_THAN(""),
    GREATER_OR_EQUAL(""),
    LESS_THAN(""),
    LESS_OR_EQUAL(""),
    AND(""),
    OR(""),
    NOT("");

    private String regex;

    public Matcher getMatcher(String query) {
        Pattern pattern = Pattern.compile(this.getRegex());
        return pattern.matcher(query);
    }
}

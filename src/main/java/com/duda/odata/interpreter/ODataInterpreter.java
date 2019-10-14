package com.duda.odata.interpreter;

import com.duda.odata.filter.ODataFilter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ODataInterpreter {

    private ODataInterpreter(){}

    public static ODataFilter getFilter(String query) {
        return ODataFilter.builder()
                .selectors(getSelectors(query))
                .count(getCounting(query))
                .build();
    }

    private static Integer getCounting(String query) {
        Pattern pattern = Pattern.compile("(?<=top=)([0-9]*)");
        Matcher matcher = pattern.matcher(query);

        if(matcher.find()){
            return Integer.valueOf(matcher.group());
        } else return null;
    }

    private static String[] getSelectors(String query) {
        Pattern pattern = Pattern.compile("(?<=select=).*?(?=&|$)");
        Matcher matcher = pattern.matcher(query);

        if(matcher.find()){
            return matcher.group().split(",");
        } else return new String[0];
    }
}

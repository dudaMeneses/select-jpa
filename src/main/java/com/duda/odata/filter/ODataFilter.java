package com.duda.odata.filter;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.awt.print.Pageable;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class ODataFilter {
    String[] selectors;
    Integer count;
    Pageable pageable;
    Sort sort;

    public boolean hasSelectors(){
        return selectors != null && selectors.length > 0;
    }
}

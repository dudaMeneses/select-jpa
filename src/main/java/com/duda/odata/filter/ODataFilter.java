package com.duda.odata.filter;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Sort;

import java.awt.print.Pageable;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
public class ODataFilter {
    List<String> selectors;
    Integer count;
    Pageable pageable;
    Sort sort;
    List<ODataOperator> filters;

    public boolean hasSelectors(){
        return CollectionUtils.isNotEmpty(selectors);
    }
}

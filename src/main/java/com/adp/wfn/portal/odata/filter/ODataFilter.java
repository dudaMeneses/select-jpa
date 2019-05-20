package com.adp.wfn.portal.odata.filter;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class ODataFilter {
    String[] selectors;
}

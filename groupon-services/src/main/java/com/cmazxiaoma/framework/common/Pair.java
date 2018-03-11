package com.cmazxiaoma.framework.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 成对出现的值
 */
@AllArgsConstructor
public class Pair<H, E> {

    @Getter
    @Setter
    private H head;
    @Getter
    @Setter
    private E end;

}

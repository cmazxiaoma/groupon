package com.cmazxiaoma.framework.spring.core.convert.support;

import org.springframework.core.convert.converter.Converter;

/**
 * <p>ObjectToStringConverter</p>
 */
public class ObjectToStringConverter implements Converter<Object, String> {

    public String convert(Object source) {
        return source.toString();
    }

}

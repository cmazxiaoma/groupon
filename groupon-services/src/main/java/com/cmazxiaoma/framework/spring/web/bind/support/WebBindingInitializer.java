package com.cmazxiaoma.framework.spring.web.bind.support;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * WebBindingInitializer
 */
public class WebBindingInitializer implements org.springframework.web.bind.support.WebBindingInitializer {

    /* (non-Javadoc)
     * @see org.springframework.web.bind.support.WebBindingInitializer#initBinder(org.springframework.web.bind.WebDataBinder, org.springframework.web.context.request.WebRequest)
     */
    @Override
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new DateConvertEditor());
        binder.registerCustomEditor(long.class, new DateConvertEditor());
        binder.registerCustomEditor(int.class, new IntConvertEditor());
    }

}

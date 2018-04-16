package com.cmazxiaoma.framework.web.controller;

import com.cmazxiaoma.framework.spring.web.bind.support.DateConvertEditor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Controller基类
 */
public abstract class BaseController {

    protected HttpServletRequest httpRequest;

    protected HttpServletResponse httpResponse;

    protected HttpSession httpSession;

    protected ModelMap modelMap;

    @ModelAttribute
    protected void initSpringMvcApiModel(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
                                         HttpSession httpSession, ModelMap modelMap) {
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;
        this.httpSession = httpSession;
        this.modelMap = modelMap;
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new DateConvertEditor());
    }


}
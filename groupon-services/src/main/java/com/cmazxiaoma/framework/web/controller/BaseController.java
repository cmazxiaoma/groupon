package com.cmazxiaoma.framework.web.controller;

import com.tortuousroad.framework.spring.web.bind.support.DateConvertEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Controller基类
 */
public abstract class BaseController {

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
	}

}
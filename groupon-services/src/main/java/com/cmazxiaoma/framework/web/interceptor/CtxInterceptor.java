package com.cmazxiaoma.framework.web.interceptor;

import com.cmazxiaoma.util.IpUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class CtxInterceptor implements HandlerInterceptor {

    public static final String CONTEXT_NAME = "ctx";

    public static final String SCHEME_SUFFEX = "://";

    public static final String SEPARATE_SERVER_PORT = ":";

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception e) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object args, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null || modelAndView.getModel() == null) {
            return;
        }

//        //自己选择城市
//        if () {
//
//        }
        modelAndView.addObject("areaName", IpUtil.getArea(request).getName());

        StringBuffer ctx = new StringBuffer();
        ctx.append(request.getScheme());
        ctx.append(SCHEME_SUFFEX);
        ctx.append(request.getServerName());
        if (request.getServerPort() != 80) {
            ctx.append(SEPARATE_SERVER_PORT);
            ctx.append(request.getServerPort());
        }
        ctx.append(request.getContextPath());

        modelAndView.getModel().put(CONTEXT_NAME, ctx.toString());

        //重定向操作特殊处理
        if (modelAndView.getViewName().startsWith("redirect:")) {
            modelAndView.getModel().clear();
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        return true;
    }

}

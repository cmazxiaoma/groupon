package com.cmazxiaoma.site.web.site.interceptor;

import com.cmazxiaoma.site.web.base.objects.WebUser;
import com.cmazxiaoma.site.web.utils.CookieUtil;
import com.cmazxiaoma.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: 登录拦截器
 * @date 2018/4/4 10:55
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        WebUser webUser = CookieUtil.getLoginUser(request);

        if (webUser == null) {
            String basePath = request.getScheme() + "//:" + request.getServerName()
                    + ":" + request.getServerPort();
            response.sendRedirect( "/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

package com.cmazxiaoma.site.web.site.interceptor;

import com.cmazxiaoma.framework.base.context.SpringApplicationContext;
import com.cmazxiaoma.groupon.cart.service.CartService;
import com.cmazxiaoma.site.web.base.objects.WebUser;
import com.cmazxiaoma.site.web.utils.CookieUtil;
import com.cmazxiaoma.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/4/4 10:55
 */
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        //从cookie中取出用户信息
        WebUser webUser = CookieUtil.getLoginUser(request);

        if (webUser != null) {
            CookieUtil.setLoginUser(response, webUser);
        }

        if (modelAndView != null && webUser != null
                && org.apache.commons.lang3.StringUtils.isNotEmpty(webUser.getUsername())) {
            Long cartSize = SpringApplicationContext.getBean(CartService.class)
                    .getCartSize(webUser.getUserId());
            modelAndView.addObject("cartSize", cartSize);
            modelAndView.addObject("username", webUser.getUsername());
        }

        if (modelAndView != null && modelAndView.getViewName().startsWith("redirect:")) {
            modelAndView.getModel().clear();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

package com.cmazxiaoma.site.web.site.controller.user;

import com.cmazxiaoma.framework.base.exception.ServiceException;
import com.cmazxiaoma.site.web.base.objects.WebUser;
import com.cmazxiaoma.site.web.constants.WebConstants;
import com.cmazxiaoma.site.web.site.controller.BaseSiteController;
import com.cmazxiaoma.site.web.utils.CookieUtil;
import com.cmazxiaoma.user.entity.User;
import com.cmazxiaoma.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆
 */
@Controller
@Slf4j
public class LoginController extends BaseSiteController {

    @Autowired
    private UserService userService;

    /**
     * 显示登陆页面
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String preLogin(HttpServletRequest request, Model model) {
        // 登陆自动跳转到某个链接
//        String referer = request.getHeader("referer");
//        String requestURL = request.getRequestURL().toString();
//        referer = handleLoginRedirectURL(requestURL, referer);
//        model.addAttribute("refererURL", referer);
        return "/user/login";
    }

    @RequestMapping(value = "/dologin", method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response, User user) {
        try {
            //验证用户
            User loginUser = userService.login(user);
            if (null == loginUser) {
                return "redirect:/login";
            } else {
                //处理cookie
                WebUser webUser = new WebUser();
                webUser.setUserId(loginUser.getId());
                webUser.setUsername(loginUser.getName());
                webUser.setLoginStatus(WebConstants.USER_LOGIN_STATUS_NORMAL);
                super.setCurrentLoginUser(response, webUser);
                return "redirect:/";
            }
        } catch (ServiceException ex) {
            log.error(ex.getMessage());
            log.info("---------------------------check--------------------");
            modelMap.put("errorMessage", ex.getMessage());
            return "/user/login";
        }
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletResponse response) {
        CookieUtil.removeCookie(httpRequest, response, CookieUtil.USER_INFO, "/");
        httpRequest.getSession().invalidate();
        log.info("user-cookies=" + CookieUtil.getCookieValue(CookieUtil.USER_INFO, httpRequest));
        return "redirect:/";
    }

}

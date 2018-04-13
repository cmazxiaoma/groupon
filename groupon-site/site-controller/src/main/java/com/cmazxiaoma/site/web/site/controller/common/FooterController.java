package com.cmazxiaoma.site.web.site.controller.common;

import com.cmazxiaoma.site.web.site.controller.BaseSiteController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面底部链接Controller
 */
@Controller
public class FooterController extends BaseSiteController {

    // 关于我们
    @RequestMapping("/about/about.html")
    public String about() {
        return "/about/about";
    }

    // 联系我们
    @RequestMapping("/about/contact.html")
    public String contact() {
        return "/about/contact";
    }

    // 招贤纳士
    @RequestMapping("/about/recruiter.html")
    public String recruiter() {
        return "/about/recruiter";
    }

    // 服务条款
    @RequestMapping("/help/service.html")
    public String service() {
        return "/helpcenter/service";
    }
}
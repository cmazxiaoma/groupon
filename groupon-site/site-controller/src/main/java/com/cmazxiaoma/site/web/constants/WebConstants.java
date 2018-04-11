package com.cmazxiaoma.site.web.constants;

/**
 * Web常量
 */
public class WebConstants {

    // ------错误页面------
    public static final String ERROR_PAGE_404 = "/common/error/404";

    // ------网站域名------
    public static final String SITE_DOMAIN_NAME = "http://127.0.0.1:8080";

    public static final String SITE_JS_DOMAIN_NAME = "http://127.0.0.1:8080";

    public static final String SITE_CSS_DOMAIN_NAME = "http://127.0.0.1:8080";

    public static final String SITE_IMAGE_DOMAIN_NAME = "http://127.0.0.1:9000";

    // ------用户登陆状态------
    /**
     * 未登录
     */
    public static final int USER_LOGIN_STATUS_NO = 0;

    /**
     * 登录
     */
    public static final int USER_LOGIN_STATUS_NORMAL = 1;

    /**
     * 安全登录
     */
    public static final int USER_LOGIN_STATUS_SECURITY = 2;

    // ------用户默认头像------
    public static final String DEFAULT_HEAD_IMG_URL = SITE_IMAGE_DOMAIN_NAME + "/images/default_head_img.png";

    // ------短信发送方网站------
    public static final String SMS_CODE_SENDER_SITE = "site";

    // ------短信发送方App------
    public static final String SMS_CODE_SENDER_APP = "app";
}
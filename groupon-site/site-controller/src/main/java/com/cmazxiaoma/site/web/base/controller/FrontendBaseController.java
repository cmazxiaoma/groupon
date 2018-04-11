package com.cmazxiaoma.site.web.base.controller;

import com.cmazxiaoma.framework.util.EmailUtil;
import com.cmazxiaoma.framework.util.EncryptionUtil;
import com.cmazxiaoma.framework.util.RegexUtil;
import com.cmazxiaoma.framework.util.SMSUtil;
import com.cmazxiaoma.framework.web.controller.BaseController;
import com.cmazxiaoma.site.web.base.objects.WebUser;
import com.cmazxiaoma.site.web.constants.WebConstants;
import com.cmazxiaoma.site.web.utils.CookieUtil;
import com.cmazxiaoma.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class FrontendBaseController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(FrontendBaseController.class);

    /**
     * 临时存储用户短信验证码（App）
     */
    public static final Map<String, String> SMS_CODES = new HashMap<>();

    @Autowired
    private MessageSource messageSource;

    protected Long getAreaId(HttpServletRequest request) {
        return IpUtil.getArea(request).getId();
    }

    /**
     * 获取当前登录用户
     */
    protected WebUser getCurrentLoginUser(HttpServletRequest request) {
        return CookieUtil.getLoginUser(request);
    }

    /**
     * 设置当前登录用户
     */
    protected void setCurrentLoginUser(HttpServletResponse response, WebUser user) {
        CookieUtil.setLoginUser(response, user);
    }

    /**
     * 生成404页面
     */
    protected String generateError404Page(HttpServletResponse response) {
        //公益信息
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return WebConstants.ERROR_PAGE_404;
    }

    /**
     * 发送短信验证码
     *
     * @param mobilephone 电话号码
     * @param action      目的，如：register(注册)、retrievePwd(找回密码)等，详见：com.tortuousroad.framework.util.SMSUtil.ACTION_MAP
     * @param sender      发送者，site、app等
     * @return
     */
    public String sendSMSCode(String mobilephone, String action, String sender) {
        if (!RegexUtil.isValidPhoneNumberCN(mobilephone)) {
            return null;
        }

        if (!SMSUtil.ACTION_MAP.containsKey(action)) {
            return null;
        }

        // 发送短信
        String smsCode = SMSUtil.generate();

        // 成功状态
        String text = SMSUtil.ACTION_MAP.get(action).replace("{0}", smsCode);


        boolean success = false;

        if (sender.equals(WebConstants.SMS_CODE_SENDER_SITE)) {
            success = SMSUtil.sendSMSText(mobilephone, text);
        } else if (sender.equals(WebConstants.SMS_CODE_SENDER_APP)) {
            success = SMSUtil.sendSMSTextForApp(mobilephone, text);

            if (success) {
                SMS_CODES.put(mobilephone, smsCode);
            }
        }

        if (!success) {
            logger.info("发送短信验证码，来源：" + sender + "，手机号：" + mobilephone + "，验证码：" + smsCode + "，状态：失败");
            return null;
        }

        logger.info("发送短信验证码，来源：" + sender + "，手机号：" + mobilephone + "，验证码：" + smsCode + "，状态：成功");
        return smsCode;
    }

    /**
     * 验证短信验证码（App）
     *
     * @param mobilephone
     * @param smsCode
     * @return
     */
    public boolean checkSMSCode(String mobilephone, String smsCode) {
        if (StringUtils.isEmpty(mobilephone) ||
                !RegexUtil.isValidPhoneNumberCN(mobilephone) || StringUtils.isEmpty(smsCode)) {
            return false;
        }

        String code = SMS_CODES.get(mobilephone);

        if (StringUtils.isEmpty(code)) {
            return false;
        }

        boolean isSMSCodeValid = code.equals(smsCode);
        if (isSMSCodeValid) {
            SMS_CODES.remove(mobilephone);
        }

        logger.info("验证短信验证码，mobilephone：" + mobilephone + "，smsCode：" + smsCode + "，smsCodeInServer：" + code + " " + (isSMSCodeValid ? "正确" : "错误"));
        return isSMSCodeValid;
    }

    /**
     * 发送注册验证邮件
     */
    public void sendVerifyEmail(Long userId, String email) {
        if (userId <= 0 || !StringUtils.hasText(email)) {
            logger.info("发送注册验证邮箱失败，邮件信息(userId、email)错误");
            return;
        }

        String verifyURL = generateRegVerifyURL(userId, email);

        String subject = "感谢注册慕课团，请验证Email";
        StringBuilder text = new StringBuilder();
        text.append("Hi，<br/>");
        text.append("感谢您注册慕课团，请点击下面的链接验证您的Email：<br/>");
        text.append("<a href='" + verifyURL + "' target='_blank'>" + verifyURL + "</a><br/><br/>");
        text.append("----------------<br/>");
        text.append("慕课团");
        EmailUtil.sendEmail(new String[]{email}, subject, text.toString(), EmailUtil.CONTENT_TYPE_HTML);
    }

    /**
     * 生成注册邮箱验证URL
     *
     * @return
     */
    private String generateRegVerifyURL(Long userId, String email) {
        StringBuilder link = new StringBuilder();
        link.append(WebConstants.SITE_DOMAIN_NAME).append("/reg/verify/").append(EncryptionUtil.encryptAES(userId + ":" + email));
        return link.toString();
    }

    /**
     * 发送找回密码验证邮件
     */
    public void sendRetrievePwdEmail(Long userId, String password, String email) {
        if (userId <= 0 || !StringUtils.hasText(email) || !StringUtils.hasText(password)) {
            logger.info("发送注册验证邮箱失败，邮件信息(userId、email、password)错误");
            return;
        }

        String verifyURL = generateRetrievePwdVerifyURL(userId, password);

        String subject = "慕课团重设密码";
        StringBuilder text = new StringBuilder();
        text.append("Hi，<br/>");
        text.append("您在慕课团申请了重设密码，请点击下面的链接，然后根据页面提示完成密码重设：<br/>");
        text.append("<a href='" + verifyURL + "' target='_blank'>" + verifyURL + "</a><br/><br/>");
        text.append("----------------<br/>");
        text.append("慕课团");
        EmailUtil.sendEmail(new String[]{email}, subject, text.toString(), EmailUtil.CONTENT_TYPE_HTML);
    }

    /**
     * 生成找回密码邮箱验证URL
     *
     * @return
     */
    private String generateRetrievePwdVerifyURL(Long userId, String password) {
        StringBuilder link = new StringBuilder();
        link.append(WebConstants.SITE_DOMAIN_NAME).append("/retrievepwd/verify/").append(EncryptionUtil.encryptAES(userId + ":" + password));
        return link.toString();
    }

    /**
     * 处理登陆跳转URL
     *
     * @param referer 跳转URL
     * @return
     */
    public String handleLoginRedirectURL(String requestURL, String referer) {
        if (StringUtils.isEmpty(requestURL) || StringUtils.isEmpty(referer)) {
            return "";
        }

        // 查看referer是否属于当前请求域名之下
        int index = requestURL.indexOf("http://");
        if (index == -1 || index != 0) {
            return "";
        }

        String tempURL = requestURL.replace("http://", "");
        index = tempURL.indexOf("/");
        if (index == -1) {
            return "";
        }

        String domainName = requestURL.substring(0, index + "http://".length());
        if (!referer.contains(domainName)) {
            return "";
        }

        // 登陆跳转URL黑名单
        String blackList = messageSource.getMessage("login_redirect_black_list", null, "", null);
        if (!StringUtils.isEmpty(blackList)) {
            String[] urls = blackList.split(",");
            if (urls != null && urls.length > 0) {
                for (String url : urls) {
                    if (referer.contains(url)) {
                        return "";
                    }
                }
            }
        }

        return referer;
    }
}
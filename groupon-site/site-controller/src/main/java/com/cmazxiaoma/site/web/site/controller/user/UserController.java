package com.cmazxiaoma.site.web.site.controller.user;

import com.cmazxiaoma.framework.util.EncryptionUtil;
import com.cmazxiaoma.framework.util.QRCodeUtil;
import com.cmazxiaoma.framework.util.RegexUtil;
import com.cmazxiaoma.framework.util.SMSUtil;
import com.cmazxiaoma.site.web.base.objects.WebUser;
import com.cmazxiaoma.site.web.constants.WebConstants;
import com.cmazxiaoma.site.web.site.controller.BaseSiteController;
import com.cmazxiaoma.user.entity.User;
import com.cmazxiaoma.user.service.UserService;
import com.google.code.kaptcha.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
public class UserController extends BaseSiteController {

    @Autowired
    private UserService userService;

    /**
     * 显示注册页面
     *
     * @return
     */
    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    public String reg() {
        return "/user/register";
    }

    /**
     * 显示注册页面
     *
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(HttpServletRequest request, HttpServletResponse response, String username, String password1, String password2) {
        if (!Objects.equals(password1, password2)) {
            return "redirect:/reg";
        }

        User user = new User();
        user.setName(username);
        user.setPassword(password1);
        boolean regSuccess = userService.register(user);
        WebUser webUser = new WebUser();
        webUser.setLoginStatus(WebConstants.USER_LOGIN_STATUS_NORMAL);
        webUser.setUserId(user.getId());
        webUser.setUsername(username);
        super.setCurrentLoginUser(response, webUser);
        return "redirect:/";
    }

    /**
     * 检查图片验证码(Ajax)
     *
     * @return
     */
    @RequestMapping(value = "/checkCaptcha", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> checkCaptcha(String captcha, HttpSession session) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("isCaptchaCorrect", session.getAttribute(Constants.KAPTCHA_SESSION_KEY).toString().equalsIgnoreCase(captcha));
        return jsonMap;
    }

    /**
     * 检查短信验证码(Ajax)
     *
     * @return
     */
    @RequestMapping(value = "/checkSMSCode", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> checkSMSCode(String mobilephone, String smsCode, HttpSession session) {
        Map<String, Object> jsonMap = new HashMap<>();
        String smsCodeInSession = String.valueOf(session.getAttribute(SMSUtil.CODE_SESSION_KEY));
        jsonMap.put("isSMSCodeCorrect", smsCodeInSession.equals(mobilephone + ":" + smsCode));
        return jsonMap;
    }

    /**
     * 查看用户名是否已经注册过(Ajax)
     *
     * @return
     */
    @RequestMapping(value = "/checkExistsUsername", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> checkExistsUsername(String username) {
        Map<String, Object> jsonMap = new HashMap<>();
        User user = userService.getByUsername(username);

        // 手机号已注册
        if (user != null) {
            jsonMap.put("isExists", true);
        } else {
            jsonMap.put("isExists", false);
        }
        return jsonMap;
    }

    /**
     * 发送短信验证码(Ajax)
     *
     * @return
     */
    @RequestMapping(value = "/sendSMSCode", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sendSMSCode(HttpSession session, String mobilephone, String action) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String smsCode = sendSMSCode(mobilephone, action, WebConstants.SMS_CODE_SENDER_SITE);
        // 发送不成功
        if (StringUtils.isEmpty(smsCode)) {
            jsonMap.put("succ", false);
        } else {
            session.setAttribute(SMSUtil.CODE_SESSION_KEY, mobilephone + ":" + smsCode);
            jsonMap.put("succ", true);
            jsonMap.put("smsCode", smsCode);
        }
        return jsonMap;
    }

    /**
     * 显示注册成功页面
     *
     * @return
     */
    @RequestMapping(value = "/regsuccess", method = RequestMethod.GET)
    public String mobileRegSuccess() {
        return "/user/reg_success";
    }

    /**
     * 显示我的二维码页面
     *
     * @return
     */
    @RequestMapping(value = "/myqrcode", method = RequestMethod.GET)
    public String showMyQRCode() {
        return "/weixin/user/my_qr_code";
    }

    /**
     * 生成二维码
     *
     * @return
     */
    @RequestMapping(value = "/generateQRCode", method = RequestMethod.GET)
    public void generateQRCode(HttpServletRequest request, HttpServletResponse response) {
        // 创建二维码文本信息
        WebUser webUser = getCurrentLoginUser(request);
        if (webUser == null) {
            return;
        }
        User user = userService.getById(webUser.getUserId());
        Long userId = user.getId();
        String username = user.getName();

        String qrCodeStr = userId + "," + username;
        qrCodeStr = EncryptionUtil.encryptAES(qrCodeStr);
        qrCodeStr = "*" + qrCodeStr + "*";

        OutputStream out;
        try {
            out = response.getOutputStream();
            QRCodeUtil.generateQRCode(qrCodeStr, 133, 133, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 找回密码
     *
     * @return
     */
    @RequestMapping(value = "/retrievepwd/index", method = RequestMethod.GET)
    public String preRetrievePwd() {
        return "/user/retrieve_pwd_index";
    }

    /**
     * 通过手机号找回密码
     *
     * @return
     */
    @RequestMapping(value = "/retrievepwd/mobile", method = {RequestMethod.GET, RequestMethod.POST})
    public String retrievePwdByMobilephone(String mobilephone, String captcha, HttpSession session, Model model) {
        if (!StringUtils.hasText(mobilephone) || !RegexUtil.isValidPhoneNumberCN(mobilephone) || !StringUtils.hasText(captcha)) {
            return "redirect:/retrievepwd/index";
        }

        if (!captcha.equalsIgnoreCase(session.getAttribute(Constants.KAPTCHA_SESSION_KEY).toString())) {
            return "redirect:/retrievepwd/index";
        }

        String smsCode = sendSMSCode(mobilephone, SMSUtil.RETRIEVE_PWD_ACTION, WebConstants.SMS_CODE_SENDER_SITE);
        session.setAttribute(SMSUtil.CODE_SESSION_KEY, mobilephone + ":" + smsCode);

        model.addAttribute("mobilephone", mobilephone);
        return "/user/retrieve_pwd_by_mobilephone";
    }

    /**
     * 检查登陆
     *
     * @return
     */
    @RequestMapping(value = "/checkLogin", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> checkLogin(HttpServletRequest request) {
        Map<String, Object> jsonMap = new HashMap<>();

        // 从Cookie中获取用户登陆信息
        WebUser webUser = getCurrentLoginUser(request);

        if (webUser == null) {
            jsonMap.put("isLogin", false);
            return jsonMap;
        } else {
            jsonMap.put("isLogin", true);
            User user = userService.getById(webUser.getUserId());
            jsonMap.put("username", user.getName());
            return jsonMap;
        }
    }

}
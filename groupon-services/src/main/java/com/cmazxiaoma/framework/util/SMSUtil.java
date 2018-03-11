package com.cmazxiaoma.framework.util;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 短信服务工具类
 */
public class SMSUtil {

	/**
	 * 短信验证码默认长度为6位
	 */
	public static final int CODE_LENGTH = 6;

	/**
	 * 短信验证码Session Key
	 */
	public static final String CODE_SESSION_KEY = "SMSCode";
	
	/**
	 * “注册”短信模板
	 */
	public static final String REGISTER_ACTION = "register";
	public static final String REGISTER_TEMPLATE = "您注册慕课团号的验证码为：{0}。【慕课团】";

	/**
	 * “找回密码”短信模板
	 */
	public static final String RETRIEVE_PWD_ACTION = "retrievePwd";
	public static final String RETRIEVE_PWD_TEMPLATE = "您正在使用找回密码功能，您本次的验证码为：{0}，请妥善保管您的密码哦！【慕课团】";
	
	/**
	 * 发送短信的目的，如：register、retrievePwd等
	 */
	public static final Map<String, String> ACTION_MAP = new HashMap<>();
	static {
		ACTION_MAP.put(REGISTER_ACTION, REGISTER_TEMPLATE);
		ACTION_MAP.put(RETRIEVE_PWD_ACTION, RETRIEVE_PWD_TEMPLATE);
	}
	
	/**
	 * 发送短信服务的URL
	 */
	public static final String SMS_URL = "http://sms.189dg.com/synch_send.ashx";
	/**
	 * 发送短信服务的用户名（网站）
	 */
	public static final String SMS_USER_NAME = "wx_platform";
	/**
	 * 发送短信服务的密码（网站）
	 */
	public static final String SMS_PASSWORD = "wx_2013";
	/**
	 * 发送短信服务的用户名（App）
	 */
	public static final String SMS_USER_NAME_FOR_APP = "app_platform";
	/**
	 * 发送短信服务的密码（App）
	 */
	public static final String SMS_PASSWORD_FOR_APP = "1Qaz2Wsx";
	
	/**
	 * 成功状态
	 */
	public static final String SUCCESS = "success";
	/**
	 * 失败状态
	 */
	public static final String FAIL = "fail";
	

	/**
	 * 生成短信验证码
	 * 
	 * @return
	 */
	public static String generate() {
		StringBuilder code = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < CODE_LENGTH; i++) {
			int randomNum = random.nextInt(10);
			code.append(String.valueOf(randomNum));
		}
		return code.toString();
	}
	
	/**
	 * 网站发短信
	 * @param phoneNums 手机号码，多个用英文逗号隔开
	 * @param text 短信文本
	 * @return
	 */
	public static boolean sendSMSText(String phoneNums, String text) {
		return send(SMS_USER_NAME, SMS_PASSWORD, phoneNums, text);
	}
	
	/**
	 * App发短信
	 * @param phoneNums 手机号码，多个用英文逗号隔开
	 * @param text 短信文本
	 * @return
	 */
	public static boolean sendSMSTextForApp(String phoneNums, String text) {
		return send(SMS_USER_NAME_FOR_APP, SMS_PASSWORD_FOR_APP, phoneNums, text);
	}
	
	/**
	 * 通过Apache HttpComponents发送请求到短信服务器，发送短信
	 * @param smsUsername 短信服务用户名
	 * @param smsPassword 短信服务密码
	 * @param phoneNums 手机号码，多个用英文逗号隔开
	 * @param text 短信文本
	 * @return 是否成功
	 */
	@SuppressWarnings("unchecked")
	private static boolean send(String smsUsername, String smsPassword, String phoneNums, String text) {
		if (StringUtils.isEmpty(phoneNums) || StringUtils.isEmpty(text)) {
			return false;
		}
		
		boolean isSuccess = false;
		return isSuccess;
	}
}
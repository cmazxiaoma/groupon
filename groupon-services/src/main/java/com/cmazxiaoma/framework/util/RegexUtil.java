package com.cmazxiaoma.framework.util;

import org.springframework.util.StringUtils;


/**
 * 正则验证工具类
 */
public class RegexUtil {
	/**
	 * 验证是否为合法的手机号码(中国)
	 * @param phoneNumber 手机号码
	 * @return
	 */
	public static boolean isValidPhoneNumberCN(String phoneNumber) {
		if (!StringUtils.hasText(phoneNumber)) {
			return false;
		}
		return phoneNumber.matches("^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$");
	}
	
	/**
	 * 验证是否为合法的Email
	 * @param email
	 * @return
	 */
	public static boolean isValidEmail(String email) {
		if (!StringUtils.hasText(email)) {
			return false;
		}
		return email.matches("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
	}
	
	/**
	 * 验证是否为合法的用户名
	 * @param username
	 * @return
	 */
	public static boolean isValidUsername(String username) {
		if (!StringUtils.hasText(username)) {
			return false;
		}
		return username.matches("^([a-zA-Z]|[\\u4e00-\\u9fa5])+\\w*[\\u4e00-\\u9fa5]*$");
	}
	
	/**
	 * 验证是否为合法的URL
	 * @param url
	 * @return
	 */
	public static boolean isValidURL(String url) {
		if (!StringUtils.hasText(url)) {
			return false;
		}
		return url.matches("[a-zA-z]+://[^\\s]*");
	}
}
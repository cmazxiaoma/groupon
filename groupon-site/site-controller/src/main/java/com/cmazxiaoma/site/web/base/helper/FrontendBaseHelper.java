package com.cmazxiaoma.site.web.base.helper;

import com.cmazxiaoma.framework.util.StringUtil;
import com.cmazxiaoma.framework.util.image.ImageUtil;
import com.cmazxiaoma.framework.web.helper.BaseHelper;
import com.cmazxiaoma.site.web.constants.WebConstants;
import org.springframework.util.StringUtils;

/**
 *
 */
public class FrontendBaseHelper extends BaseHelper {

    public String getDefaultBlankImageUrl() {
        return WebConstants.SITE_IMAGE_DOMAIN_NAME + "/images/blank.png";
    }

    protected String getObjectImageUrl(String objectClass, Long imageId, int imageIndex) {
        if (StringUtil.isEmpty(objectClass) || (imageId == null) || (imageId == 0)) {
            return getDefaultBlankImageUrl();
        }
        String imageUrl = ImageUtil.getImageUrl(
                WebConstants.SITE_IMAGE_DOMAIN_NAME + "/images/", imageId, objectClass, imageIndex);
        if (imageUrl == null) {
            return getDefaultBlankImageUrl();
        }
        return imageUrl;
    }

    /**
     * 获取静态图片URL
     */
    public String getStaticImageUrl(String uri) {
        if (StringUtils.isEmpty(uri)) {
            return getDefaultBlankImageUrl();
        } else {
            return WebConstants.SITE_IMAGE_DOMAIN_NAME + uri;
        }
    }

    /**
     * 根据邮件地址解析邮件服务器URL
     *
     * @param email
     * @return
     */
    public String getEmailServerUrl(String email) {
        if (email.contains("@163.com")) {
            return "http://mail.163.com";
        } else if (email.contains("@126.com")) {
            return "http://mail.126.com";
        } else if (email.contains("@qq.com")) {
            return "http://mail.qq.com";
        } else if (email.contains("@sina.com")) {
            return "http://mail.sina.com";
        } else if (email.contains("@sohu.com")) {
            return "http://mail.sohu.com";
        } else if (email.contains("@gmail.com")) {
            return "http://mail.google.com";
        } else {
            return WebConstants.SITE_DOMAIN_NAME;
        }
    }
}
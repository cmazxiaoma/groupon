package com.cmazxiaoma.site.web.site.helper;


import com.cmazxiaoma.groupon.deal.constant.DealConstant;
import com.cmazxiaoma.groupon.deal.entity.Deal;
import com.cmazxiaoma.site.web.base.helper.FrontendBaseHelper;
import com.cmazxiaoma.site.web.constants.WebConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 网站Helper基类
 */
@Component
public class BaseSiteHelper extends FrontendBaseHelper {

    /**
     * 获取商品列表页面，商品图片
     */
    public String getDealImageUrlForDealList(Deal deal) {
        return getObjectImageUrl(DealConstant.PICTURE_MODULE_DEAL, deal.getImageId(),
                DealConstant.PICTURE_SIZE_BY_TYPE_LIST);
    }

    public String getDealImageUrlForIndexDeal1List(Deal deal) {
        return getObjectImageUrl(DealConstant.PICTURE_MODULE_DEAL, deal.getImageId(),
                DealConstant.PICTURE_SIZE_BY_TYPE_INDEX_B);
    }

    public String getDealImageUrlForIndexDeal2List(Deal deal) {
        return getObjectImageUrl(DealConstant.PICTURE_MODULE_DEAL, deal.getImageId(),
                DealConstant.PICTURE_SIZE_BY_TYPE_INDEX_S);
    }

    public String getDealImageUrlForGrid(Long dealImgId) {
        return getObjectImageUrl(DealConstant.PICTURE_MODULE_DEAL, dealImgId,
                DealConstant.PICTURE_SIZE_BY_TYPE_INDEX_B);
    }

    /**
     * 获取CSS文件URL
     */
    public String getCSSFileUrl(String uri) {
        if (StringUtils.isEmpty(uri)) {
            return "";
        } else {
            return WebConstants.SITE_CSS_DOMAIN_NAME + uri;
        }
    }

    /**
     * 获取JS文件URL
     */
    public String getJSFileUrl(String uri) {
        if (StringUtils.isEmpty(uri)) {
            return "";
        } else {
            return WebConstants.SITE_JS_DOMAIN_NAME + uri;
        }
    }

    public String getDealImageUrlForDealView(Deal deal) {
        return getObjectImageUrl(DealConstant.PICTURE_MODULE_DEAL, deal.getImageId(),
                DealConstant.PICTURE_SIZE_BY_TYPE_DETAIL);
    }



}
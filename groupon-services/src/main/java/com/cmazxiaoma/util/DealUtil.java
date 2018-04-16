package com.cmazxiaoma.util;


import com.cmazxiaoma.common.constant.GlobalConstant;
import com.cmazxiaoma.framework.util.StringUtil;
import com.cmazxiaoma.groupon.deal.constant.DealConstant;
import com.cmazxiaoma.groupon.deal.entity.Deal;
import com.cmazxiaoma.groupon.deal.entity.DealCategory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DealUtil
 */
public class DealUtil {
    /**
     * 商品是否已经售罄
     */
    public static boolean isOOS(Deal deal) {
        return deal.getOosStatus() == GlobalConstant.YES;
    }

    /**
     * 是否团购
     */
    public static boolean isGroupon(Deal deal) {
        if (deal == null) {
            return false;
        }

        Integer dealType = deal.getDealType();

        if (dealType != null && dealType == DealConstant.DEAL_TYPE_GROUPON) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否其它
     */
    public static boolean isOther(Deal deal) {
        if (deal == null) {
            return false;
        }

        Integer dealType = deal.getDealType();
        if (dealType != null && dealType == DealConstant.DEAL_TYPE_OTHER) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否合法skuId
     */
    public static boolean isValidSkuId(Long skuId) {
        if (null == skuId) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[1-9]\\d*$");
        Matcher matcher = pattern.matcher(String.valueOf(skuId));
        return matcher.matches();
    }

    public static void main(String[] args) {
        System.out.println(isValidSkuId(0312313L));
    }

    /**
     * 是否合法id
     */
    public static boolean isValidId(String id) {
        if (StringUtil.isEmpty(id)) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[1-9]\\d*$");
        Matcher matcher = pattern.matcher(id);
        return matcher.matches();
    }

    /**
     * 是否虚拟商品
     */
    public static boolean isVirtualDeal(Deal deal) {
        if (deal.getDealClass() == DealConstant.DEAL_CLASS_VIRTUAL) { // 虚拟商品
            return true;
        }

        return false;
    }

    /**
     * 商品类别是否叶节点
     */
    public boolean isLeafDealCategory(DealCategory dealCategory) {
        return dealCategory.getDeep() == GlobalConstant.DEAL_CATEGORY_MAX_DEEP;
    }
}
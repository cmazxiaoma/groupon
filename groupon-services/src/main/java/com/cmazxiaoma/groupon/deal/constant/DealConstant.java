package com.cmazxiaoma.groupon.deal.constant;

/**
 * DealConstant
 */
public final class DealConstant {

    private DealConstant() {}

    public static final int DEAL_CLASS_COMMON = 0; // 普通商品

    public static final int DEAL_CLASS_VIRTUAL = 1; // 虚拟商品

    public static final int DEAL_TYPE_GROUPON = 0; // 团购商品类型

    public static final int DEAL_TYPE_OTHER = 1; // 其它商品类型

    public static final int DEAL_PUBLISH_STATUS_NEW = 0; // 商品新建

    public static final int DEAL_PUBLISH_STATUS_AUDITED = 1; // 商品已审核

    public static final int DEAL_PUBLISH_STATUS_PUBLISH_BUT_NOT_DISPLAY = 2; // 商品已发布但不显示

    public static final int DEAL_PUBLISH_STATUS_PUBLISH = 3; // 商品已发布

    public static final int DEAL_PUBLISH_STATUS_PUBLISH_BUT_ONLY_SEARCH = 4; // 商品已发布但仅搜索

    public static final int DEAL_PUBLISH_STATUS_DELETED = 5; // 商品已删除

    public static final int DEAL_PUBLISH_STATUS_PROCESSING = 6; // 处理中

//    public static final int DEAL_PUBLISH_TO_WECHAT = 6; // 发布到微信
//
//    public static final int DEAL_PUBLISH_TO_APP = 7; // 发布到app
//
//    public static final int DEAL_PUBLISH_TO_SITE = 8;  // 发布到主站

    public static final int DEAL_OOS_STATUS_YES = 1; // 已卖光

    public static final int DEAL_OOS_STATUS_NO = 0;  // 未卖光

    public static final int DEAL_DEFAULT_MAX_PURCHASE_COUNT = 2; // 默认最大购买数量

    /**
     * 图片尺寸--网站首页(大)
     */
    public static final int PICTURE_SIZE_BY_TYPE_INDEX_B = 1;

    /**
     * 图片尺寸--网站首页(小)
     */
    public static final int PICTURE_SIZE_BY_TYPE_INDEX_S = 2;

    /**
     * 图片尺寸--网站详情页
     */
    public static final int PICTURE_SIZE_BY_TYPE_DETAIL = 3;

    /**
     * 图片尺寸--分类列表
     */
    public static final int PICTURE_SIZE_BY_TYPE_LIST = 4;

    /**
     * 图片模块(类型)Deal
     */
    public static final String PICTURE_MODULE_DEAL = "deal";

    /**
     * 图片模块(类型)DealDetail
     */
    public static final String PICTURE_MODULE_DEAL_DETAIL = "dealDetail";

    /**
     * 图片生成--控制压缩比长宽高
     */
    public static final int PICTURE_CONTROL_FLAG_YES = 1;

    /**
     * 图片生成--不控制压缩比长宽高
     */
    public static final int PICTURE_CONTROL_FLAG_NO = 0;

    // ------每页商品数据------
    /**
     * 商品类别每页商品数量
     */
    public static final int DEAL_NUM_PER_PAGE_IN_DEALS_OF_CATEGORY_PAGE = 12;
}

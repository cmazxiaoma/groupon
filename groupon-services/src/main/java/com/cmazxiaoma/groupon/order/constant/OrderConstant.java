package com.cmazxiaoma.groupon.order.constant;

/**
 * 订单常量
 */
public final class OrderConstant {

    private OrderConstant() {
    }

    /**********订单状态**********/

    //1:待付款;2:已付款;3:待发货;4:配送中;5:完成;6:已取消;7:已关闭
    public static final int STATUS_WAITING_PAY = 1;

    public static final int STATUS_ALREADY_PAID = 2;

    public static final int STATUS_WAITING_DELIVER = 3;

    public static final int STATUS_DELIVERING = 4;

    public static final int STATUS_FINISHED = 5;

    public static final int STATUS_CANCELED = 6;

    public static final int STATUS_CLOSED = 7;

    public static final int STATUS_DELETE = -1;


    //支付方式 1.微信;2.支付宝;3.货到付款
    public static final int PAY_TYPE_WECHAT = 1;
    public static final int PAY_TYPE_ALIPAY = 2;
    public static final int PAY_TYPE_COD = 3;

}

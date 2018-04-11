package com.cmazxiaoma.groupon.order.entity;

import com.cmazxiaoma.framework.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Order
 */
public class Order extends BaseEntity {

    @Getter
    @Setter
    private Integer merchantOrderPrice;//商家订单价格

    @Getter
    @Setter
    private Long userId;//用户ID

    //1:待付款;2:已付款;3:待发货;4:配送中;5:完成;6:已取消;
    @Getter
    @Setter
    private Integer orderStatus;//订单状态

    @Getter
    @Setter
    private Integer totalPrice;//订单总价

    @Getter
    @Setter
    private Integer totalSettlementPrice;//订单结算总价

    @Getter
    @Setter
    private List<OrderDetail> orderDetails;//订单详细信息

    @Getter
    @Setter
    private String address;//收货地址

    @Getter
    @Setter
    private String receiver;//收货人

    @Getter
    @Setter
    private String phone;//收货联系电话

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private Date updateTime;

    @Getter
    @Setter
    private Integer payType;//支付方式 1.微信;2.支付宝;3.货到付款

}

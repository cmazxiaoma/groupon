package com.cmazxiaoma.groupon.order.entity;

import com.cmazxiaoma.framework.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * OrderDetail
 */
public class OrderDetail extends BaseEntity {

    @Setter
    @Getter
    private Long orderId;//订单ID

    @Setter
    @Getter
    private Long userId;//用户ID

    @Setter
    @Getter
    private Integer merchantSku;//商品商家SKU

    @Setter
    @Getter
    private Long merchantId;

    @Setter
    @Getter
    private String merchantCode;//商家唯一商品品编码

    @Setter
    @Getter
    private Long dealId;

    @Setter
    @Getter
    private Long dealSkuId;

    @Setter
    @Getter
    private Long dealImgId;

    @Setter
    @Getter
    private String dealTitle;//商品名称

    @Setter
    @Getter
    private Integer dealCount;//Deal数量

    @Setter
    @Getter
    private Integer dealPrice;//Deal单价

    @Setter
    @Getter
    private Integer totalPrice;//Deal总价

    @Setter
    @Getter
    private Integer settlementPrice;//结算价

    @Setter
    @Getter
    private Integer totalSettlementPrice;//结算总价

    @Setter
    @Getter
    private Integer detailStatus;

    @Setter
    @Getter
    private Date createTime;

    @Setter
    @Getter
    private Date updateTime;

}

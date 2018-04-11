package com.cmazxiaoma.support.remind.entity;

import com.cmazxiaoma.framework.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 开团提醒
 */
public class StartRemind extends BaseEntity {

    /**
     * 用户id
     */
    @Getter
    @Setter
    private Long userId;

    /**
     * 订单id
     */
    @Getter
    @Setter
    private Long dealId;

    /**
     * 商品sku id
     */
    @Getter
    @Setter
    private Long dealSkuId;

    /**
     * 商品title
     */
    @Getter
    @Setter
    private String dealTitle;

    @Getter
    @Setter
    private Date startTime;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private Date updateTime;

}

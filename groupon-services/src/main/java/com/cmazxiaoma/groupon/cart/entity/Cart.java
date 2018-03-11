package com.cmazxiaoma.groupon.cart.entity;

import com.cmazxiaoma.framework.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: 购物车
 * @date 2018/3/11 10:24
 */
@Data
public class Cart extends BaseEntity {

    private Long userId;

    private Long dealId;

    private Long dealSkuId;

    private Integer count;

    private Date createTime;

    private Date updateTime;
}

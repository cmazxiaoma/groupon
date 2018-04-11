package com.cmazxiaoma.groupon.merchant.entity;

import com.cmazxiaoma.framework.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 商家
 */
public class Merchant extends BaseEntity {

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String description;

    @Setter
    @Getter
    private Long imageId;

    @Setter
    @Getter
    private Integer level;

    @Setter
    @Getter
    private Integer hotLevel;

    @Setter
    @Getter
    private Integer status;

    @Setter
    @Getter
    private Date createTime;

    @Setter
    @Getter
    private Date updateTime;

    @Setter
    @Getter
    private String url;

}

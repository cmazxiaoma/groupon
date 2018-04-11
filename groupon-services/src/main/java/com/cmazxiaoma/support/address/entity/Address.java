package com.cmazxiaoma.support.address.entity;


import com.cmazxiaoma.framework.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 地址
 */
public class Address extends BaseEntity {

    /**
     * 地址归属用户ID
     */
    @Getter
    @Setter
    private Long userId;

    /**
     * 收货人
     */
    @Getter
    @Setter
    private String receiver;

    /**
     * 地区
     */
    @Getter
    @Setter
    private String area;

    /**
     * 详细地址
     */
    @Getter
    @Setter
    private String detail;

    /**
     * 类型:家、公司
     */
    @Getter
    @Setter
    private AddressType type;

    /**
     * 联系电话
     */
    @Getter
    @Setter
    private String phone;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private Date updateTime;

}

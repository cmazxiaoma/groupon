package com.cmazxiaoma.support.message.entity;

import com.cmazxiaoma.framework.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 地区
 */
public class Message extends BaseEntity {

    @Getter
    @Setter
    private Long userId;

    /**
     * 标题
     */
    @Getter
    @Setter
    private String title;

    /**
     * 内容
     */
    @Getter
    @Setter
    private String content;

    /**
     * 商品sku
     */
    @Getter
    @Setter
    private Long dealSkuId;

    /**
     * 0未读，1已读
     */
    @Getter
    @Setter
    private Integer readed;

    /**
     * 创建时间
     */
    @Getter
    @Setter
    private Date createTime;

    /**
     * 更新时间
     */
    @Getter
    @Setter
    private Date updateTime;

}

package com.cmazxiaoma.support.area.entity;

import com.cmazxiaoma.framework.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 地区
 */
public class Area extends BaseEntity {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Long parentId;

    /**
     * 常用
     */
    @Getter
    @Setter
    private Integer common;

    /**
     * 类型:省,市
     */
    @Getter
    @Setter
    private AreaType type;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private Date updateTime;

    @Getter
    @Setter
    private List<Area> children;

}

package com.cmazxiaoma.admin.security.entity;

import com.cmazxiaoma.framework.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * AdminFunction
 */
public class AdminFunction extends BaseEntity {

    private static final long serialVersionUID = 712848967452656311L;

    /**
     * 名称
     */
    @Getter
    @Setter
    private String name;

    /**
     * 状态，open/closed
     */
    @Getter
    @Setter
    private String state;

    /**
     * 父节点ID
     */
    @Getter
    @Setter
    private Long parentId;

    /**
     * 链接
     */
    @Getter
    @Setter
    private String url;

    /**
     * 创建时间
     */
    @Getter
    @Setter
    private Date createTime;

    /**
     * 修改时间
     */
    @Getter
    @Setter
    private Date updateTime;

}

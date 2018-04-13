package com.cmazxiaoma.admin.security.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cmazxiaoma.framework.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 后台用户
 */
public class AdminUser extends BaseEntity {

    //用户名称
    @Getter
    @Setter
    private String name;

    //用户密码
    @Getter
    @Setter
    private String password;

    //创建时间
    @Getter
    @Setter
    private Date createTime;

    //更新时间
    @Getter
    @Setter
    private Date updateTime;

    /**
     * 登录时间
     */
    @Getter
    @Setter
    private Date lastLoginTime;

    //拥有能访问的资源
    @Getter
    @Setter
    private List<AdminUserRole> adminUserRoles = new ArrayList<>();

}

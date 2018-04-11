package com.cmazxiaoma.admin.security.entity;

import com.cmazxiaoma.framework.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * AdminRoleFunction
 */
@NoArgsConstructor
@AllArgsConstructor
public class AdminRoleFunction extends BaseEntity {

    @Getter
    @Setter
    private Long adminRoleId; //角色ID
    @Getter
    @Setter
    private Long adminFunctionId; // 功能ID
    @Getter
    @Setter
    private Date createTime; //创建时间
    @Getter
    @Setter
    private Date updateTime; //修改时间

}

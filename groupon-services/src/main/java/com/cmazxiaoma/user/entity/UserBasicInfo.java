package com.cmazxiaoma.user.entity;

import com.cmazxiaoma.framework.base.entity.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 用户基本信息
 */
@Data
public class UserBasicInfo extends BaseEntity {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 电话
     */
    private String phone;

    private Date createTime;

    private Date updateTime;

    // private static final String SQL_MAPPER_NS = "com.tortuousroad.user.entity.UserBasicInfoMapper";
    // public static final String SAVE_SQL_ID = SQL_MAPPER_NS + ".insert";

    public static final String SAVE_SQL_ID = "insertSelective";

}

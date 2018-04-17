package com.cmazxiaoma.user.entity;

import com.cmazxiaoma.framework.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class User extends BaseEntity {

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String repwd;

    @Getter
    @Setter
    private String checked;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Date loginTime;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private Date updateTime;

    // private static final String SQL_MAPPER_NS = "com.cmazxiaoma.user.entity.UserMapper";
    // public static final String SAVE_SQL_ID = SQL_MAPPER_NS + ".insert";

//    public static final String SAVE_SQL_ID = "insert";

}
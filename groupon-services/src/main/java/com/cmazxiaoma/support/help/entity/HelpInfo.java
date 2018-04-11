package com.cmazxiaoma.support.help.entity;

import java.util.Date;

import com.cmazxiaoma.framework.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

public class HelpInfo extends BaseEntity {

    private static final long serialVersionUID = -4166365853497051893L;

    @Getter
    @Setter
    private Integer showIndex;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private Boolean isDel;

}
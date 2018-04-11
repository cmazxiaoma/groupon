package com.cmazxiaoma.site.web.base.objects;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class WebUser implements Serializable {

    @Getter
    @Setter
    private Long userId; // 用户ID

    @Getter
    @Setter
    private String username; // 用户名

    @Getter
    @Setter
    private int loginStatus; // 登陆状态

}
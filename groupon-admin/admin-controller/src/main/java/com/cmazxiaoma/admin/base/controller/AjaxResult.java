package com.cmazxiaoma.admin.base.controller;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Ajax操作返回的结果
 */
public class AjaxResult implements Serializable {

    public static final Integer AJAX_STATUS_CODE_SUCCESS = 0;
    public static final Integer AJAX_STATUS_CODE_WARN = 1;
    public static final Integer AJAX_STATUS_CODE_ERROR = 2;

    private static final long serialVersionUID = -125356135878750343L;

    @Getter
    @Setter
    private Integer statusCode;

    @Getter
    @Setter
    private String message;

    public AjaxResult() {
        super();
    }

    public AjaxResult(Integer statusCode, String message) {
        super();
        this.statusCode = statusCode;
        this.message = message;
    }

}
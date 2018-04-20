package com.cmazxiaoma.site.web.payment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 支付接口
 */
public interface Payment<T> {

    int getType();

    void pay(T totalFee, Long innerOrderId, HttpServletRequest request,
             HttpServletResponse response) throws Exception;

}

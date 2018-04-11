package com.cmazxiaoma.site.web.payment;

import javax.servlet.http.HttpServletResponse;

/**
 * 支付接口
 */
public interface Payment<T> {

    int getType();

    void pay(T totalFee, Long innerOrderId, HttpServletResponse response) throws Exception;

}

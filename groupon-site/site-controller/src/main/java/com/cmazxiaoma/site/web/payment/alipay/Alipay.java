package com.cmazxiaoma.site.web.payment.alipay;

import com.cmazxiaoma.groupon.order.constant.OrderConstant;
import com.cmazxiaoma.site.web.alipay.config.AlipayConfig;
import com.cmazxiaoma.site.web.alipay.util.AlipaySubmit;
import com.cmazxiaoma.site.web.payment.Payment;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝支付
 */
@Component
public class Alipay<Integer> implements Payment<Integer> {

    @Override
    public int getType() {
        return OrderConstant.PAY_TYPE_ALIPAY;
    }

    @Override
    public void pay(Integer totalFee, Long innerOrderId,
                    HttpServletRequest request,
                    HttpServletResponse response) throws Exception {
        String total_fee = new BigDecimal(totalFee.toString()).divide(new BigDecimal(100)).toString();
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String out_trade_no = "Road_" + innerOrderId + "_" + timestamp;
        String subject = "Road_付款测试";
        String body = "Road_test_order";

        //把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<>();
        sParaTemp.put("service", AlipayConfig.service);
        sParaTemp.put("partner", AlipayConfig.partner);
//        sParaTemp.put("seller_id", AlipayConfig.seller_id);
        sParaTemp.put("seller_email", AlipayConfig.seller_email);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("payment_type", AlipayConfig.payment_type);
        sParaTemp.put("notify_url", AlipayConfig.notify_url);
        sParaTemp.put("return_url", AlipayConfig.return_url);
//		sParaTemp.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
//		sParaTemp.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", subject);
        sParaTemp.put("total_fee", total_fee);
        sParaTemp.put("body", body);
        //其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.O9yorI&treeId=62&articleId=103740&docType=1
        //如sParaTemp.put("参数名","参数值");

        /**建立请求*/
        String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
//        response.getOutputStream().write(sHtmlText.getBytes());

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(sHtmlText);
    }
}

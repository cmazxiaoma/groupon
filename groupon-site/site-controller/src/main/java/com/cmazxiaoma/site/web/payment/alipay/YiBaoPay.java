package com.cmazxiaoma.site.web.payment.alipay;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/4/20 9:46
 */

import com.cmazxiaoma.groupon.order.constant.OrderConstant;
import com.cmazxiaoma.groupon.order.entity.Order;
import com.cmazxiaoma.groupon.order.service.OrderService;
import com.cmazxiaoma.site.web.payment.Payment;
import com.cmazxiaoma.site.web.payment.PaymentUtil;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 易宝支付
 */
@Component
public class YiBaoPay<Integer> implements Payment<Integer> {

    @Autowired
    private OrderService orderService;

    private static CloseableHttpClient client = HttpClients.createDefault();

    @Override
    public int getType() {
        return OrderConstant.PAY_TYPE_YIBAO;
    }

    @Override
    public void pay(Integer totalFee, Long innerOrderId,
                    HttpServletRequest request,
                    HttpServletResponse response) throws Exception {
        //1接收订单id
        String oid = String.valueOf(innerOrderId);
        //2、准备第三方支付平台需要的参数
        String p0_Cmd = "Buy";//业务类型
        ResourceBundle bundle = ResourceBundle.getBundle("pay", Locale.SIMPLIFIED_CHINESE);
        String p1_MerId = bundle.getString("p1_MerId");
        String p2_Order = oid;//商户的订单号
        //测试时使用
        String p3_Amt = "0.02";//订单金额
        //正式发布的时候使用
//        Order order = orderService.getOrderAndDetailByOrderId(innerOrderId);
//        p3_Amt = String.valueOf(order.getTotalPrice() / 100) + ".00";
        String p4_Cur = "CNY";//交易币种
        String p5_Pid = "";//商品名称
        String p6_Pcat = "";//商品分类
        String p7_Pdesc = "";//商品描述
        String p8_Url = bundle.getString("responseURL");
        String p9_SAF = "";//送货地址
        String pa_MP = "";//商户的扩展信息
        //支付通道编码,
        String pd_FrpId = "CCB-NET-B2C";
        //应答机制
        String pr_NeedResponse = "1";
        //调用工具类生产数据签名
        String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid,
                p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId,
                pr_NeedResponse, bundle.getString("keyValue"));

        Map<String, String> params = new HashMap<>();
        params.put("pd_FrpId", pd_FrpId);
        params.put("p0_Cmd", p0_Cmd);
        params.put("p1_MerId", p1_MerId);
        params.put("p2_Order", p2_Order);
        params.put("p3_Amt", p3_Amt);
        params.put("p4_Cur", p4_Cur);
        params.put("p5_Pid", p5_Pid);
        params.put("p6_Pcat", p6_Pcat);
        params.put("p7_Pdesc", p7_Pdesc);
        params.put("p8_Url", p8_Url);
        params.put("p9_SAF", p9_SAF);
        params.put("pa_MP", pa_MP);
        params.put("pr_NeedResponse", pr_NeedResponse);
        params.put("hmac", hmac);

        String apiUrl = "https://www.yeepay.com/app-merchant-proxy/node";

        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0) {
                param.append("?");
            }
            else {
                param.append("&");
            }
            param.append(key).append("=").append(params.get(key));
            i++;
        }
        apiUrl += param;
        response.sendRedirect(apiUrl);
    }
}

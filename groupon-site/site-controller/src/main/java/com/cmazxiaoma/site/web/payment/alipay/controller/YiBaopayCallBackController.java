package com.cmazxiaoma.site.web.payment.alipay.controller;

import com.cmazxiaoma.groupon.cart.service.CartService;
import com.cmazxiaoma.groupon.deal.service.DealService;
import com.cmazxiaoma.groupon.order.service.OrderService;
import com.cmazxiaoma.site.web.payment.PaymentUtil;
import com.cmazxiaoma.site.web.site.controller.BaseSiteController;
import com.cmazxiaoma.support.address.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/4/20 9:44
 */
@Slf4j
@Controller
public class YiBaopayCallBackController extends BaseSiteController {

    @Autowired
    private CartService cartService;

    @Autowired
    private DealService dealService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/yibaopay/return")
    public String yibaoPayReturn() throws Exception {
        //1.接受参数
        String p1_MerId = httpRequest.getParameter("p1_MerId");
        String r0_Cmd = httpRequest.getParameter("r0_Cmd");
        String r1_Code = httpRequest.getParameter("r1_Code");
        String r2_TrxId = httpRequest.getParameter("r2_TrxId");
        String r3_Amt = httpRequest.getParameter("r3_Amt");
        String r4_Cur = httpRequest.getParameter("r4_Cur");
        String r5_Pid = httpRequest.getParameter("r5_Pid");
        //商品订单号
        String r6_Order = httpRequest.getParameter("r6_Order");
        String r7_Uid = httpRequest.getParameter("r7_Uid");
        String r8_MP = httpRequest.getParameter("r8_MP");
        //交易结果的返回类型:1表示重定向  2点对点通讯
        String r9_BType = httpRequest.getParameter("r9_BType");
        String rb_BankId = httpRequest.getParameter("rb_BankId");
        //银行交易的流水号
        String ro_BankOrderId = httpRequest.getParameter("ro_BankOrderId");
        //在第三方支付平台上完成支付的时间
        String rp_PayDate = httpRequest.getParameter("rp_PayDate");
        String rq_CardNo = httpRequest.getParameter("rq_CardNo");
        String ru_Trxtime = httpRequest.getParameter("ru_Trxtime");
        //签名数据
        String hmac = httpRequest.getParameter("hmac");
        ResourceBundle bundle = ResourceBundle.getBundle("pay", Locale.SIMPLIFIED_CHINESE);
        boolean result = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
                r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order,
                r7_Uid, r8_MP, r9_BType, bundle.getString("keyValue"));
        //数据未被修改
        if (result) {
            //表示重定向过来的
            if ("1".equals(r9_BType)) {
                log.info("支付操作已执行，支付结果需要等待进一步的通知");
                //测试使用，正式发布前要删除以下两行代码
                orderService.payed(Long.parseLong(r6_Order));
                log.info("重定向-支付成功");
                modelMap.put("result", "1");
//              httpResponse.getWriter().write("支付操作已执行，支付结果需要等待进一步的通知");
            } else if ("2".equals(r9_BType)) {//点对点通知
                if ("1".equals(r1_Code)) {//点对点通知支付完成
                    //更新订单状态
                    orderService.payed(Long.parseLong(r6_Order));
                    //响应给第三方支付平台 success
//                    httpResponse.getWriter().write("success");
                    log.info("点对点通知-支付成功");
                    modelMap.put("result", "1");
                }
            }

        } else {
            log.error("数据被篡改了");
            modelMap.put("result", "0");
        }

        return "/cart/settlement_ok";
    }
}

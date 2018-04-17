//package com.cmazxiaoma.site.web.alipay.controller;//package com.tortuousroad.site.web.alipay.controller;
////
////import com.tortuousroad.framework.common.Pair;
////import com.tortuousroad.groupon.cart.entity.Cart;
////import com.tortuousroad.groupon.cart.service.CartService;
////import com.tortuousroad.groupon.deal.entity.Deal;
////import com.tortuousroad.groupon.deal.service.DealService;
////import com.tortuousroad.groupon.order.service.OrderService;
////import com.tortuousroad.site.web.alipay.config.AlipayConfig;
////import com.tortuousroad.site.web.alipay.util.AlipayNotify;
////import com.tortuousroad.site.web.alipay.util.AlipaySubmit;
////import com.tortuousroad.site.web.base.objects.WebUser;
////import com.tortuousroad.site.web.site.controller.BaseSiteController;
////import com.tortuousroad.support.address.entity.Address;
////import com.tortuousroad.support.address.service.AddressService;
////import com.tortuousroad.user.service.UserService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Controller;
////import org.springframework.ui.Model;
////import org.springframework.web.bind.annotation.RequestMapping;
////import org.springframework.web.bind.annotation.RequestMethod;
////
////import javax.servlet.http.HttpServletRequest;
////import javax.servlet.http.HttpServletResponse;
////import java.io.UnsupportedEncodingException;
////import java.text.SimpleDateFormat;
////import java.util.ArrayList;
////import java.util.Arrays;
////import java.util.Date;
////import java.util.HashMap;
////import java.util.Iterator;
////import java.util.List;
////import java.util.Map;
////import java.util.stream.Collectors;
////
/////**
//// * 支付宝支付
//// */
////@Controller
////public class AlipayController extends BaseSiteController {
////
////    @Autowired private CartService cartService;
////
////    @Autowired private DealService dealService;
////
////    @Autowired private OrderService orderService;
////
////    @Autowired private AddressService addressService;
////
////    @RequestMapping(value="/alipay", method = RequestMethod.POST)
////    public void alipay(HttpServletRequest request, HttpServletResponse response, Long[] cartIds, Long addressId,
////                       Integer payType, Integer totalPrice) throws Exception {
////        WebUser webUser = getCurrentLoginUser(request);
////
////        //保存订单
////        List<Cart> carts = cartService.getCartsByIds(Arrays.asList(cartIds));
////        List<Long> skuIds = carts.stream().map(cart -> cart.getDealSkuId()).collect(Collectors.toList());
////        List<Deal> deals = dealService.getBySkuIds(skuIds);
////        Map<Long, Deal> skuIdMap = new HashMap<>();
////        deals.forEach(deal -> skuIdMap.put(deal.getSkuId(), deal));
////
////        List<Pair<Cart, Deal>> cartDTOs = new ArrayList<>();
////        carts.forEach(cart -> cartDTOs.add(new Pair<>(cart, skuIdMap.get(cart.getDealSkuId()))));
////
////        Address address = addressService.getById(addressId);
////        Long orderId = orderService.order(webUser.getUserId(), cartDTOs, address, totalPrice, payType);
////
////        String out_trade_no = "Road_" + orderId + "_" + webUser.getUsername() + "_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
////        String subject = "Road_" + webUser.getUsername();
////        String total_fee = "0.01";//totalPrice
////        String body = "Road_test_order";
////
//////        ////////////////////////////////////请求参数//////////////////////////////////////
//////        //商户订单号，商户网站订单系统中唯一订单号，必填
//////        String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
//////
//////        //订单名称，必填
//////        String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
//////
//////        //付款金额，必填
//////        String total_fee = new String(request.getParameter("WIDtotal_fee").getBytes("ISO-8859-1"),"UTF-8");
//////
//////        //商品描述，可空
//////        String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
//////        //////////////////////////////////////////////////////////////////////////////////
////
////        //把请求参数打包成数组
////        Map<String, String> sParaTemp = new HashMap<>();
////        sParaTemp.put("service", AlipayConfig.service);
////        sParaTemp.put("partner", AlipayConfig.partner);
//////        sParaTemp.put("seller_id", AlipayConfig.seller_id);
////        sParaTemp.put("seller_email", AlipayConfig.seller_email);
////        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
////        sParaTemp.put("payment_type", AlipayConfig.payment_type);
////        sParaTemp.put("notify_url", AlipayConfig.notify_url);
////        sParaTemp.put("return_url", AlipayConfig.return_url);
//////		sParaTemp.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
//////		sParaTemp.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
////        sParaTemp.put("out_trade_no", out_trade_no);
////        sParaTemp.put("subject", subject);
////        sParaTemp.put("total_fee", total_fee);
////        sParaTemp.put("body", body);
////        //其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.O9yorI&treeId=62&articleId=103740&docType=1
////        //如sParaTemp.put("参数名","参数值");
////
////        /**建立请求*/
////        String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
//////        response.getOutputStream().write(sHtmlText.getBytes());
////
////        response.setCharacterEncoding("UTF-8");
////        response.setContentType("text/html;charset=UTF-8");
////        response.getWriter().println(sHtmlText);
////    }
////
////    @RequestMapping(value="/alipay/return")
////    public String alipayReturn(HttpServletRequest request, Model model) throws Exception {
////        //获取支付宝GET过来反馈信息
////        Map<String,String> params = new HashMap<>();
////        Map requestParams = request.getParameterMap();
////        for (Iterator ite = requestParams.keySet().iterator(); ite.hasNext();) {
////            String name = (String) ite.next();
////            String[] values = (String[]) requestParams.get(name);
////            String valueStr = "";
////            for (int i = 0; i < values.length; i++) {
////                valueStr = (i == values.length - 1) ? (valueStr + values[i]) : (valueStr + values[i] + ",");
////            }
////            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
////            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
////            params.put(name, valueStr);
////        }
////
////        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
////        //商户订单号
////        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
////
////        //支付宝交易号
////        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
////
////        //交易状态
////        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
////        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
////
////        //计算得出通知验证结果
////        boolean verify_result = AlipayNotify.verify(params);
////
////        if (verify_result) {//验证成功
////            //////////////////////////////////////////////////////////////////////////////////////////
////            //请在这里加上商户的业务逻辑程序代码
////            String orderIdStr = out_trade_no.split("_")[1];
////            orderService.payed(Long.parseLong(orderIdStr));
////            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
////            if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
////                //判断该笔订单是否在商户网站中已经做过处理
////                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
////                //如果有做过处理，不执行商户的业务程序
////            }
////
//////            //该页面可做页面美工编辑
//////            out.println("验证成功<br />");
////            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
////            //////////////////////////////////////////////////////////////////////////////////////////
////            model.addAttribute("result", 1);
////        } else {
//////            //该页面可做页面美工编辑
//////            out.println("验证失败");
////            model.addAttribute("result", 0);
////        }
////        return "/cart/settlement_ok";
////    }
////
////
////
////
////
//////    List<Cart> carts = cartService.getCartsByIds(Arrays.asList(cartIds));
//////
//////    List<Long> skuIds = carts.stream()
//////            .map(cart -> cart.getDealSkuId()).collect(Collectors.toList());
//////    List<Deal> deals = dealService.getBySkuIds(skuIds);
//////
//////    Map<Long, Deal> dealMap = new HashMap<>();
//////    deals.forEach(deal -> dealMap.put(deal.getSkuId(), deal));
//////
//////    List<Pair<Cart, Deal>> cartDTOs = new ArrayList<>();
//////    carts.forEach(cart -> cartDTOs.add(
//////            new Pair<>(cart, dealMap.get(cart.getDealSkuId()))));
//////    Address address = addressService.getById(addressId);
//////    orderService.order(webUser.getUserId(), cartDTOs, address,
//////                              totalPrice, payType);
////
////
////}

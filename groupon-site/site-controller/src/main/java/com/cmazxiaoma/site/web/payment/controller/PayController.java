package com.cmazxiaoma.site.web.payment.controller;

import com.cmazxiaoma.framework.common.Pair;
import com.cmazxiaoma.groupon.cart.entity.Cart;
import com.cmazxiaoma.groupon.cart.service.CartService;
import com.cmazxiaoma.groupon.deal.entity.Deal;
import com.cmazxiaoma.groupon.deal.service.DealService;
import com.cmazxiaoma.groupon.order.constant.OrderConstant;
import com.cmazxiaoma.groupon.order.service.OrderService;
import com.cmazxiaoma.site.web.base.objects.WebUser;
import com.cmazxiaoma.site.web.payment.Payment;
import com.cmazxiaoma.site.web.payment.PaymentFactory;
import com.cmazxiaoma.site.web.site.controller.BaseSiteController;
import com.cmazxiaoma.support.address.entity.Address;
import com.cmazxiaoma.support.address.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 通用支付Controller
 */
@Controller
public class PayController extends BaseSiteController {

    @Autowired
    private CartService cartService;

    @Autowired
    private DealService dealService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddressService addressService;

    /**
     * 通用支付方案
     *
     * @param request    request
     * @param response   response
     * @param cartIds    购物车记录ID数组
     * @param addressId  收货地址ID
     * @param payType    支付类型
     * @param totalPrice 总价
     * @throws Exception 异常
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public void pay(HttpServletRequest request, HttpServletResponse response, Long[] cartIds, Long skuId, Long addressId,
                    Integer payType, Integer totalPrice) throws Exception {
        //保存自己的订单信息
        WebUser webUser = getCurrentLoginUser(request);

        List<Pair<Cart, Deal>> cartDTOs = new ArrayList<>();
        if (null == cartDTOs || cartIds.length == 0) {
            Deal deal = dealService.getBySkuId(skuId);
            Cart cart = new Cart();
            cart.setCount(1);//可以改成多个
            cart.setDealSkuId(skuId);
            cart.setDealId(deal.getId());
            cart.setUserId(webUser.getUserId());
            cartDTOs.add(new Pair<>(cart, deal));
        } else {
            List<Cart> carts = cartService.getCartsByIds(Arrays.asList(cartIds));
            List<Long> skuIds = carts.stream().map(cart -> cart.getDealSkuId()).collect(Collectors.toList());
            List<Deal> deals = dealService.getBySkuIds(skuIds);
            Map<Long, Deal> skuIdMap = new HashMap<>();
            deals.forEach(deal -> skuIdMap.put(deal.getSkuId(), deal));
            carts.forEach(cart -> cartDTOs.add(new Pair<>(cart, skuIdMap.get(cart.getDealSkuId()))));
        }

        Address address = addressService.getById(addressId);
        Long orderId = orderService.order(webUser.getUserId(), cartDTOs, address, totalPrice, payType);

        //通过工厂类及支付类型(payType)实例化具体支付方式
        if (payType == OrderConstant.PAY_TYPE_COD) {
            String basePath = request.getScheme() + "//:" + request.getServerName() + ":" + request.getServerPort();
            response.sendRedirect("/settlement/return");
            return;
        }
        Payment payment = PaymentFactory.createPayment(payType);
        //FIXME 对payment进行判断
        //FIXME 修改默认值为1分
        totalPrice = 1;
        payment.pay(totalPrice, orderId, response);
    }

    @RequestMapping(value = "/settlement/return")
    public String payReturn(HttpServletRequest request, Model model) throws Exception {
        //FIXME 构造适用于支付宝返回及货到付款返回的方法
        // 此时不再需要com.tortuousroad.site.web.payment.impl.alipay.controller.AlipayController
        //1.参数从request中获取
        //2.货到付款,增加特定参数,如果有则执行我们自己的return;若无则支付宝的renturn
        model.addAttribute("result", 1);
        return "/cart/settlement_ok";
    }


}

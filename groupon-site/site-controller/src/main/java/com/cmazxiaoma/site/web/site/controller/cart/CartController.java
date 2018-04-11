package com.cmazxiaoma.site.web.site.controller.cart;

import com.cmazxiaoma.framework.base.entity.BaseEntity;
import com.cmazxiaoma.groupon.cart.entity.Cart;
import com.cmazxiaoma.groupon.cart.service.CartService;
import com.cmazxiaoma.groupon.deal.entity.Deal;
import com.cmazxiaoma.groupon.deal.service.DealService;
import com.cmazxiaoma.groupon.order.service.OrderService;
import com.cmazxiaoma.site.web.base.objects.WebUser;
import com.cmazxiaoma.site.web.site.controller.BaseSiteController;
import com.cmazxiaoma.site.web.site.dto.CartDTO;
import com.cmazxiaoma.support.address.entity.Address;
import com.cmazxiaoma.support.address.service.AddressService;
import com.cmazxiaoma.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 购物车
 */
@Controller
public class CartController extends BaseSiteController {

    private Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private DealService dealService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/cart/default/{skuId}")
    @ResponseBody
    public String addToCart(@PathVariable Long skuId, Integer count, HttpServletRequest request) {
        try {
            cartService.addDeal(skuId, getCurrentLoginUser(request).getUserId(), count);
            return "1";
        } catch (Exception e) {
            //日志
            logger.error("Add deal to cart error, message : {}", e.getMessage());
            return "0";
        }
    }

    /**
     * 进入购物车
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String cart(Model model, HttpServletRequest request) {
        WebUser webUser = getCurrentLoginUser(request);//当前登录用户
        if (null != webUser) {
            Long userId = webUser.getUserId();
            List<Cart> carts = cartService.getCartByUserId(userId);

            if (!CollectionUtils.isEmpty(carts)) {//购物车非空
                List<Long> dealIds = carts.stream().map(cart -> cart.getDealId()).collect(Collectors.toList());
                List<Deal> deals = dealService.getDealsForCart(dealIds);
                List<CartDTO> cartDTOs = new ArrayList<>();//CartDTO用于页面显示
                Map<Long, Deal> dealMap = BaseEntity.idEntityMap(deals);

                for (Cart cart : carts) {
                    cartDTOs.add(new CartDTO(cart, dealMap.get(cart.getDealId())));
                }
                model.addAttribute("carts", cartDTOs);
            }
            return "/cart/cart";
        }
        return "redirect:/login";
    }

    /**
     * 购物车结算
     * @param cartIds
     * @param totalPrice
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/settlement", method = RequestMethod.POST)
    public String settlement(String cartIds, Integer totalPrice, Model model, HttpServletRequest request) {
        if (!StringUtils.isEmpty(cartIds) && null != totalPrice && totalPrice > 0) {
            //将cartIds转换成List<Long>
            List<Long> ids = Arrays.asList(cartIds.split(","))
                    .stream().map(id -> Long.valueOf(id)).collect(Collectors.toList());

            //通过ids找到carts
            List<Cart> carts = cartService.getCartsByIds(ids);

            //整理出购物车中所有商品的skuId
            List<Long> skuIds = carts.stream().map(cart -> cart.getDealSkuId())
                    .collect(Collectors.toList());

            //通过skuId找到商品
            List<Deal> deals = dealService.getBySkuIds(skuIds);

            //通过skuId整理出map
            Map<Long, Deal> skuIdMap = deals.stream().collect(Collectors.toMap(Deal::getSkuId,
                    deal -> deal));

            List<CartDTO> cartDTOs = new ArrayList<>();//CartDTO用于页面显示

            for (Cart cart : carts) {
                cartDTOs.add(new CartDTO(cart, skuIdMap.get(cart.getDealSkuId())));
            }
            model.addAttribute("carts", cartDTOs);

            WebUser webUser = getCurrentLoginUser(request);
            List<Address> addresses = addressService.getByUserId(webUser.getUserId());
            model.addAttribute("addresses", addresses);
            model.addAttribute("totalPrice", totalPrice);
        }
        return "/cart/settlement";
    }

    /**
     * 选择商品直接结算
     * @param skuId
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/settlement/{skuId}")
    public String settlement(@PathVariable Long skuId, HttpServletRequest request, Model model) {
        WebUser webUser = getCurrentLoginUser(request);

        Deal deal = dealService.getBySkuId(skuId);
        Cart cart = new Cart();
        cart.setCount(1);
        cart.setDealId(deal.getId());
        cart.setDealSkuId(skuId);
        cart.setUserId(webUser.getUserId());

        CartDTO cartDTO = new CartDTO(cart, deal);
        model.addAttribute("carts", cartDTO);

        List<Address> addresses = addressService.getByUserId(webUser.getUserId());
        model.addAttribute("addresses", addresses);
        model.addAttribute("totalPrice", deal.getDealPrice());
        return "/cart/settlement";
    }

    /**
     * 添加减少购物车数量
     * @param cartId
     * @param type
     * @return
     */
    @RequestMapping(value = "/cart/{cartId}/{type}", method = RequestMethod.POST)
    @ResponseBody
    public String count(@PathVariable Long cartId, @PathVariable Integer type) {
        try {
            if (null != type && 1 == type) {
                cartService.increaseCartDealCount(cartId);
            } else if (null != type && 0 == type) {
                cartService.decreaseCartDealCount(cartId);
            }
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

}

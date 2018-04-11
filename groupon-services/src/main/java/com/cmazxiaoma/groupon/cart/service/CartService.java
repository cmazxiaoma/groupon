package com.cmazxiaoma.groupon.cart.service;

import com.cmazxiaoma.groupon.cart.dao.CartDAO;
import com.cmazxiaoma.groupon.cart.entity.Cart;
import com.cmazxiaoma.groupon.deal.dao.DealDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 购物车
 */
@Service
public class CartService {

    @Autowired
    private CartDAO cartDAO;
    @Autowired
    private DealDAO dealDAO;

    /*********************************网站**********************************/

    /**
     * 将Deal加入购物车
     *
     * @param skuId  商品SkuId
     * @param userId 用户ID
     * @param count  数量
     */
    public void addDeal(Long skuId, Long userId, Integer count) {
        if (count == null || count < 0) {
            count = 1;
        }
        Cart cart = cartDAO.findByUserIdAndSkuId(userId, skuId);
        if (null != cart) {
            cartDAO.updateCartDealCount(cart.getId(), count);
        } else {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setDealSkuId(skuId);
            cart.setDealId(dealDAO.getIdBySkuId(skuId));
            cart.setCount(count);
            Date now = new Date();
            cart.setCreateTime(now);
            cart.setUpdateTime(now);
            cartDAO.save(cart);
        }
    }

    /**
     * 查询购物车商品
     *
     * @param userId
     * @return
     */
    public List<Cart> getCartByUserId(Long userId) {
        return cartDAO.findByUserId(userId);
    }

    /**
     * 查询购物车商品数量
     *
     * @param userId
     * @return
     */
    public Long getCartSize(Long userId) {
        return cartDAO.getCartSize(userId);
    }

    /**
     * 购物车商品数量加1
     *
     * @param cartId
     */
    public void increaseCartDealCount(Long cartId) {
        cartDAO.updateCartDealCount(cartId, 1);
    }

    /**
     * 购物车商品数量减1
     *
     * @param cartId
     */
    public void decreaseCartDealCount(Long cartId) {
        cartDAO.updateCartDealCount(cartId, -1);
    }

    public List<Cart> getCartsByIds(List<Long> ids) {
        if (null == ids || ids.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return cartDAO.findByIds(ids);
    }

    /*********************************后台**********************************/


    /*********************************混用**********************************/

}

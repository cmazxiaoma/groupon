package com.cmazxiaoma.site.web.site.dto;

import com.cmazxiaoma.groupon.cart.entity.Cart;
import com.cmazxiaoma.groupon.deal.entity.Deal;
import lombok.Getter;
import lombok.Setter;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: 购物车显示
 * @date 2018/4/4 9:44
 */
public class CartDTO {

    @Getter
    @Setter
    private Cart cart;

    @Getter
    @Setter
    private Deal deal;

    @Getter
    @Setter
    private Integer subtotal;//小计

    public CartDTO(Cart cart, Deal deal) {
        this.cart = cart;
        this.deal = deal;
        this.subtotal = deal.getDealPrice() * cart.getCount();
    }
}

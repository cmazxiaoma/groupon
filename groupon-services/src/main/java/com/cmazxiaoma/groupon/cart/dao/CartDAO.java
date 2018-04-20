package com.cmazxiaoma.groupon.cart.dao;

import com.cmazxiaoma.framework.common.persistence.BaseMybatisDAO;
import com.cmazxiaoma.groupon.cart.entity.Cart;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/3/11 10:26
 */
@Repository
public class CartDAO extends BaseMybatisDAO {


    private final String MAPPER_NAMESPACE = "com.cmazxiaoma.groupon.cart.entity.CartMapper";

    /**
     * 保存
     *
     * @param cart
     */
    public void save(Cart cart) {
        try {
            super.save(MAPPER_NAMESPACE + ".insert", cart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Long cartId) {
        super.delete(MAPPER_NAMESPACE + ".deleteById", cartId);
    }

    /**
     * 根据用户ID查询
     *
     * @param userId
     * @return
     */
    public List<Cart> findByUserId(Long userId) {
        return super.findAll(MAPPER_NAMESPACE + ".selectByUserId", userId);
    }

    /**
     * 根据用户ID和SkuID查询
     *
     * @param userId
     * @return
     */
    public Cart findByUserIdAndSkuId(Long userId, Long skuId) {
        Map<String, Long> params = new HashMap<>();
        params.put("userId", userId);
        params.put("skuId", skuId);
        return super.findOne(MAPPER_NAMESPACE + ".selectByUserIdAndSkuId", params);
    }

    /**
     * 根据ID集合查询
     *
     * @param ids
     * @return
     */
    public List<Cart> findByIds(List<Long> ids) {
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        return super.findAll(MAPPER_NAMESPACE + ".selectByIds", params);
    }

    /**
     * 根据用户ID查询
     *
     * @param userId
     * @return
     */
    public Long getCartSize(Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return super.findOneObject(MAPPER_NAMESPACE + ".countCartSize", params);
    }

    /**
     * 更新购物车商品数量
     *
     * @param cartId
     * @param step
     */
    public void updateCartDealCount(Long cartId, Integer step) {
        Map<String, Object> params = new HashMap<>();
        params.put("cartId", cartId);
        params.put("step", step);
        super.update(MAPPER_NAMESPACE + ".updateCartDealCount", params);
    }
}

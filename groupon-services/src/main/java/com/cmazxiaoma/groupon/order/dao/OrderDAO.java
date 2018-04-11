package com.cmazxiaoma.groupon.order.dao;

import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.persistence.BaseMybatisDAO;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.groupon.order.entity.Order;
import com.cmazxiaoma.groupon.order.entity.OrderDetail;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OrderDAO
 */
@Repository
public class OrderDAO extends BaseMybatisDAO {

    private static final String ORDER_MAPPER_NAMESPACE = "com.cmazxiaoma.groupon.order.entity.OrderMapper";

    private static final String ORDERDETAIL_MAPPER_NAMESPACE = "com.cmazxiaoma.groupon.order.entity.OrderDetailMapper";

    /**
     * 分页查询订单信息
     *
     * @param search
     * @return
     */
    public PagingResult<Order> findPage(Search search) {
        return this.findForPage(ORDER_MAPPER_NAMESPACE + ".countPageOrders", ORDER_MAPPER_NAMESPACE + ".selectPageOrders", search);
    }

    /**
     * 根据用户ID查询该用户的订单列表
     *
     * @param userId 用户ID
     * @return 订单集合
     */
    public List<Order> getByUserId(long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return this.findAll(ORDER_MAPPER_NAMESPACE + ".selectOrders", params);
    }

    public List<OrderDetail> getOrderDetailsByOrderIds(List<Long> orderIds) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderIds", orderIds);
        return this.findAll(ORDERDETAIL_MAPPER_NAMESPACE + ".selectOrderDetailsByOrderIds", params);
    }

    /**
     * 根据ID查询订单基本信息
     *
     * @param id 订单ID
     * @return 订单信息
     */
    public Order getById(long id) {
        return this.findOne(ORDER_MAPPER_NAMESPACE + ".selectOrderById", id);
    }


    /**
     * 根据订单ID查询订单详情
     *
     * @param orderId 订单ID
     * @return 订单详情集合
     */
    public List<OrderDetail> getOrderDetailByOrderId(long orderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        return this.findAll(ORDERDETAIL_MAPPER_NAMESPACE + ".selectOrderDetailByOrderId", params);
    }

    /**
     * 保存订单信息
     *
     * @param order 订单实体
     */
    public int saveOrder(Order order) {
        return this.save(ORDER_MAPPER_NAMESPACE + ".insertOrderSelective", order);
    }

    /**
     * 更新订单状态
     *
     * @param orderId
     * @param orderStatus
     */
    public void updateOrderStatus(long orderId, int orderStatus) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("orderStatus", orderStatus);
        this.update(ORDER_MAPPER_NAMESPACE + ".updateStatusById", params);
    }

    /**
     * 批量保存订单详细信息
     *
     * @param orderDetails 订单详情集合
     */
    public void saveOrderDetails(List<OrderDetail> orderDetails) {
        this.saveBatch(ORDERDETAIL_MAPPER_NAMESPACE + ".batchInsertOrderDetails", orderDetails);
    }

}

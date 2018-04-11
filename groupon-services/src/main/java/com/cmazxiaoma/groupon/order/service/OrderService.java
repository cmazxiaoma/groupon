package com.cmazxiaoma.groupon.order.service;

import com.cmazxiaoma.framework.base.exception.BusinessException;
import com.cmazxiaoma.framework.common.Pair;
import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.groupon.cart.entity.Cart;
import com.cmazxiaoma.groupon.deal.entity.Deal;
import com.cmazxiaoma.groupon.deal.service.DealService;
import com.cmazxiaoma.groupon.order.constant.OrderConstant;
import com.cmazxiaoma.groupon.order.dao.OrderDAO;
import com.cmazxiaoma.groupon.order.entity.Order;
import com.cmazxiaoma.groupon.order.entity.OrderDetail;
import com.cmazxiaoma.support.address.entity.Address;
import com.cmazxiaoma.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * OrderService
 */
@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private DealService dealService;

    @Autowired
    private UserService userService;

    /*********************************网站**********************************/

    public Long order(Long userId, List<Pair<Cart, Deal>> cartDTOs, Address address, Integer totalPrice, Integer payType) {
        if (null == userId) {
            throw new BusinessException("用户ID不能为空");
        }
        //构造Order对象
        Order order = initOrder(userId, totalPrice, address, payType, cartDTOs);
        if (null == order) {
            throw new BusinessException("订单不能为空");
        }

        if (CollectionUtils.isEmpty(order.getOrderDetails())) {
            throw new BusinessException("订单详细信息不能为空");
        }

        orderDAO.saveOrder(order);
        for (OrderDetail detail : order.getOrderDetails()) {
            if (null == detail) {
                throw new BusinessException("订单详细信息不能为空");
            }
            detail.setOrderId(order.getId());
        }
        orderDAO.saveOrderDetails(order.getOrderDetails());
        return order.getId();
    }

    private Order initOrder(Long userId, Integer totalPrice, Address address, Integer payType,
                            List<Pair<Cart, Deal>> cartDTOs) {
        Date now = new Date();
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalPrice(totalPrice);
        order.setOrderStatus(OrderConstant.STATUS_WAITING_PAY);
        order.setCreateTime(now);
        order.setUpdateTime(now);
        order.setReceiver(address.getReceiver());
        order.setAddress(address.getArea() + " " + address.getDetail());
        order.setPhone(address.getPhone());
        order.setPayType(payType);

        List<OrderDetail> details = new ArrayList<>();
        Integer totalSettlementPrice = 0;
        for (Pair<Cart, Deal> pair : cartDTOs) {
            OrderDetail detail = new OrderDetail();
            detail.setDealCount(pair.getHead().getCount());
            detail.setDealPrice(pair.getEnd().getDealPrice());
            detail.setDealTitle(pair.getEnd().getDealTitle());
            detail.setDealId(pair.getEnd().getId());
            detail.setDealSkuId(pair.getEnd().getSkuId());
            detail.setDealImgId(pair.getEnd().getImageId());
            detail.setDetailStatus(pair.getEnd().getPublishStatus());
            detail.setMerchantCode(pair.getEnd().getMerchantCode());
            detail.setCreateTime(now);
            detail.setUpdateTime(now);
            detail.setUserId(userId);
            detail.setSettlementPrice(pair.getEnd().getSettlementPrice());
            detail.setTotalSettlementPrice(pair.getEnd().getSettlementPrice() * detail.getDealCount());
            detail.setTotalPrice(detail.getDealPrice() * detail.getDealCount());
            totalSettlementPrice += detail.getTotalSettlementPrice();
            detail.setMerchantId(pair.getEnd().getMerchantId());
            details.add(detail);
        }
        order.setOrderDetails(details);
        order.setTotalSettlementPrice(totalSettlementPrice);
        return order;
    }

    /**
     * 根据用户ID查询该用户的订单列表
     *
     * @param userId 用户ID
     * @return 订单集合
     */
    public List<Order> getOrderByUserId(Long userId) {
        if (0 == userId) {
            throw new BusinessException("用户ID不能为空。");
        }
        List<Order> orders = this.orderDAO.getByUserId(userId);
        List<Long> orderIds = orders.stream().map(order -> order.getId()).collect(Collectors.toList());
        List<OrderDetail> details = orderDAO.getOrderDetailsByOrderIds(orderIds);

        Map<Long, List<OrderDetail>> detailMap = new HashMap<>();
        details.forEach(detail -> {
            if (!detailMap.containsKey(detail.getOrderId())) {
                detailMap.put(detail.getOrderId(), new ArrayList<>());
            }
            detailMap.get(detail.getOrderId()).add(detail);
        });
        orders.forEach(order -> order.setOrderDetails(detailMap.get(order.getId())));
        //details.stream().collect(Collectors.toMap(OrderDetail::getId, detail -> detail));
        return orders;
    }

    /*********************************后台**********************************/

    /**
     * 根据订单ID获取订订单及详情
     *
     * @param orderId 订单ID
     * @return 订单及详情
     */
    public Order getOrderAndDetailByOrderId(Long orderId) {
        if (0 == orderId) {
            throw new BusinessException("订单ID不能为空。");
        }
        Order order = this.orderDAO.getById(orderId);
        order.setOrderDetails(this.orderDAO.getOrderDetailByOrderId(orderId));
        return order;
    }

    /**
     * 分页查询订单信息
     *
     * @param search
     * @return
     */
    public PagingResult<Order> findOrderForPage(Search search) {
        PagingResult<Order> result = this.orderDAO.findPage(search);
        //FIXME for循环里调用数据库操作是性能比较低的行为,需要改正
        for (Order order : result.getRows()) {
            order.setOrderDetails(this.orderDAO.getOrderDetailByOrderId(order.getId()));
        }
        return result;
    }

    public void cancel(long orderId) {
        this.orderDAO.updateOrderStatus(orderId, OrderConstant.STATUS_CANCELED);
    }

    public void deliver(long orderId) {
        //FIXME 发送短信提醒用户
        this.orderDAO.updateOrderStatus(orderId, OrderConstant.STATUS_DELIVERING);
    }

    public void complete(long orderId) {
        this.orderDAO.updateOrderStatus(orderId, OrderConstant.STATUS_FINISHED);
    }

    public void payed(long orderId) {
        //FIXME 通知财务系统处理订单款项
        this.orderDAO.updateOrderStatus(orderId, OrderConstant.STATUS_WAITING_DELIVER);
    }

    /*********************************混用**********************************/

}
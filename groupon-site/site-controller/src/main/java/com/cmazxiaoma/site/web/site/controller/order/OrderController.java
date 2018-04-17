package com.cmazxiaoma.site.web.site.controller.order;

import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.groupon.order.service.OrderService;
import com.cmazxiaoma.site.web.site.controller.BaseSiteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/order")
public class OrderController extends BaseSiteController {

	@Autowired
	private OrderService orderService;

//	@RequestMapping(value = "/list")
//	public String listOrder(long userId, Model model) {
//		model.addAttribute("rows", orderService.getOrderByUserId(userId));
//		return "/order/order_list";
//	}
//
//	@RequestMapping(value = "/detail")
//	public String viewDetail(Model model, Long orderId, Search search) {
//		model.addAttribute("order", this.orderService.getOrderAndDetailByOrderId(orderId));
//		return "/order/order_detail";
//	}

    @RequestMapping(value = "/del/{orderId}")
    @ResponseBody
    public String del(@PathVariable Long orderId) {
        try {
            this.orderService.delete(orderId);
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }

    @RequestMapping(value = "/cancel/{orderId}")
    @ResponseBody
    public String cancel(@PathVariable Long orderId) {
        try {
            this.orderService.cancel(orderId);
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }


}
package com.cmazxiaoma.site.web.site.controller.deal;

import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.groupon.deal.entity.Deal;
import com.cmazxiaoma.groupon.deal.entity.DealCategory;
import com.cmazxiaoma.groupon.deal.entity.DealDetail;
import com.cmazxiaoma.groupon.deal.service.DealCategoryService;
import com.cmazxiaoma.groupon.deal.service.DealService;
import com.cmazxiaoma.groupon.order.service.OrderService;
import com.cmazxiaoma.site.web.site.controller.BaseSiteController;
import com.cmazxiaoma.user.service.UserService;
import com.cmazxiaoma.util.DealUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Deal,商品
 */
@Controller
public class DealController extends BaseSiteController {

    @Autowired
    private DealService dealService; // 商品服务

    @Autowired
    private DealCategoryService dealCategoryService;

    @Autowired
    private OrderService orderService; // 订单服务

    @Autowired
    private UserService userService; // 用户服务

    /**
     * 商品详情页
     *
     * @param model
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/item/{skuId}", method = RequestMethod.GET)
    public String detail(Model model, @PathVariable Long skuId, HttpServletResponse response) {
        if (!DealUtil.isValidSkuId(skuId)) {
            return generateError404Page(response);
        }

        //1.查询商品即deal信息
        Deal deal = this.dealService.getBySkuIdForDetailViewOnSite(skuId);
        if (null == deal || !DealUtil.isGroupon(deal)) {
            return generateError404Page(response);
        }

        //2.查询dealDetail信息
        DealDetail dealDetail = this.dealService.getDetailByDealId(deal.getId());
        deal.setDealDetail(dealDetail);
        model.addAttribute("deal", deal);
        return "/deal/detail";
    }

	/*
    * 1.跳转到新页面并且加载数据,采用与detail方法类似方式
	* 2.考虑全需要的信息(deal+detail)
	* 3.查询条件(skuId+time, dealId)
	* 4.健壮性
	* 5.条件判断的准确性(&& || !)
	*
	*
	*
	* 1.参数逐步补全
	* 2.借助工具类与公共方法(隐藏通用逻辑与方法重用)
	*/

    /**
     * 显示某个类别下的第一页商品
     *
     * @param urlName
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/category/{urlName}", method = RequestMethod.GET)
    public String listDealsByCategoryFirst(@PathVariable String urlName, Model model, HttpServletRequest request) {
        return listDealsOfDealCategory(urlName, 1, model, request);
    }

    /**
     * 分页显示某个类别下的商品
     *
     * @param urlName
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/category/{urlName}/{page}", method = RequestMethod.GET)
    public String listDealsOfDealCategory(@PathVariable String urlName, @PathVariable Integer page, Model model,
                                          HttpServletRequest request) {
        DealCategory dealCategory = dealCategoryService.getByUrlName(urlName);

        model.addAttribute("dealCategory", dealCategory);
        PagingResult<Deal> pageResult = dealService.getDealsOfCategories(dealCategory.getSelfAndChildrenIds(),
//				getAreaId(request), page, DealConstant.DEAL_NUM_PER_PAGE_IN_DEALS_OF_CATEGORY_PAGE);
                getAreaId(request), page, 6);
        model.addAttribute("pagingDealList", pageResult);
        return "/deal/category";
    }

}
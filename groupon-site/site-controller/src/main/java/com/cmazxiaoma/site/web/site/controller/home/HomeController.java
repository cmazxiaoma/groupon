package com.cmazxiaoma.site.web.site.controller.home;

import com.cmazxiaoma.framework.base.entity.BaseEntity;
import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.search.Condition;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.groupon.cart.service.CartService;
import com.cmazxiaoma.groupon.deal.entity.Deal;
import com.cmazxiaoma.groupon.deal.service.DealService;
import com.cmazxiaoma.groupon.order.entity.Order;
import com.cmazxiaoma.groupon.order.service.OrderService;
import com.cmazxiaoma.site.web.base.objects.WebUser;
import com.cmazxiaoma.site.web.site.controller.BaseSiteController;
import com.cmazxiaoma.site.web.site.dto.FavoriteDTO;
import com.cmazxiaoma.support.address.entity.Address;
import com.cmazxiaoma.support.address.service.AddressService;
import com.cmazxiaoma.support.favorite.entity.Favorite;
import com.cmazxiaoma.support.favorite.service.FavoriteService;
import com.cmazxiaoma.support.message.entity.Message;
import com.cmazxiaoma.support.message.service.MessageService;
import com.cmazxiaoma.support.remind.entity.StartRemind;
import com.cmazxiaoma.support.remind.service.StartRemindService;
import com.cmazxiaoma.user.entity.UserBasicInfo;
import com.cmazxiaoma.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 个人中心
 */
@Controller
@RequestMapping(value = "/home")
public class HomeController extends BaseSiteController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private DealService dealService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private StartRemindService startRemindService;

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, Model model) {
        WebUser webUser = getCurrentLoginUser(request);
        UserBasicInfo info = userService.getBasicInfoByUserId(webUser.getUserId());
        model.addAttribute("info", info);
        return "/user/info";
    }

    /**
     * 加入收藏
     *
     * @param request
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/favorite/{skuId}")
    @ResponseBody
    public String favorite(HttpServletRequest request, @PathVariable Long skuId) {
        Deal deal = this.dealService.getBySkuId(skuId);
        Favorite favorite = new Favorite();
        favorite.setDealId(deal.getId());
        favorite.setDealSkuId(skuId);
        WebUser user = getCurrentLoginUser(request);
        favorite.setUserId(user.getUserId());
        favoriteService.save(favorite);
        return "1";
    }

    /**
     * 删除收藏
     */
    @RequestMapping(value = "/delFavorite/{favoriteId}")
    @ResponseBody
    public String delFavorite(HttpServletRequest request, @PathVariable Long favoriteId) {
        try {
            favoriteService.deleteById(favoriteId);
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }

    /**
     * 加入开团提醒
     *
     * @param request
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/remind/{skuId}")
    @ResponseBody
    public String remind(HttpServletRequest request, @PathVariable Long skuId) {
        WebUser user = getCurrentLoginUser(request);
        Deal deal = dealService.getBySkuId(skuId);
        StartRemind remind = new StartRemind();
        remind.setDealId(deal.getId());
        remind.setDealSkuId(skuId);
        remind.setDealTitle(deal.getDealTitle());
        remind.setUserId(user.getUserId());
        remind.setStartTime(deal.getStartTime());
        startRemindService.save(remind);
        return "1";
    }

    @RequestMapping(value = "/order")
    public String order(HttpServletRequest request, Model model) {
        WebUser webUser = getCurrentLoginUser(request);
        List<Order> orders = orderService.getOrderByUserId(webUser.getUserId());
        model.addAttribute("orders", orders);
        return "/user/order";
    }

    @RequestMapping(value = "/favorite")
    public String favorite(HttpServletRequest request, Model model) {
        WebUser user = getCurrentLoginUser(request);
        if (null != user) {
            Long userId = user.getUserId();
            List<Favorite> favorites = favoriteService.getByUserId(userId);

            if (null != favorites && !favorites.isEmpty()) {
                List<Long> dealIds = favorites.stream().map(cart -> cart.getDealId())
                        .collect(Collectors.toList());
                List<Deal> deals = dealService.getDealsForCart(dealIds);
                Map<Long, Deal> map = BaseEntity.idEntityMap(deals);
                List<FavoriteDTO> dtoList = new ArrayList<>(favorites.size());
                favorites.stream().forEach(
                        favorite -> dtoList.add(new FavoriteDTO(favorite, map.get(favorite.getDealId()))));
                model.addAttribute("favorites", dtoList);
            }
        }
        return "/user/favorite";
    }

    @RequestMapping(value = "/info")
    public String info(HttpServletRequest request, Model model) {
        WebUser webUser = getCurrentLoginUser(request);
        UserBasicInfo info = userService.getBasicInfoByUserId(webUser.getUserId());
        model.addAttribute("info", info);
        return "/user/info";
    }

    @RequestMapping(value = "/info/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateInfo(HttpServletRequest request, UserBasicInfo info) {
        if (null == info.getId()) {
            WebUser webUser = getCurrentLoginUser(request);
            userService.saveBasicInfo(info, webUser.getUserId());
        } else {
            userService.updateBasicInfo(info);
        }
        return "1";
    }

    @RequestMapping(value = "/address")
    public String address(HttpServletRequest request, Model model) {
        WebUser webUser = getCurrentLoginUser(request);
        List<Address> addresses = addressService.getByUserId(webUser.getUserId());
        model.addAttribute("addresses", addresses);
        return "/user/address";
    }

    @RequestMapping(value = "/address/new")
    public String createAddressPrompt(HttpServletRequest request, Model model) {
        return "/user/create_address";
    }

    @RequestMapping(value = "/address/create", method = RequestMethod.POST)
    public String createAddress(HttpServletRequest request, Model model, Address address) {
        WebUser webUser = getCurrentLoginUser(request);
        address.setUserId(webUser.getUserId());
        addressService.save(address);
        return "redirect:/home/address";
    }

    @RequestMapping(value = "/message")
    public String message() {
        return messagePage(1);
    }

    @RequestMapping(value = "/message/{page}")
    public String messagePage(@PathVariable Integer page) {
        Search search = new Search();
        search.setPage(page);
        search.setRows(5);
        List<Condition> conditionList = new ArrayList<>();
        conditionList.add(new Condition("userId", getCurrentLoginUser(httpRequest).getUserId()));
        search.setConditionList(conditionList);
        PagingResult<Message> pagingResult = messageService.getPage(search);
        modelMap.addAttribute("pagingResult", pagingResult);
        modelMap.addAttribute("messages", pagingResult.getRows());

        return "/user/message";
    }

    /**
     * 已读通知
     * @param messageId
     * @return
     */
    @RequestMapping(value = "/readMessage/{messageId}")
    @ResponseBody
    public String readMessage(@PathVariable Long messageId) {
        try {
            messageService.updateReadStatus(messageId);
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }

    /**
     * 删除通知
     * @param messageId
     * @return
     */
    @RequestMapping(value = "/delMessage/{messageId}")
    @ResponseBody
    public String delMessage(@PathVariable Long messageId) {
        try {
            messageService.deleteById(messageId);
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }

}
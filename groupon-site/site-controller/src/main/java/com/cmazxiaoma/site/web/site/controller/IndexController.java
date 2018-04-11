package com.cmazxiaoma.site.web.site.controller;

import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.groupon.deal.entity.Deal;
import com.cmazxiaoma.groupon.deal.entity.DealCategory;
import com.cmazxiaoma.groupon.deal.service.DealCategoryService;
import com.cmazxiaoma.groupon.deal.service.DealService;
import com.cmazxiaoma.site.web.site.dto.IndexCategoryDealDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/4/4 9:21
 */
@Controller
public class IndexController extends BaseSiteController {

    @Autowired
    private DealService dealService;

    @Autowired
    private DealCategoryService categoryService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {
        //1.分类 2.首页商品 3.每个大分类下显示8个商品
        List<DealCategory> categoryList = categoryService.getCategories();
        model.addAttribute("categories", categoryList);
        //根据ip确定
        Long areaId = getAreaId(request);
        /**
         * 1.构造一个结构体存放每个分类的8个商品 2.界面循环结构体的集合
         * 3.结构体包含8个商品,1个分类 4.8个商品分两组
         */
        List<IndexCategoryDealDTO> indexCategoryDealDTOList = new ArrayList<>();
        categoryList.forEach(category -> {
            List<Deal> deals = dealService.getDealsForIndex(areaId, category.getSelfAndChildrenIds());
            indexCategoryDealDTOList.add(new IndexCategoryDealDTO(category, deals));
        });
        model.addAttribute("indexCategoryDealDTOs", indexCategoryDealDTOList);

        return "index";
    }

    /**
     * 商品搜索
     *
     * @param s
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(value = "/search")
    public String search(String s, Model model, Integer page) {
        List<DealCategory> categoryList = categoryService.getCategories();
        model.addAttribute("categories", categoryList);
        try {
            //get提交中文为ISO-8859-1,需转成UTF-8
            String word = new String(s.getBytes("ISO-8859-1"), "UTF-8");
            PagingResult<Deal> pageResult = dealService.searchByName(word, page, 3);
            model.addAttribute("pagingDealList", pageResult);
            model.addAttribute("s", word);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "/search";
    }
}

package com.cmazxiaoma.admin.product.controller;

import com.cmazxiaoma.admin.base.controller.AjaxResult;
import com.cmazxiaoma.admin.base.controller.BaseAdminController;
import com.cmazxiaoma.admin.common.tree.EasyUITreeNode;
import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.groupon.deal.entity.DealCategory;
import com.cmazxiaoma.groupon.deal.service.DealCategoryService;
import com.cmazxiaoma.groupon.deal.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProductCategoryController
 */
@Controller
@RequestMapping(value = "/product/category")
public class ProductCategoryController extends BaseAdminController {

    @Autowired
    private DealService dealService;

    @Autowired
    private DealCategoryService dealCategoryService;

    /**
     * 显示商品类别树
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String productList() {
        return "/product/category/category_tree";
    }

    /**
     * 加载商品类别树
     *
     * @return
     */
    @RequestMapping(value = "/buildProductCategoryTree", method = RequestMethod.GET)
    public @ResponseBody
    List<EasyUITreeNode> buildProductCategoryTree() {
        List<DealCategory> list = dealCategoryService.getCategories();
        return initTree(list);
    }

    /**
     * 递归构建
     *
     * @param list
     * @return
     */
    private List<EasyUITreeNode> initTree(List<DealCategory> list) {
        List<EasyUITreeNode> nodeList = new ArrayList<>();
        for (DealCategory category : list) {
            EasyUITreeNode vo = new EasyUITreeNode();
            vo.setChecked(false);
            vo.setId(category.getId());
            vo.setText(category.getName());
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("deep", category.getDeep());
            vo.setAttributes(attributes);
            if (null != category.getChildren()) {
                vo.setChildren(initTree(category.getChildren()));
            }
            nodeList.add(vo);
        }
        return nodeList;
    }

    /**
     * 加载商品类别列表
     *
     * @param search
     * @return
     */
    @RequestMapping(value = "/listProductCategory", method = RequestMethod.POST)
    public @ResponseBody
    PagingResult<DealCategory> listProductCategory(Search search) {
        return dealCategoryService.getPageCategories(search);
    }

    /**
     * 显示商品类别编辑页面
     *
     * @param id
     * @param parentId
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editProductCategory(Long id, Long parentId, Model model) {
        if (id > 0) {
            DealCategory category = dealCategoryService.getCategoryById(id);
            model.addAttribute("category", category);
        } else {
            DealCategory category = new DealCategory();
            category.setId(0L);
            category.setParentId(parentId);

            model.addAttribute("category", category);
        }
        return "/product/category/category_edit";
    }

    /**
     * 添加或修改商品类别
     *
     * @param category
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateProductCategory", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult saveOrUpdateProductCategory(DealCategory category) {
        if (category.getId() == 0) { // 添加
            category.setOrderNum(dealCategoryService.getMaxOrderByParentId(category.getParentId()) + 1);
            category.setDeep(category.getParentId() == 0 ? 1 : dealCategoryService.getAncestorCategories(category.getParentId()).size() + 2);
            int effectRows = dealCategoryService.saveCategory(category);

            if (effectRows == 0) {
                return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_ERROR, "添加商品类别失败");
            } else {
                return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_SUCCESS, "添加商品类别成功");
            }
        } else { // 修改
            int effectRows = dealCategoryService.updateCategoryById(category);

            if (effectRows == 0) {
                return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_ERROR, "修改商品类别失败");
            } else {
                return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_SUCCESS, "修改商品类别成功");
            }
        }
    }

    /**
     * 删除商品类别
     *
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/deleteProductCategory", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResult deleteProductCategory(Long categoryId) {
        if (dealService.countByCategoryId(categoryId) > 0) {
            return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_WARN, "删除商品类别失败，该商品类别下还有商品");
        }

        int effectRows = dealCategoryService.deleteCategoryById(categoryId);

        if (effectRows == 0) {
            return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_ERROR, "删除商品类别失败");
        } else {
            return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_SUCCESS, "删除商品类别成功");
        }
    }

    /**
     * 交换两个商品类别排序序号
     *
     * @param id1
     * @param id2
     * @return
     */
    @RequestMapping(value = "/swapProductCategoryOrderNum", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResult swapProductCategoryOrderNum(Long id1, Long id2) {
        boolean success = dealCategoryService.swapOrder(id1, id2);
        if (success) {
            return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_SUCCESS, "修改商品类别排序成功");
        } else {
            return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_ERROR, "修改商品类别排序失败");
        }
    }
}
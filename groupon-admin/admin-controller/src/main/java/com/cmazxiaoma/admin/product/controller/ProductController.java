package com.cmazxiaoma.admin.product.controller;

import com.cmazxiaoma.admin.base.controller.AjaxResult;
import com.cmazxiaoma.admin.base.controller.BaseAdminController;
import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.groupon.deal.constant.DealConstant;
import com.cmazxiaoma.groupon.deal.entity.Deal;
import com.cmazxiaoma.groupon.deal.entity.DealDetail;
import com.cmazxiaoma.groupon.deal.service.DealCategoryService;
import com.cmazxiaoma.groupon.deal.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 商品Controller
 */
@Controller
@RequestMapping(value = "/product/product")
public class ProductController extends BaseAdminController {

    /**
     * 商品服务
     */
    @Autowired
    private DealService dealService;

    /**
     * 商品分类服务
     */
    @Autowired
    private DealCategoryService dealCategoryService;

    /**
     * 显示商品列表页
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String productList() {
        return "/product/product/product_list";
    }

    /**
     * 加载商品列表页数据
     *
     * @param search
     * @return
     */
    @RequestMapping(value = "/listProduct", method = RequestMethod.POST)
    public @ResponseBody PagingResult<Deal> listProduct(Search search) {
        PagingResult<Deal> dealListPage = dealService.getDealList(search);
        if (dealListPage != null && dealListPage.getRows().size() > 0) {
            for (Deal deal : dealListPage.getRows()) {
                String imagePath = getObjectImageUrl(deal.getImageId(), DealConstant.PICTURE_MODULE_DEAL, 2);
                deal.setImageGenPath(imagePath);
            }
        }
        return dealListPage;
    }

    /**
     * 商品编辑页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editProductWithoutHeaderAndFooter(@PathVariable Long id, Model model) {
        if (id != 0) {// 修改商品
            Deal deal = dealService.getById(id);
            deal.setImageGenPath(getObjectImageUrl(deal.getImageId(), DealConstant.PICTURE_MODULE_DEAL, 1));
            model.addAttribute("deal", deal);
        }
        return "/product/product/product_edit";
    }

    /**
     * 新建/保存商品信息
     *
     * @param deal           商品
     * @param dealDetailInfo 商品详情(包含图片路径)
     * @param dealImgFile    商品图片
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateProduct", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public AjaxResult saveOrUpdateProduct(Deal deal, String dealDetailInfo,
                                          @RequestParam(value = "dealImgFile") MultipartFile dealImgFile) {
        Long dealId = deal.getId();
        DealDetail dealDetail = new DealDetail();
        dealDetail.setDealDetail(dealDetailInfo);
        deal.setDealDetail(dealDetail);

        if (null == deal || 0 == dealId) {//新建商品
            //复制商品时不用必须上传图片,所以可能会出现dealImgFile为空但是imageId不为空
            if ((null == deal.getImageId() || 0 == deal.getImageId()) && dealImgFile.isEmpty()) {
                return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_ERROR, "请选择商品图片");
            }
            //复制商品:同一商品不同型号可能是不同的SKU
            boolean saveDealSuccess = false;
            try {
                saveDealSuccess = dealService.saveDeal(deal, dealImgFile.isEmpty() ? null : dealImgFile.getBytes());
            } catch (IOException e) {
                return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_ERROR, "商品保存失败, 读取图片错误");
            }
            if (saveDealSuccess) {
                return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_ERROR, "商品保存成功");
            } else {
                return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_ERROR, "商品保存失败");
            }
        } else {//更新商品
            byte[] dealImgBytes = null;

            if (!dealImgFile.isEmpty()) {
                try {
                    dealImgBytes = dealImgFile.getBytes();
                } catch (IOException e) {
                    return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_ERROR, "商品更新失败, 读取图片错误");
                }
            }
            boolean updateDealSuccess = dealService.updateDeal(deal, dealImgBytes);
            if (updateDealSuccess) {
                return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_ERROR, "商品更新成功");
            } else {
                return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_ERROR, "商品更新失败");
            }
        }
    }

}
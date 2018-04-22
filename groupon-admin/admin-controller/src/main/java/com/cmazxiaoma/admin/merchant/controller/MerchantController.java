package com.cmazxiaoma.admin.merchant.controller;

import com.cmazxiaoma.admin.base.controller.AjaxResult;
import com.cmazxiaoma.admin.base.controller.BaseAdminController;
import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.groupon.merchant.entity.Merchant;
import com.cmazxiaoma.groupon.merchant.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * MerchantController
 */
@Controller
@RequestMapping(value = "/merchant")
public class MerchantController extends BaseAdminController {

    @Autowired
    private MerchantService merchantService;

    @RequestMapping(value = "/merchantList")
    public String merchantList(Model model, Search search) {
        return "/merchant/merchant_list";
    }

    @RequestMapping(value = "/listMerchant", method = RequestMethod.POST)
    @ResponseBody
    public PagingResult<Merchant> listMerchant(Model model, Search search) {
        return merchantService.findMerchantForPage(search);
    }

    @RequestMapping(value = "/loadAll", method = RequestMethod.POST)
    @ResponseBody
    public List<Merchant> loadAllMerchant() {
        return merchantService.findAll();
    }

    @RequestMapping(value = "/edit/{id}")
    public String edit(@PathVariable Long id) {
        // 修改商家
        if (id != 0) {
            // 商家信息
            Merchant merchant = merchantService.getById(id);
            modelMap.addAttribute("merchant", merchant);
        }
        return "/merchant/merchant_edit";
    }

    /**
     * 添加或者更新
     * @param merchant
     * @param dealDetailInfo
     * @param dealImgFile
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult save(Merchant merchant, String dealDetailInfo, MultipartFile dealImgFile) {
        try {
            if (StringUtils.isEmpty(merchant.getId())) {
                merchantService.save(merchant);
            } else {
                merchantService.update(merchant);
            }
            return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_SUCCESS, "商家保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_ERROR, "商家保存失败");
        }
    }

}
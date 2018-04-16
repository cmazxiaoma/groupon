package com.cmazxiaoma.site.web.site.helper.deal;

import com.cmazxiaoma.groupon.deal.entity.DealCategory;
import com.cmazxiaoma.groupon.deal.service.DealCategoryService;
import com.cmazxiaoma.site.web.site.helper.BaseSiteHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/4/4 10:54
 */
@Component
public class DealHelper extends BaseSiteHelper {

    @Autowired
    private DealCategoryService dealCategoryService;

    public String getCategoryUrlNameById(Long categoryId) {
        return dealCategoryService.getCategoryById(categoryId).getUrlName();
    }

    public String getCategoryNameById(Long categroyId) {
        return dealCategoryService.getCategoryById(categroyId).getName();
    }

}

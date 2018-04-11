package com.cmazxiaoma.groupon.deal.cache;

import com.cmazxiaoma.framework.cache.CacheOperator;
import com.cmazxiaoma.groupon.deal.entity.DealCategory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/3/11 10:31
 */
@Component
public class DealCategoryCacheOperator extends CacheOperator {

    /**
     * 向缓存中增加DealCategory
     *
     * @param dealCategory
     */
    public void putDealCategory(DealCategory dealCategory) {
        super.putEntity("DealCategory." + dealCategory.getId(), dealCategory);

        //维护其父类别的子类别缓存
        List<DealCategory> subCategories = getSubCategories(dealCategory.getParentId());

        if (!CollectionUtils.isEmpty(subCategories)) {
            if (subCategories.contains(dealCategory)) {
                int index = 0;

                //去掉自己
                for (int i = 0; i < subCategories.size(); i++) {
                    if (subCategories.get(i).getId() == dealCategory.getId()) {
                        index = i;
                    }
                }
                subCategories.remove(index);
            }
            subCategories.add(dealCategory);
            putSubCategories(dealCategory.getParentId(), subCategories);
        }
    }

    /**
     * 根据ID从缓存中取出ProductCategory
     *
     * @param categoryId
     * @return
     */
    public DealCategory getDealCategory(Long categoryId) {
        return super.getEntity("DealCategory." + categoryId.toString(), DealCategory.class);
    }

    /**
     * 根据ID从缓存中删除ProductCategory
     *
     * @param categoryId
     */
    public void deleteDealCategory(Long categoryId) {
        //维护其父类别的子类别缓存
        DealCategory dealCategory = getDealCategory(categoryId);

        if (dealCategory != null) {
            List<DealCategory> subCategories = getSubCategories(dealCategory.getParentId());

            if (!CollectionUtils.isEmpty(subCategories)) {
                if (subCategories.contains(dealCategory)) {
                    subCategories.remove(dealCategory);
                }
                putSubCategories(dealCategory.getParentId(), subCategories);
            }
        }
        super.delete("DealCategory." + categoryId.toString());
    }

    /**
     * 获取全部DealCategory
     */
    public List<DealCategory> getAllDealCategories() {
        return super.getEntitiesByKeyPrefix("DealCategory", DealCategory.class);
    }

    /**
     * 添加下级分类
     *
     * @param parentId
     * @param dealCategories
     */
    public void putSubCategories(Long parentId, List<DealCategory> dealCategories) {
        super.putEntities("SubDealCategories." + parentId, dealCategories);
    }

    /**
     * 获取下级分类
     *
     * @param parentId
     * @return
     */
    public List<DealCategory> getSubCategories(Long parentId) {
        return super.getEntities("SubDealCategories." + parentId,
                DealCategory.class);
    }

    /**
     * 删除下级分类
     *
     * @param parentId
     */
    public void deleteSubCategories(Long parentId) {
        super.delete("SubDealCategories." + parentId);
    }
}

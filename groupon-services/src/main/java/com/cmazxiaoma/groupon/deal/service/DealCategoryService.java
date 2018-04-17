package com.cmazxiaoma.groupon.deal.service;


import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.groupon.deal.cache.DealCategoryCacheOperator;
import com.cmazxiaoma.groupon.deal.constant.DealConstant;
import com.cmazxiaoma.groupon.deal.dao.DealCategoryDAO;
import com.cmazxiaoma.groupon.deal.entity.DealCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * DealCategoryService
 */
@Service
public class DealCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(DealCategoryService.class);

    @Autowired
    private DealCategoryDAO dealCategoryDAO;

    @Autowired
    private DealCategoryCacheOperator dealCategoryCacheOperator;


    /**********************网站**************************/
    /**
     * 根据urlName查询
     * @param urlName
     * @return
     */
    public DealCategory getByUrlName(String urlName) {
//        return dealCategoryDAO.getByUrlName(urlName);
        //FIXME 此处应该从缓存中取
        List<DealCategory> dealCategories = getAllWithoutDeleted();

        //获取大分类下面所有的分类
        Optional<DealCategory> optional = dealCategories.stream()
                .filter(dealCategory ->
                        (Objects.equals(urlName, dealCategory.getUrlName()))).findFirst();

        if (optional.isPresent()) {
            DealCategory category = optional.get();
            buildSubs(category, dealCategories);
            return category;
        }
        return null;
    }

    /**********************后台**************************/


    /**********************混用**************************/

    /**
     * 查询所有分类,按顺序显示
     *
     * @return
     */
    public List<DealCategory> getCategories() {
        List<DealCategory> dealCategories = getAllWithoutDeleted();//从缓存或数据库中查询全部

        //JDK8的stream处理,把根分类区分出来
        List<DealCategory> roots = dealCategories.stream()
                .filter(dealCategory -> (dealCategory.getParentId() == 0))
                .collect(Collectors.toList());

        //对跟分类进行排序
        roots.sort(new Comparator<DealCategory>() {
            @Override
            public int compare(DealCategory o1, DealCategory o2) {
                return o1.getOrderNum() > o2.getOrderNum() ? 1 : -1;
            }
        });

        //把非根分类区分出来
        List<DealCategory> subs = dealCategories.stream().
                filter(dealCategory -> (dealCategory.getParentId() != 0))
                .collect(Collectors.toList());

        //递归构建结构化的分类信息
        roots.forEach(root -> buildSubs(root, subs));
        return roots;
    }

    /**
     * 递归构建
     *
     * @param parent
     * @param subs
     */
    private void buildSubs(DealCategory parent, List<DealCategory> subs) {
        List<DealCategory> children = subs.stream()
                .filter(sub -> (sub.getParentId() == parent.getId()))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(children)) {//有子分类的情况
            parent.setChildren(children);
            children.forEach(child -> buildSubs(child, subs));//再次递归构建
        }
    }

    /**
     * 查询所有未删除的商品类别,并且加入到缓存中
     *
     * @return
     */
    private List<DealCategory> getAllWithoutDeleted() {
        List<DealCategory> allCategories = dealCategoryCacheOperator.getAllDealCategories();
//        CacheUtil.getAllEntities(DealCategory.class);
        if (allCategories == null || allCategories.size() == 0) {
            allCategories = dealCategoryDAO.getAllWithoutDeleted();
            for (DealCategory dealCategory : allCategories) {
                dealCategoryCacheOperator.putDealCategory(dealCategory);
//                CacheUtil.putEntity("DealCategory." + dealCategory.getId(), dealCategory);
//                CacheUtil.putEntity(dealCategory);
            }
        }
        return allCategories;
    }

    /**
     * 根据ID查询
     *
     * @param categoryId
     * @return
     */
    public DealCategory getCategoryById(Long categoryId) {
        if (categoryId <= 0) {
            return null;
        }

        DealCategory dealCategory = dealCategoryCacheOperator.getDealCategory(categoryId);
        if (dealCategory == null) {
            dealCategory = dealCategoryDAO.getById(categoryId);
            dealCategoryCacheOperator.putDealCategory(dealCategory);
        }
        return dealCategory;
//        return dealCategoryDAO.getById(categoryId);
    }

    /**
     * 分页查询
     *
     * @param search
     * @return
     */
    public PagingResult<DealCategory> getPageCategories(Search search) {
        return dealCategoryDAO.getPageCategories(search);
    }

    /**
     * 获取某个商品类别下的子类别中最大的排序序号
     *
     * @param parentId
     * @return
     */
    public int getMaxOrderByParentId(Long parentId) {
        return dealCategoryDAO.getMaxOrderByParentId(parentId);
    }

    /**
     * 添加商品类别
     *
     * @param category 类别信息
     * @return
     */
    public int saveCategory(DealCategory category) {
        int effectRows = 0;
        category.setCreateTime(new Date());
        effectRows = dealCategoryDAO.save(category);
        if (effectRows == 0) {
            logger.info("添加商品类别：" + category + " 失败");
            return effectRows;
        }

        // 更新缓存
//        dealCategoryCacheOperator.putDealCategory(category);
        return effectRows;
    }

    /**
     * 修改商品类别
     *
     * @param category 类别信息
     * @return
     */
    public int updateCategoryById(DealCategory category) {
        int effectRows = 0;
        effectRows = dealCategoryDAO.updateById(category);
        if (effectRows == 0) {
            logger.info("修改商品类别：" + category + " 失败");
            return effectRows;
        }

        // 更新缓存
        // dealCategoryCacheOperator.putDealCategory(dealCategoryDAO.getById(category.getId()));
        return effectRows;
    }

    /**
     * 删除商品类别（逻辑删除）
     *
     * @param categoryId 类别ID
     * @return
     */
    public int deleteCategoryById(Long categoryId) {
        DealCategory category = new DealCategory();
        category.setId(categoryId);
        category.setPublishStatus(DealConstant.DEAL_PUBLISH_STATUS_DELETED);

        int effectRows = dealCategoryDAO.updateById(category);
        if (effectRows == 0) {
            logger.info("删除商品类别：" + category + " 失败");
            return effectRows;
        }
        // 更新缓存
        //dealCategoryCacheOperator.deleteDealCategory(categoryId);
        return effectRows;
    }

    /**
     * 获取商品父类别
     *
     * @param categoryId 类别ID
     * @return
     */
    public DealCategory getParentCategory(Long categoryId) {
        if (categoryId <= 0) {
            return null;
        }

        DealCategory category = getCategoryById(categoryId);
        return getCategoryById(category.getParentId());
    }

    /**
     * 获取某个商品类别的所有子类别
     *
     * @param categoryId 商品类别ID
     * @return
     */
    public List<DealCategory> getSubCategories(Long categoryId) {
//        List<DealCategory> subCategories = dealCategoryCacheOperator.getSubCategories(categoryId);
//        if (subCategories == null || subCategories.size() == 0) {
//            subCategories = new ArrayList<>();
//            List<DealCategory> allCategories = getAllWithoutDeleted();
//            for (DealCategory dealCategory : allCategories) {
//                if (dealCategory.getParentId() == categoryId) {
//                    subCategories.add(dealCategory);
//                }
//            }
//
//            dealCategoryCacheOperator.putSubCategories(categoryId, subCategories);
//        }
        List<DealCategory> subCategories = dealCategoryDAO.getDirectSubs(categoryId);

        // 排序
        Collections.sort(subCategories, new Comparator<DealCategory>() {
            @Override
            public int compare(DealCategory category1, DealCategory category2) {
                if (category1.getOrderNum() > category2.getOrderNum()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return subCategories;
    }

    /**
     * 获取某个商品类别的所有后代类别
     *
     * @param categoryId 商品类别ID
     * @return
     */
    public List<DealCategory> getLaterGenerationCategories(Long categoryId) {
        List<DealCategory> categories = new ArrayList<>();

        List<DealCategory> subCategories = getSubCategories(categoryId);
        for (DealCategory category : subCategories) {
            categories.add(category);
            categories.addAll(getLaterGenerationCategories(category.getId()));
        }

        return categories;
    }

    /**
     * 获取某个商品类别所有祖先类别
     *
     * @param categoryId
     * @return
     */
    public List<DealCategory> getAncestorCategories(Long categoryId) {
        List<DealCategory> categories = new ArrayList<>();

        DealCategory parentCategory = getParentCategory(categoryId);
        if (parentCategory != null) {
            categories.add(parentCategory);
            categories.addAll(getAncestorCategories(parentCategory.getId()));
        }
        return categories;
    }

    /**
     * 交换两个商品类别的排序序号
     *
     * @param id1
     * @param id2
     * @return
     */
    public boolean swapOrder(Long id1, Long id2) {
        int effectRows = dealCategoryDAO.swapOrder(id1, id2);
        if (effectRows != 2) {
            logger.info("交换商品类别排序序号[id1:" + id1 + ",id2:" + id2 + "] 失败");
            return false;
        } else {
            // 更新缓存
            DealCategory category1 = getCategoryById(id1);
            DealCategory category2 = getCategoryById(id2);

            int temp = 0;
            temp = category1.getOrderNum();
            category1.setOrderNum(category2.getOrderNum());
            category2.setOrderNum(temp);

//            dealCategoryCacheOperator.putDealCategory(category1);
//            dealCategoryCacheOperator.putDealCategory(category2);
            return true;
        }
    }


}

package com.cmazxiaoma.groupon.deal.dao;


import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.persistence.BaseMybatisDAO;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.framework.util.DateUtil;
import com.cmazxiaoma.groupon.deal.constant.DealConstant;
import com.cmazxiaoma.groupon.deal.entity.Deal;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DealDAO extends BaseMybatisDAO {

    private final String MAPPER_NAMESPACE = "com.cmazxiaoma.groupon.deal.entity.DealMapper";


    /*********************************网站**********************************/


    //FIXME 只查8个就够了,如何实现?
    public List<Deal> getDealsForIndex(Long areaId, List<Long> categoryIds, int publishStatus) {
        Map<String, Object> params = new HashMap<>();
        params.put("categoryIds", categoryIds);
        params.put("areaId", areaId);
        params.put("nowTime", new Date());
        params.put("publishStatus", publishStatus);
        return findAll(MAPPER_NAMESPACE + ".selectDealsForIndex", params);
    }

    /**
     * 搜索分页查询
     *
     * @param name
     * @param page
     * @param rows
     * @return
     */
    public PagingResult<Deal> getPageDealsForSearch(String name, int page, int rows) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        return this.findForPage(MAPPER_NAMESPACE + ".countPageDealsForSearch",
                MAPPER_NAMESPACE + ".selectPageDealsForSearch", page, rows, params);
    }


    /**
     * 查询显示在购物车的商品列表
     *
     * @param dealIds
     * @return
     */
    public List<Deal> getDealsForCart(List<Long> dealIds) {
        Map<String, Object> params = new HashMap<>();
        params.put("dealIds", dealIds);
        return findAll(MAPPER_NAMESPACE + ".selectDealsForCart", params);
    }


    /*********************************后台**********************************/


    /*********************************混用**********************************/

    /**
     * 分页查询商品信息
     *
     * @param search
     * @return
     */
    public PagingResult<Deal> getPageDeals(Search search) {
        return this.findForPage(MAPPER_NAMESPACE + ".countPageDeals", MAPPER_NAMESPACE + ".selectPageDeals", search);
    }

    /**
     * 分页查询某个类别下商品
     *
     * @param search
     * @return
     */
    public PagingResult<Deal> getDealsOfCategories(Search search) {
        return super.findForPage(MAPPER_NAMESPACE + ".countDealsOfCategories", MAPPER_NAMESPACE
                + ".selectDealsOfCategories", search);
    }

    /**
     * 保存商品信息
     *
     * @param deal
     * @return
     */
    public int saveDeal(Deal deal) {
        return this.save(MAPPER_NAMESPACE + ".insertDeal", deal);
    }

    /**
     * 更新商品信息
     *
     * @param deal
     * @return
     */
    public int updateById(Deal deal) {
        return this.update(MAPPER_NAMESPACE + ".updateById", deal);
    }

    /**
     * 删除商品信息
     *
     * @param deal
     * @return
     */
    public int modifyStatusById(Deal deal) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", deal.getId());
        params.put("publishStatus", deal.getPublishStatus());

        // 只有在发布的时候更新发布时间，其余状态则清空发布时间
        if (deal.getPublishStatus().equals(DealConstant.DEAL_PUBLISH_STATUS_PUBLISH)) {
            params.put("publishTime", new Date());
        } else {
            params.put("publishTime", null);
        }

        params.put("updateTime", new Date());

        if (deal.getPublishStatus().equals(DealConstant.DEAL_PUBLISH_STATUS_PUBLISH)) {
            if (null == deal.getEndTime()) {
                params.put("endTime", DateUtil.getSevenDaysAfterOnSale());
            } else {
                params.put("endTime", deal.getEndTime());
            }

        }
        return this.update(MAPPER_NAMESPACE + ".modifyStatusById", params);
    }

    /**
     * 根据ID查询商品信息
     *
     * @param id
     * @return
     */
    public Deal getById(Long id) {
        if (null == id) {
            return new Deal();
        }
        return this.findOne(MAPPER_NAMESPACE + ".selectByPrimaryKey", id);
    }

    /**
     * 根据SkuId获取存在的商品记录
     *
     * @param skuId
     * @return
     */
    public Deal findBySkuId(Long skuId) {
        return findOne(MAPPER_NAMESPACE + ".selectBySkuId", skuId);
    }

    /**
     * 根据SkuId获取存在的商品记录
     *
     * @param skuIds
     * @return
     */
    public List<Deal> findBySkuIds(List<Long> skuIds) {
        Map<String, Object> params = new HashMap<>();
        params.put("skuIds", skuIds);
        return findAll(MAPPER_NAMESPACE + ".selectBySkuIds", params);
    }

    /**
     * 根据SkuId获取存在的商品ID
     *
     * @param skuId
     * @return
     */
    public Long getIdBySkuId(Long skuId) {
        return super.findId(MAPPER_NAMESPACE + ".selectIdBySkuId", skuId);
    }

    /**
     * 根据类别查询商品
     *
     * @param categoryId
     * @return
     */
    public List<Deal> getByCategoryId(Long categoryId) {
        return findAll(MAPPER_NAMESPACE + ".selectByCategoryId", categoryId);
    }

    /**
     * 根据类别查询商品数量
     *
     * @param categoryId
     * @return
     */
    public long countByCategoryId(Long categoryId) {
        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        return count(MAPPER_NAMESPACE + ".countByCategoryId", params);
    }

    /**
     * 查询SkuId最大的商品
     *
     * @return
     */
    public Deal getMaxSkuId() {
        return findOne(MAPPER_NAMESPACE + ".selectMaxSkuId");
    }

    /**
     * 根据商家编码查询商品
     *
     * @param merchantCode
     * @return
     */
    public Deal getByMerchantCode(String merchantCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantCode", merchantCode);
        return findOne(MAPPER_NAMESPACE + ".selectByMerchantCode", params);
    }

    /**
     * 设置已卖光和可售
     *
     * @param deal
     * @return
     */
    public int modifyOosStatusById(Deal deal) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", deal.getId());
        params.put("oosStatus", deal.getOosStatus());
        params.put("publishTime", new Date());
        params.put("updateTime", new Date());
        return this.update(MAPPER_NAMESPACE + ".modifyOosStatusById", params);
    }

    /**
     * 查询同一个商家的同一款商品
     *
     * @param merchantId
     * @param merchantSku
     * @return
     */
    public List<Deal> getByMerchantIdAndMerchantSku(Long merchantId, int merchantSku) {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", merchantId);
        params.put("merchantSku", merchantSku);
        return findAll(MAPPER_NAMESPACE + ".selectByMerchantIdAndMerchantSku", params);
    }

    /**
     * 获取要下单的商品信息
     *
     * @param id
     * @return
     */
    public Deal getInfoForPlaceOrder(long id) {
        Map<String, Object> params = new HashMap<>();
        Date nowDate = new Date();
        params.put("id", id);
        params.put("startTime", nowDate);
        params.put("endTime", nowDate);
        return findOne(MAPPER_NAMESPACE + ".selectForPlaceOrder", params);
    }

    /**
     * 根据skuId查询可以显示在前台的商品
     *
     * @param params
     * @return
     */
    public Deal getBySkuIdForShowOnView(Map<String, Object> params) {
        return findOne(MAPPER_NAMESPACE + ".selectBySkuIdForShowOnView", params);
    }

    /**
     * 查询最新发布的商品
     *
     * @param params
     * @return
     */
    public Deal getLatestPublishedDeal(Map<String, Object> params) {
        return findOne(MAPPER_NAMESPACE + ".selectLatestPublishedDeal", params);
    }

    /**
     * 下单减库存
     *
     * @param id
     * @param inventoryAmount   更新库存数量
     * @param vendibilityAmount 更新可购买数量
     * @return
     */
    public int updateForPlaceOrder(Long id, int inventoryAmount, int vendibilityAmount) {
        Map<String, Object> params = new HashMap<String, Object>();
        Date nowDate = new Date();
        params.put("id", id);
        params.put("startTime", nowDate);
        params.put("endTime", nowDate);
        params.put("inventoryAmount", inventoryAmount);
        params.put("vendibilityAmount", vendibilityAmount);
        return update(MAPPER_NAMESPACE + ".updateForPlaceOrder", params);
    }

}

package com.cmazxiaoma.groupon.deal.service;


import com.cmazxiaoma.common.constant.GlobalConstant;
import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.search.Condition;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.framework.util.ComputeUtil;
import com.cmazxiaoma.framework.util.DateUtil;
import com.cmazxiaoma.framework.util.image.ImageUtil;
import com.cmazxiaoma.groupon.deal.constant.DealConstant;
import com.cmazxiaoma.groupon.deal.dao.DealDAO;
import com.cmazxiaoma.groupon.deal.dao.DealDetailDAO;
import com.cmazxiaoma.groupon.deal.entity.Deal;
import com.cmazxiaoma.groupon.deal.entity.DealCategory;
import com.cmazxiaoma.groupon.deal.entity.DealDetail;
import com.cmazxiaoma.support.image.entity.ImageInfo;
import com.cmazxiaoma.support.image.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * DealService
 */
@Service
public class DealService {

    private static final Logger logger = LoggerFactory.getLogger(DealService.class);

    @Autowired
    private DealDAO dealDAO;

    @Autowired
    private DealDetailDAO dealDetailDAO;

    @Autowired
    private ImageService imageService;

    @Autowired
    private DealCategoryService dealCategoryService;


    /*********************************网站**********************************/

    /**
     * 根据SkuID查询商品信息,用于在网站详情页显示
     *
     * @param skuId skuId
     * @return 商品信息
     */
    public Deal getBySkuIdForDetailViewOnSite(Long skuId) {
        Map<String, Object> params = new HashMap<>();
        params.put("skuId", skuId);
        params.put("nowTime", new Date());
        return this.dealDAO.getBySkuIdForShowOnView(params);
    }

    /**
     * 根据DealID查询商品详情
     *
     * @param dealId DealId
     * @return 商品详情
     */
    public DealDetail getDetailByDealId(Long dealId) {
        return this.dealDetailDAO.getByDealId(dealId);
    }

    /**
     * 查询首页显示的商品
     *
     * @param areaId
     * @param categoryIds
     * @return
     */
    public List<Deal> getDealsForIndex(Long areaId, List<Long> categoryIds) {
        return dealDAO.getDealsForIndex(areaId, categoryIds, DealConstant.DEAL_PUBLISH_STATUS_PUBLISH);
    }

    /**
     * 搜索分页查询
     *
     * @param name
     * @param page
     * @param rows
     * @return
     */
    public PagingResult<Deal> searchByName(String name, int page, int rows) {
        return dealDAO.getPageDealsForSearch(name, page, rows);
    }


    /**
     * 查询在购物车显示的商品
     *
     * @param dealIds 商品ID
     * @return
     */
    public List<Deal> getDealsForCart(List<Long> dealIds) {
        List<Deal> result = dealDAO.getDealsForCart(dealIds);
        return result;
    }

    /*********************************后台**********************************/

    /**
     * 保存商品
     *
     * @param deal           商品信息
     * @param dealImageBytes 商品图片
     * @return 保存结果
     */
    public boolean saveDeal(Deal deal, byte[] dealImageBytes) {
        if (null == deal) {//商品为空直接返回
            return false;
        }
        //如果是复制的商品,图片不是必须的
        if (null != dealImageBytes && dealImageBytes.length > 0) {
            Long imageId = uploadDealImage(dealImageBytes);
            if (null == imageId || 0 == imageId) {
                logger.info("保存商品图片失败!");
                return false;
            }
            deal.setImageId(imageId);
        }
        initDeal(deal);//商品信息

        //如果同一个商家同一个sku(商家sku)存在,我们直接使用已有的,这是一种逻辑,也可重新生成
        deal.setSkuId(generateSkuIdSequence(deal.getMerchantId(), deal.getDealClass()));
        Date now = new Date();
        deal.setCreateTime(now);
        deal.setUpdateTime(now);
        deal.setPublishStatus(DealConstant.DEAL_PUBLISH_STATUS_NEW);

        int effectRows = dealDAO.saveDeal(deal);
        if (0 == effectRows) {
            logger.info("新建商品, 商品信息保存失败!");
            return false;
        }

        if (DealConstant.DEAL_CLASS_VIRTUAL != deal.getDealClass()) {
            DealDetail dealDetail = deal.getDealDetail();
            dealDetail.setDealId(deal.getId());
            dealDetail.setId(deal.getId());
            effectRows = dealDetailDAO.save(dealDetail);
            if (0 == effectRows) {
                logger.info("新建商品, 商品描述信息保存失败!");
                return false;
            }
        }
        return true;
    }

    public boolean updateDeal(Deal deal, byte[] dealImageBytes) {
        //商品为空直接返回
        if (null == deal) {
            return false;
        }

        //商品图片
        if (null != dealImageBytes && dealImageBytes.length > 0) {
            Long imageId = uploadDealImage(dealImageBytes);
            if (null == imageId || 0 == imageId) {
                logger.info("更新商品[id={}],图片上传失败!", deal.getId());
                return false;
            }
            deal.setImageId(imageId);
        }

        initDeal(deal);//商品信息
        deal.setUpdateTime(new Date());

        int effectRows = dealDAO.updateById(deal);
        if (0 == effectRows) {
            logger.info("更新商品[id={}]信息失败!", deal.getId());
            return false;
        }

        //通过dealId查询该商品的描述信息
        DealDetail existDealDetail = dealDetailDAO.getByDealId(deal.getId());

        //通过前台获取更新的商品描述信息
        DealDetail dealDetail = deal.getDealDetail();
        //注入dealId
        dealDetail.setId(deal.getId());

        if (null != existDealDetail) {
            //如果是虚拟商品,需要删除商品描述信息
            if (DealConstant.DEAL_CLASS_VIRTUAL == deal.getDealClass()) {
                effectRows = dealDetailDAO.deleteById(existDealDetail.getId());
                if (0 == effectRows) {
                    logger.info("更新商品[id={}]为虚拟商品,删除描述信息失败!", deal.getId());
                    return false;
                }
            } else {
                dealDetail.setId(existDealDetail.getId());
                effectRows = dealDetailDAO.updateById(dealDetail);
                if (0 == effectRows) {
                    logger.info("更新商品[id={}]为虚拟商品,更新描述信息失败!", deal.getId());
                    return false;
                }
            }
        } else {
            if (DealConstant.DEAL_CLASS_VIRTUAL != deal.getDealClass()) {
                effectRows = dealDetailDAO.save(dealDetail);
                if (0 == effectRows) {
                    logger.info("更新商品[id={}]为虚拟商品,新增描述信息失败!", deal.getId());
                    return false;
                }
            }
        }
        return true;
    }

    private void initDeal(Deal deal) {
        if (null == deal.getPublishTime()) {//如果发布时间为空
            deal.setPublishTime(DateUtil.getDefaultDateTime());
        }
        if (null == deal.getStartTime()) {//如果开始时间为空
            deal.setStartTime(deal.getPublishTime());
        }
        //如果不填结束时间,那么默认结束时间为7天后
        if (null == deal.getEndTime()) {//如果结束时间为空
            //FIXME 把两个if合并
            if (null != deal.getPublishTime() &&
                    deal.getPublishTime().compareTo(DateUtil.getDefaultDateTime()) > 0) {
                deal.setEndTime(DateUtil.getSevenDaysAfterOnSale());
            }
        }
        //如果是虚拟商品
        if (deal.getDealClass() == DealConstant.DEAL_CLASS_VIRTUAL) {
            deal.setMarketPrice(0);
            deal.setMerchantPrice(0);
            deal.setDealPrice(0);
            deal.setSettlementPrice(0);
            deal.setSettlementPriceMax(0);
            deal.setInventoryAmount(0);
            deal.setVendibilityAmount(0);
            deal.setMaxPurchaseCount(0);
            deal.setBonusPoints(0);
            deal.getDealDetail().setDealDetail("");
            deal.setDiscount(0);
            deal.setOosStatus(DealConstant.DEAL_OOS_STATUS_NO);
            return;
        }

        //默认设置为未卖光
        if (null == deal.getOosStatus() || 0 > deal.getOosStatus()) {
            deal.setOosStatus(DealConstant.DEAL_OOS_STATUS_NO);
        }
        //默认市场价格
        if (null == deal.getMarketPrice() || 0 > deal.getMarketPrice()) {
            deal.setMarketPrice(deal.getDealPrice());
        }
        //设置默认结算价格为商品价格
        if (null == deal.getSettlementPrice() || 0 > deal.getSettlementPrice()) {
            deal.setSettlementPrice(deal.getDealPrice());
        }
        //设置默认结最大算价格为结算价格
        if (null == deal.getSettlementPriceMax() || 0 > deal.getSettlementPriceMax()) {
            deal.setSettlementPriceMax(deal.getSettlementPrice());
        }
        //设置默认积分
        if (null != deal.getDealPrice() && 0 < deal.getDealPrice() && null == deal.getBonusPoints()) {
            deal.setBonusPoints(deal.getDealPrice() / 100);
        }
        //默认折扣
        if (null == deal.getDiscount() && null != deal.getDealPrice() && 0 < deal.getDealPrice()) {
            double discountDoubleValue = Double.valueOf(deal.getDealPrice() / 100) /
                    Double.valueOf(deal.getMarketPrice() / 100);
            double defaultDiscount = ComputeUtil.round(discountDoubleValue, 2, BigDecimal.ROUND_HALF_UP);
            deal.setDiscount((int) Math.ceil(defaultDiscount * 100));//Math.ceil大于入参的最小整数
        }
        //设置默认最大购买2
        if (null == deal.getMaxPurchaseCount() || 0 > deal.getMaxPurchaseCount()) {
            deal.setMaxPurchaseCount(DealConstant.DEAL_DEFAULT_MAX_PURCHASE_COUNT);
        } else if (Integer.valueOf(deal.getMaxPurchaseCount()) < DealConstant.DEAL_DEFAULT_MAX_PURCHASE_COUNT) {
            deal.setMaxPurchaseCount(DealConstant.DEAL_DEFAULT_MAX_PURCHASE_COUNT);
        }
        //默认可售数量与库存一致
        if (null == deal.getVendibilityAmount() || 0 > deal.getVendibilityAmount()) {
            deal.setVendibilityAmount(deal.getInventoryAmount());
        }
    }

    /*********************************混用**********************************/


    /**
     * 根据条件查询商品列表
     *
     * @param search
     * @return
     */
    public PagingResult<Deal> getDealList(Search search) {
        if (search == null) {
            return null;
        }

        List<Condition> conditionList = search.getConditionList();
        if (conditionList != null && conditionList.size() > 0) {
            for (Condition condition : conditionList) {
                if ("categoryId".equals(condition.getName())) {
                    Long categoryId = Long.valueOf(String.valueOf(condition.getValue()));
                    List<DealCategory> laterGenerationCategories = dealCategoryService
                            .getLaterGenerationCategories(categoryId);

                    List<Long> categoryIdList = new ArrayList<>();
                    categoryIdList.add(categoryId);

                    for (int i = 0; i < laterGenerationCategories.size(); i++) {
                        categoryIdList.add(laterGenerationCategories.get(i).getId());
                    }
                    condition.setValue(categoryIdList);
                }
            }
        }

        PagingResult<Deal> deals = dealDAO.getPageDeals(search);
        List<Deal> readyDeals = deals.getRows();

        DealDetail tempDeal;
        for (Deal deal : readyDeals) {
            // 设置过期标识
            if (null != deal.getEndTime() && deal.getEndTime().compareTo(new Date()) < 0) {
                deal.setDownShelf(true);
            }
            //FIXME 这个写法不好
            tempDeal = dealDetailDAO.getById(deal.getId());
            deal.setDealDetail(tempDeal);
        }
        return deals;
    }

    /**
     * 分页查询某个类别下的商品（包括其子类别）
     *
     * @param categoryIds 类别ID集合
     * @param page        页码
     * @param rows        每页数量
     * @return
     */
    public PagingResult<Deal> getDealsOfCategories(List<Long> categoryIds, Long areaId, int page, int rows) {
        Search search = new Search();
        List<Condition> conditionList = new ArrayList<>();
        // 发布状态为已发布
        conditionList.add(new Condition("publishStatus", DealConstant.DEAL_PUBLISH_STATUS_PUBLISH));

        conditionList.add(new Condition("categoryIdList", categoryIds));
        conditionList.add(new Condition("nowTime", new Date()));
        conditionList.add(new Condition("areaId", areaId));
        search.setConditionList(conditionList);
        search.setPage(page);
        search.setRows(rows);

        return dealDAO.getDealsOfCategories(search);
    }

    /**
     * 根据类别查询商品
     *
     * @param categoryId
     * @return
     */
    public List<Deal> getByCategoryId(Long categoryId) {
        return dealDAO.getByCategoryId(categoryId);
    }

    /**
     * 根据类别查询商品数量
     *
     * @param categoryId
     * @return
     */
    public long countByCategoryId(Long categoryId) {
        return dealDAO.countByCategoryId(categoryId);
    }

    /**
     * 修改商品信息状态根据商品ID
     *
     * @param deal
     * @return
     */
    public int modifyStatusById(Deal deal) {
        if (null == deal) {
            return -1;
        }
        return dealDAO.modifyStatusById(deal);
    }

    /**
     * 设置已卖光
     *
     * @param deal
     * @return
     */
    public int modifyOosStatusById(Deal deal) {
        if (null == deal) {
            return -1;
        }
        return dealDAO.modifyOosStatusById(deal);
    }

    /**
     * 根据ID返回商品信息
     *
     * @param id
     * @return
     */
    public Deal getById(Long id) {
        if (null == id) {
            return new Deal();
        }
        Deal deal = dealDAO.getById(id);
        DealDetail dealDetail = null;

        if (null != deal) {
            dealDetail = dealDetailDAO.getById(deal.getId());
        } else {
            deal = new Deal();
            dealDetail = new DealDetail();
        }
        deal.setDealDetail(dealDetail);
        return deal;
    }

    /**
     * 保存商品时检查是否已经存在
     *
     * @return
     */
    public Deal getBySkuId(Long skuId) {
        return dealDAO.findBySkuId(skuId);
    }


    public List<Deal> getBySkuIds(List<Long> skuIds) {
        return dealDAO.findBySkuIds(skuIds);
    }

    /**
     * 生成商品SkuId
     *
     * @return
     */
    public Long generateSkuIdSequence(Long merchantId, Integer dealClass) {
        if (null == merchantId || null == dealClass) {
            return null;
        }

        // 一位商家编号 + 一位商品类型 + 八位商品递增Id
        // StringBuilder generateId = new StringBuilder("");
        // generateId.append(merchantId).append(dealClass);

        ReentrantLock lock = new ReentrantLock();
        // 当前最大SkuId
        lock.lock();
        Deal maxSkuNow = dealDAO.getMaxSkuId();
        Long maxSkuId = 0L;

        if (null != maxSkuNow) {
            maxSkuId = maxSkuNow.getSkuId() + 1;
        } else {
            maxSkuId = 1L;
        }
        lock.unlock();
        return maxSkuId;
    }

    /**
     * 上传商品图片
     *
     * @param imgBytes 图片数据
     * @return
     */
    public Long uploadDealImage(byte[] imgBytes) {
        FileOutputStream fos = null;
        long imgId = 0;
        try {
            String sourceFileAbsolutePath = "";// 图片文件绝对路径
            String sourceFileRelativePath = "";// 图片文件相对路径
            ImageInfo image = new ImageInfo();// 将图片信息存入数据库
            imgId = imageService.addImage(image);
            if (imgId > 0) {
                sourceFileAbsolutePath = ImageUtil.getImageSourceFileAbsolutePath(imgId);
                sourceFileRelativePath = ImageUtil.getImageSourceFileRelativePath(imgId);
                File sourceFileDir = new File(sourceFileAbsolutePath).getParentFile();// 将图片写入硬盘
                if (!sourceFileDir.exists()) {// 若图片源文件夹不存在则新建
                    sourceFileDir.mkdirs();
                }
                fos = new FileOutputStream(sourceFileAbsolutePath);
                fos.write(imgBytes);
                fos.flush();
                logger.info("上传商品图片[id=" + imgId + "]到目录：" + sourceFileAbsolutePath);
                ImageUtil.generateImage(DealConstant.PICTURE_MODULE_DEAL, imgId, sourceFileAbsolutePath);//生成各个尺寸的图片
                HashMap<String, String> imageInfo = ImageUtil.getBaseInfo(sourceFileAbsolutePath);// 完善图片信息
                image.setId(imgId);
                image.setHeight(Integer.valueOf(imageInfo.get("Height")));
                image.setWidth(Integer.valueOf(imageInfo.get("Width")));
                image.setSourcePath(sourceFileRelativePath);
                imageService.updateImage(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
        return imgId;
    }

    /**
     * 下单减库存
     *
     * @param deal   商品信息
     * @param amount 删减数量
     * @return
     */
    public int reduceInventory(Deal deal, int amount) {
        Integer updateCount = 0;
        if (deal != null) {
            updateCount = dealDAO.updateForPlaceOrder(deal.getId(),
                    deal.getInventoryAmount() - amount,
                    deal.getVendibilityAmount() - amount);
        }
        return updateCount;
    }

    /**
     * 下单时验证商品信息合法性
     *
     * @param deal 商品信息
     * @return 0：合法，其他非法
     */
    public Integer verifyDealForOrder(Deal deal, int userHasBoughtAmount) {
        // 订单商品信息非法
        if (deal == null) {
            return 3;
        }

        // 库存不够
        if (deal.getInventoryAmount() == 0) {
            return 4;
        }

        // 库存不够，已经售罄
        if (deal.getVendibilityAmount() == 0 || deal.getOosStatus() == GlobalConstant.YES) {
            return 4;
        }
        // 超出最大可购买数量
        if (deal.getMaxPurchaseCount() <= userHasBoughtAmount) {
            return 7;
        }
        return 0;
    }

}

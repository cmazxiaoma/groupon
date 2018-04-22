package com.cmazxiaoma.groupon.deal.entity;

import com.cmazxiaoma.framework.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/3/11 10:32
 */
@Data
public class Deal extends BaseEntity {

    /**
     * 地区id
     */
    private Long areaId;

    /**
     * 地区
     */
    private String areaName;

    /**
     * 商品编码id
     */
    private Long skuId;

    /**
     * 是否虚拟商品
     */
    private Integer dealClass;

    /**
     * 商家id
     */
    private Long merchantId;

    /**
     * 商家SKU 编号
     */
    private Integer merchantSku;

    /**
     * 商品标题
     */
    private String dealTitle;

    /**
     * 商品价格
     */
    private Integer dealPrice;

    /**
     * 进货价
     */
    private Integer merchantPrice;

    /**
     * 市场价
     */
    private Integer marketPrice;

    /**
     * 最大可接受结算价格
     */
    private Integer settlementPriceMax;

    /**
     * 结算价
     */
    private Integer settlementPrice;

    /**
     * 折扣
     */
    private Integer discount;

    /**
     * 积分
     */
    private Integer bonusPoints;

    /**
     * 商品类型
     */
    private Integer dealType;

    /**
     * 对应商品图片
     */
    private Long imageId;

    /**
     * 对应商品图片路径
     */
    private String imageGenPath;

    /**
     * 商品优先级
     */
    private Integer dealLevel;

    /**
     * 最大购买数量
     */
    private Integer maxPurchaseCount;

    /**
     * 发布状态
     */
    private Integer publishStatus;

    /**
     * 商品库存数量,库存量包括可售量和未取得手续，暂时不能出售的，
     */
    private Integer inventoryAmount;

    /**
     * 商品可售数量,可售量是指已经办好预售手续，能够出售的
     */
    private Integer vendibilityAmount;

    /**
     * 是否售空标识
     */
    private Integer oosStatus;

    /**
     * 商品销售开始时间
     */
    private Date startTime;

    /**
     * 商品销售结束时间
     */
    private Date endTime;

    /**
     * 商品上架时间
     */
    private Date publishTime;

    /**
     * 商户编码
     */
    private String merchantCode;

    /**
     * 商品创建时间
     */
    private Date createTime;

    /**
     * 商品更新时间
     */
    private Date updateTime;

    /**
     * 商品对应描述
     */
    private DealDetail dealDetail;

    /**
     * 是否下架的标识
     */
    private Boolean downShelf;

    /**
     * 商品类型id
     */
    private Integer categoryId;

    public boolean isStart() {
        return new Date().after(this.getStartTime());
    }

    public boolean isEnd() {
        return new Date().after(this.getEndTime());
    }
}

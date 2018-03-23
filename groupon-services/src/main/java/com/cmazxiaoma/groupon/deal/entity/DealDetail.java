package com.cmazxiaoma.groupon.deal.entity;

import lombok.Data;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/3/11 10:33
 */
@Data
public class DealDetail {

    private Long dealId;

    /**
     * 商品详细描述信息
     */
    private String dealDetail;
}

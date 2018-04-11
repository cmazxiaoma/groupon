package com.cmazxiaoma.groupon.deal.entity;

import com.cmazxiaoma.framework.base.entity.BaseEntity;
import lombok.Data;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/3/11 10:33
 */
@Data
public class DealDetail extends BaseEntity {

    private Long dealId;

    /**
     * 商品详细描述信息
     */
    private String dealDetail;
}

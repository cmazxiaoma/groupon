package com.cmazxiaoma.site.web.site.dto;

import com.cmazxiaoma.groupon.deal.entity.Deal;
import com.cmazxiaoma.support.favorite.entity.Favorite;
import lombok.Getter;
import lombok.Setter;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: 个人收藏显示
 * @date 2018/4/4 9:44
 */
public class FavoriteDTO {

    @Getter
    @Setter
    private Favorite favorite;

    @Getter
    @Setter
    private Deal deal;

    public FavoriteDTO(Favorite favorite, Deal deal) {
        this.favorite = favorite;
        this.deal = deal;
    }

}

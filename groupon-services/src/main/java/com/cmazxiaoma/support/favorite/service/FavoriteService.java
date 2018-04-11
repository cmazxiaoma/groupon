package com.cmazxiaoma.support.favorite.service;

import com.cmazxiaoma.support.favorite.dao.FavoriteDAO;
import com.cmazxiaoma.support.favorite.entity.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * FavoriteService
 */
@Service
public class FavoriteService {

    @Autowired
    private FavoriteDAO favoriteDAO;

    /**********************网站**************************/

    /**
     * 根据用户ID查询收藏
     *
     * @param userId
     * @return
     */
    public List<Favorite> getByUserId(Long userId) {
        return this.favoriteDAO.findByUserId(userId);
    }

    /**
     * 保存
     *
     * @param favorite
     */
    public void save(Favorite favorite) {
        Date now = new Date();
        favorite.setCreateTime(now);
        favorite.setUpdateTime(now);
        this.favoriteDAO.save(favorite);
    }

    /**
     * 根据ID删除收藏
     *
     * @param id
     */
    public void deleteById(Long id) {
        this.favoriteDAO.deleteById(id);
    }

    /**********************后台**************************/

//    /**
//     * 分页查询
//     * @param search
//     * @return
//     */
//    public PagingResult<Favorite> getPage(Search search) {
//        return this.favoriteDAO.findPage(search);
//    }

    /**********************混用**************************/

}

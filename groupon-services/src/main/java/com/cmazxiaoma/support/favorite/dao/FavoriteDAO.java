package com.cmazxiaoma.support.favorite.dao;

import com.cmazxiaoma.framework.common.persistence.BaseMybatisDAO;
import com.cmazxiaoma.support.favorite.entity.Favorite;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收藏
 */
@Repository
public class FavoriteDAO extends BaseMybatisDAO {

    private final String MAPPER_NAMESPACE = "com.tortuousroad.support.favorite.entity.FavoriteMapper";

    /**
     * 保存
     *
     * @param favorite
     */
    public void save(Favorite favorite) {
        try {
            super.save(MAPPER_NAMESPACE + ".insert", favorite);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据用户ID查询
     *
     * @param userId
     * @return
     */
    public List<Favorite> findByUserId(Long userId) {
        return super.findAll(MAPPER_NAMESPACE + ".selectByUserId", userId);
    }

    /**
     * 根据ID删除
     *
     * @param id
     */
    public void deleteById(Long id) {
        super.delete(MAPPER_NAMESPACE + ".deleteById", id);
    }

}
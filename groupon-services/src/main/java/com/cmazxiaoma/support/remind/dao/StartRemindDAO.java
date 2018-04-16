package com.cmazxiaoma.support.remind.dao;

import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.persistence.BaseMybatisDAO;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.support.remind.entity.StartRemind;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 开团提醒
 */
@Repository
public class StartRemindDAO extends BaseMybatisDAO {

    private final String MAPPER_NAMESPACE = "com.cmazxiaoma.support.remind.entity.StartRemindMapper";

    /**
     * 保存
     *
     * @param startRemind
     */
    public void save(StartRemind startRemind) {
        super.save(MAPPER_NAMESPACE + ".insertSelective", startRemind);
    }

    /**
     * 根据用户ID查询
     *
     * @param userId
     * @return
     */
    public List<StartRemind> getByUserId(Long userId) {
        return super.findAll(MAPPER_NAMESPACE + ".selectByUserId", userId);
    }

    /**
     * 根据用户ID和SkuID查询
     *
     * @param userId
     * @return
     */
    public StartRemind findByUserIdAndSkuId(Long userId, Long skuId) {
        Map<String, Long> params = new HashMap<>();
        params.put("userId", userId);
        params.put("skuId", skuId);
        return super.findOne(MAPPER_NAMESPACE + ".selectByUserIdAndSkuId", params);
    }

    /**
     * 分页查询
     *
     * @param search
     * @return
     */
    public PagingResult<StartRemind> findPage(Search search) {
        return super.findForPage(MAPPER_NAMESPACE + ".countPage", MAPPER_NAMESPACE + ".selectPage", search);
    }

    public List<StartRemind> getByTimeInterval(Date interval) {
        return super.findAll(MAPPER_NAMESPACE + ".selectByTimeInterval", interval);
    }

    public void deleteById(Long id) {
        super.delete(MAPPER_NAMESPACE + ".deleteById", id);
    }

    public List<StartRemind> listAll() {
        return super.findAll(MAPPER_NAMESPACE + ".listAll");
    }

}

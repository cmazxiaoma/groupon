package com.cmazxiaoma.groupon.merchant.dao;

import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.persistence.BaseMybatisDAO;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.groupon.merchant.entity.Merchant;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MerchantDAO
 */
@Repository
public class MerchantDAO extends BaseMybatisDAO {

    private final String MAPPER_NAMESPACE = "com.cmazxiaoma.groupon.merchant.entity.MerchantMapper";

    /**
     * 分页查询商家信息
     *
     * @param search
     * @return
     */
    public PagingResult<Merchant> findMerchantForPage(Search search) {
        return this.findForPage(MAPPER_NAMESPACE + ".countPageMerchant", MAPPER_NAMESPACE + ".selectPageMerchant", search);
    }

    /**
     * 查询所有商家信息
     *
     * @return
     */
    public List<Merchant> findAll() {
        return findAll(MAPPER_NAMESPACE + ".selectAll");
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    public Merchant getById(Long id) {
        return findOne(MAPPER_NAMESPACE + ".selectById", id);
    }

    /**
     * 保存
     *
     * @param merchant
     * @return
     */
    public int save(Merchant merchant) {
        return save(MAPPER_NAMESPACE + ".insertSelective", merchant);
    }

    public int update(Merchant merchant) {
        return update(MAPPER_NAMESPACE + ".updateById", merchant);
    }

}

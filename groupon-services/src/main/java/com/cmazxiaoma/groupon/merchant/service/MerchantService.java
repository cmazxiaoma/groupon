package com.cmazxiaoma.groupon.merchant.service;

import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.groupon.merchant.dao.MerchantDAO;
import com.cmazxiaoma.groupon.merchant.entity.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * MerchantService
 */
@Service
public class MerchantService {

    @Autowired
    private MerchantDAO merchantDAO;

    /*********************************网站**********************************/


    /*********************************后台**********************************/

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    public Merchant getById(Long id) {
        return merchantDAO.getById(id);
    }

    /**
     * 分页查询商家信息
     *
     * @param search
     * @return
     */
    public PagingResult<Merchant> findMerchantForPage(Search search) {
        return merchantDAO.findMerchantForPage(search);
    }

    /**
     * 查询所有商家信息
     *
     * @return
     */
    public List<Merchant> findAll() {
        return merchantDAO.findAll();
    }

    public void save(Merchant merchant) {
        if (null == merchant.getImageId()) {
            merchant.setImageId(0L);
        }
        merchant.setStatus(1);
        Date now = new Date();
        merchant.setCreateTime(now);
        merchant.setUpdateTime(now);
        merchantDAO.save(merchant);
    }

    public boolean update(Merchant merchant) {
        Date now = new Date();
        merchant.setUpdateTime(now);
        int result = merchantDAO.update(merchant);
        return result > 0;
    }

    /*********************************混用**********************************/
}

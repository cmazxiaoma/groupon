package com.cmazxiaoma.support.address.service;

import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.support.address.dao.AddressDAO;
import com.cmazxiaoma.support.address.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * AddressService
 */
@Service
public class AddressService {

    @Autowired
    private AddressDAO addressDAO;

    /**********************网站**************************/


    /**********************后台**************************/


    /**********************混用**************************/

    /**
     * 保存
     *
     * @param address
     */
    public void save(Address address) {
        Date now = new Date();
        address.setCreateTime(now);
        address.setUpdateTime(now);
        this.addressDAO.save(address);
    }

    /**
     * 根据用户ID查询全部
     *
     * @return
     */
    public List<Address> getByUserId(Long userId) {
        return this.addressDAO.findByUserId(userId);
    }

    /**
     * 根据ID查询
     *
     * @return
     */
    public Address getById(Long id) {
        return this.addressDAO.findById(id);
    }

    /**
     * 分页查询
     *
     * @param search
     * @return
     */
    public PagingResult<Address> getPage(Search search) {
        return this.addressDAO.findPage(search);
    }

}

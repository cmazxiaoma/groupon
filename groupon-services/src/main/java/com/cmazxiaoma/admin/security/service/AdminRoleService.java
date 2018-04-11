package com.cmazxiaoma.admin.security.service;

import com.cmazxiaoma.admin.security.dao.AdminRoleDAO;
import com.cmazxiaoma.admin.security.dao.AdminRoleFunctionDAO;
import com.cmazxiaoma.admin.security.entity.AdminRole;
import com.cmazxiaoma.admin.security.entity.AdminRoleFunction;
import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class AdminRoleService {

    @Autowired
    private AdminRoleDAO adminRoleDAO;

    @Autowired
    private AdminRoleFunctionDAO adminRoleFunctionDAO;

    /**
     * 分页查询角色信息
     *
     * @param search
     * @return
     */
    public PagingResult<AdminRole> getAdminRole(Search search) {
        return this.adminRoleDAO.findPage(search);
    }

    public List<AdminRole> getAdminRoleByIds(List<Long> idList) {
        return this.adminRoleDAO.findByIds(idList);
    }

    /**
     * 新建角色
     *
     * @param adminRole
     */
    public void saveAdminRole(AdminRole adminRole, List<Long> funcIds) {
        List<AdminRoleFunction> functions = new ArrayList<>();
        Date now = new Date();
        funcIds.forEach(id -> functions.add(new AdminRoleFunction(adminRole.getId(), id, now, now)));
        this.adminRoleFunctionDAO.saveBatch(functions);
        this.adminRoleDAO.save(adminRole);
    }

    /**
     * 更新角色
     *
     * @param adminRole
     */
    public void updateAdminRole(AdminRole adminRole, List<Long> funcIds) {
        this.adminRoleFunctionDAO.deleteByRoleId(adminRole.getId());
        List<AdminRoleFunction> functions = new ArrayList<>();
        Date now = new Date();
        funcIds.forEach(id -> functions.add(new AdminRoleFunction(adminRole.getId(), id, now, now)));
        this.adminRoleFunctionDAO.saveBatch(functions);
        this.adminRoleDAO.update(adminRole);
    }


    public List<AdminRoleFunction> getAdminRoleFunctionsByRoleId(Long roleId) {
        return adminRoleFunctionDAO.findByRoleId(roleId);
    }

    public List<AdminRoleFunction> getAdminRoleFunctionsByRoleIds(List<Long> roleIds) {
        return adminRoleFunctionDAO.findByRoleIds(roleIds);
    }

}

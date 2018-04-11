package com.cmazxiaoma.admin.security.dao;

import com.cmazxiaoma.admin.security.entity.AdminRoleFunction;
import com.cmazxiaoma.framework.common.persistence.BaseMybatisDAO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AdminRoleFunctionDAO
 */
@Repository
public class AdminRoleFunctionDAO extends BaseMybatisDAO {

    private final String MAPPER_NAMESPACE = "com.cmazxiaoma.admin.security.entity.AdminRoleFunctionMapper";

    /**
     * @param adminRoleFunctions
     */
    public void saveBatch(List<AdminRoleFunction> adminRoleFunctions) {
        super.saveBatch(MAPPER_NAMESPACE + ".batchInsertAdminUserRoles", adminRoleFunctions);
    }

    /**
     * @param roleId
     */
    public void deleteByRoleId(Long roleId) {
        super.delete(MAPPER_NAMESPACE + ".deleteByRoleId", roleId);
    }

    /**
     * @param roleId
     * @return
     */
    public List<AdminRoleFunction> findByRoleId(Long roleId) {
        return super.findAll(MAPPER_NAMESPACE + ".findByRoleId", roleId);
    }

    /**
     * @param roleIds
     * @return
     */
    public List<AdminRoleFunction> findByRoleIds(List<Long> roleIds) {
        Map<String, Object> params = new HashMap<>();
        params.put("roleIds", roleIds);
        return super.findAll(MAPPER_NAMESPACE + ".findByRoleIds", params);
    }

}

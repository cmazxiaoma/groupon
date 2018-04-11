package com.cmazxiaoma.admin.security.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cmazxiaoma.admin.security.entity.AdminUser;
import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.persistence.BaseMybatisDAO;
import com.cmazxiaoma.framework.common.search.Search;
import org.springframework.stereotype.Repository;

/**
 * AdminUser
 */
@Repository
public class AdminUserDAO extends BaseMybatisDAO {

    private static final String MAPPER_NAMESPACE = "com.cmazxiaoma.admin.security.entity.AdminUserMapper";

    /**
     * 根据用户名查询AdminUser
     *
     * @param username
     * @return
     */
    public AdminUser getUserByUsername(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", username);
        return super.findOne(MAPPER_NAMESPACE + ".selectAdminUserByName", params);
    }

    /**
     * 分页查询AdminUser
     *
     * @param search
     * @return
     */
    public PagingResult<AdminUser> findPage(Search search) {
        return super.findForPage(MAPPER_NAMESPACE + ".countPage", MAPPER_NAMESPACE + ".findPage", search);
    }

    /**
     * 保存
     *
     * @param adminUser
     */
    public void save(AdminUser adminUser) {
        super.save(MAPPER_NAMESPACE + ".insertAdminUser", adminUser);
    }

    /**
     * 更新
     *
     * @param adminUser
     */
    public void update(AdminUser adminUser) {
        super.update(MAPPER_NAMESPACE + ".update", adminUser);
    }

    /**
     * 根据ID查询AdminUser
     *
     * @param adminUserId
     */
    public void deleteById(Long adminUserId) {
        super.delete(MAPPER_NAMESPACE + ".deleteById", adminUserId);
    }

    /**
     * 查询全部AdminUser
     *
     * @return
     */
    public List<AdminUser> getAll() {
        return super.findAll(MAPPER_NAMESPACE + ".selectAll");
    }
}
package com.cmazxiaoma.user.dao;

import java.util.HashMap;
import java.util.Map;

import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.persistence.BaseMybatisDAO;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.user.entity.User;
import org.springframework.stereotype.Repository;


/**
 * UserDAO
 */
@Repository
public class UserDAO extends BaseMybatisDAO {
    /**
     * MyBatis的Mapper映射文件命名空间
     */
    private static final String MAPPER_NAMESPACE = "com.cmazxiaoma.user.entity.UserMapper";

    /**
     * 通过ID获取用户
     *
     * @param id
     * @return
     */
    public User getById(Long id) {
        return findOne(MAPPER_NAMESPACE + ".selectByPrimaryKey", id);
    }

    /**
     * 通过用户名查询用户
     *
     * @param name 用户名
     * @return
     */
    public User getByName(String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        return findOne(MAPPER_NAMESPACE + ".selectByName", params);
    }

    /**
     * 保存用户信息
     *
     * @param user 用户
     * @return id
     */
    public int save(User user) {
        return save(MAPPER_NAMESPACE + ".insertSelective", user);
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    public int updateById(User user) {
        return update(MAPPER_NAMESPACE + ".updateByPrimaryKeySelective", user);
    }


    /**
     * @param search
     * @return
     */
    public PagingResult<User> findUserForPage(Search search) {
        return this.findForPage(MAPPER_NAMESPACE + ".countPageUsers", MAPPER_NAMESPACE + ".selectPageUsers", search);
    }

}
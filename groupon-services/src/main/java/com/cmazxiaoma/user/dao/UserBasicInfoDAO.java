package com.cmazxiaoma.user.dao;

import com.cmazxiaoma.framework.common.persistence.BaseMybatisDAO;
import com.cmazxiaoma.user.entity.UserBasicInfo;
import org.springframework.stereotype.Repository;

/**
 * UserBasicInfoDAO
 */
@Repository
public class UserBasicInfoDAO extends BaseMybatisDAO {
    /**
     * MyBatis的Mapper映射文件命名空间
     */
    private static final String MAPPER_NAMESPACE = "com.cmazxiaoma.user.entity.UserBasicInfoMapper";

    /**
     * 通过ID获取用户基本信息
     *
     * @param id
     * @return
     */
    public UserBasicInfo getById(Long id) {
        return findOne(MAPPER_NAMESPACE + ".selectByPrimaryKey", id);
    }

    /**
     * 保存用户基本信息
     *
     * @param info 用户
     * @return id
     */
    public int save(UserBasicInfo info) {
        return save(MAPPER_NAMESPACE + ".insertSelective", info);
    }

    /**
     * 更新用户基本信息
     *
     * @param info
     * @return
     */
    public int updateById(UserBasicInfo info) {
        return update(MAPPER_NAMESPACE + ".updateByPrimaryKeySelective", info);
    }

}
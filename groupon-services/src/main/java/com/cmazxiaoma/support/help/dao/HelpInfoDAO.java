package com.cmazxiaoma.support.help.dao;

import java.util.List;

import com.cmazxiaoma.framework.common.persistence.BaseMybatisDAO;
import com.cmazxiaoma.support.help.entity.HelpInfo;
import org.springframework.stereotype.Repository;

/**
 * 帮助信息
 */
@Repository
public class HelpInfoDAO extends BaseMybatisDAO {

    private static final String MAPPER_NAMESPACE = "com.cmazxiaoma.support.help.entity.HelpInfoMapper";

    /**
     * 查询用于在页面显示的帮助信息
     *
     * @return
     */
    public List<HelpInfo> getListForShow() {
        return findAll(MAPPER_NAMESPACE + ".selectListForShow");
    }
}
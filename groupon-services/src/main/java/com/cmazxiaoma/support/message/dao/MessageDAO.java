package com.cmazxiaoma.support.message.dao;

import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.persistence.BaseMybatisDAO;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.support.message.entity.Message;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 站内消息
 */
@Repository
public class MessageDAO extends BaseMybatisDAO {

    private final String MAPPER_NAMESPACE = "com.cmazxiaoma.support.message.entity.MessageMapper";

    /**
     * 保存
     *
     * @param message
     */
    public void save(Message message) {
        super.save(MAPPER_NAMESPACE + ".insertSelective", message);
    }

    /**
     * 根据用户ID查询
     *
     * @param userId
     * @return
     */
    public List<Message> getByUserId(Long userId) {
        return super.findAll(MAPPER_NAMESPACE + ".selectByUserId", userId);
    }

    /**
     * 更新消息状态
     *
     * @param id
     */
    public void updateReadStatus(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        super.update(MAPPER_NAMESPACE + ".updateReadStatus", params);
    }

    /**
     * 分页查询
     *
     * @param search
     * @return
     */
    public PagingResult<Message> findPage(Search search) {
        return super.findForPage(MAPPER_NAMESPACE + ".countPage", MAPPER_NAMESPACE + ".selectPage", search);
    }

}

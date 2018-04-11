package com.cmazxiaoma.support.message.service;

import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.support.message.dao.MessageDAO;
import com.cmazxiaoma.support.message.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MessageService
 */
@Service
public class MessageService {

    @Autowired
    private MessageDAO messageDAO;

    /**********************网站**************************/

    /**
     * 根据用户ID查询消息
     *
     * @param userId
     * @return
     */
    public List<Message> getByUserId(Long userId) {
        return this.messageDAO.getByUserId(userId);
    }

    /**
     * 更新消息状态
     *
     * @return
     */
    public void updateReadStatus(Long id) {
        this.messageDAO.updateReadStatus(id);
    }

    /**
     * 分页查询
     *
     * @param search
     * @return
     */
    public PagingResult<Message> getPage(Search search) {
        return this.messageDAO.findPage(search);
    }

    /**********************后台**************************/
    /**
     * 保存
     *
     * @param message
     */
    public void save(Message message) {
        this.messageDAO.save(message);
    }

    /**********************混用**************************/

}

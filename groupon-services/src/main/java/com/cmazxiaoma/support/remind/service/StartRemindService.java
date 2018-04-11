package com.cmazxiaoma.support.remind.service;

import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.support.remind.dao.StartRemindDAO;
import com.cmazxiaoma.support.remind.entity.StartRemind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * RemindService
 */
@Service
public class StartRemindService {

    @Autowired
    private StartRemindDAO startRemindDAO;

    /**********************网站**************************/

    /**
     * 根据用户ID查询消息
     *
     * @param userId
     * @return
     */
    public List<StartRemind> getByUserId(Long userId) {
        return this.startRemindDAO.getByUserId(userId);
    }

    /**
     * 分页查询
     *
     * @param search
     * @return
     */
    public PagingResult<StartRemind> getPage(Search search) {
        return this.startRemindDAO.findPage(search);
    }

    /**********************后台**************************/

    /**
     * 保存
     *
     * @param startRemind
     */
    public void save(StartRemind startRemind) {
        if (null == this.startRemindDAO.findByUserIdAndSkuId(startRemind.getUserId(), startRemind.getDealSkuId())) {
            Date now = new Date();
            startRemind.setCreateTime(now);
            startRemind.setUpdateTime(now);
            this.startRemindDAO.save(startRemind);
        }
    }

    /**
     * 提前一天提醒
     *
     * @return
     */
    public List<StartRemind> getByTimeInterval() {
        //24小时正负1分钟范围内
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        return this.startRemindDAO.getByTimeInterval(calendar.getTime());
    }

    public void deleteById(Long id) {
        this.startRemindDAO.deleteById(id);
    }

    /**********************混用**************************/

}

package com.cmazxiaoma.admin.timer;


import com.cmazxiaoma.framework.base.context.SpringApplicationContext;
import com.cmazxiaoma.support.message.entity.Message;
import com.cmazxiaoma.support.message.service.MessageService;
import com.cmazxiaoma.support.remind.entity.StartRemind;
import com.cmazxiaoma.support.remind.service.StartRemindService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 开团提醒定时
 */
@Component
@Slf4j
public class GrouponStartRemindTimer {

    @Autowired
    private StartRemindService startRemindService;

    @Autowired
    private MessageService messageService;

    @PostConstruct
    public void start() {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

        pool.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                List<StartRemind> startReminds = startRemindService.getByTimeInterval();
                startReminds.forEach(startRemind -> {
                    log.info("站内通知用户" + startRemind.getUserId());
                    Message message = new Message();
                    message.setUserId(startRemind.getUserId());
                    message.setTitle("开团提醒");
                    message.setReaded(0);
                    message.setDealSkuId(startRemind.getDealSkuId());
                    message.setCreateTime(new Date());
                    message.setUpdateTime(new Date());
                    message.setContent(startRemind.getDealTitle() + "将在24小时后开团.");
                    //FIXME 循环中操作数据库性能低,需要改进
                    messageService.save(message);
//                    startRemindService.deleteById(startRemind.getId());
                });
            }
        }, 1000, 10 * 60 * 1000, TimeUnit.MILLISECONDS);
    }

}

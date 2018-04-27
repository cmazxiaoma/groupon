package com.cmazxiaoma.site.web.site;

import com.cmazxiaoma.support.message.entity.Message;
import com.cmazxiaoma.support.message.service.MessageService;
import com.cmazxiaoma.support.remind.entity.StartRemind;
import com.cmazxiaoma.support.remind.service.StartRemindService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/4/16 21:22
 */
@Component
@Slf4j
public class MessageTimer {

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
                List<StartRemind> startRemindList = startRemindService.listAll();
                log.info("startRemindList={}", startRemindList);
                startRemindList.forEach(startRemind -> {
                    log.info("站内通知用户" + startRemind.getUserId());
                    System.out.println("站内通知用户" + startRemind.getUserId());
                    Message message = new Message();
                    message.setUserId(startRemind.getUserId());
                    message.setDealSkuId(startRemind.getDealSkuId());
                    message.setTitle("开团提醒");
                    message.setContent(startRemind.getDealTitle() + "快要开团了");
                    message.setReaded(0);
                    message.setCreateTime(new Date());
                    message.setUpdateTime(new Date());
                    messageService.save(message);
//                    startRemindService.deleteById(startRemind.getId());
                });
            }
        }, 1000, 10 * 60 * 1000, TimeUnit.MILLISECONDS);
    }
}

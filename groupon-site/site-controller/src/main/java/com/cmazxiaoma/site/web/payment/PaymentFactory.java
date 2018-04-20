package com.cmazxiaoma.site.web.payment;


import com.cmazxiaoma.framework.base.context.SpringApplicationContext;
import com.cmazxiaoma.groupon.order.constant.OrderConstant;
import com.cmazxiaoma.site.web.payment.alipay.Alipay;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付工厂类
 */
public class PaymentFactory {

    /**
     * 根据类型创建支付实现类
     *
     * @param type
     * @return
     */
    public static Payment createPayment(int type) {
        Map<String, Payment> beanMap = SpringApplicationContext.getBeansOfType(Payment.class);
        //1.把beanMap转成key为整型的payType,value为Payment实例的新Map,
        // 需要在Payment的子类上加@Component注解以便Spring的Bean容器统一管理
        //2.和上面的if...else...一样,遍历beanMap的values
        Map<Integer, Payment> payTypeMap = new HashMap<>();
        beanMap.values().forEach(payment -> payTypeMap.put(payment.getType(), payment));
        return payTypeMap.get(type);
    }
}

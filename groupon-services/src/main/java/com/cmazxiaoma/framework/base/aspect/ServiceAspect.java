package com.cmazxiaoma.framework.base.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: Service中方法调用统计
 * @date 2018/3/10 23:04
 */
@Component
@Aspect
@Slf4j
public class ServiceAspect {

    @Around(value = "execution(public * com.cmazxiaoma..service.*Service.*(..))")
    public Object aroundServiceMethod(final ProceedingJoinPoint pjp) throws Throwable {
        Object returnVal = null;
        long start = System.currentTimeMillis();
        try {
            for (Object obj : pjp.getArgs()) {
                log.info(obj.toString());
            }
            returnVal = pjp.proceed();
            log.info("S " + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName()
                    + " ：" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("F " + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName()
                    + " ：" + (System.currentTimeMillis() - start) + "ms");
        }
        return returnVal;
    }
}

package com.cmazxiaoma.framework.base.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Objects;

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

    /**
     * 记录方法耗时  pjp.getSignature返回被增强后方法的信息, getDeclaringTypeName返回方法所在的包名和类名
     * getName()返回方法名
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around(value = "execution(public * com.cmazxiaoma..service.*Service.*(..))")
    public Object aroundServiceMethod(final ProceedingJoinPoint pjp) throws Throwable {
        Object returnVal = null;
        long start = System.currentTimeMillis();
        try {
            for (Object obj : pjp.getArgs()) {
                //要考虑参数是否为null的情况
                String args = Objects.isNull(obj) ? null : obj.toString();
                log.info(args);
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

    @AfterThrowing(throwing="ex", pointcut = "execution(public * com.cmazxiaoma..service.*Service.*(..))")
    public void afterThrowing(Throwable ex) {
        log.error("service层发生错误={}", ex);
    }
}

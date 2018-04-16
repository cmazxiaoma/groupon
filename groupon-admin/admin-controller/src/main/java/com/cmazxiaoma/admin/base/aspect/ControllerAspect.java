package com.cmazxiaoma.admin.base.aspect;

import com.cmazxiaoma.admin.base.controller.BaseAdminController;
import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.search.Search;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Controller中方法拦截
 */
@Component
@Aspect
@Slf4j
public class ControllerAspect {

    /**
     * 给相应的Controller方法执行完毕，生成按钮
     * getTarget()获取目标对象信息
     * @param pjp
     * @return
     */
    @Around(value = "execution(public * com.cmazxiaoma..controller.*Controller.*(..))")
    public Object aroundControllerMethod(final ProceedingJoinPoint pjp) {
        try {
            //执行Controller中的方法，returnVal是返回值
            Object returnVal = pjp.proceed();
//			MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
//			methodSignature.getMethod();

            //获取handler处理方法的参数
            Object[] args = pjp.getArgs();
            for (Object obj : args) {
                if (obj instanceof Search && returnVal instanceof PagingResult &&
                        ((Search) obj).containsRouterCall()) {
                    log.info("执行generateRouterCallButtons方法");
                    ((BaseAdminController) pjp.getTarget()).generateRouterCallButtons(
                            (PagingResult) returnVal, (Search) obj);
                }
            }
            return returnVal;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @AfterThrowing(throwing="ex", pointcut = "execution(public * com.cmazxiaoma..controller.*Controller.*(..))")
    public void afterThrowing(Throwable ex) {
        log.error("controller层发生错误={}", ex);
    }

}

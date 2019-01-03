package com.mjc.realtime;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeAspect {
    // 修正Timer注解的全局唯一限定符
    @Pointcut("@annotation(com.mjc.realtime.utils.Timer)")
    private void pointcut() {}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取目标Logger
        Logger logger = Logger.getLogger(joinPoint.getTarget().getClass());

        // 获取目标类名称
        String clazzName = joinPoint.getTarget().getClass().getName();

        // 获取目标类方法名称
        String methodName = joinPoint.getSignature().getName();

        long start = System.currentTimeMillis();

        logger.info(clazzName + ":" + methodName + "方法正在被调用");

        // 调用目标方法
        Object result = joinPoint.proceed();

        long time = System.currentTimeMillis() - start;
        logger.info(clazzName + ":" + methodName + "方法花费了: "+ time + "ms");

        return result;
    }
}

package com.moralok.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * @author moralok
 * @since 2020/12/19
 */
@Aspect
public class LogAspects {

    /**
     * 抽取公共的切入点表达式
     */
    @Pointcut("execution(public int com.moralok.aop.MathCalculator.*(..))")
    public void pointCut() {

    }

    /**
     * JointPoint 一定要是第一个参数
     *
     * @param joinPoint
     */
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().getName() + "除法运行@Before。。。参数列表为 " + Arrays.asList(joinPoint.getArgs()) + "");
    }

    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().getName() + "除法结束@After。。。");
    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result) {
        System.out.println(joinPoint.getSignature().getName() + "除法正常返回@AfterReturning。。。运行结果 " + result);
    }

    @AfterThrowing(value = "pointCut()", throwing = "e")
    public void logException(JoinPoint joinPoint, Exception e) {
        System.out.println(joinPoint.getSignature().getName() + "除法异常@AfterThrowing。。。异常信息 " + e.getMessage());
    }
}

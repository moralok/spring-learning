package com.moralok.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * JoinPoint一定要出现在参数列表的第一位
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

    /**
     * 可以指定参数result来接收返回值
     *
     * @param joinPoint
     * @param result
     */
    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result) {
        System.out.println(joinPoint.getSignature().getName() + "除法正常返回@AfterReturning。。。运行结果 " + result);
    }

    /**
     * 可以指定参数e来接收抛出的异常
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "pointCut()", throwing = "e")
    public void logException(JoinPoint joinPoint, Exception e) {
        System.out.println(joinPoint.getSignature().getName() + "除法异常@AfterThrowing。。。异常信息 " + e.getMessage());
    }

    @Around(value = "execution(public String com.moralok.bean.Car.getName(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println(joinPoint.getSignature().getName() + " @Around开始");
        Object proceed = joinPoint.proceed();
        System.out.println(joinPoint.getSignature().getName() + " @Around结束");
        return proceed;
    }
}

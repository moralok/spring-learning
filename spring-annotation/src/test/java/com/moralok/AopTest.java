package com.moralok;

import com.moralok.aop.MathCalculator;
import com.moralok.config.AopConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 注册的原理
 * registerBeanPostProcessors(beanFactory)注册BeanPostProcessor
 *     先获取ioc容器中已经定义的后置处理器
 *     加别的后置处理器
 *     按PriorityOrdered、Ordered和剩余的排序且按顺序注册（实际上就是创建实例）
 *         创建实例
 *         populateBean属性赋值
 *         初始化Bean initializeBean
 *             invokeAwareMethods
 *             applyBeanPostProcessorsBeforeInitialization
 *             invokeInitMethods
 *             applyBeanPostProcessorsAfterInitialization
 *         AnnotationAwareAspectJAutoProxyCreator创建成功，调用了initBeanFactory（里面包装了一下BeanFactory）
 *     BeanFactory.addBeanPostProcessor 后面Bean的创建就会被拦截了
 *
 * AnnotationAwareAspectJAutoProxyCreator是InstantiationAwareBeanPostProcessor类型
 *
 * 执行的时机（以前忽略了标题的作用）
 * finishBeanFactoryInitialization(beanFactory) 完成BeanFactory的初始化（创建剩余的非懒加载的Bean）
 *     beanFactory.preInstantiateSingletons()
 *         遍历容器中所有的Bean，依次创建对象getBean
 *             getBean->doGetBean->doGetSingleton()
 *         getBean
 *             先从缓存中获取，能获取到说明已经创建过，否则创建并缓存
 *             createBean
 *                 resolveBeforeInstantiation(beanName, mbdToUse)，给后置处理器一个机会返回一个代理对象
 *                     bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
 *                     InstantiationAwareBeanPostProcessor 任何Bean实例化前后进行拦截（到底哪些被切了未知，所以每一个都需要）
 *                     if (bean != null) {
 * 					       bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
 *                     }
 *                 如果能返回，就使用，否则 doCreateBean(beanName, mbdToUse, args)
 *
 * 创建AOP
 * 每一个Bean被创建前，都调用postProcessBeforeInstantiation（MathCalculator、LogAspect）
 *     判断Bean是否在advisedBean中（什么情况下会进来呢，不是应该在单例缓存就返回了吗）
 *     判断isInfrastructureClass(beanClass)
 *         实现Advise接口等等或有切面注解）不能被代理（评论里要增强单独通过后置处理器是指这里吗）
 *     判断shouldSkip(beanClass, beanName)
 *         获取候选的增强器，被包装的通知方法
 *         this.aspectJAdvisorsBuilder.buildAspectJAdvisors()，查找并构建
 *         每一个的类型为 InstantiationModelAwarePointcutAdvisor
 *         判断是否为 AspectJPointcutAdvisor
 *         最终都是跳过
 * 创建对象
 * postProcessAfterInitialization
 *     wrapIfNecessary
 *         获取当前Bean的候选的所有增强器 Object[] specificInterceptors
 *         找到可应用于当前Bean的增强器
 *         排序
 *         保存当前Bean到advisedBeans中
 *         如果需要增强则创建代理对象
 *             buildAdvisors(beanName, specificInterceptors)
 *             保存到代理工厂中
 *             创建代理对象 proxyFactory.getProxy(getProxyClassLoader())
 *                 createAopProxy() Spring自己决定JDK动态代理还是CGLIB动态代理
 *     返回 CGLIB增强的代理对象
 *     以后容器中获取的就是代理对象，执行目标方法时，会调用通知方法
 *
 * 目标方法运行
 *     容器中保存了组件的代理对象，对象里保存了详细信息（比如增强器、目标方法等等）
 *     CglibAopProxy.intercept() 拦截目标方法的执行
 *     根据ProxyFactory获取目标方法的拦截器链
 *         List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
 *             List<Object> interceptorList = new ArrayList<Object>(config.getAdvisors().length);
 *             遍历所有的增强器，转为Interceptor，registry.getInterceptors(advisor)
 *             如果是MethodInterceptor，直接加入；如果不是，使用适配器转为MethodInterceptor
 *     如果拦截器链为空，直接执行目标方法
 *         拦截器就是通知方法被包装为MethodInterceptor
 *     如果有拦截器链
 *         把需要执行的目标对象、目标方法、拦截器链等信息传入，创建一个CglibMethodInvocation对象
 *         并调用proceed方法
 *     处理返回值 retVal = processReturnType(proxy, target, method, retVal);
 *     拦截器链触发
 *
 *
 *
 *
 *
 * @author moralok
 * @since 2020/12/19
 */
public class AopTest {

    /**
     * 将逻辑组件和切面类都加入Spring容器
     * 注解@Aspect标记切面类
     * 在通知方法上使用注解告诉Spring在何地（切入点表达式）、何时（@Before等注解）运行方法
     * 配置文件中使用标签 aop:aspectj-autoproxy 开启基于注解版的切面功能；等同于使用配置类上使用注解@EnableAspectJAutoProxy
     */
    @Test
    public void aopTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AopConfig.class);
        MathCalculator mathCalculator = ac.getBean(MathCalculator.class);
        mathCalculator.div(1, 1);
        // mathCalculator.div(1, 0);
        ac.close();
    }
}

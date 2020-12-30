## Spring容器的refresh()【创建刷新】
1. prepareRefresh() 刷新前的预处理工作;
    1. initPropertySources() 初始化一些属性设置；子类自定义个性化的属性设置方法。
    2. getEnvironment().validateRequiredProperties(); 检验属性的合法
    3. this.earlyApplicationEvents = new LinkedHashSet<ApplicationEvent>(); 保存容器中的一些早期的事件
2. obtainFreshBeanFactory(); 获取BeanFactory
    - this.beanFactory = new DefaultListableBeanFactory(); 构造方法里创建工厂
    1. refreshBeanFactory(); 刷新BeanFactory；设置序列化ID
    2. getBeanFactory(); 返回GenericApplicationContext持有的BeanFactory
    3. 将创建的BeanFactory【DefaultListableBeanFactory】返回
3. prepareBeanFactory(beanFactory); BeanFactory的预准备工作（BeanFactory进行设置）
    1. 设置BeanFactory的类加载器、支持的表达式解析器。。。
    2. 添加部分BeanPostProcessor【ApplicationContextAwareProcessor】
    3. 设置忽略的自动装备的接口（EnvironmentAware、EmbeddedValueResolverAware。。。）
    4. 设置可以自动解析的自动装配，直接在任何组件中自动注入（BeanFactory、ResourceLoader。。。）
    5. 添加BeanPostProcessor【ApplicationListenerDetector】
    6. 添加编译时的AspectJ支持
    7. 给BeanFactory中注册一些能用的组件
        - environment【ConfigurableEnvironment】
        - systemProperties【Map<String, Object>】
        - systemEnvironment【Map<String, Object>】
4. postProcessBeanFactory(beanFactory); BeanFactory准备工作完成后进行的后置处理工作
    - 子类通过重写这个方法在BeanFactory创建并预准备完成后做进一步的设置

> 以上是BeanFactory的创建与预准备工作

5. invokeBeanFactoryPostProcessors(beanFactory); 执行BeanFactoryPostProcessor
    - BeanFactory的后置处理器，在BeanFactory标准初始化之后执行的
    - 两个接口：
        - 先执行 BeanDefinitionRegistryPostProcessor
            1. 获取所有的BeanDefinitionRegistryPostProcessor
            2. 看优先级执行PriorityOrdered的 postProcessor.postProcessBeanDefinitionRegistry(registry);
            3. 看优先级执行Ordered的 postProcessor.postProcessBeanDefinitionRegistry(registry);
            4. 执行没有优先级顺序的 postProcessor.postProcessBeanDefinitionRegistry(registry);
        - 再执行 BeanFactoryPostProcessor
            1. 获取所有的 BeanFactoryPostProcessor
            2. 看优先级执行PriorityOrdered的 postProcessor.postProcessBeanFactory(beanFactory);
            3. 看优先级执行Ordered的 postProcessor.postProcessBeanFactory(beanFactory);
            4. 执行没有优先级顺序的 postProcessor.postProcessBeanFactory(beanFactory);
6. registerBeanPostProcessors(beanFactory); 注册BeanPostProcessor【Bean后置处理器】
    - 不同类型的的BeanPostProcessor，在Bean创建前后的执行时机是不一样的
        - BeanPostProcessor
        - DestructionAwareBeanPostProcessor
        - InstantiationAwareBeanPostProcessor、
        - martInstantiationAwareBeanPostProcessor
        - MergedBeanDefinitionPostProcessor【internalPostProcessors】
    1. 获取所有的BeanPostProcessor【允许实现PriorityOrdered、Ordered接口指定优先级】
    2. 先注册PriorityOrdered优先级接口的BeanPostProcessor，把每一个BeanPostProcessor添加到BeanFactory中【beanFactory.addBeanPostProcessor(postProcessor);】
    3. 再注册Ordered优先级接口的BeanPostProcessor
    4. 再注册没有实现优先级接口的BeanPostProcessor
    5. 最后注册internalPostProcessors【MergedBeanDefinitionPostProcessor】
    6. 还注册ApplicationListenerDetector，在Bean创建完成后检查是否是ApplicationListener【this.applicationContext.addApplicationListener((ApplicationListener<?>) bean);】
7. initMessageSource(); 初始化MessageSource组件（做国际化）
    1. 获取BeanFactory
    2. 看容器中是否有messageSource的组件（取出国际化配置文件中的某个key的值，能按照区域信息获取）
        - 如果有，赋值给messageSource
        - 如果没有，创建一个DelegatingMessageSource
    3. 把创建好的MessageSource注册到容器中
        - beanFactory.registerSingleton(MESSAGE_SOURCE_BEAN_NAME, this.messageSource);
        - 用于自动注册
        - MessageSource.getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException;
8. initApplicationEventMulticaster(); 初始化事件派发器
    1. 获取BeanFactory
    2. 从BeanFactory中获取applicationEventMulticaster【ApplicationEventMulticaster】的组件
    3. 如果没有配置，创建一个SimpleApplicationEventMulticaster
    4. 将创建的ApplicationEventMulticaster添加到BeanFactory中，用以自动注入
9. onRefresh(); 留给子容器（子类）
    - 子类重写这个方法，在容器刷新的时候可以自定义逻辑
10. registerListeners(); 给容器中将所有项目里面的ApplicationListener注册进来
    1. 从容器中拿到所有的ApplicationListeners
    2. 将每个监听器添加到事件派发器中【getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);】
    3. 派发之前步骤产生的事件
11. finishBeanFactoryInitialization(beanFactory); 初始化所有剩下的单实例Bean
    1. beanFactory.preInstantiateSingletons(); 初始化剩下的单实例Bean
        1. 获取容器中的所有Bean，依次进行初始化和创建对象
        2. 获取Bean的定义信息：RootBeanDefinition
        3. Bean不是抽象的且是单实例且不是懒加载
            1. 判断是否是FactoryBean（是否实现了FactoryBean接口的Bean）
            2. 不是工厂Bean，利用getBean(beanName);创建对象
                0. getBean(beanName);
                1. doGetBean(name, null, null, false);
                2. 先获取缓存中保存的单实例Bean，如果能获取到，说明之前创建过（创建过就会被缓存）【private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>(256);】
                3. 缓存中获取不到，开始Bean的创建对象流程
                4. 标记当前Bean已经被创建
                5. 获取Bean的定义信息
                6. 【获取当前Bean依赖的其他Bean，如果有按照getBean()，把依赖的Bean先创建出来】
                7. 启动单实例的创建流程
                    1. createBean(beanName, mbd, args);
                    2. Object bean = resolveBeforeInstantiation(beanName, mbdToUse); 让BeanPostProcessor【InstantiationAwareBeanPostProcessor】先拦截返回代理对象
                        - InstantiationAwareBeanPostProcessor 此时执行
                        - 触发postProcessBeforeInstantiation
                        - 如果有返回值，触发postProcessAfterInitialization
                    3. 如果前面的InstantiationAwareBeanPostProcessor没有返回代理对象，继续4
                    4. Object beanInstance = doCreateBean(beanName, mbdToUse, args);
                        1. 【创建Bean实例】：createBeanInstance(beanName, mbd, args); 利用工厂方法或者对象的构造器创建出Bean实例
                        2. applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName); 调用MergedBeanDefinitionPostProcessor的postProcessMergedBeanDefinition方法
                        3. 【Bean属性赋值】populateBean(beanName, mbd, instanceWrapper);
                            1. 拿到InstantiationAwareBeanPostProcessor后置处理器调用postProcessAfterInstantiation
                            2. 拿到InstantiationAwareBeanPostProcessor后置处理器调用postProcessPropertyValues
                            - 赋值之前
                            3. 应用Bean属性的值：为属性利用setter方法进行赋值：applyPropertyValues(beanName, mbd, bw, pvs);
                        4. 【Bean初始化】initializeBean(beanName, exposedObject, mbd);
                            1. 【执行Aware接口方法】invokeAwareMethods(beanName, bean); 执行xxxAware接口的方法
                                - BeanNameAware
                                - BeanClassLoaderAware
                                - BeanFactoryAware
                            2. 【执行后置处理器初始化前】applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);（postProcessBeforeInitialization）
                            3. 【执行初始化方法】invokeInitMethods(beanName, wrappedBean, mbd);
                                1. 是否实现InitializingBean接口：执行afterPropertiesSet
                                2. 是否自定义初始化方法
                            4. 【执行后置处理器初始化前】applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);（postProcessAfterInitialization）
                            5. 按需注册Bean销毁方法：registerDisposableBeanIfNecessary(beanName, bean, mbd);
                        5. 将创建的Bean添加到缓存中singletonObjects。ioc容器就是各类map，保存单实例Bean，环境信息
            3. 检查所有的Bean是否是SmartInitializingSingleton的实现
                - 如果是，就执行afterSingletonsInstantiated
12. finishRefresh(); 完成BeanFactory的初始化创建工作，ioc就创建完成。
    1. initLifecycleProcessor(); 初始化和生命周期有关的后置处理器LifecycleProcessor
        - 从容器中找是否有lifecycleProcessor的组件，
        - 如果没有new DefaultLifecycleProcessor();加入容器
        - 两个方法
            - void onRefresh();
            - void onClose();
    2. getLifecycleProcessor().onRefresh(); 拿到上一步的生命周期处理器，回调
    3. publishEvent(new ContextRefreshedEvent(this)); 发布容器刷新完成事件
    4. LiveBeansView.registerApplicationContext(this);
    
## 总结
1. Spring容器在启动的时候，先会保存所有注册进来的Bean的定义信息
    1. Xml注册Bean：<bean>
    2. 注解注册Bean：@Service、@Component、@Bean。。。
2. Spring容器会在合适的时机创建Bean
    1. 用到Bean的时候：利用getBean创建Bean，创建好以后保存在容器里
    2. 统一创建剩下所有Bean的时候：finishBeanFactoryInitialization()
3. 后置处理器：每一个Bean创建完成，都会使用各种后置处理器进行处理，来增强Bean的功能
    - AutowiredAnnotationBeanPostProcessor：处理自动注入
    - AnnotationAwareAspectJAutoProxyCreator：AOP功能
    - AsyncAnnotationBeanPostProcessor
4. 事件驱动模型
    ApplicationListener：事件监听
    ApplicationEventMulticaster：事件派发
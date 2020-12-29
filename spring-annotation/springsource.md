Spring容器的refresh()【创建刷新】
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
    
    

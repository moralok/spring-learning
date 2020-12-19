1. spring-context 依赖
2. 创建容器
    1. 使用xml：new ClassPathXmlApplicationContext("beans.xml");
    2. 使用annotation：new AnnotationConfigApplicationContext(PersonConfig.class);
3. 从容器中获取Bean
    1. 根据id
    2. 根据类型

# 组件注册

1. 包扫描+组件标注注解（@Controller/@Service/@Repository/@Component）。有局限性，适用于自己编写的组件
2. @Bean【导入第三方包的组件】
3. @Import【快速给容器中导入组件】
4. 使用Spring提供的FactoryBean（工厂Bean）

### @Configuration & @Bean 给容器中注册组件    
1. 使用 @Configuration 标注一个配置类
2. 使用 @Bean 标注一个方法：给容器中注册一个Bean，类型为返回值的类型，id默认是用方法名作为id，可以通过参数自定义id

### @ComponentScan 自动扫描组件 & 指定扫描规则
1. value 指定要扫描的包
2. excludeFilters 指定扫描的时候按什么规则排除组件 @Filter[]
3. includeFilters 指定扫描的时候按什么规则包含组件 @Filter[]，必须禁用默认的规则(useDefaultFilters = false)
4. @Repeatable
5. @Filter 可指定规则类型
    - ANNOTATION：按照注解
    - ASSIGNABLE_TYPE：按照给定的类型
    - ASPECTJ：使用AspectJ表达式，不太常用
    - REGEX：使用正则表达式
    - CUSTOM：使用自定义规则
        1. 实现 TypeFilter 接口和对应的match方法，即使没有标注@Controller之类的注解，若满足match方法，也会被注册到容器中，比如MyTypeFilter自身
        2. match方法有两个参数
            - MetadataReader：读取到的当前正在扫描的类的信息，有以下3种
                1. AnnotationMetadata
                2. ClassMetadata
                3. Resource
            - MetadataReaderFactory：可以获取其他任何类的MetadataReader
            
### @Scope-设置组件作用域
1. 类型
    - 多实例：org.springframework.beans.factory.config.ConfigurableBeanFactory#SCOPE_PROTOTYPE
    - 单实例：org.springframework.beans.factory.config.ConfigurableBeanFactory#SCOPE_SINGLETON
    - Web环境，同一次请求创建一个实例：org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST
    - Web环境，同一个session创建一个实例：org.springframework.web.context.WebApplicationContext#SCOPE_SESSION
2. 创建时机
    - 单实例：容器启动的时候
    - 多实例：每次使用组件的时候
    
### @Lazy-Bean 懒加载
单实例Bean：默认在容器启动的时候创建对象
懒加载：容器启动的时候不创建对象，第一次(获取)使用Bean创建对象

### @Conditional-按照条件注册Bean
SpringBoot中大量用到，按照一定的条件进行判断，满足条件给容器中注册Bean。步骤如下：
1. 自定义Condition接口的实现类并实现matches方法。matches方法有两个参数：
    - ConditionContext：判断条件是否满足的上下文环境
        1. 能获取到ioc使用的BeanFactory
        2. 能获取到类加载器
        3. 能获取到当前环境信息，如系统名称
        4. 能获取到Bean定义的注册中心
        5. 能获取到资源加载器
    - AnnotatedTypeMetadata：注解信息
    
2. 在方法或者类上标注@Conditional并给定对应的Condition实现类。当标注在类上，只有满足条件，类中配置的所有Bean才能生效

### @Import-给容器中快速导入组件
1. @Import(X.class)：容器就会自动注册这个组件，id默认是全类名
2. ImportSelector：返回需要导入的组件的全类名
    - 实现 ImportSelector 接口并实现 selectImports 方法
    - 方法返回需要导入的组件的全类名

### 使用FactoryBean注册组件
1. 默认获取到的是工厂Bean调用getObject创建的对象
2. 要获取工厂Bean本身，需要给id前面加一个&

# 生命周期

1. 使用 @Bean 指定 initMethod 和 destroyMethod 方法
2. 通过让 Bean 实现接口
    - InitializingBean（定义初始化逻辑）
    - DisposableBean（定义销毁逻辑）
3. 使用JSR250：
    - @PostConstruct：在Bean创建完成并属性赋值完成，执行初始化方法
    - @PreDestroy：在容器销毁Bean之前执行销毁方法
4. BeanPostProcessor：Bean的后置处理器：在Bean初始化前后进行一些工作
    - postProcessBeforeInitialization：在初始化（即1-3中自定义init-method或者afterPropertiesSet）之前调用
    - postProcessAfterInitialization：在初始化（即1-3中自定义init-method或者afterPropertiesSet）之后调用
    - 原理
    - 在Spring底层的应用（通过打断点找到查看对应的BeanPostProcessor）

### @Bean 指定初始化和销毁方法

##### Bean的生命周期

Bean创建——初始化——销毁

1. 初始化：对象创建完成，并赋值好，调用初始化方法
2. 销毁
    - 单实例：容器关闭的时候
    - 多实例：容器不会管理这个Bean，容器不会调用销毁方法


容器管理Bean的生命周期：我们可以自定义初始化和销毁方法，容器在Bean进行到当前生命周期的时候调用我们自定义的初始化方法和销毁方法。

1. XML中使用方法： init-method="" destroy-method=""
2. @Bean：@Bean(initMethod = "init", destroyMethod = "destroy")

##### 应用场景：
1. 数据源初始化时赋值
2. 数据源销毁的时候管理连接

### InitializingBean 和 DisposableBean

分别实现 afterPropertiesSet 和 destroy方法

### @PostConstruct & @PreDestroy

JSR250规范的注解

### BeanPostProcessor-后置处理器

- 从日志上看，该接口的两个方法包围了init方法
- 所有的Bean都会执行，即使没有init方法

### BeanPostProcessor 原理

- populateBean(beanName, mbd, instanceWrapper); 【给Bean进行属性赋值。】
- initializeBean(beanName, exposedObject, mbd); 【初始化Bean】
    - applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName); 【遍历得到的容器中所有的BeanPostProcessor，挨个执行；一旦返回null，跳出For循环，不会执行后续的BeanPostProcessor】
    - invokeInitMethods(beanName, wrappedBean, mbd); 【执行init方法】
    - applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);

### BeanPostProcessor 在Spring底层的应用

1. ApplicationContextAwareProcessor：postProcessBeforeInitialization检查创建的Bean是否实现了Aware接口，根据Aware接口类型调用方法
2. InitDestroyAnnotationBeanPostProcessor：处理生命周期注解，检查@PostConstruct和@PreDestroy注解并执行相应的方法
3. AutowiredAnnotationBeanPostProcessor：处理 @Autowired
4. AsyncAnnotationBeanPostProcessor：处理 @Async


# 属性赋值

### @Value 赋值

使用 @Value 赋值，等同于XML中的value标签，后者能写什么，前者也能写什么。

1. 基本数值
2. 可以写SpEL：#{}
3. 可以写${}：取出配置文件中的值（严格上说，就是运行环境变量里面的值）

### @PropertySource 加载外部配置文件

1. XML：<context:property-placeholder location="player.properties"/> 加载配置文件
2. @PropertySource：指定文件、指定编码
    - 可以重复注解或者String[]指定多个配置文件
3. 通过 Environment 验证，相关的k/v存到了环境变量中


# 自动装配

### @Autowired & @Qualified & @Primary【Spring定义】

Spring利用依赖注入（DI），完成对IOC容器中各个组件的依赖关系赋值。@Autowired 自动注入：

- 默认优先按照类型去容器里找对应的组件，类似于 ac.getBean(BookService.class);
- 如果找到多个相同类型的组件，再将属性的名称作为组件的id去查找
- 使用 @Qualifier("manualBookDao")，指定注入的组件id
- 自动装配默认一定要将属性赋值好，否则报错；可以指定 required = false
- 使用 @Primary，让Spring进行自动装配的时候，默认使用首选的Bean。可以继续用@Qualifier指定目标。

### @Resource(JSR250) & @Inject(JSR330)【Java规范的注解】

1. @Resource
    - 可以和@Autowired一样实现自动装配的功能，默认是按照组件名称装配的
    - 没有能支持@Primary功能
    - 没有能支持 required = false
2. @Inject
    - 需要导入javax.inject的包，和@Autowired的功能一样，支持@Primary
    - 没有能支持 required = false

### 方法、构造器、参数位置的自动装配

@Autowired：构造器、参数、方法、属性。

1. 标注在方法上：Spring容器创建当前对象，就会调用方法，完成赋值。方法使用的参数，自定义类型的值会从容器中获取。
2. 标注在构造器上，如果组件仅有一个有参构造器，可以省略@Autowired
3. 标注在参数上（标注在非构造器的参数上无效呢）
4. @Bean标注在方法上：方法参数从容器中获取，默认不写@Autowired

默认加载IOC容器中的组件，容器启动会调用无参构造器创建对象，再进行初始化赋值等操作。

### Aware注入Spring底层组件 & 原理

自定义组件想要使用Spring容器底层的一些组件（ApplicationContext、BeanFactory等）。
需要实现xxxAware接口，容器在创建对象时，会调用接口的方法注入相关组件。
xxxAware功能是通过xxxBeanPostProcessor实现的。

- ApplicationContextAware
- BeanNameAware
- EmbeddedValueResolverAware

打断点体会 ApplicationContextAwareProcessor

1. 判断什么接口的实现类：instanceof
2. 调用对应接口的方法：invokeAwareInterfaces(bean);

### @Profile 环境搭建

Spring为我们提供的可以根据当前环境，动态地激活和切换一系列组件的功能。

- 开发环境、测试环境、生产环境
- 数据源

@Profile

- 作用：指定组件在哪个环境的情况下才能被注册到容器中
- 不标识的话，任何环境下都能注册
- 加了标识的Bean，只要在对应环境激活的时候才会注册到容器中
- 环境默认为 default
- 当写在配置类上时，只有在对应的环境时，所有配置才能生效（配置类都不会注册）

如何切换环境？

1. 使用命令行动态参数：VM参数设置 -Dspring.profiles.active=dev
2. 通过代码设置容器
    - 无参构造器：创建一个ApplicationContext
    - 设置一个需要激活的环境
    - 注册配置类
    - 启动刷新容器


# AOP

### AOP 功能测试

定义：指在程序运行期间动态地将某段代码切入到指定方法指定位置进行运行地编程方式

原理：动态代理

步骤：

1. 导入AOP模块：Spring AOP（spring-aspects）
2. 定义业务逻辑类（MathCalculator）；要求在业务逻辑运行的时候将日志进行打印（方法之前、方法运行结束、方法出现异常）
3. 定义一个日志切面类（LogAspects）；要求类里面的方法能动态感知MathCalculator.div运行到哪里，然后执行
    - 通知方法
        1. 前置通知(@Before)：logStart-运行之前
        2. 后置通知(@After)：logEnd-运行之后，无论正常还是异常
        3. 返回通知(@AfterReturning)：logReturn-返回之后
        4. 异常通知(@AfterThrowing)：logException-异常之后
        5. 环绕通知(@Around)：动态代理，手动推进方法执行（joinPoint.proceed()）
    - 可以注入方法参数
        1. JoinPoint，如果有，一定要是第一个参数
        2. returning
        3. throwing
4. 给切面类的目标方法标注何时何地运行（通知注解）
    - 切入点表达式
5. 将切面类和业务逻辑类都加入容器中
6. @Aspect 告诉Spring当前类是一个切面类
7. 开启基于注解的切面功能
    - XML使用名称空间 `<aop:aspectj-autoproxy/>`
    - 配置类标注 @EnableAspectJAutoProxy（Spring有很多类似的注解）
    
### 分析 @EnableAspectJAutoProxy

1. @Import(AspectJAutoProxyRegistrar.class)，AspectJAutoProxyRegistrar是什么？
2. AspectJAutoProxyRegistrar 实现了 ImportBeanDefinitionRegistrar 接口，自定义在容器中注册Bean。注册了什么Bean呢？
    1. AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(registry);【注解感知的AspectJ自动代理创建器】
        - registerOrEscalateApcAsRequired(AnnotationAwareAspectJAutoProxyCreator.class, registry, source);
            - 判断容器中是否有：org.springframework.aop.config.internalAutoProxyCreator = AnnotationAwareAspectJAutoProxyCreator，
            - 如果没有就注册到容器中。【Spring很多功能可以通过看——给容器中注册了什么组件，这个组件什么时候工作，功能是什么，从而了解原理。】
    2. AnnotationConfigUtils.attributesFor(importingClassMetadata, EnableAspectJAutoProxy.class); 获取注解的信息：
        - 查看属性 proxyTargetClass
        - 查看属性 exposeProxy
3. AnnotationAwareAspectJAutoProxyCreator
    - 继承关系：
        AnnotationAwareAspectJAutoProxyCreator 
            extends->AspectJAwareAdvisorAutoProxyCreator 
                extends->AbstractAdvisorAutoProxyCreator 
                    extends->AbstractAutoProxyCreator 
                        implement->SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware 
    - 关注后置处理器（在Bean初始化前后做的事情）、自动装配BeanFactory                

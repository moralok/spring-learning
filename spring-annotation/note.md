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

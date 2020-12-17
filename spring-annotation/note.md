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

### @Import-给容器中快速导入一个组件
1. @Import(X.class)：容器就会自动注册这个组件，id默认是全类名

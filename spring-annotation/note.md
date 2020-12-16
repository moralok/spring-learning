1. spring-context 依赖
2. 创建容器
    1. 使用xml：new ClassPathXmlApplicationContext("beans.xml");
    2. 使用annotation：new AnnotationConfigApplicationContext(PersonConfig.class);
3. 从容器中获取Bean
    1. 根据id
    2. 根据类型

# 组件注册

### @Configuration & @Bean 给容器中注册组件    
1. 使用 @Configuration 标注一个配置类
2. 使用 @Bean 标注一个方法：给容器中注册一个Bean，类型为返回值的类型，id默认是用方法名作为id，可以通过参数自定义id

### @ComponentScan 自动扫描组件 & 指定扫描规则
1. value 指定要扫描的包
2. excludeFilters 指定扫描的时候按什么规则排除组件 @Filter[]
3. includeFilters 指定扫描的时候按什么规则包含组件 @Filter[]，必须禁用默认的规则(useDefaultFilters = false)
4. @Repeatable
5. @Filter 可指定规则类型


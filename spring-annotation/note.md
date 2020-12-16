1. spring-context 依赖
2. 创建容器
    1. 使用xml：new ClassPathXmlApplicationContext("beans.xml");
    2. 使用annotation：new AnnotationConfigApplicationContext(PersonConfig.class);
        1. 使用 @Configuration 标注一个配置类
        2. 使用 @Bean 标注一个方法：给容器中注册一个Bean，类型为返回值的类型，id默认是用方法名作为id，可以通过参数自定义id
3. 从容器中获取Bean
    1. 根据id
    2. 根据类型

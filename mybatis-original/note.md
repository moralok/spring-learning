### 全局文件配置
##### properties引入外部配置

mybatis可以使用properties来引入外部properties配置文件内容

1. resource: 引入类路径下的资源
2. url: 引入网络路径或者磁盘路径下的资源

##### settings运行时行为设置

settings包含很多重要的设置项，用来设置每一个设置项。name:设置项名 value:取值。

##### typeAliases类型别名

别名处理器，为Java类型取别名。推荐还是使用全类名。

- type: 指定要取别名的类型全类名，默认别名是类名小写
- alias(1): 指定新的别名
- package(2): 为一个包下的所有类批量取别名
    - name: 指定包名，为当前包以及下面所有的后代包的每一个类取一个默认的的别名（类名小写）
- 别名不区分大小写
- @Alias(3): 使用注解自定义别名（优先级更高，解决包扫描下自动取别名冲突）
- 内建的别名如int=Integer，_int=int

##### typeHandlers类型处理器

后续细看

##### plugins插件

通过动态代理在以下四大组件中的方法进行拦截。

- Executor: 执行器
- ParameterHandler 参数处理器
- ResultSetHandler 结果集处理器
- StatementHandler 语句处理器

##### environments运行环境

mybatis可以配置多套环境，每一个环境必须有transactionManager和dataSource标签。
id代表环境的唯一标识，使用default快速指定环境。 
- transactionManager，事务管理器
    1. JDBC(JdbcTransactionFactory)
    2. MANAGED(ManagedTransactionFactory)
    3. 自定义事务管理器（交给Spring），实现TransactionFactory接口，type指定为全类名
- dataSource，数据源
    1. UNPOOLED(UnpooledDataSourceFactory)
    2. POOLED(PooledDataSourceFactory)
    3. JNDI(JndiDataSourceFactory)
    4. 自定义数据源，实现DataSourceFactory接口，type指定为全类名
    
##### databaseIdProvider

支持多数据库厂商，type=VENDOR(VendorDatabaseIdProvider)，mybatis就能根据数据库厂商标识来执行不同的SQL。
驱动的Connection->getMetaData->getDatabaseProductName。
- MySQL
- Oracle
- SQL Server
- 等等

mybatis会加载默认不带标识和带数据库厂商标识的SQL语句，如果重名，不带标识的语句会被舍弃。

##### mappers SQL映射注册

将SQL映射注册到全剧配置中，一个mapper对应一个SQL映射。
- mapper 单独注册
    1. 注册配置文件
        - resource: 引用类路径下的SQL映射文件
        - url: 引用网络路径或者磁盘路径下的SQL映射文件
    2. 注册接口
        1. 有SQL映射文件，映射文件必须与接口同名，且必须在同一个目录下。
        2. 没有SQL映射文件，SQL利用注解写在接口方法上。
- package 批量注册
    
推荐通过配置文件写SQL文件，除非很简单不重要的。

##### objectFactory

### 映射文件

##### 增删改查

sqlSession 默认不自动提交。
- sqlSessionFactory.openSession() 不自动提交
- sqlSessionFactory.openSession(true) 自动提交 

允许增删改定义以下返回值：
- Integer
- Boolean
- Long

方法有以下：
- insert
- delete
- update
- select

insert 获取自增主键（Statement.getGeneratedKeys）
1. Mybatis使用useGenerateKeys=true
2. keyProperty="idName"指定对应的主键属性，Mybatis会封装给JavaBean的这个属性

##### 参数处理&单个参数&多个参数

1. 单个参数：mybatis不会做特殊处理，#{参数名任意取}取出参数值
2. 多个参数：
    1. 不处理
        - 未处理时出现异常：`org.apache.ibatis.binding.BindingException: Parameter 'id' not found. Available parameters are [0, 1, param1, param2]`
        - 原因：mybatis特殊处理，多个参数被封装成一个map，#{}就是从map中获取指定key的值
            - key：param1...paramN，或者使用索引0...N-1
            - value：参数值
    2. 推荐命名参数：明确指定封装参数时map的key，使用@Param，key就是注解指定的值
    3. POJO：如果参数正好是我们业务逻辑的数据模型，我们可以直接传入POJO，使用#{属性名}进行取值
    4. map：如果不是属于POJO属性
    5. TO：如果不是属于POJO属性但是非常常用，例如Page{int page, int size}
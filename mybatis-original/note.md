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
    6. 多层次嵌套使用.分隔，#{employee.lastName}
    7. Collection(List, Set)或者数组，也会特殊处理
        - Collection->collection
        - List->list，比如`#{list[0]}`
        - array->array
        
##### 参数处理-封装map源码解析
1. MapperProxy->invoke，如果是Object下的方法，直接执行。
2. mapperMethod.execute(sqlSession, args);
3. method.convertArgsToSqlCommandParam(args);
4. ParamNameResolver.getNamedParams 解析参数封装map
    - 【构造器】
        1. 获取每一个标了@Param注解的参数的value：id和lastName，赋值给name
        2. 解析一个参数，在map中保存(key参数索引, name)，name的值，类似于{0=id,1=lastName,2=2}
            - 标注@Param，注解的值
            - 没有注解
                - 全局配置：useActualParamName（jdk1.8），name=参数名
                - name=map.size()，相当于当前索引
    - 【getNamedParams】
        1. 参数为null，返回null
        2. 如果只有一个参数，并且没有@Param，返回`args[0]`
        3. 多个参数或者单个参数标注了@Param，封装map
            - 遍历names集合，value作为key，names={0="id",1="lastName",2="2"}
            - names集合的key作为取值的参考`args[key]`
            - `{"id"=args[0],"lastName"="args[1],"2"=args[2]}`
            - 额外也保存`{"params1"=args[0],..."paramsN"=args[N-1]}`
##### #和$取值的区别以及#{}的特殊用法
- `#{}` 和 `${}` 都可以取值
- `#{}` 是以预编译的形式，将参数设置到sql语句中，PreparedStatement（id = ?）
- `${}` 是取出的值直接拼装到sql语句中，会有安全问题（id = 2）
- 大多数情况中，应该用#{}，有些情况下JDBC不支持占位符，例如分表场景拼接表名，2020_salary；排序拼接字段名 order by id
- 特殊用法#{}，
    - 指定参数的规则
        - javaType、jdbcType、mode（存储过程）、numericScale
        - resultMap、typeHandler、jdbcTypeName、expression（未来准备支持的特性）
    - 一般用不到，说一下jdbcType在某些特殊场景下需要被设置
        - 在我们数据为null时，有些数据库可能不能识别mybatis对null的默认处理（Oracle）
        - JdbcType.OTHER：无效的类型。Mybatis对所有的null都映射为JDBC原生的OTHER(Types.OTHER)
        - 通过 #{email, jdbcType=NULL}
        - 也可以通过全局配置jdbcTypeForNull=NULL
        
### select

- id: 唯一标识符
- parameterType: 参数类型。可以不传，Mybatis会根据TypeHandler自动推断。
- resultType: 返回值类型
    - 别名或全类名，集合使用元素类型
    - 不能和resultMap共用

##### select返回集合

##### select返回封装map
1. 返回一条记录的map，key就是列名，值就是对应的值
2. 多条记录封装map，使用@MapKey("id")指定key

##### select指定resultMap

1. 全局setting设置
    - autoMappingBehavior默认是PARTIAL
    - mapUnderscoreToCamelCase
2. resultMap，实现高级结果集映射，和resultType二选一
    - id，定义主键底层会有优化
    - column，指定哪一列
    - property，指定对应的JavaBean属性
    - 不指定的列也会自动封装，但是推荐全写上

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
    - type，封装Java类型
    - 不指定的列也会自动封装，但是推荐全写上Depart
    
1. 普通查询
    - 普通映射，column、property、type
2. 一对一关联
    - 级联映射，用.分隔
    - 使用association，property、javaType
        - 嵌套结果集
        - 分步查询 select指定查询方法、column指定传哪一列的值(如果需association里要映射怎么处理)
            1. 先按id查询员工信息
            2. 根据员工d_id查询部门信息
            3. 部门设置到员工信息中
        - 分步查询的优势
            1. 组合已有的查询
            2. 使用延迟加载，部门信息在使用的时候再查询（默认toString也会触发）
3. 一对多关联，使用collection(ofType、select、column)
    
##### association和collection扩展

1. 指定column时需要多个参数：column="{key1=column1,key2=column2}"，比如column="{deptId=id}"
2. fetchType="lazy"，默认为延迟加载。在开启全局的延迟加载后，仍然可以指定为立即加载。

##### 鉴别器 discriminator

Mybatis可以使用鉴别器判断某一列的值，然后根据某列的值改变封装行为。

场景举例：如果员工为女生，把部门信息查出来；如果是男生，把lastName赋值给email

- javaType：指定列的Java类型
- column：指定列名
- case：resultType不能少

### 动态SQL

OGNL表达式，类似JSJL。特殊符号使用转义字符。

- if
- choose
- trim
- foreach

##### if
缺点：拼接完前后可能有多余

##### where
不要使用1=1，使用where标签。where会将拼装sql中多余的and或者or移除。如果where中没有内容，不会拼接where。

缺点：有人会将and放在语句结尾（不要这么写不就好了。。。）

##### trim 自定义sql截取规则
- prefix：加前缀
- prefixOverrides：覆盖前缀
- suffix：加后缀
- suffixOverrides：覆盖后缀

##### choose(when、otherwise)
场景描述：如果带了id就用id查询，如果带了lastName就用lastName查询。

##### set与if结合进行更新
不为null才进行更新。
也可以使用trim进行操作。

##### foreach
- collection: 指定要遍历的集合，list类型的参数会特殊处理封装在map中，map的key就叫list 
- item: 将当前遍历到的元素赋值给指定变量
- separator: 每个元素之间的分隔符
- open: 给遍历出的结果拼接一个开始的字符
- close: 给遍历出的结果拼接一个结束的字符
- index: 索引。遍历list时是索引，遍历map时是key
- 取值，#{变量名}

1. 批量查询
2. 批量添加
3. 批量分步添加
    - MySQLSyntaxErrorException
    - 需要数据库连接属性allowMultiQueries=true支持

##### 两个内置参数

不只是方法传递过来的参数可以被用来判断或者取值，Mybatis默认还有两个内置参数（需要通过OGNL使用呀，There is no getter for property named '_parameter'，没人提这回事吗）：
- _parameter
    - 单个参数，代表该参数
    - 多个参数，代表封装起来的map
- _databaseId，如果配置了databaseIdProvider标签，databaseId就是代表当前数据库的别名

##### bind绑定参数值
场景：模糊查询拼接%（仍然推荐先拼接好再传入）

- 使用$拼接不安全
- bind(name、value)

##### sql抽取可重用的片段
- sql: 定义sql
- include: 引用外部定义的sql
- 可以在include中使用property自定义变量再使用`${取值}`

场景：将要查询的列名，或者插入的列名抽取出来方便引用

### 缓存机制

##### 缓存介绍
Mybatis系统中默认定义了两级缓存：
- 默认情况下，只有一级缓存（SqlSession级别的缓存，也称为本地缓存）开启
- 二级缓存需要手动开启和配置，他是基于namespace级别的缓存
- 为了提高扩展性。Mybatis定义了缓存接口Cache。我们可以通过实现Cache接口来自定义二级缓存

##### 两级缓存
- 一级缓存（本地缓存、SqlSession级别缓存）。与数据库同一次会话期间查询到的数据会放在本地缓存中。以后如果需要相同的数据，直接从缓存中拿，没必要再去查询数据库。
    - 一直开启，不能关闭
    - SqlSession级别的Map
    - 4种失效情况
        1. sqlSession 不同
        2. 查询条件不同
        3. 两次中间执行过增删改
        4. 手动清理缓存
- 二级缓存（全局缓存、namespace级别缓存）。一个namespace对应一个缓存。
    - 一个会话，查询数据后，数据会被保存在会话的一级缓存中
    - 如果会话关闭且二级缓存正常开启，一级缓存的数据会被保存到二级缓存中，新的会话将可以使用二级缓存中的数据
    - sqlSession，一个一级缓存；多个二级缓存
    - 使用
        1. 全局设置开启二级缓存 `<setting name="cacheEnabled" value="true"/>`
        2. 在mapper.xml中需要配置使用二级缓存 `<cache/>`，缓存策略如下
            - eviction: 回收策略
                1. LRU（默认），最近最少使用，移除最长时间不被使用的对象
                2. FIFO，先进先出，按对象进入缓存的先后顺序移除
                3. SOFT，软引用，移除基于垃圾回收器状态和软引用规则的对象
                4. WEAK，弱引用，更积极地移除基于垃圾回收器状态和软引用规则的对象
            - flushInterval: 缓存刷新时间，默认不清空，设置单位为毫秒
            - readOnly: 是否只读。
                - true：Mybatis认为从缓存中获取数据的操作都是只读操作，不会修改数据。为了加快速度，可以直接将缓存中的引用（？？？）交给用户
                - false：数据可能被修改，Mybatis会利用序列化和反序列化克隆一份数据给用户【安全、速度慢一点】
            - size: 缓存多少元素
            - type: 指定自定义缓存的全类名【实现Cache接口】
        3. POJO需要实现序列化接口
    - sqlSession提交或者关闭才会保存到二级缓存中
- 和缓存有关的设置/属性
    1. cacheEnabled（默认true）: 只能关闭二级缓存；不能关闭一级缓存
    2. 每个select标签都有useCache标签（默认true）: 只能关闭二级缓存；不能关闭一级缓存
    3. 每个增删改标签都有flushCache标签（默认true）: 每次执行后都会清空缓存，一二级缓存都会清空
    4. 每个select标签中都有flushCache标签（默认为false）: 每次查询之前都会清空缓存（分前后两种清空吗？？？）
    5. sqlSession.clearCache() 只会清空sqlSession的一级缓存
    6. localCacheScope【本地缓存作用域】，3.3版本之后有
        - SESSION
        - STATEMENT：相当于禁用一级缓存，在Session中不共享数据
        
##### 缓存原理图示
- 数据库
- sqlSession【一级缓存】、sqlSession【一级缓存】、sqlSession【一级缓存】、sqlSession【一级缓存】
- namespace1【二级缓存】、namespace2【二级缓存】

- 过程：新会话->二级缓存（->一级缓存）（->数据库）->用户

- Cache
    - PerpetualCache
    - SerializedCache
    - LruCache
    - etc.
    
- 整合第三方（如Redis）做二级缓存
    - 实现Cache接口（应该要学会自己写吧）
    - 简便方式
        - 引用官方适配包
        - 配置redis信息
        - 没有密码属性有点坑啊，可能是我没找到

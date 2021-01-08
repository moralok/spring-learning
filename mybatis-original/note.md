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
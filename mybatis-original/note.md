### 全局文件配置
##### properties引入外部配置

mybatis可以使用properties来引入外部properties配置文件内容

1. resource: 引入类路径下的资源
2. url: 引入网络路径或者磁盘路径下的资源

##### settings运行时行为设置

settings包含很多重要的设置项，用来设置每一个设置项。name:设置项名 value:取值。
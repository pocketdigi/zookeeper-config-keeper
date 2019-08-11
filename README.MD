## ZooKeeper Config Keeper
 Spring cloud zookeeper项目可以使用ZooKeeper实现分布式配置中心，参考 [Distributed Configuration with ZooKeeper](https://cloud.spring.io/spring-cloud-zookeeper/reference/html/#spring-cloud-zookeeper-config)，但并没有提供图形化的工具来管理配置。
 如果都用zkCli来添加修改配置，很不方便，而ZooKeeper Config Keeper项目就是这些配置的管理工具。
 
### 配置中心架构图
 
 
 
 

### 目录结构
```
├── demo  spring-cloud-zookeeper-config demo
├── docs  文档相关素材
├── server  ZooKeeper Config Keeper 服务端
└── web-front ZooKeeper Config Keeper 前端

```

### 技术架构
#### 服务端
Spring boot、curator、ZooKeeper。

用户账号存储在Zookeeper的/zkck节点，密码保存md5摘要，忘记密码请手动删除/zkck节点。

#### 前端
Vue、element-ui

前后端接口通讯使用JWT鉴权。


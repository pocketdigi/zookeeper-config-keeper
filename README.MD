## ZooKeeper Config Keeper
 Spring cloud zookeeper项目可以使用ZooKeeper实现分布式配置中心，参考 [Distributed Configuration with ZooKeeper](https://cloud.spring.io/spring-cloud-zookeeper/reference/html/#spring-cloud-zookeeper-config)，但并没有提供图形化的工具来管理配置。
 如果都用zkCli来添加修改配置，很不方便，而ZooKeeper Config Keeper项目就是这些配置的管理工具。
 
### 配置中心架构图
 

![架构图](https://github.com/pocketdigi/zookeeper-config-keeper/raw/master/docs/images/configuration-architecture.png)
 
 

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

### 部署
#### 源码编译
clone 源码，分别编译server和web-front,server需要修改jwtsecret和zookeeperaddress，web-front修改src/constants/constants.js里的BASE_URL，换成server的地址。

#### docker部署
```
docker run  --name=zkck -p 80:80 -e jwtsecret=asdfwefasdf -e zookeeperaddress=192.168.2.103:2181 pocketdigi/zookeeper-config-keeper:v0.1 
```
修改jwtsecret和zookeeperaddress

#### 线上Demo

[http://zookeeper-config-keeper.test.pocketdigi.com](http://zookeeper-config-keeper.test.pocketdigi.com)  账号密码都是admin
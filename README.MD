**Alxa**专业外包团队，扫码加微信 Star-11-21  
![Image text](/src/main/resources/static/images/weixin/myweixin.jpg)  

本网络问答社区Web服务基本结构由Controller+Thymeleaf组成，Service服务使用Spring Boot+Dubbo对外暴露服务，Thymeleaf是返回给用户的视图，Model有专门的类与其对应，对于数据的操作则完全通过Model结合Mybatis来完成。在这种模式下，将系统的各个模块才分出来，提供了一个高可用的场景，这种情况下，即使有服务宕机也不会影响其他服务，如果部署多节点的话，部分不服务宕机也不会对系统造成影响，提高了应用系统的可扩展行、可维护性、可复用性，降低可模块之间的耦合度。

极智社群功能模块：

*  登录注册
*  找回密码
*  个人主页
*  个人帖子中心
*  基本信息
*  社区话题列表
*  话题分类
*  话题搜索
*  新建/编辑话题
*  话题详情
*  用户签到
*  温馨通道
*  广告列表

### 技术栈
#### 后端技术栈
1. SpringBoot
2. Dubbo
3. MySQL
4. Redis
5. Solr

#### 前端技术栈
1. Vue.js
2. ElementUI
3. Axios
4. Vue-router

还有其他一些琐碎的技术就不一一列举了。

### 系统架构图
![Image text](/picture/系统架构图.jpg) 

### 整体效果展示
首页
![Image text](/picture/首页.png) 
登录注册
![Image text](/picture/登录注册.png) 
找回密码
![Image text](/picture/找回密码.png) 
个人中心
![Image text](/picture/个人中心.png) 
话题编辑
![Image text](/picture/话题编辑.png) 
话题评论
![Image text](/picture/话题评论.png) 
话题详情
![Image text](/picture/话题详情.png) 
基本信息设置
![Image text](/picture/基本信息设置.png) 

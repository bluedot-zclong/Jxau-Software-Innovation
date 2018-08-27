# Jxau-Software-Innovation
江西农业大学软件创新大赛官网

第一版本地址：
https://gitee.com/zclong/MST.git


## 项目背景与开发目的
-  作为大赛管理的一个步骤，大赛报名具有相当程度的重要性，但由于报名信息、数据过于庞杂导致了报名工作的烦琐和难以管理，传统的手工管理不仅存在着查询困难、不易更新的缺点，而且在人力物力上又耗费极大。伴随着Internet技术的飞速发展和信息化进程的迅猛发展，快捷方便的网络应用为人们的工作、交流提供了方便而又广阔的平台，同时也为大赛报名提供了一个新的渠道——网上报名。
-  网上报名系统的出现，克服了以上种种缺点，对学生这一流动性及大的群体的管理工作，提供了很大的帮助和支持，已得到了普遍的应用。目前国家“中国软件杯”、“英特尔杯”、蓝桥杯以及软件测试等大赛都已经实现了网上报名，网上报名系统的开通有效解决了伴随选手不断增加而出现的选手报名困难问题，使选手可以有更多的时间和精力投入学习，同时也极大的提高了管理组织部门的工作效率。在这样的环境下，学校的信息化建设也不例外：数字教室、多媒体教学、校园局域网都在不断的完善。实现大赛网上报名已成为一种新的趋势。
-  由于以往的报名系统存在很多问题和弊端。本项目设计开发的报名信息处理系统主要出于两种考虑，第一是针对传统报名中的缓慢问题，第二是可以减轻有关人员的负担. 有了这套系统以上问题就迎刃而解。在应用系统的设计上，此时系统按照事先预先设定好的结构，接收选手的报名信息并存入数据库，而且也不用担心传统报名中学生姓名,信息写错的情况，填上基本信息后，此时系统就已完成保存姓名等的工作。本系统克服了现有人工报名模式中的诸多弊端，极大地提高了学校教学质量，可以让工作人员从繁琐、重复的手工操作中解脱出来，给学校的教学有关工作带来很大的便利。


## 项目角色划分

![输入图片说明](https://images.gitee.com/uploads/images/2018/0826/112933_199248d2_1222466.png "屏幕截图.png")


## 业务流程

![输入图片说明](https://images.gitee.com/uploads/images/2018/0826/115547_bced8ee2_1222466.png "屏幕截图.png")


## 技术选型
### 服务端
- 核心框架: Spring Framework 4.x 
- 持久层框架: MyBatis 3.4.x      
- 数据库连接池: Druid 1.0.x      
- 缓存框架: Ehcache 2.x      
- 权限管理框架: Shiro      
- 日志管理: slf4j + log4j12      
- 测试框架: junit4 / testing      
- 后台模板引擎： FreeMarker      
- 前端框架: Bootstrap + Jquery
--------------------

## 项目导入
> 准备环境
- JDK7+
- Tomcat8+
- Maven3.2+
- Eclipse4.5+ / IntelliJ IDEA / MyEclipse10+
- MySQL5.5+

--------------------

- 导入项目前

1.用SVN/Git客户端检出项目
```
Git: https://gitee.com/zclong/Software-Innovation.git
SVN: svn://gitee.com/zclong/Software-Innovation
```

2.导入eclipse/IDEA/myeclipse,选中项目右键选择Maven->Update Project...

- 运行 :     

1.选择 Jxau-Software-Innovation -> Run As -> Run On Server...或

2.开启Tomcat，输入网址 http://localhost:8080/Jxau-Software-Innovation
--------------------

- 项目展示

![输入图片说明](https://images.gitee.com/uploads/images/2018/0826/120424_ec819fd9_1222466.png "屏幕截图.png")

![输入图片说明](https://images.gitee.com/uploads/images/2018/0826/121037_71ec749b_1222466.png "屏幕截图.png")



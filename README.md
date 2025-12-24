# -Financial Management System（财务管理系统）
项目简介

本项目是一个基于复式记账原则设计与实现的财务管理系统，主要面向个人用户及中小型企业。系统以资产、负债、权益、收入和支出五大类会计科目为核心，实现财务数据的规范化记录、集中管理与自动化分析，并支持财务报表的一键生成。同时，引入 AI 财务分析模块，对用户财务数据进行智能问诊与辅助分析，提高财务管理效率。

系统整体设计参考 GnuCash 的会计模型，采用前后端分离架构，在保证专业性的同时兼顾系统的易用性与扩展性。

技术架构

前端：HTML5、CSS3、原生 JavaScript、ECharts

后端：Spring Boot、MyBatis、MyBatis-Plus

数据库：MySQL

通信方式：RESTful API（JSON）


系统功能

用户注册、登录与权限管理

会计科目管理（资产、负债、权益、收入、支出）

交易录入与复式记账校验

财务数据统计与可视化展示

财务报表自动生成

资产负债表

利润表

现金流量表

会计科目自定义申请与审批机制

AI 财务助手（财务分析与智能问诊）

项目结构
GnuCashAPP
├── src/main/java/com/gnu/cash
│   ├── controller    # 控制层
│   ├── mapper        # 数据访问层
│   ├── pojo          # 实体类
│   ├── config        # 系统与AI配置
│   └── Application.java
├── src/main/resources
│   ├── mapper        # MyBatis 映射文件
│   ├── sql           # 数据库建表脚本
│   ├── static        # 前端页面
│   └── application.yaml
└── pom.xml


说明

本系统主要用于教学与研究目的，未对接真实财务申报平台。系统功能可根据实际需求进一步扩展，如多币种处理、税务管理与审计支持等。
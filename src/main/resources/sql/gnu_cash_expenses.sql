create table expenses
(
    id          int auto_increment
        primary key,
    level1      varchar(50) not null comment '一级科目',
    level2      varchar(50) not null comment '二级科目',
    level3      varchar(50) null comment '三级科目',
    description text        null comment '说明'
);

INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (1, '支出', '保险', '健康保险', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (2, '支出', '保险', '汽车保险', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (3, '支出', '保险', '人寿保险', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (4, '支出', '汽车', '燃气', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (5, '支出', '汽车', '收费', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (6, '支出', '汽车', '停车', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (7, '支出', '汽车', '维修和保养', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (8, '支出', '水电费', '电', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (9, '支出', '水电费', '水', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (10, '支出', '水电费', '燃气', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (11, '支出', '水电费', '垃圾收集', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (12, '支出', '税费', '国家', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (13, '支出', '税费', '其它税', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (14, '支出', '税费', '省', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (15, '支出', '税费', '社会保险', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (16, '支出', '税费', '医疗保险', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (17, '支出', '物资', '衣服', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (18, '支出', '物资', '洗衣/干洗', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (19, '支出', '物资', '用餐', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (20, '支出', '物资', '医疗费用', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (21, '支出', '娱乐', '旅行', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (22, '支出', '娱乐', '音乐/电影', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (23, '支出', '娱乐', '娱乐', null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (24, '支出', '慈善', null, null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (25, '支出', '电报', null, null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (26, '支出', '电话', null, null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (27, '支出', '电脑', null, null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (28, '支出', '调整', null, null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (29, '支出', '订阅费', null, null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (30, '支出', '公共交通', null, null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (31, '支出', '教育', null, null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (32, '支出', '礼品', null, null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (33, '支出', '食品杂货', null, null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (34, '支出', '书籍', null, null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (35, '支出', '银行服务收费', null, null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (36, '支出', '杂项', null, null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (37, '支出', '在线服务', null, null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (38, '支出', '用餐', null, null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (39, '支出', '爱好', null, null);
INSERT INTO gnu_cash.expenses (id, level1, level2, level3, description) VALUES (40, '支出', '技能提升', '在线课程', '用于记录员工参加在线技能培训课程的相关费用支出，便于财务核算和预算管理');

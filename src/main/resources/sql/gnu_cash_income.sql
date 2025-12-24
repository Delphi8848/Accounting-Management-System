create table income
(
    id          int auto_increment
        primary key,
    level1      varchar(50) not null comment '一级科目',
    level2      varchar(50) not null comment '二级科目',
    level3      varchar(50) null comment '三级科目',
    description text        null comment '说明'
);

INSERT INTO gnu_cash.income (id, level1, level2, level3, description) VALUES (1, '收入', '薪金', null, null);
INSERT INTO gnu_cash.income (id, level1, level2, level3, description) VALUES (2, '收入', '奖金', null, null);
INSERT INTO gnu_cash.income (id, level1, level2, level3, description) VALUES (3, '收入', '利息收入', '其他收入', null);
INSERT INTO gnu_cash.income (id, level1, level2, level3, description) VALUES (4, '收入', '收到的礼物', null, null);

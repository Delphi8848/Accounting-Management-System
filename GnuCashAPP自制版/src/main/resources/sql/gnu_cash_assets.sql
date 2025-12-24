create table assets
(
    id          int auto_increment
        primary key,
    level1      varchar(50) not null comment '一级科目',
    level2      varchar(50) not null comment '二级科目',
    level3      varchar(50) null comment '三级科目',
    description text        null comment '说明'
);

INSERT INTO gnu_cash.assets (id, level1, level2, level3, description) VALUES (1, '资产', '流动资产', '现金', null);
INSERT INTO gnu_cash.assets (id, level1, level2, level3, description) VALUES (2, '资产', '流动资产', '活期存款', null);
INSERT INTO gnu_cash.assets (id, level1, level2, level3, description) VALUES (3, '资产', '流动资产', '储蓄存款', null);

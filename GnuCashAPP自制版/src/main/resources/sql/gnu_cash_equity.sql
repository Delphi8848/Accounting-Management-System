create table equity
(
    id          int auto_increment
        primary key,
    level1      varchar(50) not null comment '一级科目',
    level2      varchar(50) not null comment '二级科目',
    level3      varchar(50) null comment '三级科目',
    description text        null comment '说明'
);

INSERT INTO gnu_cash.equity (id, level1, level2, level3, description) VALUES (1, '所有者权益', '期初余额', null, null);

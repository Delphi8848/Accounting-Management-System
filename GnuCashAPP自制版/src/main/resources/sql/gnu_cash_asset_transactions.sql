create table asset_transactions
(
    id               int auto_increment comment '交易记录唯一标识'
        primary key,
    user_id          int                                      not null comment '关联用户ID',
    asset_id         int                                      not null comment '关联资产科目ID',
    transaction_date date                                     not null comment '交易发生日期',
    description      varchar(255)                             not null comment '交易描述信息',
    debit_amount     decimal(15, 2) default 0.00              null comment '借方金额（资产增加）',
    credit_amount    decimal(15, 2) default 0.00              null comment '贷方金额（资产减少）',
    created_at       timestamp      default CURRENT_TIMESTAMP null comment '记录创建时间',
    constraint asset_transactions_ibfk_1
        foreign key (user_id) references users (id)
            on delete cascade,
    constraint asset_transactions_ibfk_2
        foreign key (asset_id) references assets (id)
)
    comment '资产科目交易记录表';

create index asset_id
    on asset_transactions (asset_id);

create index idx_user_asset
    on asset_transactions (user_id, asset_id)
    comment '用户资产联合索引';

INSERT INTO gnu_cash.asset_transactions (id, user_id, asset_id, transaction_date, description, debit_amount, credit_amount, created_at) VALUES (15, 1, 1, '2025-01-01', '初始资金：现金', 5000.00, 0.00, '2025-10-18 20:05:39');
INSERT INTO gnu_cash.asset_transactions (id, user_id, asset_id, transaction_date, description, debit_amount, credit_amount, created_at) VALUES (16, 1, 2, '2025-01-01', '初始资金：活期存款', 15000.00, 0.00, '2025-10-18 20:05:39');
INSERT INTO gnu_cash.asset_transactions (id, user_id, asset_id, transaction_date, description, debit_amount, credit_amount, created_at) VALUES (17, 1, 2, '2025-05-05', '工资存入活期存款', 15000.00, 0.00, '2025-10-18 20:08:13');
INSERT INTO gnu_cash.asset_transactions (id, user_id, asset_id, transaction_date, description, debit_amount, credit_amount, created_at) VALUES (18, 1, 2, '2025-06-25', '信用卡还款支出', 0.00, 2580.00, '2025-10-18 20:09:49');
INSERT INTO gnu_cash.asset_transactions (id, user_id, asset_id, transaction_date, description, debit_amount, credit_amount, created_at) VALUES (19, 1, 1, '2025-10-19', '平安人寿保险十月份月缴费', 0.00, 145.00, '2025-10-19 13:40:47');
INSERT INTO gnu_cash.asset_transactions (id, user_id, asset_id, transaction_date, description, debit_amount, credit_amount, created_at) VALUES (20, 1, 1, '2025-10-19', '10月超额绩效', 3000.00, 0.00, '2025-10-19 13:43:19');

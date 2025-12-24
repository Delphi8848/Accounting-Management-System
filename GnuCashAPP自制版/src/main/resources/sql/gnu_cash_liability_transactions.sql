create table liability_transactions
(
    id               int auto_increment comment '交易记录唯一标识'
        primary key,
    user_id          int                                      not null comment '关联用户ID',
    liability_id     int                                      not null comment '关联负债科目ID',
    transaction_date date                                     not null comment '交易发生日期',
    description      varchar(255)                             not null comment '交易描述信息',
    debit_amount     decimal(15, 2) default 0.00              null comment '借方金额（负债减少）',
    credit_amount    decimal(15, 2) default 0.00              null comment '贷方金额（负债增加）',
    created_at       timestamp      default CURRENT_TIMESTAMP null comment '记录创建时间',
    constraint liability_transactions_ibfk_1
        foreign key (user_id) references users (id)
            on delete cascade,
    constraint liability_transactions_ibfk_2
        foreign key (liability_id) references liabilities (id)
)
    comment '负债科目交易记录表';

create index idx_user_liability
    on liability_transactions (user_id, liability_id)
    comment '用户负债联合索引';

create index liability_id
    on liability_transactions (liability_id);

INSERT INTO gnu_cash.liability_transactions (id, user_id, liability_id, transaction_date, description, debit_amount, credit_amount, created_at) VALUES (8, 1, 1, '2025-06-15', '京东618购物', 0.00, 2580.00, '2025-10-18 20:09:49');
INSERT INTO gnu_cash.liability_transactions (id, user_id, liability_id, transaction_date, description, debit_amount, credit_amount, created_at) VALUES (9, 1, 1, '2025-06-25', '信用卡还款', 2580.00, 0.00, '2025-10-18 20:09:49');
INSERT INTO gnu_cash.liability_transactions (id, user_id, liability_id, transaction_date, description, debit_amount, credit_amount, created_at) VALUES (10, 1, 1, '2025-10-15', '信用卡购物消费', 0.00, 1000.00, '2025-10-18 20:18:52');

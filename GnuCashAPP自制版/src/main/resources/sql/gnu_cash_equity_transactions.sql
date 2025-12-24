create table equity_transactions
(
    id               int auto_increment comment '交易记录唯一标识'
        primary key,
    user_id          int                                      not null comment '关联用户ID',
    equity_id        int                                      not null comment '关联权益科目ID',
    transaction_date date                                     not null comment '交易发生日期',
    description      varchar(255)                             not null comment '交易描述信息',
    debit_amount     decimal(15, 2) default 0.00              null comment '借方金额（权益减少）',
    credit_amount    decimal(15, 2) default 0.00              null comment '贷方金额（权益增加）',
    created_at       timestamp      default CURRENT_TIMESTAMP null comment '记录创建时间',
    constraint equity_transactions_ibfk_1
        foreign key (user_id) references users (id)
            on delete cascade,
    constraint equity_transactions_ibfk_2
        foreign key (equity_id) references equity (id)
)
    comment '权益科目交易记录表';

create index equity_id
    on equity_transactions (equity_id);

create index idx_user_equity
    on equity_transactions (user_id, equity_id)
    comment '用户权益联合索引';

INSERT INTO gnu_cash.equity_transactions (id, user_id, equity_id, transaction_date, description, debit_amount, credit_amount, created_at) VALUES (1, 1, 1, '2025-01-01', '系统初始化：初始资金注入', 0.00, 20000.00, '2025-10-18 20:05:39');

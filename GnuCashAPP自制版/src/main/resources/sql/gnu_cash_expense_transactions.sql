create table expense_transactions
(
    id               int auto_increment comment '交易记录唯一标识'
        primary key,
    user_id          int                                      not null comment '关联用户ID',
    expense_id       int                                      not null comment '关联支出科目ID',
    transaction_date date                                     not null comment '交易发生日期',
    description      varchar(255)                             not null comment '交易描述信息',
    debit_amount     decimal(15, 2) default 0.00              null comment '借方金额（支出增加）',
    credit_amount    decimal(15, 2) default 0.00              null comment '贷方金额（支出冲减）',
    created_at       timestamp      default CURRENT_TIMESTAMP null comment '记录创建时间',
    constraint expense_transactions_ibfk_1
        foreign key (user_id) references users (id)
            on delete cascade,
    constraint expense_transactions_ibfk_2
        foreign key (expense_id) references expenses (id)
)
    comment '支出科目交易记录表';

create index expense_id
    on expense_transactions (expense_id);

create index idx_user_expense
    on expense_transactions (user_id, expense_id)
    comment '用户支出联合索引';

INSERT INTO gnu_cash.expense_transactions (id, user_id, expense_id, transaction_date, description, debit_amount, credit_amount, created_at) VALUES (8, 1, 27, '2025-06-15', '电子产品购买', 2580.00, 0.00, '2025-10-18 20:09:49');
INSERT INTO gnu_cash.expense_transactions (id, user_id, expense_id, transaction_date, description, debit_amount, credit_amount, created_at) VALUES (9, 1, 33, '2025-10-15', '十月购物消费 - 服装', 1000.00, 0.00, '2025-10-18 20:18:52');
INSERT INTO gnu_cash.expense_transactions (id, user_id, expense_id, transaction_date, description, debit_amount, credit_amount, created_at) VALUES (10, 1, 3, '2025-10-19', '平安人寿保险十月份月缴费', 145.00, 0.00, '2025-10-19 13:40:39');

create table income_transactions
(
    id               int auto_increment comment '交易记录唯一标识'
        primary key,
    user_id          int                                      not null comment '关联用户ID',
    income_id        int                                      not null comment '关联收入科目ID',
    transaction_date date                                     not null comment '交易发生日期',
    description      varchar(255)                             not null comment '交易描述信息',
    debit_amount     decimal(15, 2) default 0.00              null comment '借方金额（收入冲减）',
    credit_amount    decimal(15, 2) default 0.00              null comment '贷方金额（收入增加）',
    created_at       timestamp      default CURRENT_TIMESTAMP null comment '记录创建时间',
    constraint income_transactions_ibfk_1
        foreign key (user_id) references users (id)
            on delete cascade,
    constraint income_transactions_ibfk_2
        foreign key (income_id) references income (id)
)
    comment '收入科目交易记录表';

create index idx_user_income
    on income_transactions (user_id, income_id)
    comment '用户收入联合索引';

create index income_id
    on income_transactions (income_id);

INSERT INTO gnu_cash.income_transactions (id, user_id, income_id, transaction_date, description, debit_amount, credit_amount, created_at) VALUES (5, 1, 1, '2025-05-05', '五月工资收入', 0.00, 15000.00, '2025-10-18 20:08:13');
INSERT INTO gnu_cash.income_transactions (id, user_id, income_id, transaction_date, description, debit_amount, credit_amount, created_at) VALUES (6, 1, 2, '2025-10-19', '10月超额绩效', 0.00, 3000.00, '2025-10-19 13:43:17');

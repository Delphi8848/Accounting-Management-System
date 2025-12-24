create table account_application
(
    id                 int auto_increment comment '申请ID'
        primary key,
    category           enum ('资产', '负债', '所有者权益', '收入', '支出')               not null comment '科目分类',
    level1             varchar(50)                                          not null comment '一级科目',
    level2             varchar(50)                                          not null comment '二级科目',
    level3             varchar(50)                                          null comment '三级科目',
    application_reason text                                                 not null comment '申请理由',
    status             enum ('待审批', '已拒绝', '已通过') default '待审批'             null comment '申请状态',
    applicant_id       int                                                  not null comment '申请人ID',
    applicant_name     varchar(100)                                         not null comment '申请人姓名',
    application_time   datetime                   default CURRENT_TIMESTAMP null comment '申请时间',
    reviewer_id        int                                                  null comment '审批人ID',
    reviewer_name      varchar(100)                                         null comment '审批人姓名',
    review_time        datetime                                             null comment '审批时间',
    review_comment     text                                                 null comment '审批意见'
)
    comment '科目申请审批表';

INSERT INTO gnu_cash.account_application (id, category, level1, level2, level3, application_reason, status, applicant_id, applicant_name, application_time, reviewer_id, reviewer_name, review_time, review_comment) VALUES (1, '支出', '支出', '技能提升', '在线课程', '用于记录员工参加在线技能培训课程的相关费用支出，便于财务核算和预算管理', '已通过', 1, 'zhangsan', '2025-10-25 17:33:21', 9, '系统管理员', '2025-10-25 20:03:22', '同意');
INSERT INTO gnu_cash.account_application (id, category, level1, level2, level3, application_reason, status, applicant_id, applicant_name, application_time, reviewer_id, reviewer_name, review_time, review_comment) VALUES (2, '支出', '支出', '游戏充值', '网游角色充值购买', '用于个人日常网游充值使用', '已拒绝', 1, 'zhangsan', '2025-10-25 17:56:56', 9, '系统管理员', '2025-10-25 20:02:47', 'no no no 网路游戏支出不在系统范畴内');

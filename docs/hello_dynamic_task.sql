-- auto-generated definition
create schema hello_dynamic_task collate utf8mb4_0900_ai_ci;

-- auto-generated definition
create table task_job
(
    job_id          bigint auto_increment comment '任务 ID'
        primary key,
    bean_name       varchar(20)  null comment '任务 bean 名称',
    method_name     varchar(50)  null comment '任务方法名称',
    method_params   varchar(50)  null comment '任务方法参数',
    cron_expression varchar(50)  null comment '任务 Cron 表达式',
    remark          varchar(100) null comment '备注',
    job_status      int          null comment '任务状态（1：正常    0：暂停）',
    create_time     datetime     null comment '创建时间',
    update_time     datetime     null comment '更新时间'
)
    comment '定时任务';


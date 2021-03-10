drop table if exists `test`;
SET default_storage_engine=INNODB;
create table `test`(
    `id` bigint not null comment 'id',
    `name` varchar(50) comment '名称',
    primary key (`id`)
)default charset =utf8 comment='测试';
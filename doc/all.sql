drop table if exists `demo`;
SET default_storage_engine=INNODB;
create table `demo`(
    `id` bigint not null comment 'id',
    `name` varchar(50) comment '名称',
    primary key (`id`)
)default charset =utf8 comment='测试';
insert into demo values (1,'张三');
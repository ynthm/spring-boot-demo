CREATE TABLE `user`
(
    `id`          int unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `age`         int                     DEFAULT NULL COMMENT '年龄',
    `name`        varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
    `balance`     decimal(12, 2) NOT NULL DEFAULT '0.00' COMMENT '微信头像',
    `sex`         tinyint                 DEFAULT '-1' COMMENT '性别',
    `phone`       bigint                  DEFAULT NULL COMMENT '手机',
    `province`    varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '注册地址：省',
    `city`        varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '注册地址：城市',
    `country`     varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '注册地址：县/区',
    `status`      tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否标记删除 0：否 1：是',
    `create_time` datetime       NOT NULL COMMENT '创建时间',
    `update_time` datetime                DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';
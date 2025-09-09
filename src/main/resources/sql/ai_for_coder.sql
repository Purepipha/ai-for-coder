-- 创建库
create database if not exists ai_for_coder;

-- 切换库
use ai_for_coder;

-- 用户表
-- 以下是建表语句
create table if not exists user
(
    id            bigint       auto_increment                     comment 'id'     primary key,
    user_account  varchar(256)                           not null comment '账号',
    user_password varchar(512)                           not null comment '密码',
    user_name     varchar(256)                           null comment '用户昵称',
    user_avatar   varchar(1024)                          null comment '用户头像',
    user_profile  varchar(512)                           null comment '用户简介',
    user_role     varchar(256) default 'user'            not null comment '用户角色：user/admin',
    edit_time     datetime     default CURRENT_TIMESTAMP not null comment '编辑时间',
    create_time   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete     tinyint      default 0                 not null comment '是否删除',
    UNIQUE KEY uk_userAccount (user_account),
    INDEX idx_userName (user_name)
    ) comment '用户' collate = utf8mb4_unicode_ci;

-- 插入 100 条测试用户数据
INSERT INTO user (user_account, user_password, user_name, user_avatar, user_profile, user_role, edit_time, create_time, update_time, is_delete)
VALUES
-- 使用生成的数据
('user1',     'e10adc3949ba59abbe56e057f20f883e', 'Alice',     'https://example.com/avatar/1.jpg', '热爱编程的前端开发者', 'user', NOW(), NOW(), NOW(), 0),
('user2',     'e10adc3949ba59abbe56e057f20f883e', 'Bob',       'https://example.com/avatar/2.jpg', '后端工程师，喜欢写 Java', 'user', NOW(), NOW(), NOW(), 0),
('user3',     'e10adc3949ba59abbe56e057f20f883e', 'Charlie',   'https://example.com/avatar/3.jpg', '全栈爱好者，正在学习 AI', 'user', NOW(), NOW(), NOW(), 0),
('user4',     'e10adc3949ba59abbe56e057f20f883e', 'Diana',     'https://example.com/avatar/4.jpg', 'UI 设计师，也懂点代码', 'user', NOW(), NOW(), NOW(), 0),
('user5',     'e10adc3949ba59abbe56e057f20f883e', 'Evan',      'https://example.com/avatar/5.jpg', '算法工程师，专注 NLP', 'admin', NOW(), NOW(), NOW(), 0),
('user6',     'e10adc3949ba59abbe56e057f20f883e', 'Fiona',     'https://example.com/avatar/6.jpg', '喜欢开源，贡献过 Spring', 'user', NOW(), NOW(), NOW(), 0),
('user7',     'e10adc3949ba59abbe56e057f20f883e', 'George',    'https://example.com/avatar/7.jpg', 'DevOps 工程师，玩转 Docker', 'user', NOW(), NOW(), NOW(), 0),
('user8',     'e10adc3949ba59abbe56e057f20f883e', 'Hellen',    'https://example.com/avatar/8.jpg', '产品经理，懂技术', 'user', NOW(), NOW(), NOW(), 0),
('user9',     'e10adc3949ba59abbe56e057f20f883e', 'Ivan',      'https://example.com/avatar/9.jpg', '移动开发，专注 Android', 'user', NOW(), NOW(), NOW(), 0),
('user10',    'e10adc3949ba59abbe56e057f20f883e', 'Julia',     'https://example.com/avatar/10.jpg', 'iOS 开发者，Swift 爱好者', 'user', NOW(), NOW(), NOW(), 0),
('user11',    'e10adc3949ba59abbe56e057f20f883e', 'Kevin',     'https://example.com/avatar/11.jpg', '数据分析师，擅长 SQL', 'user', NOW(), NOW(), NOW(), 0),
('user12',    'e10adc3949ba59abbe56e057f20f883e', 'Linda',     'https://example.com/avatar/12.jpg', '技术写作，写过技术博客', 'user', NOW(), NOW(), NOW(), 0),
('user13',    'e10adc3949ba59abbe56e057f20f883e', 'Mike',      'https://example.com/avatar/13.jpg', '网络安全爱好者', 'user', NOW(), NOW(), NOW(), 0),
('user14',    'e10adc3949ba59abbe56e057f20f883e', 'Nina',      'https://example.com/avatar/14.jpg', 'AI 研究生，研究大模型', 'user', NOW(), NOW(), NOW(), 0),
('user15',    'e10adc3949ba59abbe56e057f20f883e', 'Oscar',     'https://example.com/avatar/15.jpg', '游戏开发，用 Unity', 'user', NOW(), NOW(), NOW(), 0),
('user16',    'e10adc3949ba59abbe56e057f20f883e', 'Peggy',     'https://example.com/avatar/16.jpg', '前端切图仔，精通 CSS', 'user', NOW(), NOW(), NOW(), 0),
('user17',    'e10adc3949ba59abbe56e057f20f883e', 'Quinn',     'https://example.com/avatar/17.jpg', '区块链开发者', 'user', NOW(), NOW(), NOW(), 0),
('user18',    'e10adc3949ba59abbe56e057f20f883e', 'Ryan',      'https://example.com/avatar/18.jpg', '运维工程师，熟悉 Linux', 'user', NOW(), NOW(), NOW(), 0),
('user19',    'e10adc3949ba59abbe56e057f20f883e', 'Sarah',     'https://example.com/avatar/19.jpg', '测试工程师，自动化专家', 'user', NOW(), NOW(), NOW(), 0),
('user20',    'e10adc3949ba59abbe56e057f20f883e', 'Tom',       'https://example.com/avatar/20.jpg', '架构师，设计微服务', 'admin', NOW(), NOW(), NOW(), 0);
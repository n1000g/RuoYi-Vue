-- ----------------------------
-- 考生管理一、考生组表（參考 dept）
-- ----------------------------
drop table if exists candidate_set;
create table candidate_set (
  candidate_set_id      bigint(20)      not null auto_increment    comment '考生组id',
  dept_id               bigint(20)      default null               comment '部门id',
  parent_id             bigint(20)      default 0                  comment '父节点id',
  ancestors             varchar(50)     default ''                 comment '祖级列表',
  candidate_set_name    varchar(30)     default ''                 comment '组名称',
  order_num             int(4)          default 0                  comment '显示顺序',
  create_by             varchar(64)     default ''                 comment '创建者',
  create_time 	        datetime                                   comment '创建时间',
  update_by             varchar(64)     default ''                 comment '更新者',
  update_time           datetime                                   comment '更新时间',
  primary key (candidate_set_id)
) engine=innodb comment = '考生组表';
insert into candidate_set values(1, 100, 0, '0',   '考生组1', 1, '', sysdate(), '', null);
insert into candidate_set values(2, 100, 1, '0,1', '考生组2', 1, '', sysdate(), '', null);


-- ----------------------------
-- 考生管理二、考生信息表
-- ----------------------------
drop table if exists candidate;
create table candidate (
  candidate_id      bigint(20)      not null auto_increment    comment '考生ID',
  candidate_set_id  bigint(20)      default null               comment '组ID',
  username          varchar(30)     not null                   comment '用户名',
  password          varchar(100)    default ''                 comment '密码',
  candidate_name    varchar(30)     default ''                 comment '考生姓名',
  email             varchar(50)     default null               comment '邮箱',
  phone             varchar(11)     default null               comment '联系电话',
  primary key (candidate_id)
) engine=innodb auto_increment=100 comment = '考生信息表';
insert into candidate values(1,  0, 'user1', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '测试考生',  'ry@163.com', '15888888888');
insert into candidate values(2,  0, 'user2', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '测试考生2',  'ry@qq.com',  '15666666666');
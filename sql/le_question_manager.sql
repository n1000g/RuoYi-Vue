-- ----------------------------
-- 题库管理一、题库表（參考 dept）
-- ----------------------------
drop table if exists question_bank;
create table question_bank (
  question_bank_id      bigint(20)      not null auto_increment    comment '题库id',
  dept_id               bigint(20)      default null               comment '部门id',
  parent_id             bigint(20)      default 0                  comment '父节点id',
  ancestors             varchar(50)     default ''                 comment '祖级列表',
  question_bank_name    varchar(30)     default ''                 comment '题库名称',
  order_num             int(4)          default 0                  comment '显示顺序',
  create_by             varchar(64)     default ''                 comment '创建者',
  create_time 	        datetime                                   comment '创建时间',
  update_by             varchar(64)     default ''                 comment '更新者',
  update_time           datetime                                   comment '更新时间',
  primary key (question_bank_id)
) engine=innodb auto_increment=200 comment = '题库表';
insert into question_bank values(100, 100, 0,   '0',          'CS专业课',      0, 'admin', sysdate(), '', null);
insert into question_bank values(101, 100, 100, '0,100',      '923操作系统',   1, 'admin', sysdate(), '', null);
insert into question_bank values(102, 100, 100, '0,100',      '925数据结构',   2, 'admin', sysdate(), '', null);
insert into question_bank values(103, 100, 101, '0,100,101',  '引论',         1, 'admin', sysdate(), '', null);
insert into question_bank values(104, 100, 101, '0,100,101',  '进程管理',      2, 'admin', sysdate(), '', null);
insert into question_bank values(105, 100, 101, '0,100,101',  '内存管理',      3, 'admin', sysdate(), '', null);
insert into question_bank values(106, 100, 101, '0,100,101',  '文件管理',      4, 'admin', sysdate(), '', null);
insert into question_bank values(107, 100, 101, '0,100,101',  '设备管理',      5, 'admin', sysdate(), '', null);
insert into question_bank values(108, 100, 102, '0,100,102',  '基本结构',      1, 'admin', sysdate(), '', null);
insert into question_bank values(109, 100, 102, '0,100,102',  '高级结构',      2, 'admin', sysdate(), '', null);


-- ----------------------------
-- 题库管理二、试题信息表
-- todo: 为每个题目设置标签，用标签组卷，标签是字典类型，题目标签都有索引
-- ----------------------------
drop table if exists question;
create table question (
  question_id       bigint(20)      not null auto_increment    comment '试题ID',
  question_bank_id  bigint(20)      default null               comment '题库ID',
  question_type     int(2)          default null               comment '试题类型（0选判1单选2多选3填空4简答）',
  difficulty        int(1)          default null               comment '试题难度（0简单1一般2困难）',
  score             int(2)          default null               comment '试题分值',
  content           text            default null               comment '题面',
  answer            text            default null               comment '参考答案',
  analysis          text            default null               comment '答案解析',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (question_id)
) engine=innodb comment = '试题信息表';


-- ----------------------------
-- 题库管理三、选项信息表（试题的子表）
-- ----------------------------
drop table if exists choice;
create table choice (
  choice_id         bigint(20)      not null auto_increment    comment '选项ID',
  question_id       bigint(20)      default null               comment '试题ID',
  content           text            default null               comment '选项内容',
  primary key (choice_id)
) engine=innodb comment = '选项信息表';
create index idx_question_id on choice(question_id);
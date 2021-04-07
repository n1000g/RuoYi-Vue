-- ----------------------------
-- 一、题库表（參考 dept）
-- ----------------------------
drop table if exists question_bank;
create table question_bank (
  question_bank_id  bigint(20)      not null auto_increment    comment '题库id',
  parent_id         bigint(20)      default 0                  comment '父节点id',
  ancestors         varchar(50)     default ''                 comment '祖级列表',
  dept_name         varchar(30)     default ''                 comment '题库名称',
  order_num         int(4)          default 0                  comment '显示顺序',
  status            char(1)         default '0'                comment '题库状态（0正常 1停用）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (question_bank_id)
) engine=innodb auto_increment=200 comment = '题库表';

-- ---------- ------------------
-- 初始化-题库表数据
-- ----------------------------
insert into question_bank values(100,  0,   '0',          'CS专业课',      0, '0', 'admin', sysdate(), '', null);
insert into question_bank values(101,  100, '0,100',      '923操作系统',   1, '0', 'admin', sysdate(), '', null);
insert into question_bank values(102,  100, '0,100',      '925数据结构',   2, '0', 'admin', sysdate(), '', null);
insert into question_bank values(103,  101, '0,100,101',  '引论',         1, '0', 'admin', sysdate(), '', null);
insert into question_bank values(104,  101, '0,100,101',  '进程管理',      2, '0', 'admin', sysdate(), '', null);
insert into question_bank values(105,  101, '0,100,101',  '内存管理',      3, '0', 'admin', sysdate(), '', null);
insert into question_bank values(106,  101, '0,100,101',  '文件管理',      4, '0', 'admin', sysdate(), '', null);
insert into question_bank values(107,  101, '0,100,101',  '设备管理',      5, '0', 'admin', sysdate(), '', null);
insert into question_bank values(108,  102, '0,100,102',  '基础知识',      1, '0', 'admin', sysdate(), '', null);
insert into question_bank values(109,  102, '0,100,102',  '程序设计',      2, '0', 'admin', sysdate(), '', null);


-- ----------------------------
-- 二、题目信息表
-- ----------------------------
drop table if exists question;
create table question (
  question_id       bigint(20)      not null auto_increment    comment '题目ID',
  question_bank_id  bigint(20)      default null               comment '题库ID',
  type              varchar(2)      default '00'               comment '题目类型（00选判01多选02填空）',
  difficulty        char(1)         default '0'                comment '题目难度（0简单1一般2困难）',
  score             tinyint(2)      default null               comment '题目分值',
  content           text            default null               comment '题面',
  answer            varchar(128)    default ''                 comment '参考答案',
  analysis          varchar(500)    default ''                 comment '答案解析',
  status            char(1)         default '0'                comment '题目状态（0正常 1停用）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (question_id)
) engine=innodb auto_increment=100 comment = '题目信息表';


-- ----------------------------
-- 三、选项信息表
-- ----------------------------
drop table if exists option;
create table option (
  option_id         bigint(20)      not null auto_increment    comment '选项ID',
  content           text            default null               comment '选项内容',
  status            char(1)         default '0'                comment '状态（0正常 1停用）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (option_id)
) engine=innodb auto_increment=100 comment = '选项信息表';

-- ----------------------------
-- 初始化-选项表数据
-- ----------------------------
insert into option values(1,  '正确',     '0', 'admin', sysdate(), '', null);
insert into option values(2,  '错误',     '0', 'admin', sysdate(), '', null);
insert into option values(3,  '测试选项1', '0', 'admin', sysdate(), '', null);
insert into option values(4,  '测试选项2', '0', 'admin', sysdate(), '', null);
insert into option values(5,  '测试选项3', '0', 'admin', sysdate(), '', null);
insert into option values(6,  '测试选项4', '0', 'admin', sysdate(), '', null);


-- ----------------------------
-- 四、题目和选项关联表  题目1-N选项
-- ----------------------------
drop table if exists question_option;
create table question_option (
  question_id   bigint(20) not null comment '题目ID',
  option_id     bigint(20) not null comment '选项ID',
  primary key(question_id, option_id)
) engine=innodb comment = '题目和选项关联表';
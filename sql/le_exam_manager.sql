-- ----------------------------
-- 试卷管理一、试卷目录表（參考 dept）
-- ----------------------------
drop table if exists paper_menu;
create table paper_menu (
  paper_menu_id         bigint(20)      not null auto_increment    comment '试卷目录id',
  dept_id               bigint(20)      default null               comment '部门id',
  parent_id             bigint(20)      default 0                  comment '父节点id',
  ancestors             varchar(50)     default ''                 comment '祖级列表',
  paper_menu_name       varchar(30)     default ''                 comment '试卷目录名称',
  order_num             int(4)          default 0                  comment '显示顺序',
  create_by             varchar(64)     default ''                 comment '创建者',
  create_time 	        datetime                                   comment '创建时间',
  update_by             varchar(64)     default ''                 comment '更新者',
  update_time           datetime                                   comment '更新时间',
  primary key (paper_menu_id)
) engine=innodb comment = '试卷目录表';
insert into paper_menu values(1, 100, 0, '0',   '17级本科', 1, 'admin', sysdate(), '', null);
insert into paper_menu values(2, 100, 1, '0,1', '计科期末', 1, 'admin', sysdate(), '', null);


-- ----------------------------
-- 试卷管理二、试卷表
-- ----------------------------
drop table if exists paper;
create table paper (
  paper_id          bigint(20)      not null auto_increment    comment '试卷id',
  paper_menu_id     bigint(20)      default null               comment '试卷目录id',
  paper_name        varchar(30)     default ''                 comment '试卷名称',
  paper_url         varchar(100)    default ''                 comment '试卷资源地址',
  paper_type        int(2)          default 0                  comment '考试类型（0模拟1测试2正式考试对应监控等级）',
  notice            varchar(500)    default ''                 comment '考试通知',
  start_time        datetime                                   comment '开始时间',
  end_time          datetime                                   comment '结束时间',
  total_score       int(4)          default null               comment '试卷总分',
  passing_score     int(2)          default null               comment '试卷及格分',
  question_ooo      int(1)          default 0                  comment '题目乱序（0未用 1启用）',
  choice_ooo        int(1)          default 0                  comment '选项乱序（0未用 1启用）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default ''                 comment '备注',
  primary key (paper_id)
) engine=innodb comment = '试卷信息表';


-- ----------------------------
-- 试卷管理三、试卷和试题关联表
-- ----------------------------
drop table if exists paper_question;
create table paper_question (
  paper_id      bigint(20) not null comment '试卷ID',
  question_id   bigint(20) not null comment '试题ID',
  primary key(paper_id, question_id)
) engine=innodb comment = '试卷和试题关联表';


-- ----------------------------
-- 试卷管理四、试卷和考生组关联表
-- ----------------------------
drop table if exists paper_candidate_set;
create table paper_candidate_set (
  paper_id           bigint(20) not null comment '试卷ID',
  candidate_set_id   bigint(20) not null comment '考生组ID',
  primary key(paper_id, candidate_set_id)
) engine=innodb comment = '试卷和考生组关联表';


-- ----------------------------
-- 考试管理五、答卷表（todo 建立三种联合索引）
-- ----------------------------
drop table if exists candidate_answer;
create table candidate_answer (
  candidate_id   bigint(20)      not null       comment '考生id',
  paper_id       bigint(20)      not null       comment '试卷id',
  question_id    varchar(30)     not null       comment '题目id',
  content        text            default null   comment '考试答案',
  status         int(1)          default 0      comment '批改状态（0未 1已）',
  score          int(4)          default 0      comment '得分',
  create_by      varchar(64)     default ''     comment '创建者',
  create_time    datetime                       comment '创建时间',
  primary key (candidate_id, paper_id, question_id)
) engine=innodb comment = '考生答案表';

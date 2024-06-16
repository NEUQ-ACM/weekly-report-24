create table emp(
                    id  int auto_increment comment 'ID' primary key,
                    name varchar(50) not null comment '姓名',
                    age  int comment '年龄',
                    job varchar(20) comment '职位',
                    salary int comment '薪资',
                    entrydate date comment '入职时间',
                    managerid int comment '直属领导ID',
                    dept_id int comment '部门ID'
)comment '员工表';

INSERT INTO emp (id, name, age, job,salary, entrydate, managerid, dept_id) VALUES
                                                                               (1, '金庸', 66, '总裁',20000, '2000-01-01', null,5),

                                                                               (2, '张无忌', 20, '项目经理',12500, '2005-12-05', 1,1),
                                                                               (3, '杨逍', 33, '开发', 8400,'2000-11-03', 2,1),
                                                                               (4, '韦一笑', 48, '开发',11000, '2002-02-05', 2,1),
                                                                               (5, '常遇春', 43, '开发',10500, '2004-09-07', 3,1),
                                                                               (6, '小昭', 19, '程序员鼓励师',6600, '2004-10-12', 2,1),

                                                                               (7, '灭绝', 60, '财务总监',8500, '2002-09-12', 1,3),
                                                                               (8, '周芷若', 19, '会计',48000, '2006-06-02', 7,3),
                                                                               (9, '丁敏君', 23, '出纳',5250, '2009-05-13', 7,3),

                                                                               (10, '赵敏', 20, '市场部总监',12500, '2004-10-12', 1,2),
                                                                               (11, '鹿杖客', 56, '职员',3750, '2006-10-03', 10,2),
                                                                               (12, '鹤笔翁', 19, '职员',3750, '2007-05-09', 10,2),
                                                                               (13, '方东白', 19, '职员',5500, '2009-02-12', 10,2),

                                                                               (14, '张三丰', 88, '销售总监',14000, '2004-10-12', 1,4),
                                                                               (15, '俞莲舟', 38, '销售',4600, '2004-10-12', 14,4),
                                                                               (16, '宋远桥', 40, '销售',4600, '2004-10-12', 14,4),
                                                                               (17, '陈友谅', 42, null,2000, '2011-10-12', 1,null);

select * from emp where job='开发' and age between 20 and 40;
select  job ,COUNT(*) from emp where age<40 and salary>8000 group by  job;
select name, age ,entryDate from emp where age<=45 order by age asc, entrydate desc;
select name,age from emp where age>15 order by age asc ;
use mysql;
select * from user;
create user 'zihe'@'localhost' identified by '123456';
select * from user;
create user 'huangdizhe'@'%' identified by '1234';
alter user 'zihe'@'localhost' identified with mysql_native_password by '1';
show grants for 'zihe'@'localhost';
grant all on owenliu.* to 'zihe'@'localhost';
show grants for 'zihe'@'localhost';
revoke all on owenliu.* from 'zihe'@'localhost';
show grants for 'zihe'@'localhost';
select concat('hello',' mysql');
use owenliu;
update emp set id = lpad(id,5,'0');
select lpad(round(rand()*1000000,0),6,'0');
select curdate();
select curtime();
select name, datediff(curdate(),entryDate) from emp;
select
    name,
    (case job when '总裁' then '老大' when '项目经理' then '二把手' else '牛马' end) as 职务
from emp;
select name,(case  when salary>= 10000 then '人上人' when salary>=5000 and salary<10000 then '中产' else '牛马' end) as 薪资 from emp;
create table user(
                     id int primary key auto_increment comment '主键',
                     name varchar(10) not null unique comment '姓名',
                     age int check ( age>0 and age<=120 ) comment '年龄',
                     status char(1) default '1',
                     gender char(1)
) comment '用户表';
insert into user(name,age,status,gender) values ('owen',19,'1','男'),('zihe',22,'0','女');
insert into user(name, age, gender) VALUES ('June',21,'女');
create table dept1(
                      id   int auto_increment comment 'ID' primary key,
                      name varchar(50) not null comment '部门名称'
)comment '部门表';
INSERT INTO dept1 (id, name) VALUES (1, '研发部'), (2, '市场部'),(3, '财务部'), (4, '销售部'), (5, '总经办');


create table emp1(
                     id  int auto_increment comment 'ID' primary key,
                     name varchar(50) not null comment '姓名',
                     age  int comment '年龄',
                     job varchar(20) comment '职位',
                     salary int comment '薪资',
                     entrydate date comment '入职时间',
                     managerid int comment '直属领导ID',
                     dept_id int comment '部门ID'
)comment '员工表';

INSERT INTO emp1 (id, name, age, job,salary, entrydate, managerid, dept_id) VALUES
                                                                                (1, '金庸', 66, '总裁',20000, '2000-01-01', null,5),(2, '张无忌', 20, '项目经理',12500, '2005-12-05', 1,1),
                                                                                (3, '杨逍', 33, '开发', 8400,'2000-11-03', 2,1),(4, '韦一笑', 48, '开发',11000, '2002-02-05', 2,1),
                                                                                (5, '常遇春', 43, '开发',10500, '2004-09-07', 3,1),(6, '小昭', 19, '程序员鼓励师',6600, '2004-10-12', 2,1);
alter table emp1 add constraint fk_emp1_demt1_id foreign key (dept_id) references dept1(id);
alter table emp1 drop foreign key fk_emp1_demt1_id;

insert into employee(id, workno, name, gender, age, idcard, entrydate)  values(1,'1','itheima','男',10,'123456789012345678','2000-01-01');
-- 添加--
insert into employee values(2,'2','小昭','女',18,'123456789012345671','2005-01-01');
insert into employee values(3,'1','赵敏','女',18,'123456789012345672','2005-01-01');
insert into employee values(4,'1','张三丰','男',20,'123456789012345673','2005-01-01');
insert into employee values(5,'1','周芷若','女',null,'123456789012345673','2005-01-01');


delete from employee where gender='女';

-- 修改，将所有员工的入职日期都进行修改--
update employee set entrydate='2008-01-01';

-- 删除客户id为1的员工--
delete from employee where id = 1;

select name,workno,age from employee;
-- 查找所有员工--
select *from employee;

-- 将id显示为工号并进行查找--
select id as '工号' from employee;
-- 查找时合并重复项--
select distinct  workno from employee;

select *from employee where age=18;
-- 查找空年龄为空的--
select *from employee where age is null;

select *from employee where age is not null;
-- 查找年龄在15到19岁之间的员工--
select *from employee where age>=15&&age<=19;
select *from employee where age>=15 and age<=19;
select *from employee where age between 15 and 19;

select *from employee where age=18 or age=29;
select *from employee where age in(18,20);

select *from employee where name like '__';

select *from employee where idcard like '%2';
select *from employee where idcard like '_________________2';


select count(*) from employee;

select count(age) from employee;

select max(age) from employee;

select avg(age) from employee;

select sum(age) from employee where workno=1;

select gender,count(*) from employee group by gender;

select gender,avg(age) from employee group by gender;

select id ,count(*) from employee where age<20 group by gender;

select * from employee order by age asc;
select * from employee order by age desc;

select * from employee order by age asc, entrydate desc;

-- 分页查询--
-- 查询第一页员工数据，每页展示10 条--
select * from employee limit 0,10;

select * from employee limit 10,10;

-- 查询第2页员工数据，每页展示十条记录，（页码-1）*页展示记录
select * from employee limit 2,1;


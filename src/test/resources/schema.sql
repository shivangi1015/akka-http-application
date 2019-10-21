DROP TABLE IF EXISTS users;

create table users(
id int primary key,
fname varchar(20),
lname varchar(20),
age int,
gender varchar(5),
state varchar(10),
country varchar(20)
);

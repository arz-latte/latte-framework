# latte
Latte Integration Framework

create db latte on postgresql:

create user latte with password 'latte';
create database latte;
grant all privileges on database latte to latte;

run JUnit ModuleTest
execut tomcat_view.sql
run latte-framework
run latte-demo-modul-1
run role_init.sql
# latte
Latte Integration Framework

<h3>Database Configuration</h3>
create db latte on postgresql:

create user latte with password 'latte';<br/>
create database latte;<br/>
grant all privileges on database latte to latte;<br/>
execute initialize.sql

<h3>Deploy and run application</h3>
run 'latte-framework' and 'latte-demo-modul-1'

either log in as administrator with admin@arz.at / admin<br/>
or log in as user with user@arz.at / user

<h3>Module-Configuration 'service-config.xml':</h3>
name: module name, e.g. Demo Modul 1<br/>
url: link when clicking on module, e.g. http://localhost:8080/demo1/index.html<br/>
order: sorting order for module, e.g. 10<br/>
submenu:<br/>
- name: menu name<br/>
- url: optional menu link either relativ or absolute path (automatically uses # if missing, e.g index.html<br/>
- script: optional on click function, only in combination with missing url or '#', e.g. appDemo.demoFunction("ok")<br/>
- permission: optional permission for this entry<br/>
- disabled: optional disable this menu entry<br/>
- type: optional type class to this menu entry, separate multiple values with comma ','<br/>
- group: optional group class to this menu entry, separate multiple values with comma ','<br/>
- submenu: optional recursive sub menu structure<br/>
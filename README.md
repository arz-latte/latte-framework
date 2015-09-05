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
<code>
<menu>
    <name>Demo Modul 1</name>
    <url>http://localhost:8080/demo1/index.html</url>
    <order>10</order>
    <submenu>
		<name>SubMenü für user</name>			
		
		<!--  optional: relativ or absolute path (uses # if missing) -->
		<url>index.html</url>
		
		<!--  optional: on click function: only in combination with missing url or '#' -->
		<script>appDemo.demoFunction("ok")</script>
		
		<!--  optional: required permission for this entry -->
		<permission>user</permission>
		
		<!--  optional: disable this menu entry -->
		<disabled>false</disabled>
		
		<!--  optional: add type class to this menu entry -->
		<type>t</type>
		
		<!--  optional: add type class to this menu entry -->
		<group>g</group>

		<!-- optional: recursive sub menu structure -->
		<submenu>
			<!-- same as above -->
	    </submenu>
    </submenu>
</menu>
</code>


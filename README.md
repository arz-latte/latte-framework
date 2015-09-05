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
&lt;menu&gt;
    &lt;name&gt;Demo Modul 1&lt;/name&gt;
    &lt;url&gt;http://localhost:8080/demo1/index.html&lt;/url&gt;
    &lt;order&gt;10&lt;/order&gt;
    &lt;submenu&gt;
		&lt;name&gt;SubMenü für user&lt;/name&gt;			
		
		&lt;!--  optional: relativ or absolute path (uses # if missing) --&gt;
		&lt;url&gt;index.html&lt;/url&gt;
		
		&lt;!--  optional: on click function: only in combination with missing url or '#' --&gt;
		&lt;script&gt;appDemo.demoFunction("ok")&lt;/script&gt;
		
		&lt;!--  optional: required permission for this entry --&gt;
		&lt;permission&gt;user&lt;/permission&gt;
		
		&lt;!--  optional: disable this menu entry --&gt;
		&lt;disabled&gt;false&lt;/disabled&gt;
		
		&lt;!--  optional: add type class to this menu entry --&gt;
		&lt;type&gt;t&lt;/type&gt;
		
		&lt;!--  optional: add type class to this menu entry --&gt;
		&lt;group&gt;g&lt;/group&gt;

		&lt;!-- optional: recursive sub menu structure --&gt;
		&lt;submenu&gt;
			&lt;!-- same as above --&gt;
	    &lt;/submenu&gt;
    &lt;/submenu&gt;
&lt;/menu&gt;
</code>


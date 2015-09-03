create view tc_realm_roles as
select 
  users.username as username,
  roles.name as rolename
from users 
left join (
        select 
                users_roles.user_id, roles.name 
        from roles
        left join 
                users_roles 
                on users_roles.role_id=roles.id
) as roles on roles.user_id=id;

create view tc_realm_users as
select 
  username, password
from users;

ALTER VIEW tc_realm_users OWNER TO latte;
ALTER VIEW tc_realm_roles OWNER TO latte;
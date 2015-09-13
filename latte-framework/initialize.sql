--
-- pg_dump -U latte --column-inserts latte > backup.sql
--
--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: latte_seq; Type: TABLE; Schema: public; Owner: latte; Tablespace: 
--

CREATE TABLE latte_seq (
    key0 character varying(255) NOT NULL,
    value0 bigint
);


ALTER TABLE latte_seq OWNER TO latte;

--
-- Name: menus; Type: TABLE; Schema: public; Owner: latte; Tablespace: 
--

CREATE TABLE menus (
    id bigint NOT NULL,
    lastmodified bigint,
    name character varying(255),
    order0 integer,
    url character varying(255),
    version bigint,
    permission_id bigint
);


ALTER TABLE menus OWNER TO latte;

--
-- Name: modules; Type: TABLE; Schema: public; Owner: latte; Tablespace: 
--

CREATE TABLE modules (
    id bigint NOT NULL,
    enabled boolean,
    "interval" integer,
    lastmodified bigint,
    name character varying(255),
    provider character varying(255),
    running boolean,
    url character varying(255),
    version bigint,
    menu_id bigint
);


ALTER TABLE modules OWNER TO latte;

--
-- Name: permissions; Type: TABLE; Schema: public; Owner: latte; Tablespace: 
--

CREATE TABLE permissions (
    id bigint NOT NULL,
    name character varying(255),
    version bigint
);


ALTER TABLE permissions OWNER TO latte;

--
-- Name: roles; Type: TABLE; Schema: public; Owner: latte; Tablespace: 
--

CREATE TABLE roles (
    id bigint NOT NULL,
    name character varying(255),
    version bigint
);


ALTER TABLE roles OWNER TO latte;

--
-- Name: roles_permissions; Type: TABLE; Schema: public; Owner: latte; Tablespace: 
--

CREATE TABLE roles_permissions (
    role_id bigint,
    permission_id bigint
);


ALTER TABLE roles_permissions OWNER TO latte;

--
-- Name: submenus; Type: TABLE; Schema: public; Owner: latte; Tablespace: 
--

CREATE TABLE submenus (
    id bigint NOT NULL,
    disabled boolean,
    group0 character varying(255),
    name character varying(255),
    order0 integer,
    script character varying(511),
    type character varying(255),
    url character varying(511),
    version bigint,
    menu_id bigint,
    permission_id bigint,
    submenu_id bigint
);


ALTER TABLE submenus OWNER TO latte;

--
-- Name: users; Type: TABLE; Schema: public; Owner: latte; Tablespace: 
--

CREATE TABLE users (
    id bigint NOT NULL,
    email character varying(255),
    firstname character varying(255),
    lastname character varying(255),
    password character varying(255),
    version bigint
);


ALTER TABLE users OWNER TO latte;

--
-- Name: users_roles; Type: TABLE; Schema: public; Owner: latte; Tablespace: 
--

CREATE TABLE users_roles (
    user_id bigint,
    role_id bigint
);


ALTER TABLE users_roles OWNER TO latte;

--
-- Name: tc_realm_roles; Type: VIEW; Schema: public; Owner: latte
--

CREATE VIEW tc_realm_roles AS
 SELECT users.email AS username,
    roles.name AS rolename
   FROM (users
     LEFT JOIN ( SELECT users_roles.user_id,
            roles_1.name
           FROM (roles roles_1
             LEFT JOIN users_roles ON ((users_roles.role_id = roles_1.id)))) roles ON ((roles.user_id = users.id)));


ALTER TABLE tc_realm_roles OWNER TO latte;

--
-- Name: tc_realm_users; Type: VIEW; Schema: public; Owner: latte
--

CREATE VIEW tc_realm_users AS
 SELECT users.email AS username,
    users.password
   FROM users;


ALTER TABLE tc_realm_users OWNER TO latte;

--
-- Data for Name: latte_seq; Type: TABLE DATA; Schema: public; Owner: latte
--

INSERT INTO latte_seq (key0, value0) VALUES ('Role.ID', 50);
INSERT INTO latte_seq (key0, value0) VALUES ('User.ID', 50);
INSERT INTO latte_seq (key0, value0) VALUES ('Module.ID', 50);
INSERT INTO latte_seq (key0, value0) VALUES ('Menu.ID', 50);
INSERT INTO latte_seq (key0, value0) VALUES ('SubMenu.ID', 50);
INSERT INTO latte_seq (key0, value0) VALUES ('Permission.ID', 50);


--
-- Data for Name: menus; Type: TABLE DATA; Schema: public; Owner: latte
--

INSERT INTO menus (id, lastmodified, name, order0, url, version, permission_id) VALUES (2, 1441731900694, 'Demo Modul 1', 10, '/demo1/index.html', 94, NULL);
INSERT INTO menus (id, lastmodified, name, order0, url, version, permission_id) VALUES (1, 1441731842613, 'Administration', 20, '/latte/index.html', 95, NULL);


--
-- Data for Name: modules; Type: TABLE DATA; Schema: public; Owner: latte
--

INSERT INTO modules (id, enabled, "interval", lastmodified, name, provider, running, url, version, menu_id) VALUES (2, true, 10, 1441731900694, 'Demo Modul 1', 'ARZ', true, 'http://localhost:8080/demo1/api/v1/demo', 97, 2);
INSERT INTO modules (id, enabled, "interval", lastmodified, name, provider, running, url, version, menu_id) VALUES (1, true, 10, 1441731842613, 'Administration', 'ARZ', true, 'http://localhost:8080/latte/api/v1/administration', 96, 1);


--
-- Data for Name: permissions; Type: TABLE DATA; Schema: public; Owner: latte
--

INSERT INTO permissions (id, name, version) VALUES (1, 'admin', 1);
INSERT INTO permissions (id, name, version) VALUES (2, 'user', 1);


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: latte
--

INSERT INTO roles (id, name, version) VALUES (1, 'tomcat', 1);
INSERT INTO roles (id, name, version) VALUES (2, 'Administrator', 1);
INSERT INTO roles (id, name, version) VALUES (3, 'Benutzer', 1);


--
-- Data for Name: roles_permissions; Type: TABLE DATA; Schema: public; Owner: latte
--

INSERT INTO roles_permissions (role_id, permission_id) VALUES (2, 1);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (3, 2);


--
-- Data for Name: submenus; Type: TABLE DATA; Schema: public; Owner: latte
--

INSERT INTO submenus (id, disabled, group0, name, order0, script, type, url, version, menu_id, permission_id, submenu_id) VALUES (4, NULL, NULL, 'Demo Modul', 0, NULL, NULL, '/demo1/index.html', 1, 2, NULL, NULL);
INSERT INTO submenus (id, disabled, group0, name, order0, script, type, url, version, menu_id, permission_id, submenu_id) VALUES (9, true, NULL, 'Deaktiviert', 2, NULL, 'war', NULL, 1, 2, NULL, NULL);
INSERT INTO submenus (id, disabled, group0, name, order0, script, type, url, version, menu_id, permission_id, submenu_id) VALUES (8, false, NULL, 'Men� 2', 1, NULL, NULL, NULL, 1, 2, NULL, NULL);
INSERT INTO submenus (id, disabled, group0, name, order0, script, type, url, version, menu_id, permission_id, submenu_id) VALUES (10, NULL, NULL, 'Kompilieren', 3, NULL, NULL, NULL, 1, 2, NULL, NULL);
INSERT INTO submenus (id, disabled, group0, name, order0, script, type, url, version, menu_id, permission_id, submenu_id) VALUES (6, NULL, NULL, 'SubMen� 2', 1, NULL, NULL, NULL, 1, NULL, NULL, 4);
INSERT INTO submenus (id, disabled, group0, name, order0, script, type, url, version, menu_id, permission_id, submenu_id) VALUES (12, NULL, NULL, 'WAR', 1, 'appDemo.functionTest("ok")', 'war', NULL, 1, NULL, NULL, 10);
INSERT INTO submenus (id, disabled, group0, name, order0, script, type, url, version, menu_id, permission_id, submenu_id) VALUES (11, NULL, NULL, 'EAR', 0, 'alert("ok")', 'ear', NULL, 1, NULL, NULL, 10);
INSERT INTO submenus (id, disabled, group0, name, order0, script, type, url, version, menu_id, permission_id, submenu_id) VALUES (5, NULL, NULL, 'SubMen� f�r user', 0, NULL, NULL, '/demo1/api/v1/demo/useronly.json', 1, NULL, 2, 4);
INSERT INTO submenus (id, disabled, group0, name, order0, script, type, url, version, menu_id, permission_id, submenu_id) VALUES (13, NULL, NULL, 'EAR & WAR', 2, NULL, 'ear,war', NULL, 1, NULL, NULL, 10);
INSERT INTO submenus (id, disabled, group0, name, order0, script, type, url, version, menu_id, permission_id, submenu_id) VALUES (7, NULL, NULL, 'SubSubMen� f�r admin', 0, NULL, NULL, NULL, 1, NULL, 1, 6);
INSERT INTO submenus (id, disabled, group0, name, order0, script, type, url, version, menu_id, permission_id, submenu_id) VALUES (3, NULL, NULL, 'Rollen', 2, NULL, NULL, '/latte/role.html', 2, 1, 1, NULL);
INSERT INTO submenus (id, disabled, group0, name, order0, script, type, url, version, menu_id, permission_id, submenu_id) VALUES (2, NULL, NULL, 'Benutzer', 1, NULL, NULL, '/latte/user.html', 2, 1, 1, NULL);
INSERT INTO submenus (id, disabled, group0, name, order0, script, type, url, version, menu_id, permission_id, submenu_id) VALUES (1, NULL, NULL, 'Module', 0, NULL, NULL, '/latte/module.html', 2, 1, 1, NULL);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: latte
--

INSERT INTO users (id, email, firstname, lastname, password, version) VALUES (1, 'admin@arz.at', 'Admin', 'Admin', 'admin', 1);
INSERT INTO users (id, email, firstname, lastname, password, version) VALUES (2, 'user@arz.at', 'User', 'User', 'user', 1);


--
-- Data for Name: users_roles; Type: TABLE DATA; Schema: public; Owner: latte
--

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 3);


--
-- Name: latte_seq_pkey; Type: CONSTRAINT; Schema: public; Owner: latte; Tablespace: 
--

ALTER TABLE ONLY latte_seq
    ADD CONSTRAINT latte_seq_pkey PRIMARY KEY (key0);


--
-- Name: menus_pkey; Type: CONSTRAINT; Schema: public; Owner: latte; Tablespace: 
--

ALTER TABLE ONLY menus
    ADD CONSTRAINT menus_pkey PRIMARY KEY (id);


--
-- Name: modules_pkey; Type: CONSTRAINT; Schema: public; Owner: latte; Tablespace: 
--

ALTER TABLE ONLY modules
    ADD CONSTRAINT modules_pkey PRIMARY KEY (id);


--
-- Name: permissions_pkey; Type: CONSTRAINT; Schema: public; Owner: latte; Tablespace: 
--

ALTER TABLE ONLY permissions
    ADD CONSTRAINT permissions_pkey PRIMARY KEY (id);


--
-- Name: roles_pkey; Type: CONSTRAINT; Schema: public; Owner: latte; Tablespace: 
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: submenus_pkey; Type: CONSTRAINT; Schema: public; Owner: latte; Tablespace: 
--

ALTER TABLE ONLY submenus
    ADD CONSTRAINT submenus_pkey PRIMARY KEY (id);


--
-- Name: u_prmssns_name; Type: CONSTRAINT; Schema: public; Owner: latte; Tablespace: 
--

ALTER TABLE ONLY permissions
    ADD CONSTRAINT u_prmssns_name UNIQUE (name);


--
-- Name: u_roles_name; Type: CONSTRAINT; Schema: public; Owner: latte; Tablespace: 
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT u_roles_name UNIQUE (name);


--
-- Name: u_users_email; Type: CONSTRAINT; Schema: public; Owner: latte; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT u_users_email UNIQUE (email);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: latte; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: menus_permission_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: latte
--

ALTER TABLE ONLY menus
    ADD CONSTRAINT menus_permission_id_fkey FOREIGN KEY (permission_id) REFERENCES permissions(id) DEFERRABLE;


--
-- Name: modules_menu_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: latte
--

ALTER TABLE ONLY modules
    ADD CONSTRAINT modules_menu_id_fkey FOREIGN KEY (menu_id) REFERENCES menus(id) DEFERRABLE;


--
-- Name: roles_permissions_permission_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: latte
--

ALTER TABLE ONLY roles_permissions
    ADD CONSTRAINT roles_permissions_permission_id_fkey FOREIGN KEY (permission_id) REFERENCES permissions(id) DEFERRABLE;


--
-- Name: roles_permissions_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: latte
--

ALTER TABLE ONLY roles_permissions
    ADD CONSTRAINT roles_permissions_role_id_fkey FOREIGN KEY (role_id) REFERENCES roles(id) DEFERRABLE;


--
-- Name: submenus_menu_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: latte
--

ALTER TABLE ONLY submenus
    ADD CONSTRAINT submenus_menu_id_fkey FOREIGN KEY (menu_id) REFERENCES menus(id) DEFERRABLE;


--
-- Name: submenus_permission_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: latte
--

ALTER TABLE ONLY submenus
    ADD CONSTRAINT submenus_permission_id_fkey FOREIGN KEY (permission_id) REFERENCES permissions(id) DEFERRABLE;


--
-- Name: submenus_submenu_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: latte
--

ALTER TABLE ONLY submenus
    ADD CONSTRAINT submenus_submenu_id_fkey FOREIGN KEY (submenu_id) REFERENCES submenus(id) DEFERRABLE;


--
-- Name: users_roles_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: latte
--

ALTER TABLE ONLY users_roles
    ADD CONSTRAINT users_roles_role_id_fkey FOREIGN KEY (role_id) REFERENCES roles(id) DEFERRABLE;


--
-- Name: users_roles_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: latte
--

ALTER TABLE ONLY users_roles
    ADD CONSTRAINT users_roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id) DEFERRABLE;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO latte;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--
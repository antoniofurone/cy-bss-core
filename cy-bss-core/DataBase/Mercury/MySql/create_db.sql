-- with root
--ALTER USER USER() IDENTIFIED BY 'xxxxx';
--ALTER USER 'root'@'localhost' PASSWORD EXPIRE NEVER;
create database cybss;
create user cybss identified by 'cybss';
set password for 'cybss'@'%' = password('cybss');
grant all privileges on cybss.* to 'cybss'@'localhost' identified by 'cybss' with grant option;

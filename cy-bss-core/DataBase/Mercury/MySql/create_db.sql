--To initialize the data directory, invoke mysqld with the --initialize or --initialize-insecure option, depending on 
--whether you want the server to generate a random initial password for the 'root'@'localhost' account. 

-- with root
--ALTER USER USER() IDENTIFIED BY 'xxxxx';
--ALTER USER 'root'@'localhost' PASSWORD EXPIRE NEVER;

create database cybss;
create user cybss identified by 'cybss';
set password for 'cybss'@'%' = password('cybss');
grant all privileges on cybss.* to 'cybss'@'localhost' identified by 'cybss' with grant option;


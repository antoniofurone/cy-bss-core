Cy-bss-core is a core business service system that provides a set of common services to support the development of business apps.
The services are provided via rest web services. 
Cy-bss-core is based on a set of open source products: Spring, LogBack, AngularJs, Bootstrap.
Pruduct requires JRE 1.7 or above, servlet container (has been tested using Tomcat 7), and MySql 5.7 or above.
Althought the project is at initial phase and only a small set of services and operations are implemented, its are already useful to develope various types of app. Urbanbot (https://github.com/antoniofurone/cy-urbanbot.git) is an example of this app.

Installation
------------ 
1) Download zip from https://github.com/antoniofurone/cy-bss-core.git (or import project in Eclipse);
2) After MySql installation, create db and related objects running (in sequence) the following script: 
	- DataBase/MySql -> create_db.sql with root user;
	- access using cybss user (for example: mysql -u cybss -p), select database cybss (digit: use cybss;), and run script under
		DataBase/MySql/tables from cr_tb_01_*.sql to cr_tb_<n>_*.sql;
	- DataBase/MySql/setup -> run script from load_tb_01_*.sql to load_tb_<n>_*.sql;	
	- DataBase/MySql/config -> run script from config_01_*.sql to config_<n>_*.sql;
	- DataBase/MySql/views -> run script from cr_vw_01_*.sql to cr_vw_<n>_*.sql;
3) go in src/LogBack.xml and change log folder: es. <property name="USER_HOME" value="/home/user1/cy-bss-core/logs" />
4) go in src/cy-bss-core.properties and change parameters for db connection: es.
	mysql.url=jdbc:mysql://localhost:3306/cybss
	mysql.driver=com.mysql.jdbc.Driver
	mysql.user=cybss
	mysql.psw=cybss
5) After JRE, Tomcat and ANT installations (set env variables JAVA_HOME and CATALINA_HOME), from root folder of project 
(contains build.xml) run ant;
6) Deploy cy-bss-core.war to Tomcat.

Try Services
------------
If the installation is successful completed at http://<address>:<port>/cy-bss-core you can try the services.

 
	
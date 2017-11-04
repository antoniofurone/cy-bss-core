Cy-bss-core is a core business service system that provides a set of common services to support the development of business apps.
The services are provided via rest web services. 
Cy-bss-core is based on a set of open source products: Spring, LogBack, AngularJs, Bootstrap,...
Pruduct requires JRE 1.8 or above, servlet container (has been tested using Tomcat 8), and MySql 5.7 or above.
Urbanbot (https://github.com/antoniofurone/cy-urbanbot.git) is an app example based on cy-bss-core.

Installation
------------ 
1) Download zip from https://github.com/antoniofurone/cy-bss-core.git (or import project in Eclipse);
2) After MySql installation, create db and related objects running (in sequence) the following script: 
	- DataBase/Mercury/MySql -> create_db.sql with root user;
	- access using cybss user (for example: mysql -u cybss -p), select database cybss (digit: use cybss;), and run script under
		DataBase/{Version}/MySql/tables from cr_tb_01_*.sql to cr_tb_<n>_*.sql;
	- DataBase/{Version}/MySql/setup -> run script from load_tb_01_*.sql to load_tb_<n>_*.sql;	
	- DataBase/{Version}/MySql/config -> run script from config_01_*.sql to config_<n>_*.sql;
	- DataBase/{Version}/MySql/views -> run script from cr_vw_01_*.sql to cr_vw_<n>_*.sql.
	The version order to run scripts is: Mercury, Venus.
3) Set app home directory in environment variable CYBUSINESS_HOME (e.g. c:\CyBusiness). If you don't do it, app log will write in user home directory.
4) go in src/cy-bss-core.properties and change parameters for db connection. Default is: 
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

	
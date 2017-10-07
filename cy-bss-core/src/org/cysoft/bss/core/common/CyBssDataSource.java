package org.cysoft.bss.core.common;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

public class CyBssDataSource extends BasicDataSource{
	
	private static final Logger logger = LoggerFactory.getLogger(CyBssDataSource.class);
	
	public CyBssDataSource(Environment environment){
		super();
		logger.info("CyBssDataSource() >>>");
		
		String driver=environment.getProperty("mysql.driver");
		logger.info("mysql.driver="+driver);
		
		String dbUrl=System.getenv("CYBUSINESS_DB_URL");
		if (dbUrl==null || dbUrl.equals(""))
			dbUrl=environment.getProperty("mysql.url");
		logger.info("mysql.url="+dbUrl);
		
		String dbUser=System.getenv("CYBUSINESS_DB_USER");
		if (dbUser==null || dbUser.equals(""))
			dbUser=environment.getProperty("mysql.user");
		logger.info("mysql.user="+dbUser);
		
		String dbPsw=System.getenv("CYBUSINESS_DB_PSW");
		if (dbPsw==null || dbPsw.equals(""))
			dbPsw=environment.getProperty("mysql.psw");
		 
		setDriverClassName(driver);
	    setUrl(dbUrl);
	    setUsername(dbUser);
        setPassword(dbPsw);
        
        setTestOnBorrow(true);
        setValidationQuery("select 1");
	     
	
		logger.info("CyBssDataSource() <<<");
	}
	
}


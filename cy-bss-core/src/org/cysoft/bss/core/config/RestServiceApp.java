package org.cysoft.bss.core.config;


import org.cysoft.bss.core.common.CyBssDataSource;
import org.cysoft.bss.core.dao.BssServiceDao;
import org.cysoft.bss.core.dao.mysql.BssServiceMysql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
//import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan({"org.cysoft.bss.core.web.rest"})
@PropertySource("classpath:cy-bss-core.properties")
@EnableWebMvc

public class RestServiceApp {
	private static final Logger logger = LoggerFactory.getLogger(RestServiceApp.class);
	
	
	@Autowired
	Environment environment;
	
	 /*
	 @Bean
	 @Description("Message Source")
	 public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource ms=new ReloadableResourceBundleMessageSource();
		ms.setBasename("/WEB-INF/messages/messages");
		ms.setDefaultEncoding("UTF-8");
		return ms;
	 }
	 */
	
	 @Bean
	 @Description("Service Dao")
	 public BssServiceDao categoryDao(){
		 	BssServiceDao serviceDao=new BssServiceMysql();
			return serviceDao;
		 }
	
    
	 @Bean
	 @Description("MySql Data Source")
	 public CyBssDataSource mySqlDS() {
		 
		 logger.info("CyErpApp.mySqlDS() >>>");
		
		 CyBssDataSource mySql = new CyBssDataSource();
		 String driver=environment.getProperty("mysql.driver");
		 logger.info("mysql.driver="+driver);
		 String url=environment.getProperty("mysql.url");
		 logger.info("mysql.url="+url);
		 String user=environment.getProperty("mysql.user");
		 logger.info("mysql.user="+user);
		 String psw=environment.getProperty("mysql.psw");
		 
		 mySql.setDriverClassName(driver);
	     mySql.setUrl(url);
	     mySql.setUsername(user);
         mySql.setPassword(psw);
        
         logger.info("CyOpcApp.mySqlDS() <<<");
 		
         
		 return mySql;
	 }
	
	
	 public static void main(String[] args) {
        
    	logger.info("Start Rest Application ...");
    	SpringApplication.run(RestServiceApp.class, args);
    	
    }
    
}
    
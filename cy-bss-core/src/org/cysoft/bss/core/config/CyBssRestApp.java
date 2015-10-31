package org.cysoft.bss.core.config;


import org.cysoft.bss.core.common.CyBssDataSource;
import org.cysoft.bss.core.dao.AppDao;
import org.cysoft.bss.core.dao.CyBssAuthDao;
import org.cysoft.bss.core.dao.LanguageDao;
import org.cysoft.bss.core.dao.CyBssServiceDao;
import org.cysoft.bss.core.dao.UserDao;
import org.cysoft.bss.core.dao.mysql.AppMysql;
import org.cysoft.bss.core.dao.mysql.CyBssAuthMysql;
import org.cysoft.bss.core.dao.mysql.LanguageMysql;
import org.cysoft.bss.core.dao.mysql.CyBssServiceMysql;
import org.cysoft.bss.core.dao.mysql.UserMysql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.MessageSource;
//import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
//import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan({"org.cysoft.bss.core.web.service.rest"})
@PropertySource("classpath:cy-bss-core.properties")
@EnableWebMvc
@EnableAsync
@EnableScheduling

public class CyBssRestApp {
	private static final Logger logger = LoggerFactory.getLogger(CyBssRestApp.class);
	
	
	@Autowired
	Environment environment;
	
	@Autowired
	CyBssAuthDao authDao;
	
	
	 @Bean
	 @Description("Message Source Rest")
	 public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource ms=new ReloadableResourceBundleMessageSource();
		ms.setBasename("/WEB-INF/messages/messages");
		ms.setDefaultEncoding("UTF-8");
		return ms;
	 }
	
	 @Bean
	 @Description("Bss Service Dao")
	 public CyBssServiceDao bssServiceDao(){
		 	CyBssServiceDao serviceDao=new CyBssServiceMysql();
			return serviceDao;
		 }
	
	 @Bean
	 @Description("Bss Auth Dao Rest")
	 public CyBssAuthDao bssAuthDao(){
		 	CyBssAuthDao authDao=new CyBssAuthMysql();
			return authDao;
		 }
	
	 @Bean
	 @Description("Language Dao Rest")
	 public LanguageDao languageDao(){
		 	LanguageDao langDao=new LanguageMysql();
			return langDao;
		 }
	 
	 @Bean
	 @Description("App Dao Rest")
	 public AppDao appDao(){
		 	AppDao appDao=new AppMysql();
			return appDao;
		 }
	
	 
	 
	 @Bean
	 @Description("User Dao Rest")
	 public UserDao userDao(){
		 	UserDao userDao=new UserMysql();
			return userDao;
		 }
	
	 
	 
	 @Bean
	 @Description("MySql Data Source Rest ")
	 public CyBssDataSource mySqlDS() {
		 
		 logger.info("CyBssRestApp.mySqlDS() >>>");
		
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
        
         logger.info("CyBssRestApp.mySqlDS() <<<");
 		
         
		 return mySql;
	 }
	
	
	 @Scheduled(fixedDelay=180000)
	 public void discardSessions() {
	     // something that should execute periodically
		 logger.info("CyBssRestApp.discardSessions() >>>");
		 authDao.discardSessions();
		 logger.info("CyBssRestApp.discardSessions() <<<");
		 
	 }
	 
	 
	 public static void main(String[] args) {
        
    	logger.info("Start Rest Application ...");
    	SpringApplication.run(CyBssRestApp.class, args);
    	
    }
    
}
    
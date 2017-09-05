package org.cysoft.bss.core.config;

import javax.servlet.MultipartConfigElement;

import org.cysoft.bss.core.common.CyBssDataSource;
import org.cysoft.bss.core.common.CyBssMessageSource;
import org.cysoft.bss.core.dao.AppDao;
import org.cysoft.bss.core.dao.CyBssAuthDao;
import org.cysoft.bss.core.dao.FileDao;
import org.cysoft.bss.core.dao.LanguageDao;
import org.cysoft.bss.core.dao.UserDao;
import org.cysoft.bss.core.dao.mysql.AppMysql;
import org.cysoft.bss.core.dao.mysql.CyBssAuthMysql;
import org.cysoft.bss.core.dao.mysql.FileMysql;
import org.cysoft.bss.core.dao.mysql.LanguageMysql;
import org.cysoft.bss.core.dao.mysql.UserMysql;
import org.cysoft.bss.core.service.AppService;
import org.cysoft.bss.core.service.CyBssAuthService;
import org.cysoft.bss.core.service.LanguageService;
import org.cysoft.bss.core.service.UserService;
import org.cysoft.bss.core.service.impl.AppServiceImpl;
import org.cysoft.bss.core.service.impl.CyBssAuthServiceImpl;
import org.cysoft.bss.core.service.impl.LanguageServiceImpl;
import org.cysoft.bss.core.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan({"org.cysoft.bss.core.web.service.file"})
@PropertySource("classpath:cy-bss-core.properties")
public class CyBssFileApp {
private static final Logger logger = LoggerFactory.getLogger(CyBssFileApp.class);
	
	@Autowired
	Environment environment;
	
	
	@Bean
	@Description("Message Source File")
	 public CyBssMessageSource messageSource(){
		CyBssMessageSource ms=new CyBssMessageSource();
		ms.setBasename("/WEB-INF/messages/messages");
		ms.setDefaultEncoding("UTF-8");
		return ms;
	 }
	
	 @Bean
	 @Description("File Dao")
	 public FileDao fileDao(){
		 	FileDao fileDao=new FileMysql();
			return fileDao;
		 }
	
	
	 @Bean
	 @Description("Bss Auth Dao File")
	 public CyBssAuthDao bssAuthDao(){
		 	CyBssAuthDao authDao=new CyBssAuthMysql();
			return authDao;
		 }
	 
	
	 @Bean
	 @Description("Language Dao File")
	 public LanguageDao languageDao(){
		 	LanguageDao langDao=new LanguageMysql();
			return langDao;
		 }
	 
	 @Bean
	 @Description("App Dao File")
	 public AppDao appDao(){
		 	AppDao appDao=new AppMysql();
			return appDao;
		 }
	 
	 @Bean
	 @Description("User Dao File")
	 public UserDao userDao(){
		 	UserDao userDao=new UserMysql();
			return userDao;
		 }
	 
	 @Bean
	 @Description("App Service File")
	 public AppService appService(){
		 	AppService appService=new AppServiceImpl();
			return appService;
		 }
	 
	 @Bean
	 @Description("Auth Service File")
	 public CyBssAuthService authService(){
		 	CyBssAuthService authService=new CyBssAuthServiceImpl();
			return authService;
		 }
	 
	 @Bean
	 @Description("User Service File")
	 public UserService userService(){
		 	UserService userService=new UserServiceImpl();
			return userService;
		 }
	 
	 @Bean
	 @Description("Language Service File")
	 public LanguageService languageService(){
		 	LanguageService languageService=new LanguageServiceImpl();
			return languageService;
		 }
	 
	 @Bean
	 @Description("MySql Data Source File ")
	 public CyBssDataSource mySqlDS() {
		 
		 logger.info("CyBssFileApp.mySqlDS() >>>");
		
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
         
         mySql.setTestOnBorrow(true);
         mySql.setValidationQuery("select 1");
        
         logger.info("CyBssFileApp.mySqlDS() <<<");
 		
         
		 return mySql;
	 }
	
	 
	 @Bean
	 @Description("Transaction Manager File ")
	 public DataSourceTransactionManager transactionManager() {
		 logger.info("CyBssRestApp.transactionManager() >>>");
		 DataSourceTransactionManager transactionManager=new DataSourceTransactionManager(mySqlDS());
		 logger.info("CyBssRestApp.transactionManager() <<<");
 		 return transactionManager;
	 }
	 
	@Bean
	@Description("Multipart Resover")
	public CommonsMultipartResolver multipartResolver(){
		CommonsMultipartResolver multiPartResolver=new CommonsMultipartResolver();
		return multiPartResolver;
	}
	
	@Bean
	@Description("Multipart Config Element")
	public MultipartConfigElement multipartConfigElement(){
		MultipartConfigFactory factor=new MultipartConfigFactory();
		factor.setMaxFileSize("10MB");
		factor.setMaxRequestSize("10MB");
		return factor.createMultipartConfig();
	}
	
	
	 public static void main(String[] args) {
    	logger.info("Start Application ...");
    	SpringApplication.run(CyBssFileApp.class, args);
   }	

}

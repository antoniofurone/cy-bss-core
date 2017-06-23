package org.cysoft.bss.core.config;


import org.cysoft.bss.core.common.CyBssDataSource;
import org.cysoft.bss.core.dao.AppDao;
import org.cysoft.bss.core.dao.CityDao;
import org.cysoft.bss.core.dao.CompanyDao;
import org.cysoft.bss.core.dao.ContactDao;
import org.cysoft.bss.core.dao.CountryDao;
import org.cysoft.bss.core.dao.CyBssAuthDao;
import org.cysoft.bss.core.dao.FileDao;
import org.cysoft.bss.core.dao.LanguageDao;
import org.cysoft.bss.core.dao.CyBssServiceDao;
import org.cysoft.bss.core.dao.LocationDao;
import org.cysoft.bss.core.dao.MetricDao;
import org.cysoft.bss.core.dao.ObjectDao;
import org.cysoft.bss.core.dao.PersonDao;
import org.cysoft.bss.core.dao.PriceDao;
import org.cysoft.bss.core.dao.ProductDao;
import org.cysoft.bss.core.dao.TicketDao;
import org.cysoft.bss.core.dao.UserDao;
import org.cysoft.bss.core.dao.mysql.AppMysql;
import org.cysoft.bss.core.dao.mysql.CityMysql;
import org.cysoft.bss.core.dao.mysql.CompanyMysql;
import org.cysoft.bss.core.dao.mysql.ContactMysql;
import org.cysoft.bss.core.dao.mysql.CountryMysql;
import org.cysoft.bss.core.dao.mysql.CyBssAuthMysql;
import org.cysoft.bss.core.dao.mysql.FileMysql;
import org.cysoft.bss.core.dao.mysql.LanguageMysql;
import org.cysoft.bss.core.dao.mysql.CyBssServiceMysql;
import org.cysoft.bss.core.dao.mysql.LocationMysql;
import org.cysoft.bss.core.dao.mysql.MetricMysql;
import org.cysoft.bss.core.dao.mysql.ObjectMysql;
import org.cysoft.bss.core.dao.mysql.PersonMysql;
import org.cysoft.bss.core.dao.mysql.PriceMysql;
import org.cysoft.bss.core.dao.mysql.ProductMysql;
import org.cysoft.bss.core.dao.mysql.TicketMysql;
import org.cysoft.bss.core.dao.mysql.UserMysql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
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
	 @Description("File Dao Rest")
	 public FileDao fileDao(){
		 	FileDao fileDao=new FileMysql();
			return fileDao;
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
	 @Description("Country Dao Rest")
	 public CountryDao countryDao(){
		 	CountryDao countryDao=new CountryMysql();
			return countryDao;
		 }
	
	 
	 @Bean
	 @Description("City Dao Rest")
	 public CityDao cityDao(){
		 	CityDao cityDao=new CityMysql();
			return cityDao;
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
	 @Description("Person Dao Rest")
	 public PersonDao personDao(){
		 	PersonDao personDao=new PersonMysql();
			return personDao;
		 }
	 
	
	 @Bean
	 @Description("Company Dao Rest")
	 public CompanyDao companyDao(){
		 	CompanyDao companyDao=new CompanyMysql();
			return companyDao;
		 }
	
	 
	 @Bean
	 @Description("Location Dao Rest")
	 public LocationDao locationDao(){
		 	LocationDao locationDao=new LocationMysql();
			return locationDao;
		 }
	
	
	 @Bean
	 @Description("Ticket Dao Rest")
	 public TicketDao ticketDao(){
		 	TicketDao ticketDao=new TicketMysql();
			return ticketDao;
		 }
	
	 @Bean
	 @Description("Contact Dao Rest")
	 public ContactDao contactDao(){
		 	ContactDao contactDao=new ContactMysql();
			return contactDao;
		 }
	 
	 @Bean
	 @Description("Metric Dao Rest")
	 public MetricDao metricDao(){
		 	MetricDao metricDao=new MetricMysql();
			return metricDao;
		 }
	
	 @Bean
	 @Description("Product Dao Rest")
	 public ProductDao productDao(){
		 	ProductDao productDao=new ProductMysql();
			return productDao;
		 }
	 
	
	 @Bean
	 @Description("Price Dao Rest")
	 public PriceDao priceDao(){
		 	PriceDao priceDao=new PriceMysql();
			return priceDao;
		 }
	 
	 @Bean
	 @Description("Object Dao Rest")
	 public ObjectDao objectDao(){
		 	ObjectDao objectDao=new ObjectMysql();
			return objectDao;
		 }
	
	 
	 @Bean
	 @Description("MySql Data Source Rest ")
	 public CyBssDataSource mySqlDS() {
		 
		 logger.info("CyBssRestApp.mySqlDS() >>>");
		
		 CyBssDataSource mySqlDs = new CyBssDataSource();
		 String driver=environment.getProperty("mysql.driver");
		 logger.info("mysql.driver="+driver);
		 String url=environment.getProperty("mysql.url");
		 logger.info("mysql.url="+url);
		 String user=environment.getProperty("mysql.user");
		 logger.info("mysql.user="+user);
		 String psw=environment.getProperty("mysql.psw");
		 
		 mySqlDs.setDriverClassName(driver);
	     mySqlDs.setUrl(url);
	     mySqlDs.setUsername(user);
         mySqlDs.setPassword(psw);
         
         mySqlDs.setTestOnBorrow(true);
         mySqlDs.setValidationQuery("select 1");
	     
         
		 logger.info("CyBssRestApp.mySqlDS() <<<");
 	     
		 return mySqlDs;
	 }
	
	 @Bean
	 @Description("Transaction Manager Rest ")
	 public DataSourceTransactionManager transactionManager() {
		 logger.info("CyBssRestApp.transactionManager() >>>");
		 DataSourceTransactionManager transactionManager=new DataSourceTransactionManager(mySqlDS());
		 logger.info("CyBssRestApp.transactionManager() <<<");
 		 return transactionManager;
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
    
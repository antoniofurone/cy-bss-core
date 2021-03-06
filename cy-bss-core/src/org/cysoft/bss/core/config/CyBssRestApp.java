package org.cysoft.bss.core.config;


import org.cysoft.bss.core.common.CyBssDataSource;
import org.cysoft.bss.core.dao.AppDao;
import org.cysoft.bss.core.dao.BillableDao;
import org.cysoft.bss.core.dao.CityDao;
import org.cysoft.bss.core.dao.CompanyDao;
import org.cysoft.bss.core.dao.ContactDao;
import org.cysoft.bss.core.dao.CountryDao;
import org.cysoft.bss.core.dao.CyBssAuthDao;
import org.cysoft.bss.core.dao.FileDao;
import org.cysoft.bss.core.dao.InvoiceDao;
import org.cysoft.bss.core.dao.LanguageDao;
import org.cysoft.bss.core.dao.CyBssServiceDao;
import org.cysoft.bss.core.dao.LocationDao;
import org.cysoft.bss.core.dao.MetricDao;
import org.cysoft.bss.core.dao.ObjectDao;
import org.cysoft.bss.core.dao.PersonDao;
import org.cysoft.bss.core.dao.PriceDao;
import org.cysoft.bss.core.dao.ProductDao;
import org.cysoft.bss.core.dao.PurchaseDao;
import org.cysoft.bss.core.dao.SaleDao;
import org.cysoft.bss.core.dao.ServerDao;
import org.cysoft.bss.core.dao.TicketDao;
import org.cysoft.bss.core.dao.UserDao;
import org.cysoft.bss.core.dao.mysql.AppMysql;
import org.cysoft.bss.core.dao.mysql.BillableCostMysql;
import org.cysoft.bss.core.dao.mysql.BillableRevenueMysql;
import org.cysoft.bss.core.dao.mysql.CityMysql;
import org.cysoft.bss.core.dao.mysql.CompanyMysql;
import org.cysoft.bss.core.dao.mysql.ContactMysql;
import org.cysoft.bss.core.dao.mysql.CountryMysql;
import org.cysoft.bss.core.dao.mysql.CyBssAuthMysql;
import org.cysoft.bss.core.dao.mysql.FileMysql;
import org.cysoft.bss.core.dao.mysql.InvoiceMysql;
import org.cysoft.bss.core.dao.mysql.LanguageMysql;
import org.cysoft.bss.core.dao.mysql.CyBssServiceMysql;
import org.cysoft.bss.core.dao.mysql.LocationMysql;
import org.cysoft.bss.core.dao.mysql.MetricMysql;
import org.cysoft.bss.core.dao.mysql.ObjectMysql;
import org.cysoft.bss.core.dao.mysql.PassiveInvoiceMysql;
import org.cysoft.bss.core.dao.mysql.PersonMysql;
import org.cysoft.bss.core.dao.mysql.PriceMysql;
import org.cysoft.bss.core.dao.mysql.ProductMysql;
import org.cysoft.bss.core.dao.mysql.PurchaseMysql;
import org.cysoft.bss.core.dao.mysql.SaleMysql;
import org.cysoft.bss.core.dao.mysql.ServerMysql;
import org.cysoft.bss.core.dao.mysql.TicketMysql;
import org.cysoft.bss.core.dao.mysql.UserMysql;
import org.cysoft.bss.core.message.CyBssMessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan({"org.cysoft.bss.core.web.service.rest","org.cysoft.bss.core.service.impl"})
@PropertySource("classpath:cy-bss-core.properties")
@EnableWebMvc
@EnableAsync
@EnableScheduling
public class CyBssRestApp {
	private static final Logger logger = LoggerFactory.getLogger(CyBssRestApp.class);
	
	
	 @Autowired
	 Environment environment;
	
	 @Autowired
	 CyBssDataSource dataSource;
	 
	 @Autowired
	 CyBssAuthDao authDao;
	
	 @Bean
	 @Description("Message Source Rest")
	 public CyBssMessageSource messageSource(){
		CyBssMessageSource ms=new CyBssMessageSource();
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
	 @Description("Purchase Dao Rest")
	 public PurchaseDao purchaseDao(){
		 	PurchaseDao purchaseDao=new PurchaseMysql();
			return purchaseDao;
		 }
	 
	 @Bean
	 @Description("Sale Dao Rest")
	 public SaleDao saleDao(){
		 	SaleDao saleDao=new SaleMysql();
			return saleDao;
		 }
	 
	 @Bean
	 @Description("BillableCost Dao Rest")
	 public BillableDao billableCostDao(){
		 	BillableDao billableCostDao=new BillableCostMysql();
			return billableCostDao;
		 }
	
	 
	 @Bean
	 @Description("BillableRevenue Dao Rest")
	 public BillableDao billableRevenueDao(){
		 	BillableDao billableRevenueDao=new BillableRevenueMysql();
			return billableRevenueDao;
		 }
	 
	 @Bean
	 @Description("PassiveInvoice Dao Rest")
	 public InvoiceDao passiveInvoiceDao(){
		 	InvoiceDao passiveInvoiceDao=new PassiveInvoiceMysql();
			return passiveInvoiceDao;
		 }
	
	 @Bean
	 @Description("Invoice Dao Rest")
	 public InvoiceDao invoiceDao(){
		 	InvoiceDao invoiceDao=new InvoiceMysql();
			return invoiceDao;
		 }
	 
	 @Bean
	 @Description("Server Dao Rest")
	 public ServerDao serverDao(){
		 	ServerDao serverDao=new ServerMysql();
			return serverDao;
		 }
	 
	 
	 @Bean
	 @Description("MySql Data Source Rest ")
	 public CyBssDataSource mySqlDS() {
		 CyBssDataSource mySqlDs = new CyBssDataSource(environment);
		 return mySqlDs;
	 }
	
	 @Bean
	 @Description("Transaction Manager Rest ")
	 public DataSourceTransactionManager transactionManager() {
		 logger.info("CyBssRestApp.transactionManager() >>>");
		 DataSourceTransactionManager transactionManager=new DataSourceTransactionManager(dataSource);
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
    
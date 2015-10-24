package org.cysoft.bss.core.config;

import javax.servlet.MultipartConfigElement;

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
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan({"org.cysoft.bss.core.web.cntl"})
@PropertySource("classpath:cy-bss-core.properties")
public class CyBssFileApp {
private static final Logger logger = LoggerFactory.getLogger(CyBssFileApp.class);
	
	@Autowired
	Environment environment;
	
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

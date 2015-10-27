package org.cysoft.bss.core.config;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitializer implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// TODO Auto-generated method stub
		
		// Rest context
        AnnotationConfigWebApplicationContext ctxRest = new AnnotationConfigWebApplicationContext();  
        ctxRest.register(CyBssRestApp.class);  
        ctxRest.setServletContext(servletContext);    
        
        Dynamic dynamicRest = servletContext.addServlet("RestDispatcher", new DispatcherServlet(ctxRest));
        dynamicRest.addMapping("/rest/*");
        dynamicRest.setLoadOnStartup(1);
        
        // File context
        AnnotationConfigWebApplicationContext ctxFile = new AnnotationConfigWebApplicationContext();  
        ctxFile.register(CyBssFileApp.class);  
        ctxFile.setServletContext(servletContext);  
               
        Dynamic dynamicFile = servletContext.addServlet("FileDispatcher", new DispatcherServlet(ctxFile));
        dynamicFile.addMapping("/fileservice/*");
        dynamicFile.setLoadOnStartup(1);
        
	}

}

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
		
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();  
        ctx.register(RestServiceApp.class);  
        ctx.setServletContext(servletContext);    
        
        Dynamic dynamic = servletContext.addServlet("RestDispatcher", new DispatcherServlet(ctx));
        dynamic.addMapping("/rest/*");
        dynamic.setLoadOnStartup(1);
        
	}

}

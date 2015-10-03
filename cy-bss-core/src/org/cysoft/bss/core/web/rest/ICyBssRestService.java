package org.cysoft.bss.core.web.rest;

import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;

public interface ICyBssRestService {

	public void setEnvironment(Environment env);
	public void setMessageSource(MessageSource msgSource);
	
	
}

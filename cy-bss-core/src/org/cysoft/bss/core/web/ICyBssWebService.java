package org.cysoft.bss.core.web;

import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;

public interface ICyBssWebService {

	public void setEnvironment(Environment env);
	public void setMessageSource(MessageSource msgSource);
	
	
}

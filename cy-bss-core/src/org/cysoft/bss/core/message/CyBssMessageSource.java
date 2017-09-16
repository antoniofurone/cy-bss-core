package org.cysoft.bss.core.message;

import java.util.Locale;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class CyBssMessageSource extends ReloadableResourceBundleMessageSource{
	
	public CyBssMessageSource(){
		setBasename("classpath:org/cysoft/bss/core/message/messages");
		setDefaultEncoding("UTF-8");
	}
	
	
	public String getMessage(String messageId){
		return getMessage(ICyBssMessageConst.RESULT_D_NOT_FOUND, null, Locale.ENGLISH);
	}

}

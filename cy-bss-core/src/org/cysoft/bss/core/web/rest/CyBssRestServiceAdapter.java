package org.cysoft.bss.core.web.rest;

import java.util.Locale;

import org.cysoft.bss.core.model.rest.response.ICyBssRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;

public abstract class CyBssRestServiceAdapter {
	
	private Environment env;
	
	@Autowired
	public void setEnvironment(Environment env){
		this.env=env;
	}
	
	private MessageSource msgSource;
	
	@Autowired
	public void setMessageSource(MessageSource msgSource){
		this.msgSource=msgSource;
	}
	
	
	protected void setResult(ICyBssRestResponse response,
					String resultCode,String resultDescId,Locale locale){
		response.setResultCode(resultCode);
		response.setResultDesc(msgSource.getMessage(resultDescId, null, locale));
	}

	protected void setResult(ICyBssRestResponse response,
			String resultCode,String resultDescId){
		setResult(response,resultCode,resultDescId,Locale.ENGLISH);	
	}
	
}

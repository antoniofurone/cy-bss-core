package org.cysoft.bss.core.common;

import java.util.Locale;

import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class CyBssMessageSource extends ReloadableResourceBundleMessageSource{
	
	public String getMessage(String messageId){
		return getMessage(ICyBssResultConst.RESULT_D_NOT_FOUND, null, Locale.ENGLISH);
	}

}

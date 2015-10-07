package org.cysoft.bss.core.common;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Locale;

public class CyBssUtility {
	private static SecureRandom random=new SecureRandom();
	
	public static String genToken(String postFix){
		return new BigInteger(130,random).toString(32)+"-"+postFix;
	}
	
	public static Locale getLocale(String localeCode){
		
		if (localeCode.equalsIgnoreCase(ICyBssConst.LOCALE_IT))
			return Locale.ITALIAN;
		else
			return Locale.ENGLISH;
	}

}

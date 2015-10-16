package org.cysoft.bss.core.common;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Locale;

import org.cysoft.bss.core.model.ICyBssConst;

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

	public static String toCamelCase(String s){
		 String[] parts = s.replaceAll("\\s+", " ").split(" ");
		 String camelCaseString = "";
		 for (String part : parts){
			 	camelCaseString = camelCaseString + (!camelCaseString.equals("")?" ":"")+ toProperCase(part);
		   }
		 return camelCaseString;
		}

	public static String toProperCase(String s) {
		 return s.substring(0, 1).toUpperCase() +
		        s.substring(1).toLowerCase();
	}
	
	
}

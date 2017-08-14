package org.cysoft.bss.core.common;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
	
	   public static final String DATE_ddsMMsyyyy="dd/MM/yyyy";
	   public static final String DATE_yyyy_MM_dd="yyyy-MM-dd";
	   
	   public static Date stringToDate(String date,String fmt) throws java.text.ParseException{
		   DateFormat datefmt = new SimpleDateFormat(fmt);
		   return datefmt.parse(date);
	   }
		
	   public static String dateToString(Date date,String fmt) {
		   DateFormat datefmt = new SimpleDateFormat(fmt);
		   return datefmt.format(date);	  
	   }
	   
	   public static Date tryStringToDate(String date) 
			   throws java.text.ParseException{
		   Date dt=null;
		   try {
			dt=stringToDate(date,DATE_ddsMMsyyyy);
		   } catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			   try {
				dt=stringToDate(date,DATE_yyyy_MM_dd);
			} catch (java.text.ParseException e1) {
				// TODO Auto-generated catch block
				throw e1;
			}
		   }
		   return dt;
	   }
	   
	   
	   public static String dateChangeFormat(String sDate,String fmtOut) throws java.text.ParseException{
		   return dateToString(tryStringToDate(sDate),fmtOut);
	   }

	   
	   public static String byteArrayToHex(byte[] a) {
		   StringBuilder sb = new StringBuilder(a.length * 2);
		   for(byte b: a)
		      sb.append(String.format("%02x", b & 0xff));
		   return sb.toString();
		}
	   
	   public static Date getCurrentDate()
	    {
	        Calendar c=Calendar.getInstance();
	        return c.getTime();
	    }
	   
	   public static int getYear(Date date){
		   Calendar c=Calendar.getInstance();
		   c.setTime(date);
		   return c.get(Calendar.YEAR);
	   }
}

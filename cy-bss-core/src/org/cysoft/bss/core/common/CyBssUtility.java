package org.cysoft.bss.core.common;

import java.math.BigInteger;
import java.security.SecureRandom;

public class CyBssUtility {
	private static SecureRandom random=new SecureRandom();
	
	public static String genToken(String postFix){
		return new BigInteger(130,random).toString(32)+"-"+postFix;
	}

}

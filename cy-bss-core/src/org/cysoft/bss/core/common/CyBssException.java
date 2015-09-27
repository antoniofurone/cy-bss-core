package org.cysoft.bss.core.common;

public class CyBssException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CyBssException(Exception e){
		super(e);
	}

	public CyBssException(String  msg){
		super(msg);
	}
	
}

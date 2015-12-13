package org.cysoft.bss.core.model;

import java.util.Arrays;

public class PwdEncrypted {
	
	private byte[] pwd=null;
	public byte[] getPwd() {
		return pwd;
	}
	public void setPwd(byte[] pwd) {
		this.pwd = pwd;
	}
	

	private byte[] salt=null;
	public byte[] getSalt() {
		return salt;
	}
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
	
	@Override
	public String toString() {
		return "PwdEncrypted [pwd=" + Arrays.toString(pwd) + ", salt="
				+ Arrays.toString(salt) + "]";
	}
	
	

}

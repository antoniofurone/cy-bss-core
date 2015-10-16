package org.cysoft.bss.core.model;

public class ChangePwd {
	
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	private String oldPwd="";
	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	private String newPwd="";
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	
	
	@Override
	public String toString() {
		return "ChangePwd [id=" + id + ", oldPwd=" + oldPwd + ", newPwd="
				+ newPwd + "]";
	}
	
	
}

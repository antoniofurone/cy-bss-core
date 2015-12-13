package org.cysoft.bss.core.model;

import java.util.Locale;

public class User {
	
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	private String userId="";
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	private String pwd="";
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	private String name="";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private long roleId;
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	
	private String role="";
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	private long personId;
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}

	
	private String personFirstName="";
	public String getPersonFirstName() {
		return personFirstName;
	}
	public void setPersonFirstName(String personFirstName) {
		this.personFirstName = personFirstName;
	}

	
	private String personSecondName="";
	public String getPersonSecondName() {
		return personSecondName;
	}
	public void setPersonSecondName(String personSecondName) {
		this.personSecondName = personSecondName;
	}

	private long languageId;
	public long getLanguageId() {
		return languageId;
	}
	public void setLanguageId(long languageId) {
		this.languageId = languageId;
	}
	
	private String languageCode=Locale.ENGLISH.getISO3Language(); 
	public String getLanguageCode() {
		return languageCode;
	}
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	private String flgActive=ICyBssConst.YES;
	public String getFlgActive() {
		return flgActive;
	}
	public void setFlgActive(String flgActive) {
		this.flgActive = flgActive;
	}
	
	public boolean isActive(){
		return this.flgActive.equals(ICyBssConst.YES)?true:false;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", pwd=" + pwd
				+ ", name=" + name + ", roleId=" + roleId + ", role=" + role
				+ ", personId=" + personId + ", personFirstName="
				+ personFirstName + ", personSecondName=" + personSecondName
				+ ", languageId=" + languageId + ", languageCode="
				+ languageCode + ", flgActive=" + flgActive + "]";
	}
	
	
	
}

package org.cysoft.bss.core.model;

public class CompanyPerson {
	
	private long roleId;
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	
	private String roleName="";
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	private long deptId;
	public long getDeptId() {
		return deptId;
	}
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	private String deptCode="";
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	private String deptName="";
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	private long companyId;
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	
	private String companyCode="";
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	private String companyName="";
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Override
	public String toString() {
		return "CompanyPerson [roleId=" + roleId + ", roleName=" + roleName + ", personId=" + personId
				+ ", personFirstName=" + personFirstName + ", personSecondName=" + personSecondName + ", deptId="
				+ deptId + ", deptCode=" + deptCode + ", deptName=" + deptName + ", companyId=" + companyId
				+ ", companyCode=" + companyCode + ", companyName=" + companyName + "]";
	}
	
	
}

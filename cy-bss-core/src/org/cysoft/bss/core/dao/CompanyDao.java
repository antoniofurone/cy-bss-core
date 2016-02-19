package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Company;
import org.cysoft.bss.core.model.CompanyDept;
import org.cysoft.bss.core.model.PersonRole;

public interface CompanyDao {
	public long add(Company company) throws CyBssException;
	public long addDept(CompanyDept dept) throws CyBssException;
	
	public void update(long id,Company company) throws CyBssException;
	public void updateDept(CompanyDept dept) throws CyBssException;
	
	public List<CompanyDept> getDeptAll(long companyId);
	public List<CompanyDept> getDeptChild(long deptId);
	
	public List<Company> find(String code,String name);
	
	public void remove(long id) throws CyBssException;
	public void removeDept(long deptId) throws CyBssException;
		
	public Company getByCode(String code);
	public Company get(long id);
	public CompanyDept getDept(long deptId);
	
	public List<PersonRole> getPersonRoleAll(long langId);
}

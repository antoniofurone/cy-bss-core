package org.cysoft.bss.core.service;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Company;
import org.cysoft.bss.core.model.CompanyDept;
import org.cysoft.bss.core.model.CompanyPerson;
import org.cysoft.bss.core.model.PersonRole;

public interface CompanyService {
	
	public long add(Company company) throws CyBssException;
	public long addDept(CompanyDept dept) throws CyBssException;
	public void addPerson(CompanyPerson companyPerson,String languageCode) throws CyBssException; 
	public void addSubs(long id,long subsId);
	
	public void update(long id,Company company) throws CyBssException;
	public void updateDept(CompanyDept dept) throws CyBssException;
	
	public List<CompanyDept> getDeptAll(long companyId);
	public List<CompanyDept> getSubDept(long deptId);
	public List<CompanyPerson> getPersonAll(long companyId,long langId);
	
	public List<Company> find(String code,String name);
	
	public void remove(long id) throws CyBssException;
	public void removeDept(long deptId) throws CyBssException;
	public void removePerson(long personId,long deptId);
	public void removeSubs(long id,long subsId);
		
	public Company getByCode(String code);
	public Company get(long id); 
	public CompanyDept getDept(long deptId);
	
	public List<PersonRole> getPersonRoleAll(long langId);
	public PersonRole getPersonRole(long roleId,long langId);
	
	// Managed Company
	public void addManaged(long id, long invoiceLogoId) throws CyBssException;
	public void updateManaged(long id, long invoiceLogoId) throws CyBssException;
	public Company getManaged(long id) throws CyBssException;
	public List<Company> getManagedAll();
	public void removeManaged(long id) throws CyBssException;	

}

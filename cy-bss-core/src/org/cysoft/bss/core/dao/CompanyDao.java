package org.cysoft.bss.core.dao;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Company;
import org.cysoft.bss.core.model.CompanyDept;

public interface CompanyDao {
	public long add(Company company) throws CyBssException;
	public long addDept(CompanyDept dept) throws CyBssException;
	public Company getByCode(String code);
	public Company get(long id);
	
}

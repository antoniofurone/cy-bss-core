package org.cysoft.bss.core.web.response.rest;

import org.cysoft.bss.core.model.CompanyDept;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class CompanyDeptResponse  extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private CompanyDept companyDept=null;

	public CompanyDept getCompanyDept() {
		return companyDept;
	}

	public void setCompanyDept(CompanyDept companyDept) {
		this.companyDept = companyDept;
	}

	
}

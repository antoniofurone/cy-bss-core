package org.cysoft.bss.core.web.response.rest;

import java.util.List;

import org.cysoft.bss.core.model.CompanyDept;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class CompanyDeptListResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private List<CompanyDept> companyDepts=null;

	public List<CompanyDept> getCompanyDepts() {
		return companyDepts;
	}

	public void setCompanyDepts(List<CompanyDept> companyDepts) {
		this.companyDepts = companyDepts;
	}

	

}

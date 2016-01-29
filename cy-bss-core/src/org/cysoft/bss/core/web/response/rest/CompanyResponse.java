package org.cysoft.bss.core.web.response.rest;

import org.cysoft.bss.core.model.Company;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class CompanyResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private Company company=null;

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
}

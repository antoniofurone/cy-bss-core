package org.cysoft.bss.core.web.response.rest;

import java.util.List;

import org.cysoft.bss.core.model.Company;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class CompanyListResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private List<Company> companies=null;

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}
	

}

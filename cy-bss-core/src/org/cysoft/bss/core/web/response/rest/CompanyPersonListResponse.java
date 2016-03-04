package org.cysoft.bss.core.web.response.rest;

import java.util.List;

import org.cysoft.bss.core.model.CompanyPerson;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class CompanyPersonListResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private List<CompanyPerson> companyPersons=null;

	public List<CompanyPerson> getCompanyPersons() {
		return companyPersons;
	}

	public void setCompanyPersons(List<CompanyPerson> companyPersons) {
		this.companyPersons = companyPersons;
	}

	

}

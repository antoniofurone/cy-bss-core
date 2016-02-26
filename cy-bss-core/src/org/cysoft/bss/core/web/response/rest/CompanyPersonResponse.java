package org.cysoft.bss.core.web.response.rest;

import org.cysoft.bss.core.model.CompanyPerson;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class CompanyPersonResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private CompanyPerson companyPerson=null;

	public CompanyPerson getCompanyPerson() {
		return companyPerson;
	}

	public void setCompanyPerson(CompanyPerson companyPerson) {
		this.companyPerson = companyPerson;
	}
	

}

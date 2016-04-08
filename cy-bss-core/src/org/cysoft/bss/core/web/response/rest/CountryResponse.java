package org.cysoft.bss.core.web.response.rest;

import org.cysoft.bss.core.model.Country;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class CountryResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	Country country=null;

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
}

package org.cysoft.bss.core.web.response.rest;

import org.cysoft.bss.core.model.City;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class CityResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	City city=null;

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
	

}

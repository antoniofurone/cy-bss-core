package org.cysoft.bss.core.web.response.rest;

import org.cysoft.bss.core.model.Location;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class LocationResponse extends CyBssResponseAdapter
implements ICyBssResponse{

	Location location=null;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	
}

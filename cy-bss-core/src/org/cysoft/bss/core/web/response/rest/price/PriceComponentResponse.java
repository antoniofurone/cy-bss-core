package org.cysoft.bss.core.web.response.rest.price;

import org.cysoft.bss.core.model.PriceComponent;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class PriceComponentResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	PriceComponent component=null;

	public PriceComponent getComponent() {
		return component;
	}

	public void setComponent(PriceComponent component) {
		this.component = component;
	}
	
			
}

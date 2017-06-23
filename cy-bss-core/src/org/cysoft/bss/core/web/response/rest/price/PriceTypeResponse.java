package org.cysoft.bss.core.web.response.rest.price;

import org.cysoft.bss.core.model.PriceType;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class PriceTypeResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	PriceType type=null;

	public PriceType getType() {
		return type;
	}

	public void setType(PriceType type) {
		this.type = type;
	}
	
	
		
}

package org.cysoft.bss.core.web.response.rest;

import org.cysoft.bss.core.model.ProductType;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class ProductTypeResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	ProductType type=null;

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

		
	
}

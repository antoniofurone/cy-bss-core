package org.cysoft.bss.core.web.response.rest.product;

import java.util.List;

import org.cysoft.bss.core.model.ProductType;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class ProductTypeListResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private List<ProductType> types=null;

	public List<ProductType> getTypes() {
		return types;
	}

	public void setTypes(List<ProductType> types) {
		this.types = types;
	}

		

}

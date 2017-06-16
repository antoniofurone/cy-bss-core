package org.cysoft.bss.core.web.response.rest;

import org.cysoft.bss.core.model.Product;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class ProductResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	Product product=null;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}

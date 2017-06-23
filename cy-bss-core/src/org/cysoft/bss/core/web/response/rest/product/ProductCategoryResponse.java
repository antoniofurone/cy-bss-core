package org.cysoft.bss.core.web.response.rest.product;

import org.cysoft.bss.core.model.ProductCategory;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class ProductCategoryResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	ProductCategory category=null;

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}
	
}

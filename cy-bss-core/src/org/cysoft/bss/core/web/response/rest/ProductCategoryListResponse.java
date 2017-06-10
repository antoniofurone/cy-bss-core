package org.cysoft.bss.core.web.response.rest;

import java.util.List;

import org.cysoft.bss.core.model.ProductCategory;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class ProductCategoryListResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private List<ProductCategory> categories=null;

	public List<ProductCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<ProductCategory> categories) {
		this.categories = categories;
	}

	

}

package org.cysoft.bss.core.web.response.rest.sale;

import org.cysoft.bss.core.model.Sale;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class SaleResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	Sale sale=null;

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

		

	
}

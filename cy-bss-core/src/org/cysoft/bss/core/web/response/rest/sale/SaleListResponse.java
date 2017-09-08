package org.cysoft.bss.core.web.response.rest.sale;

import java.util.List;

import org.cysoft.bss.core.model.Sale;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class SaleListResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private List<Sale> sales=null;

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}
	
	
}

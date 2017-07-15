package org.cysoft.bss.core.web.response.rest.purchase;

import java.util.List;

import org.cysoft.bss.core.model.Purchase;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class PurchaseListResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private List<Purchase> purchases=null;
	public List<Purchase> getPurchases() {
		return purchases;
	}
	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}
	
}

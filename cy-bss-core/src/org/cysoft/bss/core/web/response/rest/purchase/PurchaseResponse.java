package org.cysoft.bss.core.web.response.rest.purchase;

import org.cysoft.bss.core.model.Purchase;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class PurchaseResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	Purchase purchase=null;

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	

	
}

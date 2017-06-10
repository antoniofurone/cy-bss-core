package org.cysoft.bss.core.web.response.rest;

import org.cysoft.bss.core.model.Currency;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class CurrencyResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private Currency currency=null;

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

			
	
}

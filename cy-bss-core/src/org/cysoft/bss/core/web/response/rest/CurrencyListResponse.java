package org.cysoft.bss.core.web.response.rest;

import java.util.List;

import org.cysoft.bss.core.model.Currency;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class CurrencyListResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private List<Currency> currencies=null;
	
		public List<Currency> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<Currency> currencies) {
		this.currencies = currencies;
	}

}

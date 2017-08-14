package org.cysoft.bss.core.web.response.rest.invoice;

import org.cysoft.bss.core.model.Invoice;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class InvoiceResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	Invoice invoice=null;
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
}

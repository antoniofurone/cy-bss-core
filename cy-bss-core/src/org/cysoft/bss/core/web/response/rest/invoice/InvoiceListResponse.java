package org.cysoft.bss.core.web.response.rest.invoice;

import java.util.List;

import org.cysoft.bss.core.model.Invoice;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class InvoiceListResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private List<Invoice> invoices=null;
	public List<Invoice> getInvoices() {
		return invoices;
	}
	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}
	
}

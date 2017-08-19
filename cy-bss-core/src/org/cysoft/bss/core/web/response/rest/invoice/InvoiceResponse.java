package org.cysoft.bss.core.web.response.rest.invoice;

import java.util.List;

import org.cysoft.bss.core.model.Billable;
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

	List<Billable> billables=null;
	public List<Billable> getBillables() {
		return billables;
	}
	public void setBillables(List<Billable> billables) {
		this.billables = billables;
	}
	
	
}

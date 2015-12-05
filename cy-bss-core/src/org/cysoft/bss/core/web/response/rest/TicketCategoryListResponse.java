package org.cysoft.bss.core.web.response.rest;

import java.util.List;

import org.cysoft.bss.core.model.TicketCategory;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class TicketCategoryListResponse extends CyBssResponseAdapter
implements ICyBssResponse{

	private List<TicketCategory> ticketCategories=null;
	public List<TicketCategory> getTicketCategories() {
		return ticketCategories;
	}
	public void setTicketCategories(List<TicketCategory> ticketCategories) {
		this.ticketCategories = ticketCategories;
	}
	
	
}

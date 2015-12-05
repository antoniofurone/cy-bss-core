package org.cysoft.bss.core.web.response.rest;

import java.util.List;

import org.cysoft.bss.core.model.TicketStatus;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class TicketStatusListResponse extends CyBssResponseAdapter
implements ICyBssResponse{

	private List<TicketStatus> ticketStates=null;
	public List<TicketStatus> getTicketStates() {
		return ticketStates;
	}
	public void setTicketStates(List<TicketStatus> ticketStates) {
		this.ticketStates = ticketStates;
	}
	
	
}

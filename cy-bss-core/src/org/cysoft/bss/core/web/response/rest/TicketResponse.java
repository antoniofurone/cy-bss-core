package org.cysoft.bss.core.web.response.rest;

import org.cysoft.bss.core.model.Ticket;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class TicketResponse extends CyBssResponseAdapter
implements ICyBssResponse{

	Ticket ticket=null;
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	
	
}

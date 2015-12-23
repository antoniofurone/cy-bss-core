package org.cysoft.bss.core.web.response.rest;

import java.util.List;

import org.cysoft.bss.core.model.Ticket;
import org.cysoft.bss.core.model.TicketStatusTrace;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class TicketResponse extends CyBssResponseAdapter
implements ICyBssResponse{

	private Ticket ticket=null;
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	
	private List<TicketStatusTrace> statusTraces=null;
	public List<TicketStatusTrace> getStatusTraces() {
		return statusTraces;
	}
	public void setStatusTraces(List<TicketStatusTrace> statusTraces) {
		this.statusTraces = statusTraces;
	}
	
}

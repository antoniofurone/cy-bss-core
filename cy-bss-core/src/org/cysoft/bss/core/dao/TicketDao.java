package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Ticket;

public interface TicketDao {
	public long add(Ticket ticket) throws CyBssException;
	public Ticket get(long id);
	public void update(long id,Ticket ticket) throws CyBssException;
	
	public List<Ticket> find(String text,long categoryId,long statusId,long personId,
			String fromDate,String toDate) throws CyBssException;
	
}

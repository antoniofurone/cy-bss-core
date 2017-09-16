package org.cysoft.bss.core.service.impl;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.FileDao;
import org.cysoft.bss.core.dao.LocationDao;
import org.cysoft.bss.core.dao.TicketDao;
import org.cysoft.bss.core.message.ICyBssMessageConst;
import org.cysoft.bss.core.model.CyBssFile;
import org.cysoft.bss.core.model.Location;
import org.cysoft.bss.core.model.Ticket;
import org.cysoft.bss.core.model.TicketCategory;
import org.cysoft.bss.core.model.TicketStatus;
import org.cysoft.bss.core.model.TicketStatusTrace;
import org.cysoft.bss.core.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class TicketServiceImpl extends CyBssServiceBase 
implements TicketService{

	protected TicketDao ticketDao=null;
	@Autowired
	public void setPersonDao(TicketDao ticketDao){
			this.ticketDao=ticketDao;
	}
	
	protected  LocationDao locationDao=null;
	@Autowired
	public void setLocationDao(LocationDao locationDao){
			this.locationDao=locationDao;
	}
	
	protected  FileDao fileDao=null;
	@Autowired
	public void setFileDao(FileDao fileDao){
			this.fileDao=fileDao;
	}
	@Override
	public long add(Ticket ticket) throws CyBssException {
		// TODO Auto-generated method stub
		return ticketDao.add(ticket);
	}
	@Override
	public Ticket get(long id, long langId) {
		// TODO Auto-generated method stub
		return ticketDao.get(id, langId);
	}
	@Override
	public void update(final long id, final Ticket ticket, final long langId) throws CyBssException {
		// TODO Auto-generated method stub
		
		final Ticket oldVersion=get(id, langId);
		if (oldVersion==null)
			throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_NOT_FOUND));
		
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		txTemplate.execute(new TransactionCallbackWithoutResult(){

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				
				try {
					if (oldVersion.getLocationId()!=0){
						Location location=locationDao.get(oldVersion.getLocationId(),langId);
						if (location==null)
							throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_NOT_FOUND));
						
						if (location.getLocationType().equals(Ticket.ENTITY_NAME)){
							ticketDao.removeLocation(id);
							locationDao.remove(location.getId());
						}
						
						if (ticket.getLocation()!=null){
							long locationId = locationDao.add(ticket.getLocation());
							ticket.setLocationId(locationId);
						}
						ticketDao.update(id, ticket);
					}
				}
				catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
			
		});
		
		
	}
	@Override
	public void remove(final long id, final long langId) throws CyBssException {
		// TODO Auto-generated method stub
		
		Ticket ticket=get(id, langId);
		if (ticket==null) 
			throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_NOT_FOUND));
		
		Location location=null;
		if (ticket.getLocationId()!=0){
			location=locationDao.get(ticket.getLocationId(),langId);
			if (location==null) 
				throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_NOT_FOUND));
		}
		
		final Location _location=location;
		
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		txTemplate.execute(new TransactionCallbackWithoutResult(){

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus txStatus) {
				// TODO Auto-generated method stub
				
				try {
					
					List<CyBssFile> files=fileDao.getByEntity(Ticket.ENTITY_NAME,id);
					if (files!=null)
						for(CyBssFile file:files)
							fileDao.remove(file.getId());
					
					ticketDao.removeTrace(id);
					ticketDao.remove(id);
					
					if (_location!=null && _location.getLocationType().equals(Ticket.ENTITY_NAME)){
						List<CyBssFile> locFiles=fileDao.getByEntity(Location.ENTITY_NAME,_location.getId());
						if (locFiles!=null)
							for(CyBssFile file:locFiles)
								fileDao.remove(file.getId());
						
						locationDao.remove(_location.getId());
					}
				}
				catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		});

	}
	
	@Override
	public List<Ticket> find(String text, long categoryId, long statusId, long personId, String fromDate, String toDate,
			long langId) throws CyBssException {
		// TODO Auto-generated method stub
		return ticketDao.find(text, categoryId, statusId, personId, fromDate, toDate, langId);
	}
	@Override
	public List<TicketCategory> getCategoryAll(long langId) {
		// TODO Auto-generated method stub
		return ticketDao.getCategoryAll(langId);
	}
	@Override
	public List<TicketStatus> getStatusAll(long langId) {
		// TODO Auto-generated method stub
		return ticketDao.getStatusAll(langId);
	}
	@Override
	public List<TicketStatus> getNextStates(long stateId, long langId) {
		// TODO Auto-generated method stub
		return ticketDao.getNextStates(stateId, langId);
	}
	@Override
	public List<TicketStatusTrace> getStatusTrace(long id, long langId) {
		// TODO Auto-generated method stub
		return ticketDao.getStatusTrace(id, langId);
	}
	@Override
	public void changeStatus(final Ticket ticket, final long newStatus, final long userId, final String note, long langId)
			throws CyBssException {
		// TODO Auto-generated method stub
		
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		txTemplate.execute(new TransactionCallbackWithoutResult(){

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus txStatus) {
				// TODO Auto-generated method stub
				
				try {
					ticketDao.addTrace(ticket.getId(), ticket.getStatusId(), newStatus, userId, note);
					ticketDao.changeStatus(ticket.getId(), newStatus);
					
				} catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				
			}
		
		});		
		
	}

}

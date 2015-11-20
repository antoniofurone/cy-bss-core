package org.cysoft.bss.core.web.service.rest;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.FileDao;
import org.cysoft.bss.core.dao.LocationDao;
import org.cysoft.bss.core.dao.TicketDao;
import org.cysoft.bss.core.model.Location;
import org.cysoft.bss.core.model.Ticket;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.cysoft.bss.core.web.response.file.FileListResponse;
import org.cysoft.bss.core.web.response.rest.TicketResponse;
import org.cysoft.bss.core.web.service.CyBssWebServiceAdapter;
import org.cysoft.bss.core.web.service.ICyBssWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
@CyBssService(name = "Ticket")
public class TicketWs extends CyBssWebServiceAdapter
	implements ICyBssWebService{
	
	private static final Logger logger = LoggerFactory.getLogger(TicketWs.class);
	
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
	
	protected FileDao fileDao=null;
	@Autowired
	public void setFileDao(FileDao fileDao){
			this.fileDao=fileDao;
	}
	
	
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@CyBssOperation(name = "add")
	public TicketResponse add(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody Ticket ticket
			) throws CyBssException
	{
		TicketResponse response=new TicketResponse();
		
		logger.info("TicketWs.add() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"add",String.class,Ticket.class))
				return response;
		// end checkGrant 
		
		ticket.setUserId(response.getUserId());
		long ticketId=ticketDao.add(ticket);
		ticket.setId(ticketId);
		response.setTicket(ticket);
		
		logger.info("TicketWs.add() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/getFiles",method = RequestMethod.GET)
	@CyBssOperation(name = "getFiles")
	public FileListResponse getFiles(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("TicketWs.getFiles() >>> id="+id);
		FileListResponse response=new FileListResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getFiles",String.class,Long.class))
			return response;
		// end checkGrant 
				
		Ticket ticket=ticketDao.get(id);
		if (ticket!=null){
			response.setFiles(fileDao.getByEntity(Ticket.ENTITY_NAME, id));
		}
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		
		
		logger.info("TicketWs.getFiles() <<< ");
		
		return response;
	}
	
	
	
	
	
	@RequestMapping(value = "/{id}/get",method = RequestMethod.GET)
	@CyBssOperation(name = "get")
	public TicketResponse get(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("TicketWs.get() >>> id="+id);
		TicketResponse response=new TicketResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"get",String.class,Long.class))
			return response;
		// end checkGrant 
				
		Ticket ticket=ticketDao.get(id);
		if (ticket!=null){
			response.setTicket(ticket);
			if (ticket.getLocationId()!=0)
				ticket.setLocation(locationDao.get(ticket.getLocationId()));
		}
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		
		
		logger.info("TicketWs.get() <<< ");
		
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/setLocation",method = RequestMethod.POST)
	@CyBssOperation(name = "setLocation")
	public TicketResponse setLocation(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody Location location
			) throws CyBssException
	{
		TicketResponse response=new TicketResponse();
		
		logger.info("TicketWs.setLocation() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"setLocation",String.class,Long.class,Location.class))
			return response;
		// end checkGrant 
		
		Ticket ticket=ticketDao.get(id);
		if (ticket==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		
		location.setName("Ticket #"+id);
		location.setLocationType(Ticket.ENTITY_NAME);
		long locationId=locationDao.add(location);
		
		ticket.setLocationId(locationId);
		ticket.setLocation(locationDao.get(locationId));
		
		ticketDao.update(id, ticket);
		
		response.setTicket(ticket);
		
		logger.info("TicketWs.setLocation() <<<");
		
		return response;
	}
	
	
}

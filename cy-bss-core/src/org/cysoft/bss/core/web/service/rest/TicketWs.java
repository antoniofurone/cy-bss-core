package org.cysoft.bss.core.web.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.message.ICyBssMessageConst;
import org.cysoft.bss.core.model.Language;
import org.cysoft.bss.core.model.Location;
import org.cysoft.bss.core.model.Ticket;
import org.cysoft.bss.core.model.TicketCategory;
import org.cysoft.bss.core.model.TicketChangeStatus;
import org.cysoft.bss.core.model.TicketStatus;
import org.cysoft.bss.core.service.FileService;
import org.cysoft.bss.core.service.LocationService;
import org.cysoft.bss.core.service.TicketService;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.file.FileListResponse;
import org.cysoft.bss.core.web.response.rest.ticket.TicketCategoryListResponse;
import org.cysoft.bss.core.web.response.rest.ticket.TicketListResponse;
import org.cysoft.bss.core.web.response.rest.ticket.TicketResponse;
import org.cysoft.bss.core.web.response.rest.ticket.TicketStatusListResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
@CyBssService(name = "Ticket")
public class TicketWs extends CyBssWebServiceAdapter
	implements ICyBssWebService{
	
	private static final Logger logger = LoggerFactory.getLogger(TicketWs.class);
	
	protected TicketService ticketService=null;
	@Autowired
	public void setTicketService(TicketService ticketService){
			this.ticketService=ticketService;
	}
	
	protected  LocationService locationService=null;
	@Autowired
	public void setLocationService(LocationService locationService){
			this.locationService=locationService;
	}
	
	protected FileService fileService=null;
	@Autowired
	public void setFileService(FileService fileService){
			this.fileService=fileService;
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
		long ticketId=ticketService.add(ticket);
		ticket.setId(ticketId);
		response.setTicket(ticket);
		
		logger.info("TicketWs.add() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/getFiles",method = RequestMethod.GET)
	@CyBssOperation(name = "getFiles")
	public FileListResponse getFiles(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("TicketWs.getFiles() >>> id="+id);
		FileListResponse response=new FileListResponse();
		
		Language language=languageService.getLanguage(response.getLanguageCode());		
		Ticket ticket=ticketService.get(id,language.getId());
		if (ticket!=null){
			response.setFiles(fileService.getByEntity(Ticket.ENTITY_NAME, id));
		}
		else
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		
		
		logger.info("TicketWs.getFiles() <<< ");
		
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/get",method = RequestMethod.GET)
	@CyBssOperation(name = "get")
	public TicketResponse get(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken,
			@RequestHeader(value="Language",required=false, defaultValue="") String languageCode,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("TicketWs.get() >>> id="+id);
		TicketResponse response=new TicketResponse();
		
		Language language=null;
		if (languageCode.equals(""))
			language=languageService.getLanguage(response.getLanguageCode());
		else	
			language=languageService.getLanguage(languageCode);
		
		Ticket ticket=ticketService.get(id,language.getId());
		if (ticket!=null){
			response.setTicket(ticket);
			if (ticket.getLocationId()!=0)
				ticket.setLocation(locationService.get(ticket.getLocationId(),language.getId()));
		}
		else
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		
		
		logger.info("TicketWs.get() <<< ");
		
		return response;
	}
	
	
	@RequestMapping(value = "/getCategoryAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getCategoryAll")
	public TicketCategoryListResponse getCategoryAll(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken,
			@RequestHeader(value="Language",required=false, defaultValue="") String languageCode
			) throws CyBssException{
		
		logger.info("TicketWs.getCategoryAll() >>> ");
		TicketCategoryListResponse response=new TicketCategoryListResponse();
		
		
		Language language=null;
		if (languageCode.equals(""))
			language=languageService.getLanguage(response.getLanguageCode());
		else	
			language=languageService.getLanguage(languageCode);
			
		List<TicketCategory> categories=ticketService.getCategoryAll(language.getId());
		response.setTicketCategories(categories);
		
		logger.info("TicketWs.getCategoryAll() <<< ");
		
		return response;
	}
	
	
	@RequestMapping(value = "/getStatusAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getStatusAll")
	public TicketStatusListResponse getStatusAll(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken,
			@RequestHeader(value="Language",required=false, defaultValue="") String languageCode
			) throws CyBssException{
		
		logger.info("TicketWs.getStatusAll() >>> ");
		TicketStatusListResponse response=new TicketStatusListResponse();
		
		
		Language language=null;
		if (languageCode.equals(""))
			language=languageService.getLanguage(response.getLanguageCode());
		else	
			language=languageService.getLanguage(languageCode);
			
		List<TicketStatus> states=ticketService.getStatusAll(language.getId());
		response.setTicketStates(states);
		
		logger.info("TicketWs.getStatusAll() <<< ");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/getStatusTrace",method = RequestMethod.GET)
	@CyBssOperation(name = "getStatusTrace")
	public TicketResponse getStatusTrace(
			@RequestHeader(value="Security-Token",required=true) String securityToken,
			@RequestHeader(value="Language",required=false, defaultValue="") String languageCode,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("TicketWs.getStatusTrace() >>> id="+id);
		TicketResponse response=new TicketResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getStatusTrace",String.class,String.class,Long.class))
			return response;
		// end checkGrant 
		
		Language language=null;
		if (languageCode.equals(""))
			language=languageService.getLanguage(response.getLanguageCode());
		else	
			language=languageService.getLanguage(languageCode);
		
		response.setStatusTraces(ticketService.getStatusTrace(id,language.getId()));
		
		logger.info("TicketWs.getStatusTrace() <<< ");
		
		return response;
	}
	
	
	
	
	@RequestMapping(value = "/{statusId}/getNextStates",method = RequestMethod.GET)
	@CyBssOperation(name = "getNextStates")
	public TicketStatusListResponse getNextStates(
			@RequestHeader("Security-Token") String securityToken,
			@RequestHeader(value="Language",required=false, defaultValue="") String languageCode,
			@PathVariable("statusId") Long statusId
			) throws CyBssException{
		
		logger.info("TicketWs.getStatusNext() >>> "+statusId);
		TicketStatusListResponse response=new TicketStatusListResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getNextStates",String.class,String.class,Long.class))
			return response;
		// end checkGrant 
		
		
		Language language=null;
		if (languageCode.equals(""))
			language=languageService.getLanguage(response.getLanguageCode());
		else	
			language=languageService.getLanguage(languageCode);
		
		
		List<TicketStatus> states=ticketService.getNextStates(statusId, language.getId());
		response.setTicketStates(states);
		
		logger.info("TicketWs.getStatusNext() <<< ");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/changeStatus",method = RequestMethod.POST)
	@CyBssOperation(name = "changeStatus")
	public TicketResponse changeStatus(
			@RequestHeader("Security-Token") String securityToken,
			@RequestHeader(value="Language",required=false, defaultValue="") String languageCode,
			@PathVariable("id") Long id,
			@RequestBody TicketChangeStatus changeStatus
			) throws CyBssException
	{
	TicketResponse response=new TicketResponse();
	logger.info("TicketWs.changeStatus() >>> id="+id+";changeStatus="+changeStatus);
	
	// checkGrant
	if (!checkGrant(response,securityToken,"changeStatus",String.class,String.class,Long.class,TicketChangeStatus.class))
		return response;
	// end checkGrant 
	
	Language language=null;
	if (languageCode.equals(""))
		language=languageService.getLanguage(response.getLanguageCode());
	else	
		language=languageService.getLanguage(languageCode);
	
	Ticket ticket=ticketService.get(id, language.getId());
	if (ticket==null){
		setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
				ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		return response;
	}
	
	List<TicketStatus> nextStates=ticketService.getNextStates(ticket.getStatusId(), language.getId());
	TicketStatus newState=new TicketStatus();
	newState.setId(changeStatus.getStatusId());
	if (nextStates==null || !nextStates.contains(newState)){
		setResult(response, ICyBssMessageConst.RESULT_STATUS_CANNOT, 
				ICyBssMessageConst.RESULT_D_STATUS_CANNOT,response.getLanguageCode());
		return response;
	}
	
	ticketService.changeStatus(ticket,changeStatus.getStatusId(),response.getUserId(),changeStatus.getNote(),language.getId());
	response.setTicket(ticketService.get(id, language.getId()));
	
	logger.info("TicketWs.changeStatus() <<< ");
	return response;
	}	
	
	@RequestMapping(value = "/{id}/setLocation",method = RequestMethod.POST)
	@CyBssOperation(name = "setLocation")
	public TicketResponse setLocation(
			@RequestHeader("Security-Token") String securityToken,
			@RequestHeader(value="Language",required=false, defaultValue="") String languageCode,
			@PathVariable("id") Long id,
			@RequestBody Location location
			) throws CyBssException
	{
		TicketResponse response=new TicketResponse();
		
		logger.info("TicketWs.setLocation() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"setLocation",String.class,String.class,Long.class,Location.class))
				return response;
		// end checkGrant 
		
		Language language=null;
		if (languageCode.equals(""))
			language=languageService.getLanguage(response.getLanguageCode());
		else	
			language=languageService.getLanguage(languageCode);
		
		Ticket ticket=ticketService.get(id,language.getId());
		if (ticket==null){
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		
		location.setName("Ticket #"+id);
		location.setLocationType(Ticket.ENTITY_NAME);
		location.setUserId(response.getUserId());
		location.setPersonId(ticket.getPersonId());
		
		ticket.setLocation(location);
		
		ticketService.update(id, ticket,language.getId());
		ticket=ticketService.get(id, language.getId());
		ticket.setLocation(locationService.get(ticket.getLocationId(),language.getId()));
		
		response.setTicket(ticket);
		
		logger.info("TicketWs.setLocation() <<<");
		
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/update",method = RequestMethod.POST)
	@CyBssOperation(name = "update")
	public TicketResponse update(
			@RequestHeader("Security-Token") String securityToken,
			@RequestHeader(value="Language",required=false, defaultValue="") String languageCode,
			@PathVariable("id") Long id,
			@RequestBody Ticket ticket
			) throws CyBssException
	{
		TicketResponse response=new TicketResponse();
		
		logger.info("TicketWs.update() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"update",String.class,String.class,Long.class,Ticket.class))
			return response;
		// end checkGrant 
		
		Language language=null;
		if (languageCode.equals(""))
			language=languageService.getLanguage(response.getLanguageCode());
		else	
			language=languageService.getLanguage(languageCode);
		
		if (ticketService.get(id,language.getId())==null){
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		ticketService.update(id, ticket,language.getId());
		ticket=ticketService.get(id, language.getId());
		ticket.setLocation(locationService.get(ticket.getLocationId(),language.getId()));
		
		response.setTicket(ticket);
		
		logger.info("TicketWs.update() <<<");
		return response;
	}
	
	@RequestMapping(value = "/{id}/remove",method = RequestMethod.GET)
	@CyBssOperation(name = "remove")
	public TicketResponse remove(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("TicketWs.remove() >>> id="+id);
		TicketResponse response=new TicketResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",String.class,Long.class))
			return response;
		// end checkGrant 
		
		Language language=languageService.getLanguage(response.getLanguageCode());
		ticketService.remove(id,language.getId());
		
		logger.info("TicketWs.remove() <<< ");
		return response;
	}
	
	
	
	@RequestMapping(value = "/find",method = RequestMethod.GET)
	@CyBssOperation(name = "find")
	public TicketListResponse find(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken,
			@RequestHeader(value="Language",required=false, defaultValue="") String languageCode,
			@RequestParam(value="text", required=false, defaultValue="") String text,
			@RequestParam(value="categoryId", required=false, defaultValue="0") Long categoryId,
			@RequestParam(value="statusId", required=false, defaultValue="0") Long statusId,
			@RequestParam(value="personId", required=false, defaultValue="0") Long personId,
			@RequestParam(value="fromDate", required=false, defaultValue="") String fromDate,
			@RequestParam(value="toDate", required=false, defaultValue="") String toDate,
			@RequestParam(value="offset", required=false, defaultValue="0") Integer offset,
			@RequestParam(value="size", required=false, defaultValue="100") Integer size
			) throws CyBssException{
		
		logger.info("TicketWs.find() >>>" );
		TicketListResponse response=new TicketListResponse();
		
		Language language=null;
		if (languageCode.equals(""))
			language=languageService.getLanguage(response.getLanguageCode());
		else	
			language=languageService.getLanguage(languageCode);
		
		
		List<Ticket> tickets=ticketService.find(text, categoryId, statusId, personId, fromDate, toDate,language.getId());
		int lsize=tickets.size();
		
		if (offset!=0){
			if (offset<=lsize)
				response.setTickets(tickets.subList(offset-1, ((offset-1)+size)>lsize?lsize:(offset-1)+size));
			else
				response.setTickets(new ArrayList<Ticket>());
			}
		else
			response.setTickets(tickets);
		
		logger.info("TicketWs.find() <<< ");
		
		return response;
	}
	
	
	
}

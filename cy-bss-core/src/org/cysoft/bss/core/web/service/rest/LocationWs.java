package org.cysoft.bss.core.web.service.rest;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.FileDao;
import org.cysoft.bss.core.dao.LocationDao;
import org.cysoft.bss.core.model.Location;
import org.cysoft.bss.core.model.Ticket;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.rest.LocationResponse;
import org.cysoft.bss.core.web.response.rest.TicketResponse;
import org.cysoft.bss.core.web.service.CyBssWebServiceAdapter;
import org.cysoft.bss.core.web.service.ICyBssWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
@CyBssService(name = "Location")
public class LocationWs extends CyBssWebServiceAdapter
implements ICyBssWebService{
	
	private static final Logger logger = LoggerFactory.getLogger(LocationWs.class);
	
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
	public LocationResponse add(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody Location location
			) throws CyBssException
	{
		LocationResponse response=new LocationResponse();
		
		logger.info("LocationWs.add() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"add",String.class,Location.class))
				return response;
		// end checkGrant 
		
		long locationId=locationDao.add(location);
		location.setId(locationId);
		response.setLocation(location);
		
		logger.info("LocationWs.add() <<<");
		
		return response;
	}


}

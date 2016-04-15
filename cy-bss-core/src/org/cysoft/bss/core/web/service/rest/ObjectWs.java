package org.cysoft.bss.core.web.service.rest;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.LocationDao;
import org.cysoft.bss.core.dao.ObjectDao;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.rest.ContactTypeListResponse;
import org.cysoft.bss.core.web.response.rest.ObjectListResponse;
import org.cysoft.bss.core.web.service.CyBssWebServiceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/object")
@CyBssService(name = "Object")
public class ObjectWs extends CyBssWebServiceAdapter
{
	
	private static final Logger logger = LoggerFactory.getLogger(ObjectWs.class);
	
	protected  ObjectDao objectDao=null;
	@Autowired
	public void setObjectDao(ObjectDao objectDao){
			this.objectDao=objectDao;
	}
	
	@RequestMapping(value = "/getObjectAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getObjectAll")
	public ObjectListResponse getObjectAll(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken
			) throws CyBssException{
		
		logger.info("ObjectWs.getObjectAll() >>>");
		ObjectListResponse response=new ObjectListResponse(); 
		response.setCyBssObjects(objectDao.getObjectAll());
		return response;
	}
	

}

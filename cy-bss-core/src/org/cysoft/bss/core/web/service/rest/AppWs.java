package org.cysoft.bss.core.web.service.rest;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.AppDao;
import org.cysoft.bss.core.model.App;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.rest.AppResponse;
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
@RequestMapping("/app")
@CyBssService(name = "App")
public class AppWs extends CyBssWebServiceAdapter
	implements ICyBssWebService{

	private static final Logger logger = LoggerFactory.getLogger(AppWs.class);
	
	
	private AppDao appDao=null;
	@Autowired
	public void setAppDao(AppDao appDao){
			this.appDao=appDao;
	}
	
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@CyBssOperation(name = "add")
	public AppResponse add(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody App app
			) throws CyBssException
	{
		AppResponse response=new AppResponse();
		
		logger.info("AppWs.add() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"add",String.class,App.class))
				return response;
		// end checkGrant 
	
		logger.info("AppWs.add() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/getParam/{paramName}",method = RequestMethod.GET)
	@CyBssOperation(name = "getParam")
	public AppResponse getParam(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long appId,
			@PathVariable("paramName") String paramName
			) throws CyBssException
	{
		AppResponse response=new AppResponse();
		
		logger.info("AppWs.getParam() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getParam",String.class,Long.class,String.class))
				return response;
		// end checkGrant 
	
		response.setAppParam(appDao.getParam(appId, paramName));
		
		logger.info("AppWs.getParam() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/{appId}/getMessage/{langCode}/{id}",method = RequestMethod.GET)
	@CyBssOperation(name = "getMessage")
	public AppResponse getMessage(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("appId") Long appId,
			@PathVariable("langCode") String langCode,
			@PathVariable("id") String id
			) throws CyBssException
	{
		AppResponse response=new AppResponse();
		
		logger.info("AppWs.getMessage() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getMessage",String.class,Long.class,String.class,String.class))
			return response;
		//end checkGrant 
	
		response.setAppMessage(appDao.getMessage(appId, languageDao.getLanguage(langCode).getId(), id));

		logger.info("AppWs.getMessage() <<<");
		
		return response;
	}
	
	
	
}

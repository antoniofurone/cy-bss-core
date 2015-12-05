package org.cysoft.bss.core.web.service.rest;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.App;
import org.cysoft.bss.core.model.AppVariable;
import org.cysoft.bss.core.model.Language;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.cysoft.bss.core.web.response.rest.AppResponse;
import org.cysoft.bss.core.web.service.CyBssWebServiceAdapter;
import org.cysoft.bss.core.web.service.ICyBssWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
@CyBssService(name = "App")
public class AppWs extends CyBssWebServiceAdapter
	implements ICyBssWebService{

	private static final Logger logger = LoggerFactory.getLogger(AppWs.class);
	
	
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
		
		appDao.add(app);
		response.setApp(app);
		logger.info("AppWs.add() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/update",method = RequestMethod.POST)
	@CyBssOperation(name = "update")
	public AppResponse update(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody App app
			) throws CyBssException
	{
		AppResponse response=new AppResponse();
		
		logger.info("AppWs.update() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"update",String.class,Long.class,App.class))
			return response;
		// end checkGrant
		
		appDao.update(id, app);
		app=appDao.get(app.getId());
		if (app!=null)
			response.setApp(app);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
	
		logger.info("AppWs.update() <<<");
		
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/get",method = RequestMethod.GET)
	@CyBssOperation(name = "get")
	public AppResponse get(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("AppWs.get() >>> id="+id);
		AppResponse response=new AppResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"get",String.class,Long.class))
			return response;
		// end checkGrant
				
		response.setApp(appDao.get(id));
		
		logger.info("AppWs.get() <<< ");
		
		return response;
	}
	
	
	@RequestMapping(value = "/getByName",method = RequestMethod.GET)
	@CyBssOperation(name = "getByName")
	public AppResponse getByName(
			@RequestHeader("Security-Token") String securityToken,
			@RequestParam(value="name", required=true) String name
			) throws CyBssException{
		
		logger.info("AppWs.getByName() >>> name="+name);
		AppResponse response=new AppResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getByName",String.class,String.class))
			return response;
		// end checkGrant
		
		response.setApp(appDao.getByName(name));
		
		logger.info("AppWs.getByName() <<< ");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/remove",method = RequestMethod.GET)
	@CyBssOperation(name = "remove")
	public AppResponse remove(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("AppWs.remove() >>> id="+id);
		AppResponse response=new AppResponse();
		
		appDao.remove(id);
		
		logger.info("AppWs.remove() <<< ");
		
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
	
	@RequestMapping(value = "/{appId}/getMessage/{id}",method = RequestMethod.GET)
	@CyBssOperation(name = "getMessage")
	public AppResponse getMessage(
			@RequestHeader("Security-Token") String securityToken,
			@RequestHeader(value="Language",required=false, defaultValue="") String langCode,
			@PathVariable("appId") Long appId,
			@PathVariable("id") String id
			) throws CyBssException
	{
		AppResponse response=new AppResponse();
		
		logger.info("AppWs.getMessage() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getMessage",String.class,String.class,Long.class,String.class))
			return response;
		//end checkGrant 
	
		Language language=null;
		if (langCode.equals(""))
			language=languageDao.getLanguage(response.getLanguageCode());
		else	
			language=languageDao.getLanguage(langCode);
		
		response.setAppMessage(appDao.getMessage(appId, language.getId(), id));

		logger.info("AppWs.getMessage() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/{appId}/getVariable/{name}",method = RequestMethod.GET)
	@CyBssOperation(name = "getVariable")
	public AppResponse getVariable(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("appId") Long appId,
			@PathVariable("name") String name
			) throws CyBssException
	{
		AppResponse response=new AppResponse();
		
		logger.info("AppWs.getVariable() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getVariable",String.class,Long.class,String.class))
			return response;
		//end checkGrant 
	
		response.setAppVariable(appDao.getVariable(appId, name));

		logger.info("AppWs.getVariable() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/putVariable",method = RequestMethod.POST)
	@CyBssOperation(name = "putVariable")
	public AppResponse putVariable(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody AppVariable appVariable
			) throws CyBssException
	{
		AppResponse response=new AppResponse();
		
		logger.info("AppWs.putVariable() >>> id="+appVariable.getAppId());
		
		// checkGrant
		if (!checkGrant(response,securityToken,"putVariable",String.class,AppVariable.class))
			return response;
		//end checkGrant 
		
		appDao.putVariable(appVariable.getAppId(), appVariable.getName(), appVariable.getValue(), appVariable.getType());
		response.setAppVariable(appVariable);
		
		logger.info("AppWs.putVariable() <<<");
		
		return response;
	}
	
}

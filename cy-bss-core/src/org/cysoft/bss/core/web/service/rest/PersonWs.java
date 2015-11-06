package org.cysoft.bss.core.web.service.rest;


import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Person;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.rest.PersonListResponse;
import org.cysoft.bss.core.web.response.rest.PersonResponse;
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
@RequestMapping("/person")
@CyBssService(name = "Person")
public class PersonWs extends CyBssWebServiceAdapter
	implements ICyBssWebService{
	
	private static final Logger logger = LoggerFactory.getLogger(PersonWs.class);
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@CyBssOperation(name = "add")
	public PersonResponse add(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody Person person
			) throws CyBssException
	{
		PersonResponse response=new PersonResponse();
		
		logger.info("PersonWs.add() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"add",String.class,Person.class))
				return response;
		// end checkGrant 
		
		/*
		if (userDao.getByUserId(user.getUserId())!=null){
			setResult(response, ICyBssResultConst.RESULT_USERID_USED, 
					ICyBssResultConst.RESULT_D_USERID_USED,response.getLanguageCode());
			return response;
		}
		
		userDao.add(user);
		response.setUser(userDao.getByUserId(user.getUserId()));
		*/
		
		logger.info("PersonWs.add() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/update",method = RequestMethod.POST)
	@CyBssOperation(name = "update")
	public PersonResponse update(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody Person person
			) throws CyBssException
	{
		PersonResponse response=new PersonResponse();
		
		logger.info("PersonWs.update() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"update",String.class,Long.class,Person.class))
			return response;
		// end checkGrant 
		
		/*
		if (userDao.get(id)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		userDao.update(id, user);
		response.setUser(userDao.get(id));
		*/
		
		logger.info("PersonWs.update() <<<");
		
		return response;
	}
	
	
	
	@RequestMapping(value = "/{id}/get",method = RequestMethod.GET)
	@CyBssOperation(name = "get")
	public PersonResponse get(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("PersonWs.get() >>> id="+id);
		PersonResponse response=new PersonResponse();
		
		// checkGrant
		if (!checkGrantSelf(response,securityToken,id,"get",String.class,Long.class)&&
			response.getUserId()!=id)
			return response;
		// end checkGrant 
				
		/*
		User user=userDao.get(id);
		if (user!=null)
			response.setUser(user);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		*/
		
		logger.info("PersonWs.get() <<< ");
		
		return response;
	}
	
	@RequestMapping(value = "/getByCode",method = RequestMethod.GET)
	@CyBssOperation(name = "getByCode")
	public PersonResponse getByPersonId(
			@RequestHeader("Security-Token") String securityToken,
			@RequestParam(value="code", required=true) String code
			) throws CyBssException{
		
		logger.info("PersonWs.getByCode() >>> personId="+code);
		PersonResponse response=new PersonResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getByCode",String.class,String.class))
			return response;
		// end checkGrant 
				
		/*
		User user=userDao.getByUserId(userId);
		if (user!=null)
			response.setUser(user);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		*/
		
		logger.info("PersonWs.getByCode() <<< ");
		
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/remove",method = RequestMethod.GET)
	@CyBssOperation(name = "remove")
	public PersonResponse remove(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("PersonWs.remove() >>> id="+id);
		PersonResponse response=new PersonResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",String.class,Long.class))
			return response;
		// end checkGrant 
		/*
		userDao.remove(id);
		
		logger.info("UserWs.remove() <<< ");
		*/
		return response;
	}
	
	
	@RequestMapping(value = "/find",method = RequestMethod.GET)
	@CyBssOperation(name = "find")
	public PersonListResponse find(
			@RequestHeader("Security-Token") String securityToken,
			@RequestParam(value="firstName", required=false, defaultValue="") String firstName,
			@RequestParam(value="secondName", required=false, defaultValue="") String secondName,
			@RequestParam(value="offset", required=false, defaultValue="0") Integer offset,
			@RequestParam(value="size", required=false, defaultValue="100") Integer size
			) throws CyBssException{
		
		logger.info("PersonWs.find() >>> firstName="+firstName+";secondName="+secondName);
		PersonListResponse response=new PersonListResponse();
		
		
		// checkGrant
		if (!checkGrant(response,securityToken,"find",String.class,String.class,String.class,Integer.class,Integer.class))
			return response;
		// end checkGrant 
		
		/*
		List<User> users=userDao.find(name);
		int lsize=users.size();
		if (offset!=0)
			response.setUsers(users.subList(offset-1, (offset-1)+(size>lsize?lsize:size)));
		else
			response.setUsers(users);
		*/
		logger.info("PersonWs.find() <<< ");
		
		return response;
	}
	
		
}

package org.cysoft.bss.core.web.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssPwdEncryption;
import org.cysoft.bss.core.model.ChangePwd;
import org.cysoft.bss.core.model.PwdEncrypted;
import org.cysoft.bss.core.model.User;
import org.cysoft.bss.core.model.UserRole;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.cysoft.bss.core.web.response.rest.user.UserListResponse;
import org.cysoft.bss.core.web.response.rest.user.UserResponse;
import org.cysoft.bss.core.web.response.rest.user.UserRoleListResponse;
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
@RequestMapping("/user")
@CyBssService(name = "User")
public class UserWs extends CyBssWebServiceAdapter
	implements ICyBssWebService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserWs.class);
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@CyBssOperation(name = "add")
	public UserResponse add(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody User user
			) throws CyBssException
	{
		UserResponse response=new UserResponse();
		
		logger.info("UserWs.add() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"add",String.class,User.class))
				return response;
		// end checkGrant 
		
		if (userService.getByUserId(user.getUserId())!=null){
			setResult(response, ICyBssResultConst.RESULT_USERID_USED, 
					ICyBssResultConst.RESULT_D_USERID_USED,response.getLanguageCode());
			return response;
		}
		
		userService.add(user);
		response.setUser(userService.getByUserId(user.getUserId()));
		
		logger.info("UserWs.add() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/update",method = RequestMethod.POST)
	@CyBssOperation(name = "update")
	public UserResponse update(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody User user
			) throws CyBssException
	{
		UserResponse response=new UserResponse();
		
		logger.info("UserWs.update() >>> id="+id);
		
		// checkGrant
		if (!checkGrantSelf(response,securityToken,id,"update",String.class,Long.class,User.class)&&
			response.getUserId()!=id)
			return response;
		// end checkGrant 
		
		if (userService.get(id)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		userService.update(id, user);
		response.setUser(userService.get(id));
		
		logger.info("UserWs.update() <<<");
		
		return response;
	}
	
	
	
	@RequestMapping(value = "/{id}/get",method = RequestMethod.GET)
	@CyBssOperation(name = "get")
	public UserResponse get(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("UserWs.get() >>> id="+id);
		UserResponse response=new UserResponse();
		
		// checkGrant
		if (!checkGrantSelf(response,securityToken,id,"get",String.class,Long.class)&&
			response.getUserId()!=id)
			return response;
		// end checkGrant 
				
		
		User user=userService.get(id);
		if (user!=null)
			response.setUser(user);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		
		logger.info("UserWs.get() <<< ");
		
		return response;
	}
	
	@RequestMapping(value = "/getByUserId",method = RequestMethod.GET)
	@CyBssOperation(name = "getByUserId")
	public UserResponse getByUserId(
			@RequestHeader("Security-Token") String securityToken,
			@RequestParam(value="userId", required=true) String userId
			) throws CyBssException{
		
		logger.info("UserWs.getUserId() >>> userId="+userId);
		UserResponse response=new UserResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getByUserId",String.class,String.class))
			return response;
		// end checkGrant 
				
		
		User user=userService.getByUserId(userId);
		if (user!=null)
			response.setUser(user);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		
		
		logger.info("UserWs.getUserId() <<< ");
		
		return response;
	}
	
	
	
	@RequestMapping(value = "/{id}/disable",method = RequestMethod.GET)
	@CyBssOperation(name = "disable")
	public UserResponse disable(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("UserWs.disable() >>> id="+id);
		UserResponse response=new UserResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"disable",String.class,Long.class))
			return response;
		// end checkGrant 
		
		userService.enableDisable(id,false);
		response.setUser(userService.get(id));
		
		logger.info("UserWs.disable() <<< ");
		return response;
	}
	
	@RequestMapping(value = "/{id}/enable",method = RequestMethod.GET)
	@CyBssOperation(name = "enable")
	public UserResponse enable(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("UserWs.enable() >>> id="+id);
		UserResponse response=new UserResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"enable",String.class,Long.class))
			return response;
		// end checkGrant 
		
		userService.enableDisable(id,true);
		response.setUser(userService.get(id));
		
		logger.info("UserWs.enable() <<< ");
		
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/remove",method = RequestMethod.GET)
	@CyBssOperation(name = "remove")
	public UserResponse remove(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("UserWs.remove() >>> id="+id);
		UserResponse response=new UserResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",String.class,Long.class))
			return response;
		// end checkGrant 
		
		userService.remove(id);
		
		logger.info("UserWs.remove() <<< ");
		
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/removePerson",method = RequestMethod.GET)
	@CyBssOperation(name = "removePerson")
	public UserResponse removePerson(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("UserWs.removePerson() >>> id="+id);
		UserResponse response=new UserResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"removePerson",String.class,Long.class))
			return response;
		// end checkGrant 
		
		userService.updatePeson(id, 0);
		User user=userService.get(id);
		if (user!=null)
			response.setUser(user);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		
		logger.info("UserWs.removePerson() <<< ");
		
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/addPerson/{personId}",method = RequestMethod.GET)
	@CyBssOperation(name = "addPerson")
	public UserResponse addPerson(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@PathVariable("personId") Long personId
			) throws CyBssException{
		
		logger.info("UserWs.addPerson() >>> id="+id+";personId="+personId);
		UserResponse response=new UserResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"addPerson",String.class,Long.class,Long.class))
			return response;
		// end checkGrant 
		
		userService.updatePeson(id, personId);
		
		User user=userService.get(id);
		if (user!=null)
			response.setUser(user);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		
		logger.info("UserWs.addPerson() <<< ");
		
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/changePwd",method = RequestMethod.POST)
	@CyBssOperation(name = "changePwd")
	public UserResponse changePwd(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody ChangePwd changePwd
			) throws CyBssException{
		
		logger.info("UserWs.chargePwd() >>> id="+id);
		UserResponse response=new UserResponse();
		
		// checkGrant
		if (!checkGrantSelf(response,securityToken,id,"changePwd",String.class,Long.class,ChangePwd.class)&&
			response.getUserId()!=id)
			return response;
		// end checkGrant 
		
		PwdEncrypted oldPwd=userService.getPwd(id);
		
		if (oldPwd==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
		}
		
		if (!CyBssPwdEncryption.authenticate(changePwd.getOldPwd(), oldPwd.getPwd(), oldPwd.getSalt())){
			setResult(response, ICyBssResultConst.RESULT_PWD_DIFF, 
					ICyBssResultConst.RESULT_D_PWD_DIFF,response.getLanguageCode());
			return response;
		}
		
		userService.changPwd(id, changePwd.getNewPwd());
		
		logger.info("UserWs.changePwd() <<< ");
		
		return response;
	}

	
	
	
	@RequestMapping(value = "/find",method = RequestMethod.GET)
	@CyBssOperation(name = "find")
	public UserListResponse find(
			@RequestHeader("Security-Token") String securityToken,
			@RequestParam(value="name", required=false, defaultValue="") String name,
			@RequestParam(value="offset", required=false, defaultValue="0") Integer offset,
			@RequestParam(value="size", required=false, defaultValue="100") Integer size
			) throws CyBssException{
		
		logger.info("UserWs.find() >>> name="+name);
		UserListResponse response=new UserListResponse();
		
		
		// checkGrant
		if (!checkGrant(response,securityToken,"find",String.class,String.class,Integer.class,Integer.class))
			return response;
		// end checkGrant 
		
		List<User> users=userService.find(name);
		int lsize=users.size();
		
		if (offset!=0){
			if (offset<=lsize)
				response.setUsers(users.subList(offset-1, ((offset-1)+size)>lsize?lsize:(offset-1)+size));
			else
				response.setUsers(new ArrayList<User>());
			}
		else
			response.setUsers(users);
		
		logger.info("UserWs.find() <<< ");
		
		return response;
	}
	
	
	@RequestMapping(value = "/getRoleAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getRoleAll")
	public UserRoleListResponse getRoleAll(
			@RequestHeader("Security-Token") String securityToken
			) throws CyBssException{
		
		logger.info("UserWs.getRoleAll() >>>");
		UserRoleListResponse response=new UserRoleListResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getRoleAll",String.class))
			return response;
		// end checkGrant 
		
		
		List<UserRole> roles=userService.getRoleAll();
		response.setRoles(roles);
		
		logger.info("UserWs.getRoleAll() <<<");
		
		return response;
	}
	
	
}

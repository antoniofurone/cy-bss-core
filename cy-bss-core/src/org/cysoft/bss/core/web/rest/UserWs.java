package org.cysoft.bss.core.web.rest;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.User;
import org.cysoft.bss.core.rest.response.user.UserAdd;
import org.cysoft.bss.core.web.rest.annotation.CyBssOperation;
import org.cysoft.bss.core.web.rest.annotation.CyBssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CyBssService(name = "User")
public class UserWs extends CyBssRestServiceAdapter
	implements ICyBssRestService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserWs.class);
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@CyBssOperation(name = "add")
	public UserAdd add(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody User user
			) throws CyBssException
	{
		UserAdd response=new UserAdd();
		
		logger.info("UserWs.add() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"add",String.class,User.class))
				return response;
		// end checkGrant 
		System.out.println("user="+user);
		
		userDao.add(user);
		
		logger.info("UserWs.add() <<<");
		
		return response;
	}

}

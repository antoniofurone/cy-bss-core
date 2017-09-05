package org.cysoft.bss.core.web.service.rest;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.model.User;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.cysoft.bss.core.web.response.rest.auth.CyBssAuthLogOff;
import org.cysoft.bss.core.web.response.rest.auth.CyBssAuthLogOn;
import org.cysoft.bss.core.web.service.CyBssWebServiceAdapter;
import org.cysoft.bss.core.web.service.ICyBssWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cybss-auth")
@CyBssService(name = "BssAuth")
public class CyBssAuthWs extends CyBssWebServiceAdapter
	implements ICyBssWebService
{
	
	private static final Logger logger = LoggerFactory.getLogger(CyBssAuthWs.class);
	
	
	@RequestMapping(value = "/logOn",method = RequestMethod.POST)
	public CyBssAuthLogOn logOn(
			@RequestBody User pUser
			) throws CyBssException{
		
		logger.info("CyBssAuthWs.logOn() >>>");
		
		CyBssAuthLogOn auth=new CyBssAuthLogOn(); 
		
		if (authService.logOn(pUser.getUserId(), pUser.getPwd())){
			setResult(auth, ICyBssResultConst.RESULT_OK, ICyBssResultConst.RESULT_D_OK);
			
			String securityToken=CyBssUtility.genToken(pUser.getUserId());
			User user=userService.getByUserId(pUser.getUserId());
			authService.createSession(user.getId(),securityToken);
			
			auth.setSecurityToken(securityToken);
			auth.setUser(user);
			
		}
		else
		{
			setResult(auth, ICyBssResultConst.RESULT_NOK, ICyBssResultConst.RESULT_D_LOGON_NOK);
			auth.setUser(pUser);
			auth.getUser().setPwd("***");
		}
		
		
	    return auth;
	}
	
	@RequestMapping(value = "/logOff",method = RequestMethod.GET)
	@CyBssOperation(name = "logOff")
	public CyBssAuthLogOff logOff(
			@RequestHeader("Security-Token") String securityToken
			) throws CyBssException
	{		
		
		CyBssAuthLogOff response=new CyBssAuthLogOff(); 
		
		// checkGrant
		if (!checkGrant(response,securityToken,"logOff",String.class))
				return response;
		// end checkGrant 
		
		authService.discardSession(securityToken);
		
		return response;
	}
	
	
}

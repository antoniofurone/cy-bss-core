package org.cysoft.bss.core.web.rest;




import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.dao.CyBssAuthDao;
import org.cysoft.bss.core.dao.UserDao;
import org.cysoft.bss.core.model.User;
import org.cysoft.bss.core.model.rest.response.CyBssAuthLogOff;
import org.cysoft.bss.core.model.rest.response.CyBssAuthLogOn;
import org.cysoft.bss.core.model.rest.response.ICyBssResultConst;
import org.cysoft.bss.core.web.rest.annotation.CyBssOperation;
import org.cysoft.bss.core.web.rest.annotation.CyBssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cybss-auth")
@CyBssService(name = "BssAuth")
public class CyBssAuthWs extends CyBssRestServiceAdapter
	implements ICyBssRestService
{
	
	private static final Logger logger = LoggerFactory.getLogger(CyBssAuthWs.class);
	
	private CyBssAuthDao authDao=null;
	
	@Autowired
	public void setAuthDao(CyBssAuthDao authDao){
			this.authDao=authDao;
	}
	
	private UserDao userDao=null;
	
	@Autowired
	public void setUserDao(UserDao userDao){
			this.userDao=userDao;
	}
	
	
	@RequestMapping(value = "/logOn",method = RequestMethod.POST)
	public CyBssAuthLogOn logOn(
			@RequestBody User pUser
			){
		
		logger.info("CyBssAuthWs.logOn() >>>");
		
		CyBssAuthLogOn auth=new CyBssAuthLogOn(); 
		
		if (authDao.logOn(pUser.getUserId(), pUser.getPwd())){
			setResult(auth, ICyBssResultConst.RESULT_OK, ICyBssResultConst.RESULT_D_OK);
			
			String securityToken=CyBssUtility.genToken(pUser.getUserId());
			User user=userDao.getByUserId(pUser.getUserId());
			authDao.createSession(user.getId(),securityToken);
			
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
			)
	{		
		CyBssAuthLogOff response=new CyBssAuthLogOff(); 
		System.out.println("Security-Token="+securityToken);
		CyBssService bssService=this.getClass().getAnnotation(CyBssService.class);
		System.out.println(bssService.name());
		
		try {
			CyBssOperation bssOperation=this.getClass().getMethod("logOff",String.class).getAnnotation(CyBssOperation.class);
			System.out.println(bssOperation.name());
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}
}

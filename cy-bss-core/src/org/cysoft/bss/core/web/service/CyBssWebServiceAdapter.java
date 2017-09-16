package org.cysoft.bss.core.web.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.message.ICyBssMessageConst;
import org.cysoft.bss.core.model.User;
import org.cysoft.bss.core.model.UserRole;
import org.cysoft.bss.core.service.AppService;
import org.cysoft.bss.core.service.CyBssAuthService;
import org.cysoft.bss.core.service.LanguageService;
import org.cysoft.bss.core.service.UserService;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;

public abstract class CyBssWebServiceAdapter {
	
	protected Environment env;
	@Autowired
	public void setEnvironment(Environment env){
		this.env=env;
	}
	
	protected MessageSource msgSource;
	@Autowired
	public void setMessageSource(MessageSource msgSource){
		this.msgSource=msgSource;
	}
	
	protected CyBssAuthService authService=null;
	@Autowired
	public void setAuthService(CyBssAuthService authService){
			this.authService=authService;
	}
	
	protected UserService userService=null;
	@Autowired
	public void setUserService(UserService userService){
			this.userService=userService;
	}
	
	protected LanguageService languageService=null;
	@Autowired
	public void setLanguageService(LanguageService languageService){
			this.languageService=languageService;
	}
	
	protected AppService appService=null;
	@Autowired
	public void setAppService(AppService appService){
			this.appService=appService;
	}
	
	protected void setResult(ICyBssResponse response,
					String resultCode,String resultDescId,Locale locale){
		response.setResultCode(resultCode);
		response.setResultDesc(msgSource.getMessage(resultDescId, null, locale));
	}

	protected void setResult(ICyBssResponse response,
			String resultCode,String resultDescId,String languageCode){
		setResult(response,resultCode,resultDescId,CyBssUtility.getLocale(languageCode));	
	}
	
	protected void setResult(ICyBssResponse response,
			String resultCode,String resultDescId){
		setResult(response,resultCode,resultDescId,Locale.ENGLISH);	
	}
	
	protected boolean checkGrant(ICyBssResponse response,Method method, String securityToken, long id){
		boolean ret=false;
		
		authService.refreshSession(securityToken);
		long userId=authService.getUserIdByToken(securityToken);
		if (userId==0){
			setResult(response,ICyBssMessageConst.RESULT_SESSION_NOK,ICyBssMessageConst.RESULT_D_SESSION_NOK);
			return ret;
		}
		
		User user=userService.get(userId);
		response.setUserId(user.getId());
		response.setLanguageCode(user.getLanguageCode());
		
		// Grant self
		if (response.getUserId()==id)
			return true;
		
		List<UserRole> roles=userService.getRoleAll(); 
		
		List<UserRole> userRoles=new ArrayList<UserRole>();
		long roleId=user.getRoleId();
		while (roleId!=0){
			UserRole userRole=getUserRole(roleId,roles);
			if (userRole!=null){
				userRoles.add(userRole);
				roleId=userRole.getParentId();
			}
			else
				break;
		}
		
		String serviceName=this.getClass().getAnnotation(CyBssService.class).name();
		String operationName=method.getAnnotation(CyBssOperation.class).name();
		
		for(UserRole role:userRoles){
			if (authService.checkGrant(role.getId(), serviceName, operationName)){
				ret=true;
				break;
			}
		}
			
		if (!ret)
			setResult(response,ICyBssMessageConst.RESULT_GRANT_NOK,
					ICyBssMessageConst.RESULT_D_GRANT_NOK,
					CyBssUtility.getLocale(user.getLanguageCode()));
				
		return ret;
	}
	
	protected boolean checkGrantSelf(ICyBssResponse response, String securityToken, 
			long id,String methodName,Class<?> ...paramClasses) throws CyBssException{
		
		boolean ret=false;
		
		try {
			ret=checkGrant(response,this.getClass().getMethod(methodName, paramClasses),securityToken,id);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CyBssException(e);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CyBssException(e);
		}
		
		return ret;
	} 
	
	protected boolean checkGrant(ICyBssResponse response, String securityToken, 
			String methodName,Class<?> ...paramClasses) throws CyBssException{
		
		boolean ret=false;
		
		try {
			ret=checkGrant(response,this.getClass().getMethod(methodName, paramClasses),securityToken,0);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CyBssException(e);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CyBssException(e);
		}
		
		return ret;
	}
		
	
	
	
	private UserRole getUserRole(long roleId,List<UserRole> roles){
		for(UserRole r:roles){
			if (r.getId()==roleId)
				return r;
		}
		return null;
	}
}

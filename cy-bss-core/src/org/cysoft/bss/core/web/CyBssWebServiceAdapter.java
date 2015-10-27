package org.cysoft.bss.core.web;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.dao.CyBssAuthDao;
import org.cysoft.bss.core.dao.UserDao;
import org.cysoft.bss.core.model.User;
import org.cysoft.bss.core.model.UserRole;
import org.cysoft.bss.core.web.CyBssOperation;
import org.cysoft.bss.core.web.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResponse;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
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
	
	
	protected CyBssAuthDao authDao=null;
	@Autowired
	public void setAuthDao(CyBssAuthDao authDao){
			this.authDao=authDao;
	}
	
	protected UserDao userDao=null;
	@Autowired
	public void setUserDao(UserDao userDao){
			this.userDao=userDao;
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
		
		authDao.refreshSession(securityToken);
		long userId=authDao.getUserIdByToken(securityToken);
		if (userId==0){
			setResult(response,ICyBssResultConst.RESULT_SESSION_NOK,ICyBssResultConst.RESULT_D_SESSION_NOK);
			return ret;
		}
		
		User user=userDao.get(userId);
		response.setUserId(user.getId());
		response.setLanguageCode(user.getLanguageCode());
		
		// Grant self
		if (response.getUserId()==id)
			return true;
		
		List<UserRole> roles=userDao.getRoleAll(); 
		
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
			if (authDao.checkGrant(role.getId(), serviceName, operationName)){
				ret=true;
				break;
			}
		}
			
		if (!ret)
			setResult(response,ICyBssResultConst.RESULT_GRANT_NOK,
					ICyBssResultConst.RESULT_D_GRANT_NOK,
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

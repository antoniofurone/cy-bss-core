package org.cysoft.bss.core.service.impl;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.CyBssAuthDao;
import org.cysoft.bss.core.service.CyBssAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CyBssAuthServiceImpl extends CyBssServiceBase 
implements CyBssAuthService{

	protected CyBssAuthDao authDao=null;
	@Autowired
	public void setAuthDao(CyBssAuthDao authDao){
			this.authDao=authDao;
	}
	
	@Override
	public boolean logOn(String userId, String pwd) throws CyBssException {
		// TODO Auto-generated method stub
		return authDao.logOn(userId, pwd);
	}

	@Override
	public void createSession(long userId, String securityToken) {
		// TODO Auto-generated method stub
		authDao.createSession(userId, securityToken);
	}

	@Override
	public void refreshSession(String securityToken) {
		// TODO Auto-generated method stub
		authDao.refreshSession(securityToken);
	}

	@Override
	public void discardSession(String securityToken) {
		// TODO Auto-generated method stub
		authDao.discardSession(securityToken);
	}

	@Override
	public void discardSessions() {
		// TODO Auto-generated method stub
		authDao.discardSessions();
	}

	@Override
	public long getUserIdByToken(String securityToken) {
		// TODO Auto-generated method stub
		return authDao.getUserIdByToken(securityToken);
	}

	@Override
	public boolean checkGrant(long roleId, String serviceName, String operationName) {
		// TODO Auto-generated method stub
		return authDao.checkGrant(roleId, serviceName, operationName);
	}

}

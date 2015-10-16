package org.cysoft.bss.core.dao;

public interface CyBssAuthDao {
	public boolean logOn(String userId,String pwd);
	
	public void createSession(long userId,String securityToken);
	public void refreshSession(String securityToken);
	public void discardSession(String securityToken);
	public void discardSessions();
	
	
	public long getUserIdByToken(String securityToken);
	public boolean checkGrant(long roleId,String serviceName,String operationName);
	
	
}

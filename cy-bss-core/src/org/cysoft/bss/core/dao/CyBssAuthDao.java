package org.cysoft.bss.core.dao;

public interface CyBssAuthDao {
	public boolean logOn(String userId,String pwd);
	public void createSession(long userId,String securityToken);
	public void logOff(String securityToken);
	public void discardSessions();
}

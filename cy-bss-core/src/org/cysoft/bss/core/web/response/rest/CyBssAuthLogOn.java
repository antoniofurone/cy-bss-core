package org.cysoft.bss.core.web.response.rest;

import org.cysoft.bss.core.model.User;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;


public class CyBssAuthLogOn extends CyBssResponseAdapter
	implements ICyBssResponse
{
	
	private User user=null;
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	private String securityToken="";
	public String getSecurityToken() {
		return securityToken;
	}

	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
	}

	@Override
	public String toString() {
		return "CyBssAuthLogOn [user=" + user + ", securityToken="
				+ securityToken + "]";
	}

	
	
}

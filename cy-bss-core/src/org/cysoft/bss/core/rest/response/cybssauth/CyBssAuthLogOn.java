package org.cysoft.bss.core.rest.response.cybssauth;

import org.cysoft.bss.core.model.User;
import org.cysoft.bss.core.rest.response.CyBssRestResponseAdapter;
import org.cysoft.bss.core.rest.response.ICyBssRestResponse;


public class CyBssAuthLogOn extends CyBssRestResponseAdapter
	implements ICyBssRestResponse
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

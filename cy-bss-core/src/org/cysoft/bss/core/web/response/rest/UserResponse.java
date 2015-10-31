package org.cysoft.bss.core.web.response.rest;

import org.cysoft.bss.core.model.User;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class UserResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private User user=null;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

}

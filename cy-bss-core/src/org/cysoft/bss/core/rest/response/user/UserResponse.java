package org.cysoft.bss.core.rest.response.user;

import org.cysoft.bss.core.model.User;
import org.cysoft.bss.core.rest.response.CyBssRestResponseAdapter;
import org.cysoft.bss.core.rest.response.ICyBssRestResponse;

public class UserResponse extends CyBssRestResponseAdapter
implements ICyBssRestResponse{
	
	private User user=null;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

}

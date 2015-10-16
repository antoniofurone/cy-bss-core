package org.cysoft.bss.core.rest.response.user;

import java.util.List;

import org.cysoft.bss.core.model.User;
import org.cysoft.bss.core.rest.response.CyBssRestResponseAdapter;
import org.cysoft.bss.core.rest.response.ICyBssRestResponse;

public class UserListResponse extends CyBssRestResponseAdapter
implements ICyBssRestResponse{
	
	private List<User> users=null;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}

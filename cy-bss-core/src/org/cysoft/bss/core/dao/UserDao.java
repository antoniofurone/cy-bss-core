package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.model.User;
import org.cysoft.bss.core.model.UserRole;

public interface UserDao {
	public User getByUserId(String userId);
	public User get(long id);
	
	public List<UserRole> getRoleAll();

}

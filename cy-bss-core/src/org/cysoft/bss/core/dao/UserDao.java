package org.cysoft.bss.core.dao;

import org.cysoft.bss.core.model.User;

public interface UserDao {
	public User getByUserId(String userId);

}

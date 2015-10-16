package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.model.User;
import org.cysoft.bss.core.model.UserRole;

public interface UserDao {
	public User getByUserId(String userId);
	public User get(long id);
	public void add(User user);
	public void update(long id,User user);
	public void enableDisable(long id,boolean enable);
	public void remove(long id);
	public void changPwd(long id, String pwd);
	
	public List<UserRole> getRoleAll();
	public List<User> find(String name);
	public String getPwd(long id);
	
}

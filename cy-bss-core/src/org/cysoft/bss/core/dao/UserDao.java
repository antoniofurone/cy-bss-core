package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.PwdEncrypted;
import org.cysoft.bss.core.model.User;
import org.cysoft.bss.core.model.UserRole;

public interface UserDao {
	public User getByUserId(String userId);
	public User get(long id);
	public void add(User user) throws CyBssException;
	public void update(long id,User user);
	public void enableDisable(long id,boolean enable);
	public void remove(long id);
	public void updatePeson(long id,long personId);
	public void changPwd(long id, String pwd) throws CyBssException;
	
	
	public List<UserRole> getRoleAll();
	public List<User> find(String name);
	public PwdEncrypted getPwd(long id);
	
}

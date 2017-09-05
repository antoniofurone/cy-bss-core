package org.cysoft.bss.core.service.impl;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.UserDao;
import org.cysoft.bss.core.model.PwdEncrypted;
import org.cysoft.bss.core.model.User;
import org.cysoft.bss.core.model.UserRole;
import org.cysoft.bss.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends CyBssServiceBase 
implements UserService{

	protected UserDao userDao=null;
	@Autowired
	public void setUserDao(UserDao userDao){
			this.userDao=userDao;
	}
	
	@Override
	public User getByUserId(String userId) {
		// TODO Auto-generated method stub
		return userDao.getByUserId(userId);
	}

	@Override
	public User get(long id) {
		// TODO Auto-generated method stub
		return userDao.get(id);
	}

	@Override
	public void add(User user) throws CyBssException {
		// TODO Auto-generated method stub
		userDao.add(user);
	}

	@Override
	public void update(long id, User user) {
		// TODO Auto-generated method stub
		userDao.update(id, user);
	}

	@Override
	public void enableDisable(long id, boolean enable) {
		// TODO Auto-generated method stub
		userDao.enableDisable(id, enable);
	}

	@Override
	public void remove(long id) {
		// TODO Auto-generated method stub
		userDao.remove(id);
	}

	@Override
	public void updatePeson(long id, long personId) {
		// TODO Auto-generated method stub
		userDao.updatePeson(id, personId);
	}

	@Override
	public void changPwd(long id, String pwd) throws CyBssException {
		// TODO Auto-generated method stub
		userDao.changPwd(id, pwd);
	}

	@Override
	public List<UserRole> getRoleAll() {
		// TODO Auto-generated method stub
		return userDao.getRoleAll();
	}

	@Override
	public List<User> find(String name) {
		// TODO Auto-generated method stub
		return userDao.find(name);
	}

	@Override
	public PwdEncrypted getPwd(long id) {
		// TODO Auto-generated method stub
		return userDao.getPwd(id);
	}

}

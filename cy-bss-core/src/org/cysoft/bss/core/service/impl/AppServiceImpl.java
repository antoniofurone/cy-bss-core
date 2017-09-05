package org.cysoft.bss.core.service.impl;

import org.cysoft.bss.core.dao.AppDao;
import org.cysoft.bss.core.model.App;
import org.cysoft.bss.core.model.AppMessage;
import org.cysoft.bss.core.model.AppParam;
import org.cysoft.bss.core.model.AppVariable;
import org.cysoft.bss.core.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppServiceImpl extends CyBssServiceBase 
implements AppService {

	protected AppDao appDao=null;
	@Autowired
	public void setAppDao(AppDao appDao){
			this.appDao=appDao;
	}
	
	@Override
	public void add(App app) {
		// TODO Auto-generated method stub
		appDao.add(app);
	}

	@Override
	public void update(long appId, App app) {
		// TODO Auto-generated method stub
		appDao.update(appId, app);
	}

	@Override
	public App get(long appId) {
		// TODO Auto-generated method stub
		return appDao.get(appId);
	}

	@Override
	public App getByName(String name) {
		// TODO Auto-generated method stub
		return appDao.getByName(name);
	}

	@Override
	public void remove(long appId) {
		// TODO Auto-generated method stub
		appDao.remove(appId);
	}

	@Override
	public AppParam getParam(long appId, String paramName) {
		// TODO Auto-generated method stub
		return appDao.getParam(appId, paramName);
	}

	@Override
	public AppMessage getMessage(long appId, long languageId, String id) {
		// TODO Auto-generated method stub
		return appDao.getMessage(appId, languageId, id);
	}

	@Override
	public void putVariable(long appId, String name, String value, String type) {
		// TODO Auto-generated method stub
		appDao.putVariable(appId, name, value, type);
	}

	@Override
	public AppVariable getVariable(long appId, String name) {
		// TODO Auto-generated method stub
		return appDao.getVariable(appId, name);
	}
	
}

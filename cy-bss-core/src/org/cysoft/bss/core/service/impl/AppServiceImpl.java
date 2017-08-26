package org.cysoft.bss.core.service.impl;

import org.cysoft.bss.core.model.App;
import org.cysoft.bss.core.model.AppMessage;
import org.cysoft.bss.core.model.AppParam;
import org.cysoft.bss.core.model.AppVariable;
import org.cysoft.bss.core.service.AppService;

public class AppServiceImpl extends CyBssServiceImpl 
implements AppService {

	@Override
	public void add(App app) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(long appId, App app) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public App get(long appId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public App getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(long appId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AppParam getParam(long appId, String paramName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppMessage getMessage(long appId, long languageId, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putVariable(long appId, String name, String value, String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AppVariable getVariable(long appId, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	
}

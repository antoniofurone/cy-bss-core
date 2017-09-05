package org.cysoft.bss.core.service;

import org.cysoft.bss.core.model.App;
import org.cysoft.bss.core.model.AppMessage;
import org.cysoft.bss.core.model.AppParam;
import org.cysoft.bss.core.model.AppVariable;


public interface AppService {
	
	public void add(App app);
	public void update(long appId,App app);
	public App get(long appId);
	public App getByName(String name);
	public void remove(long appId);
	
	public AppParam getParam(long appId,String paramName);
	public AppMessage getMessage(long appId,long languageId,String id);
	
	
	public void putVariable(long appId, String name, String value, String type);
	public AppVariable getVariable (long appId,String name);
	

}

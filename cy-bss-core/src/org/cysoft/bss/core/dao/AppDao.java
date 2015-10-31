package org.cysoft.bss.core.dao;

import org.cysoft.bss.core.model.App;
import org.cysoft.bss.core.model.AppMessage;
import org.cysoft.bss.core.model.AppParam;

public interface AppDao {
	public void add(App app);
	public AppParam getParam(long appId,String paramName);
	public AppMessage getMessage(long appId,long languageId,String id);
	
}

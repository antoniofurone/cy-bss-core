package org.cysoft.bss.core.web.response.rest;

import org.cysoft.bss.core.model.App;
import org.cysoft.bss.core.model.AppMessage;
import org.cysoft.bss.core.model.AppParam;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class AppResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private App app=null;
	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	private AppParam appParam=null;
	public AppParam getAppParam() {
		return appParam;
	}
	public void setAppParam(AppParam appParam) {
		this.appParam = appParam;
	}
	
	private AppMessage appMessage=null;
	public AppMessage getAppMessage() {
		return appMessage;
	}
	public void setAppMessage(AppMessage appMessage) {
		this.appMessage = appMessage;
	}
	
}

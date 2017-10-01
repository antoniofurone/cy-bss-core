package org.cysoft.bss.core.common;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;

public class CyBssLoggerListener extends 
ContextAwareBase implements LoggerContextListener, LifeCycle{

	private boolean started = false;
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		if (started) return;
		String logHome=System.getenv("CYBUSINESS_HOME");
		if (logHome==null || logHome.equals(""))
			logHome=System.getProperty("user.home"); 
		
		Context context = getContext();

        context.putProperty("LOG_HOME", logHome);
        started = true;
	}

	@Override
	public boolean isStarted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isResetResistant() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLevelChange(Logger arg0, Level arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(LoggerContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(LoggerContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStop(LoggerContext arg0) {
		// TODO Auto-generated method stub
		
	}


}

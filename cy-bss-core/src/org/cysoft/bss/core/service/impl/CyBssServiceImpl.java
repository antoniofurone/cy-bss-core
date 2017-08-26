package org.cysoft.bss.core.service.impl;

import org.cysoft.bss.core.common.CyBssMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class CyBssServiceImpl{
	
	protected CyBssMessageSource msgSource;
	@Autowired
	public void setMessageSource(CyBssMessageSource msgSource){
		this.msgSource=msgSource;
	}
	
	protected DataSourceTransactionManager tx=null;
	@Autowired
	public void setTransactionManager(DataSourceTransactionManager tx){
			this.tx=tx;
	}
	
}

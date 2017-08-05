package org.cysoft.bss.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class CyBssServiceImpl{
	
	
	protected DataSourceTransactionManager tx=null;
	@Autowired
	public void setTransactionManager(DataSourceTransactionManager tx){
			this.tx=tx;
	}
	
}

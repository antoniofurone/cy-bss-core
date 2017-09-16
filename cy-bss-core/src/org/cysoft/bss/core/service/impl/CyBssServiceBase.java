package org.cysoft.bss.core.service.impl;

import org.cysoft.bss.core.dao.LanguageDao;
import org.cysoft.bss.core.message.CyBssMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public abstract class CyBssServiceBase{
	
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
	
	protected LanguageDao languageDao=null;
	@Autowired
	public void setLanguageDao(LanguageDao languageDao){
			this.languageDao=languageDao;
	}
	
}

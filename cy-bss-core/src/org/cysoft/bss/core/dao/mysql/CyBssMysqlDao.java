package org.cysoft.bss.core.dao.mysql;

import org.cysoft.bss.core.common.CyBssDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class CyBssMysqlDao {
	
	protected CyBssDataSource ds=null;
	
	@Autowired
	public void setMySqlDataSource(CyBssDataSource ds){
			this.ds=ds;
	}
	
	protected Environment env;
	
	@Autowired
	public void setEnvironment(Environment env){
		this.env=env;
	}
	
	
}

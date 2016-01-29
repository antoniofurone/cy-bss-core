package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.cysoft.bss.core.common.CyBssDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class CyBssMysqlDao{
	
	
	protected DataSourceTransactionManager tx=null;
	@Autowired
	public void setTransactionManager(DataSourceTransactionManager tx){
			this.tx=tx;
	}
	
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
	
	protected long getLastInsertId(JdbcTemplate jdbcTemplate){
		
		String query="SELECT LAST_INSERT_ID()";
		return jdbcTemplate.queryForObject(query, new Object[] { },new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
            	return rs.getLong(1);
            }
        });
	}
}

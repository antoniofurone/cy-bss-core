package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.cysoft.bss.core.dao.AppDao;
import org.cysoft.bss.core.model.App;
import org.cysoft.bss.core.model.AppMessage;
import org.cysoft.bss.core.model.AppParam;
import org.cysoft.bss.core.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class AppMysql extends CyBssMysqlDao
	implements AppDao{
	
	private static final Logger logger = LoggerFactory.getLogger(AppMysql.class);
	
	@Override
	public void add(App app) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AppParam getParam(long appId, String paramName) {
		// TODO Auto-generated method stub
		logger.info("AppMysql.getParam() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		
		String query="select  APA_S_NAME, APA_S_VALUE, APA_C_TYPE from BSST_APA_APP_PARAM where APP_N_APP_ID=? and APA_S_NAME=?";
		
		logger.info(query+"["+appId+","+paramName+"]");
		
		AppParam ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { appId,paramName },new RowMapper<AppParam>() {
	            @Override
	            public AppParam mapRow(ResultSet rs, int rowNum) throws SQLException {
	            	AppParam param=new AppParam();
	                
	                param.setName(rs.getString("APA_S_NAME"));
	                param.setValue(rs.getString("APA_S_VALUE"));
	                param.setType(rs.getString("APA_C_TYPE"));
	                return param;
	            }
	        });
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("UserMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		if (ret!=null)
			ret.setAppId(appId);
		
		logger.info("UserMysql.getBy() <<<");
		return ret;
		
	}

	@Override
	public AppMessage getMessage(long appId, long languageId, String id) {
		// TODO Auto-generated method stub
		logger.info("AppMysql.getMessage() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		String query="select  AME_S_ID,AME_S_VALUE from BSST_AME_APP_MESSAGE where APP_N_APP_ID=? and LAN_N_LANG_ID=? and AME_S_ID=?";
				
		logger.info(query+"["+appId+","+languageId+","+id+"]");
		
		AppMessage ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { appId,languageId,id },new RowMapper<AppMessage>() {
	            @Override
	            public AppMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
	            	AppMessage message=new AppMessage();
	                
	                message.setId(rs.getString("AME_S_ID"));
	                message.setText(rs.getString("AME_S_VALUE"));
	                
	                return message;
	            }
	        });
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("UserMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		if (ret!=null){
			ret.setAppId(appId);
			ret.setLanguageId(languageId);
		}
	
		logger.info("AppMysql.getMessage() <<<");
		
		return ret;
		
	}

}

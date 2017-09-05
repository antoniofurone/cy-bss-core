package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.cysoft.bss.core.dao.AppDao;
import org.cysoft.bss.core.model.App;
import org.cysoft.bss.core.model.AppMessage;
import org.cysoft.bss.core.model.AppParam;
import org.cysoft.bss.core.model.AppVariable;
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
		logger.info("AppMysql.add() >>>");
		String cmd="insert into BSST_APP_APP(APP_S_NAME,APP_S_DESC) values (?,?)";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+app.getName()+","+app.getDescr()+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
				app.getName(),app.getDescr()		
			});
		
		
		logger.info("AppMysql.add() <<<");
	}

	@Override
	public AppParam getParam(long appId, String paramName) {
		// TODO Auto-generated method stub
		logger.info("AppMysql.getParam() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
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

	@Override
	public void putVariable(final long appId, final String name, final String value, final String type) {
		// TODO Auto-generated method stub
		
		logger.info("AppMysql.putVariable() >>>");
		
		String cmd="";
		if (getVariable(appId,name)==null){
			cmd+="insert into BSST_AVA_APP_VAR(ARV_S_VALUE,ARV_C_TYPE,APP_N_APP_ID,ARV_S_NAME) ";
			cmd+=" values (?,?,?,?)";
		}
		else 
			cmd+="update BSST_AVA_APP_VAR set ARV_S_VALUE=?,ARV_C_TYPE=? where APP_N_APP_ID=? and ARV_S_NAME=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+value+","+type+","+appId+","+name+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
				value, type, appId, name		
			});
				
		logger.info("AppMysql.putVariable() <<<");
		
	}

	@Override
	public AppVariable getVariable(long appId, String name) {
		// TODO Auto-generated method stub
		
		logger.info("AppMysql.getVariable() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		String query="select ARV_S_NAME,ARV_S_VALUE,ARV_C_TYPE from BSST_AVA_APP_VAR where APP_N_APP_ID=? and ARV_S_NAME=?";
				
		logger.info(query+"["+appId+","+name+"]");
		
		AppVariable ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { appId,name },new RowMapper<AppVariable>() {
	            @Override
	            public AppVariable mapRow(ResultSet rs, int rowNum) throws SQLException {
	            	AppVariable variable=new AppVariable();
	                
	                variable.setName(rs.getString("ARV_S_NAME"));
	                variable.setValue(rs.getString("ARV_S_VALUE"));
	                variable.setType(rs.getString("ARV_C_TYPE"));
	                
	                return variable;
	            }
	        });
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("AppMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		if (ret!=null){
			ret.setAppId(appId);
		}
	
		logger.info("AppMysql.getVariable() <<<");
		
		return ret;
	}

	@Override
	public void update(long appId, App app) {
		// TODO Auto-generated method stub
		
		logger.info("AppMysql.update() >>>");
		String cmd="update BSST_APP_APP set APP_S_NAME=?,APP_S_DESC=? where APP_N_APP_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+app.getName()+","+app.getDescr()+","+app.getId()+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
				app.getName(),app.getDescr(),app.getId()		
			});
		
		logger.info("AppMysql.update() <<<");
		
	}

	@Override
	public App get(long appId) {
		// TODO Auto-generated method stub
		return get(appId,null);
	}

	private App get(long appId,String name){

		logger.info("AppMysql.get() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		String query="select APP_N_APP_ID,APP_S_NAME,APP_S_DESC from BSST_APP_APP where "+(appId!=0?"APP_N_APP_ID=?":"APP_S_NAME=?");
				
		logger.info(query+"["+appId+","+name+"]");
		
		App ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { appId!=0?appId:name },new RowMapper<App>() {
	            @Override
	            public App mapRow(ResultSet rs, int rowNum) throws SQLException {
	            	App app=new App();
	                
	            	app.setId(rs.getLong("APP_N_APP_ID"));
	                app.setName(rs.getString("APP_S_NAME"));
	                app.setDescr(rs.getString("APP_S_DESC"));
	                
	                return app;
	            }
	        });
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("AppMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
	
		logger.info("AppMysql.get() <<<");
		
		return ret;
		
	}
	
	
	
	@Override
	public App getByName(String name) {
		// TODO Auto-generated method stub
		return get(0,name);
	}

	@Override
	public void remove(long appId) {
		// TODO Auto-generated method stub
		
		logger.info("AppMysql.remove() >>>");
		String cmd="delete from BSST_APP_APP where APP_N_APP_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+appId+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
				appId
			});
		
		logger.info("AppMysql.remove() <<<");
		
	}

}

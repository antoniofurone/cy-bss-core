package org.cysoft.bss.core.dao.mysql;

import org.cysoft.bss.core.dao.CyBssAuthDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

public class CyBssAuthMysql extends CyBssMysqlDao 
	implements CyBssAuthDao{

	private static final Logger logger = LoggerFactory.getLogger(CyBssAuthMysql.class);
	
	
	@Override
	public boolean logOn(String userId, String pwd) {
		// TODO Auto-generated method stub
		boolean ret=false;
		
		logger.info("BssAuthMysql.logOn() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

		String query="select count(0) ";
		query+="from BSST_USR_USER where USR_S_USER_ID=? and USR_S_PSW=?";
		logger.info("query="+query+"["+userId+",****]");
		
		Long count=jdbcTemplate.queryForObject(query,Long.class,new Object[] { userId,pwd });
		if (count>0)
			ret=true;
		
		logger.info("BssAuthMysql.logOn() <<<");
		return ret;

	}


	@Override
	@Transactional
	public void createSession(long userId, String securityToken) {
		// TODO Auto-generated method stub
		logger.info("BssAuthMysql.createSession() >>>");
	
		String cmd = "DELETE FROM BSST_USE_USER_SESSION WHERE USR_N_USER_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info("cmd="+cmd+"["+userId+"]");
		
		
		jdbcTemplate.update(cmd, new Object[]{
				userId		
			});
		
		cmd = "insert into BSST_USE_USER_SESSION (USR_N_USER_ID,USE_S_TOKEN,USE_T_CREATE_TIME,USE_T_UPDATE_TIME)";
		cmd+=" values";
		cmd+=" (?,?,now(),now())";
		logger.info("cmd="+cmd+"["+userId+","+securityToken+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
				userId,securityToken		
			});
			
		
		logger.info("BssAuthMysql.createSession() <<<");
		
	}


	@Override
	public void logOff(String securityToken) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void discardSessions() {
		// TODO Auto-generated method stub
		logger.info("BssAuthMysql.discardSessions() >>>");
		
		int sessionTimeOut=Integer.parseInt(env.getProperty("user.session.timeOut"));
		
		String cmd = "DELETE FROM BSST_USE_USER_SESSION WHERE (now()-USE_T_UPDATE_TIME)>?";
		logger.info("cmd="+cmd+"["+sessionTimeOut+"]");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

		jdbcTemplate.update(cmd, new Object[]{
				sessionTimeOut		
			});
		 
		
		logger.info("BssAuthMysql.discardSessions() <<<");
	}
	

}

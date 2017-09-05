package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssPwdEncryption;
import org.cysoft.bss.core.dao.CyBssAuthDao;
import org.cysoft.bss.core.model.PwdEncrypted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class CyBssAuthMysql extends CyBssMysqlDao 
	implements CyBssAuthDao{

	private static final Logger logger = LoggerFactory.getLogger(CyBssAuthMysql.class);
	
	
	@Override
	public boolean logOn(String userId, String pwd) throws CyBssException {
		// TODO Auto-generated method stub
		boolean ret=false;
		
		logger.info("BssAuthMysql.logOn() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());

		String query="select USR_B_PSW,USR_B_SALT ";
		query+="from BSST_USR_USER where USR_S_USER_ID=? AND USR_C_ACTIVE='Y'";
		logger.info(query+"["+userId+"]");
		
		PwdEncrypted pwdEncr=null;
		try {
			pwdEncr=jdbcTemplate.queryForObject(query, new Object[] { userId },new RowMapper<PwdEncrypted>() {
	            @Override
	            public PwdEncrypted mapRow(ResultSet rs, int rowNum) throws SQLException {
	            	PwdEncrypted pwdEncr=new PwdEncrypted(); 
	            	pwdEncr.setPwd(rs.getBytes("USR_B_PSW"));
	            	pwdEncr.setSalt(rs.getBytes("USR_B_SALT"));
	            	return pwdEncr;
	            }
	        });
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("IncorrectResultSizeDataAccessException:"+e.getMessage());
			return ret;
		}
		
		ret=CyBssPwdEncryption.authenticate(pwd, pwdEncr.getPwd(), pwdEncr.getSalt());
		
		logger.info("BssAuthMysql.logOn() <<<");
		return ret;

	}

	
	@Override
	public void createSession(final long userId, final String securityToken) {
		// TODO Auto-generated method stub
		logger.info("BssAuthMysql.createSession() >>>");
	
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		txTemplate.execute(new TransactionCallbackWithoutResult(){

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus txStatus) {
				// TODO Auto-generated method stub
				
				String cmd = "DELETE FROM BSST_USE_USER_SESSION WHERE USR_N_USER_ID=?";
				JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
				logger.info(cmd+"["+userId+"]");
				
				
				jdbcTemplate.update(cmd, new Object[]{
						userId		
					});
				
				cmd = "insert into BSST_USE_USER_SESSION (USR_N_USER_ID,USE_S_TOKEN,USE_T_CREATE_TIME,USE_T_UPDATE_TIME)";
				cmd+=" values";
				cmd+=" (?,?,now(),now())";
				logger.info(cmd+"["+userId+","+securityToken+"]");
				
				jdbcTemplate.update(cmd, new Object[]{
						userId,securityToken		
					});
			}
		
		});
		
		logger.info("BssAuthMysql.createSession() <<<");
		
	}


	@Override
	public void discardSessions() {
		// TODO Auto-generated method stub
		logger.info("BssAuthMysql.discardSessions() >>>");
		
		int sessionTimeOut=Integer.parseInt(env.getProperty("user.session.timeOut"));
		
		String cmd = "DELETE FROM BSST_USE_USER_SESSION WHERE TIME_TO_SEC(TIMEDIFF(now(),USE_T_UPDATE_TIME))>?";
		logger.info(cmd+"["+sessionTimeOut+"]");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());

		jdbcTemplate.update(cmd, new Object[]{
				sessionTimeOut		
			});
		 
		
		logger.info("BssAuthMysql.discardSessions() <<<");
	}



	@Override
	public long getUserIdByToken(String securityToken) {
		// TODO Auto-generated method stub
		long ret=0;
		
		logger.info("BssAuthMysql.getUserIdByToken() >>>");
		
		String query="select USR_N_USER_ID";
		query+=" from BSST_USE_USER_SESSION where USE_S_TOKEN=?";
		
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"["+securityToken+"]");
		
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { securityToken },new RowMapper<Long>() {
	            @Override
	            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
	            	Long userId=rs.getLong("USR_N_USER_ID");
	                return userId;
	            }
	        });
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("IncorrectResultSizeDataAccessException:"+e.getMessage());
			
		}
		
		logger.info("BssAuthMysql.getUserIdByToken() <<<");
		return ret;
	}


	@Override
	public void refreshSession(String securityToken) {
		// TODO Auto-generated method stub
		logger.info("BssAuthMysql.refreshSession() >>>");
		
		String cmd = "UPDATE BSST_USE_USER_SESSION SET USE_T_UPDATE_TIME=now() WHERE USE_S_TOKEN=?";
		logger.info(cmd+"["+securityToken+"]");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());

		jdbcTemplate.update(cmd, new Object[]{
				securityToken		
			});
		 
		logger.info("BssAuthMysql.refreshSession() <<<");
	}


	@Override
	public boolean checkGrant(long roleId, String serviceName,
			String operationName) {
		// TODO Auto-generated method stub
		logger.info("BssAuthMysql.checkGrant() >>>");
		boolean ret=false;
		
		String query="select count(0)";
		query+=" from BSST_OPE_OPERATION_GRANT a ";
		query+=" join BSST_BSO_SERVICE_OPERATION b on b.BSO_N_OPERATION_ID=a.BSO_N_OPERATION_ID";
		query+=" join BSST_BSV_SERVICE c on c.BSV_N_SERVICE_ID=b.BSV_N_SERVICE_ID";
		query+=" where a.URO_N_ROLE_ID=? and a.OPE_C_ACTIVE='Y' and b.BSO_S_NAME=? and c.BSV_S_SERVICE_NAME=?";		
		
		logger.info(query+"["+roleId+","+serviceName+","+operationName+"]");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		Long count=jdbcTemplate.queryForObject(query,Long.class,new Object[] { roleId,operationName,serviceName });
		if (count>0)
			ret=true;
		
		logger.info("BssAuthMysql.checkGrant() <<<");
		return ret;
	}


	@Override
	public void discardSession(String securityToken) {
		// TODO Auto-generated method stub
		logger.info("BssAuthMysql.discardSession() >>>");
		
		
		String cmd = "DELETE FROM BSST_USE_USER_SESSION WHERE USE_S_TOKEN=?";
		logger.info(cmd+"["+securityToken+"]");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());

		jdbcTemplate.update(cmd, new Object[]{
				securityToken		
			});
		
		logger.info("BssAuthMysql.discardSession() <<<");
	}
	

}

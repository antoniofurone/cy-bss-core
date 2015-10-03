package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.cysoft.bss.core.dao.UserDao;
import org.cysoft.bss.core.model.CyBssService;
import org.cysoft.bss.core.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserMysql extends CyBssMysqlDao
	implements UserDao{

	private static final Logger logger = LoggerFactory.getLogger(UserMysql.class);
	
	
	@Override
	public User getByUserId(String userId) {
		// TODO Auto-generated method stub
		
		logger.info("UserMysql.getByUserId() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

		String query="select  a.USR_N_USER_ID, a.USR_S_USER_ID, a.USR_S_NAME, a.URO_N_ROLE_ID, ";
		query+="b.URO_S_NAME, a.LAN_N_LANG_ID, c.LAN_S_CODE, a.USR_C_ACTIVE ";
		query+="from BSST_USR_USER a";
		query+=" join BSST_URO_ROLE b on b.URO_N_ROLE_ID=a.URO_N_ROLE_ID";
		query+=" join BSST_LAN_LANGUAGE c on c.LAN_N_LANG_ID=a.LAN_N_LANG_ID";
		query+=" where USR_S_USER_ID=?";
		
		logger.info("query="+query+"["+userId+",****]");
		
		User ret=jdbcTemplate.queryForObject(query, new Object[] { userId },new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            	User user=new User();
                
                user.setId(rs.getLong("USR_N_USER_ID"));
                user.setUserId(rs.getString("USR_S_USER_ID"));
                user.setPwd("***");
                user.setName(rs.getString("USR_S_NAME"));
                user.setRoleId(rs.getLong("URO_N_ROLE_ID"));
                user.setRole(rs.getString("URO_S_NAME"));
                user.setLanguageId(rs.getLong("LAN_N_LANG_ID"));
                user.setLanguageCode(rs.getString("LAN_S_CODE"));
                user.setFlgActive(rs.getString("USR_C_ACTIVE"));
                
                return user;
            }
        });
		
		logger.info("UserMysql.getByUserId() <<<");
		return ret;
	}

}

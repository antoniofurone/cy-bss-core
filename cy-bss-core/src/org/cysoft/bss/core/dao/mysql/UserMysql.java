package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.cysoft.bss.core.dao.UserDao;
import org.cysoft.bss.core.model.User;
import org.cysoft.bss.core.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserMysql extends CyBssMysqlDao
	implements UserDao{

	private static final Logger logger = LoggerFactory.getLogger(UserMysql.class);
	
	
	private User getBy(String id,boolean bUserId) {
		// TODO Auto-generated method stub
		
		logger.info("UserMysql.getBy() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

		String query="select  a.USR_N_USER_ID, a.USR_S_USER_ID, a.USR_S_NAME, a.URO_N_ROLE_ID, ";
		query+="b.URO_S_NAME, a.LAN_N_LANG_ID, c.LAN_S_CODE, a.USR_C_ACTIVE ";
		query+="from BSST_USR_USER a";
		query+=" join BSST_URO_ROLE b on b.URO_N_ROLE_ID=a.URO_N_ROLE_ID";
		query+=" join BSST_LAN_LANGUAGE c on c.LAN_N_LANG_ID=a.LAN_N_LANG_ID";
		if (bUserId)
			query+=" where USR_S_USER_ID=?";
		else
			query+=" where USR_N_USER_ID=?";
		
		
		logger.info("query="+query+"["+id+",****]");
		
		User ret=jdbcTemplate.queryForObject(query, new Object[] { bUserId?id:Long.parseLong(id) },new RowMapper<User>() {
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
		
		logger.info("UserMysql.getBy() <<<");
		return ret;
	}


	@Override
	public User get(long id) {
		// TODO Auto-generated method stub
		logger.info("UserMysql.get() >>>");
		return getBy((new Long(id)).toString(),false);
	}
	
	@Override
	public User getByUserId(String userId) {
		// TODO Auto-generated method stub
		logger.info("UserMysql.getByUserId() >>>");
		return getBy(userId,true);
	}


	@Override
	public List<UserRole> getRoleAll() {
		// TODO Auto-generated method stub
		logger.info("UserMysql.getRoleAll() >>>");
		
		String query="select URO_N_ROLE_ID, URO_S_NAME, URO_S_DESCRIPTION, URO_N_PARENT_ROLE_ID";
		query+=" from BSST_URO_ROLE";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info("query="+query);
		
		List<UserRole> ret = jdbcTemplate.query(
                query, 
                new RowMapper<UserRole>() {
                    @Override
                    public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
                    	UserRole role=new UserRole();
                        
                        role.setId(rs.getLong("URO_N_ROLE_ID"));
                        role.setName(rs.getString("URO_S_NAME"));
                        role.setDescription(rs.getString("URO_S_DESCRIPTION"));
                        role.setParentId(rs.getLong("URO_N_PARENT_ROLE_ID"));
                        
                        return role;
		            }
                });
		
		
        
		logger.info("UserMysql.getRoleAll() <<<");
		
		return ret;
	}	
}

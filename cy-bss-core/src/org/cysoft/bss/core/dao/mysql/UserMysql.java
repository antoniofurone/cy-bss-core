package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssPwdEncryption;
import org.cysoft.bss.core.dao.UserDao;
import org.cysoft.bss.core.model.PwdEncrypted;
import org.cysoft.bss.core.model.User;
import org.cysoft.bss.core.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserMysql extends CyBssMysqlDao
	implements UserDao{

	private static final Logger logger = LoggerFactory.getLogger(UserMysql.class);
	
	
	private User getBy(String id,boolean bUserId) {
		// TODO Auto-generated method stub
		
		logger.info("UserMysql.getBy() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());

		String query="select  a.USR_N_USER_ID, a.USR_S_USER_ID, a.USR_S_NAME, a.URO_N_ROLE_ID, ";
		query+="b.URO_S_NAME, a.LAN_N_LANG_ID, c.LAN_S_CODE, a.USR_C_ACTIVE,a.PER_N_PERSON_ID,PER_S_FIRST_NAME, PER_S_SECOND_NAME ";
		query+="from BSST_USR_USER a";
		query+=" join BSST_URO_ROLE b on b.URO_N_ROLE_ID=a.URO_N_ROLE_ID";
		query+=" join BSST_LAN_LANGUAGE c on c.LAN_N_LANG_ID=a.LAN_N_LANG_ID";
		query+=" left join BSST_PER_PERSON d on d.PER_N_PERSON_ID=a.PER_N_PERSON_ID";
		if (bUserId)
			query+=" where USR_S_USER_ID=?";
		else
			query+=" where USR_N_USER_ID=?";
		
		
		logger.info(query+"["+id+",****]");
		User ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { bUserId?id:Long.parseLong(id) },new RowMapper<User>() {
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
	                user.setPersonId(rs.getLong("PER_N_PERSON_ID"));
	                user.setPersonFirstName(rs.getString("PER_S_FIRST_NAME"));
	                user.setPersonSecondName(rs.getString("PER_S_SECOND_NAME"));
	                
	                return user;
	            }
	        });
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("UserMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		
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
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query);
		
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


	@Override
	public void add(User user) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("UserMysql.add() >>>");
		
		String cmd="insert into BSST_USR_USER(USR_S_USER_ID,USR_B_PSW,USR_B_SALT,USR_S_NAME,URO_N_ROLE_ID,LAN_N_LANG_ID) ";
		cmd+=" values (?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+user+"]");
		
		byte[] salt=CyBssPwdEncryption.generateSalt();
		byte[] encPwd=CyBssPwdEncryption.encryptPwd(user.getPwd(), salt);
		
		jdbcTemplate.update(cmd, new Object[]{
				user.getUserId(), encPwd, salt, user.getName(), user.getRoleId(),user.getLanguageId()		
			});
		
		logger.info("UserMysql.add() <<<");
	}


	@Override
	public List<User> find(String name) {
		// TODO Auto-generated method stub
		logger.info("UserMysql.find() >>> name="+name);
		
		String query="select a.USR_N_USER_ID, a.USR_S_USER_ID, a.USR_S_NAME, a.USR_C_ACTIVE, a.LAN_N_LANG_ID, c.LAN_S_CODE, a.URO_N_ROLE_ID, b.URO_S_NAME, a.PER_N_PERSON_ID,PER_S_FIRST_NAME, PER_S_SECOND_NAME";
		query+=" from BSST_USR_USER a";
		query+=" join BSST_URO_ROLE b on b.URO_N_ROLE_ID=a.URO_N_ROLE_ID";
		query+=" join BSST_LAN_LANGUAGE c on c.LAN_N_LANG_ID=a.LAN_N_LANG_ID";
		query+=" left join BSST_PER_PERSON d on d.PER_N_PERSON_ID=a.PER_N_PERSON_ID";
		if (!name.equals(""))
			if (!name.contains("%"))
				query+=" where a.USR_S_NAME=?";
			else
				query+=" where a.USR_S_NAME like ?";
		query+=" order by a.USR_N_USER_ID";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"[name="+name+"]");
		
		List<User> ret=null;
		
		RowMapper<User> rowMapper=new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            	User user=new User();
                
            	user.setId(rs.getLong("USR_N_USER_ID"));
            	user.setUserId(rs.getString("USR_S_USER_ID"));
            	user.setName(rs.getString("USR_S_NAME"));
            	user.setFlgActive(rs.getString("USR_C_ACTIVE"));
            	user.setLanguageId(rs.getLong("LAN_N_LANG_ID"));
                user.setLanguageCode(rs.getString("LAN_S_CODE"));
            	user.setRoleId(rs.getLong("URO_N_ROLE_ID"));
            	user.setRole(rs.getString("URO_S_NAME"));
            	user.setPersonId(rs.getLong("PER_N_PERSON_ID"));
                user.setPersonFirstName(rs.getString("PER_S_FIRST_NAME"));
                user.setPersonSecondName(rs.getString("PER_S_SECOND_NAME"));
                
                return user;
            }
		};
		
		if (!name.equals(""))
			ret=jdbcTemplate.query(query, new Object[]{name},rowMapper);
		else
			ret=jdbcTemplate.query(query, rowMapper);
	   		
		logger.info("UserMysql.find() <<<");
		return ret;
	}


	@Override
	public void update(long id, User user) {
		// TODO Auto-generated method stub
		
		logger.info("UserMysql.update() >>>");
		
		String cmd="update BSST_USR_USER set USR_S_USER_ID=?,USR_S_NAME=?,URO_N_ROLE_ID=?,LAN_N_LANG_ID=? ";
		cmd+=" where USR_N_USER_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+","+user+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
				user.getUserId(),  user.getName(), user.getRoleId(),user.getLanguageId(),id		
			});
		
		logger.info("UserMysql.update() <<<");
		
	}


	@Override
	public void enableDisable(long id,boolean enable) {
		// TODO Auto-generated method stub
		logger.info("UserMysql.enableDisable() >>>");
		
		String cmd="update BSST_USR_USER set USR_C_ACTIVE=? ";
		cmd+=" where USR_N_USER_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
				enable?"Y":"N",id		
			});
		
		logger.info("UserMysql.enableDisable() <<<");
		
	}


	@Override
	public void remove(long id) {
		// TODO Auto-generated method stub
		logger.info("UserMysql.remove() >>>");
		
		String cmd="delete from BSST_USR_USER where USR_N_USER_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
				id		
			});
		
		logger.info("UserMysql.remove() <<<");
		
	}


	@Override
	public void changPwd(long id, String pwd) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("UserMysql.changPwd() >>>");
		byte[] salt=CyBssPwdEncryption.generateSalt();
		byte[] encPwd=CyBssPwdEncryption.encryptPwd(pwd, salt);
		
		String cmd="update BSST_USR_USER set USR_B_PSW=?,USR_B_SALT=? where USR_N_USER_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"[***,"+id+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
				encPwd,salt,id		
			});
		
		logger.info("UserMysql.changPwd() <<<");
		
	}


	@Override
	public PwdEncrypted getPwd(long id) {
		// TODO Auto-generated method stub
		PwdEncrypted ret=null;
		logger.info("UserMysql.getPwd() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());

		String query="select USR_B_PSW,USR_B_SALT from BSST_USR_USER";
		query+=" where USR_N_USER_ID=?";
				
		logger.info(query+"["+id+"]");
		
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },
			new RowMapper<PwdEncrypted>() {
			            @Override
			            public PwdEncrypted mapRow(ResultSet rs, int rowNum) throws SQLException {
			            	PwdEncrypted ret=new PwdEncrypted();
			            	ret.setPwd(rs.getBytes("USR_B_PSW"));
			            	ret.setSalt(rs.getBytes("USR_B_SALT"));
			            	return ret;
			            }
			        });
			}
		catch(IncorrectResultSizeDataAccessException e){
				logger.info("UserMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
			}
		
		logger.info("UserMysql.getPwd() <<<");
		return ret;
	}


	@Override
	public void updatePeson(long id,long personId) {
		// TODO Auto-generated method stub
		
		String cmd="update BSST_USR_USER set PER_N_PERSON_ID=? where USR_N_USER_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+","+personId+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
				(personId==0)?null:personId,
				id		
			});
		
		logger.info("UserMysql.removePerson() <<<");

	}	
}

package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.ContactDao;
import org.cysoft.bss.core.model.ContactType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ContactMysql extends CyBssMysqlDao
implements ContactDao{
	
	private static final Logger logger = LoggerFactory.getLogger(ContactMysql.class);
	
	@Override
	public List<ContactType> getContactTypeAll() {
		// TODO Auto-generated method stub
		
		String query="select  CTY_N_TYPE_ID, CTY_S_NAME, CTY_S_DESC, CTY_S_TYPE  from BSST_CTY_CONTACT_TYPE";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(query);
		
		List<ContactType> ret=jdbcTemplate.query(query,new RowMapperContactType());
		
		return ret;
	}

	
	private class RowMapperContactType implements RowMapper<ContactType>{

		@Override
		public ContactType mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			ContactType type=new ContactType();
			type.setId(rs.getLong("CTY_N_TYPE_ID"));
			type.setName(rs.getString("CTY_S_NAME"));
			type.setDescription(rs.getString("CTY_S_DESC"));
	       	type.setType(rs.getString("CTY_S_TYPE"));
            return type;
		}
		
	}


	@Override
	public long add(ContactType contactType) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("ContactMysql.add() >>>");
		
		String cmd="insert into BSST_CTY_CONTACT_TYPE(CTY_S_NAME,CTY_S_DESC,CTY_S_TYPE) ";
		cmd+=" values (?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(cmd+"["+contactType+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					contactType.getName(),  
					(contactType.getDescription()==null || contactType.getDescription().equals(""))?null:contactType.getDescription(),
					(contactType.getType()==null || contactType.getType().equals(""))?null:contactType.getType()		
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("ContactMysql.add() <<<");
		return getLastInsertId(jdbcTemplate);
	}


	@Override
	public void update(long id, ContactType contactType) throws CyBssException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void remove(long id) throws CyBssException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ContactType get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}

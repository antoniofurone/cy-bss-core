package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.ContactDao;
import org.cysoft.bss.core.model.Contact;
import org.cysoft.bss.core.model.ContactType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ContactMysql extends CyBssMysqlDao
implements ContactDao{
	
	private static final Logger logger = LoggerFactory.getLogger(ContactMysql.class);
	
	@Override
	public List<ContactType> getContactTypeAll() {
		// TODO Auto-generated method stub
		
		String query="select  CTY_N_TYPE_ID, CTY_S_NAME, CTY_S_DESC, CTY_S_TYPE  from BSST_CTY_CONTACT_TYPE";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
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

	private class RowMapperContact implements RowMapper<Contact>{

		@Override
		public Contact mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			Contact contact=new Contact();
			
			contact.setId(rs.getLong("CON_N_CONTACT_ID"));
			contact.setContact(rs.getString("CON_S_CONTACT"));
			contact.setEntityName(rs.getString("CON_S_ENTITY_NAME"));
			contact.setEntityId(rs.getLong("CON_N_ENTITY_ID"));
			contact.setContactTypeId(rs.getLong("CTY_N_TYPE_ID"));
			contact.setContactTypeName(rs.getString("CTY_S_NAME"));
			
			
	        return contact;
		}
		
	}

	
	
	@Override
	public long addType(ContactType contactType) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("ContactMysql.addType() >>>");
		
		String cmd="insert into BSST_CTY_CONTACT_TYPE(CTY_S_NAME,CTY_S_DESC,CTY_S_TYPE) ";
		cmd+=" values (?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
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
		
		logger.info("ContactMysql.addType() <<<");
		return getLastInsertId(jdbcTemplate);
	}


	@Override
	public void updateType(long id, ContactType contactType) throws CyBssException {
		// TODO Auto-generated method stub

		logger.info("ContactMysql.updateType() >>>");
		
		String cmd="update BSST_CTY_CONTACT_TYPE set CTY_S_NAME=?,CTY_S_DESC=?,CTY_S_TYPE=?";
		cmd+=" where CTY_N_TYPE_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+","+contactType+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					contactType.getName(),  
					(contactType.getDescription()==null || contactType.getDescription().equals(""))?null:contactType.getDescription(),
					(contactType.getType()==null || contactType.getType().equals(""))?null:contactType.getType(),		
					id		
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		}
		
		logger.info("ContactMysql.updateType() <<<");
	}


	@Override
	public void removeType(long id) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("ContactMysql.removeType() >>>");
		
		String cmd="delete from BSST_CTY_CONTACT_TYPE where CTY_N_TYPE_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					id
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("ContactMysql.removeType() <<<");
	}


	@Override
	public ContactType getType(long id) {
		// TODO Auto-generated method stub
		logger.info("ContactMysql.getType() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String query="select CTY_N_TYPE_ID,CTY_S_NAME,CTY_S_DESC,CTY_S_TYPE";
		query+=" from BSST_CTY_CONTACT_TYPE";
		query+=" where CTY_N_TYPE_ID=?";
		
		logger.info(query+"["+id+",****]");
		ContactType ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },
					new RowMapperContactType());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("ContactMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		logger.info("ContactMysql.getType() <<<");
		return ret;
	}


	@Override
	public long add(Contact contact) throws CyBssException {
		// TODO Auto-generated method stub
		
		logger.info("ContactMysql.add() >>>");
		
		String cmd="insert into BSST_CON_CONTACT(CON_S_CONTACT,CON_S_ENTITY_NAME,CON_N_ENTITY_ID,CTY_N_TYPE_ID) ";
		cmd+=" values (?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+contact+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					contact.getContact(), contact.getEntityName(),contact.getEntityId(),contact.getContactTypeId() 
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("ContactMysql.addType() <<<");
		return getLastInsertId(jdbcTemplate);
		
	}


	@Override
	public void update(long id, Contact contact) throws CyBssException {
		// TODO Auto-generated method stub

		logger.info("ContactMysql.update() >>>");
		
		String cmd="update BSST_CON_CONTACT set CON_S_CONTACT=?,CON_S_ENTITY_NAME=?,CON_N_ENTITY_ID=?,CTY_N_TYPE_ID=?";
		cmd+=" where CON_N_CONTACT_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+","+contact+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					contact.getContact(),contact.getEntityName(),contact.getEntityId(),contact.getContactTypeId(),
					id		
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		}
		
		logger.info("ContactMysql.update() <<<");
		
	}


	@Override
	public void remove(long id) throws CyBssException {
		// TODO Auto-generated method stub
		
		logger.info("ContactMysql.remove() >>>");
		
		String cmd="delete from BSST_CON_CONTACT where CON_N_CONTACT_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					id
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("ContactMysql.remove() <<<");
		
	}
	
	@Override
	public void removeByEntityId(long entityId,String entityName) throws CyBssException{
		logger.info("ContactMysql.removeByEntityId() >>>");
		
		String cmd="delete from BSST_CON_CONTACT where CON_N_ENTITY_ID=? and CON_S_ENTITY_NAME=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		logger.info(cmd+"["+entityId+","+entityName+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					entityId,entityName
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("ContactMysql.removeByEntityId() <<<");
		
	}


	@Override
	public List<Contact> getByEntity(long entityId,String entityName) {
		// TODO Auto-generated method stub
		logger.info("ContactMysql.getByEntity() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
				
		String query="select a.CON_N_CONTACT_ID,a.CON_S_CONTACT,a.CON_S_ENTITY_NAME,a.CON_N_ENTITY_ID,a.CTY_N_TYPE_ID,b.CTY_S_NAME";
		query+=" from BSST_CON_CONTACT a";
		query+=" join BSST_CTY_CONTACT_TYPE b on a.CTY_N_TYPE_ID=b.CTY_N_TYPE_ID";
		query+=" where a.CON_N_ENTITY_ID=? and a.CON_S_ENTITY_NAME=?";
		
		List<Contact> ret=jdbcTemplate.query(query,new Object[] { entityId,entityName },new RowMapperContact());
		
		logger.info("ContactMysql.getByEntity() <<<");
		return ret;
	}


	@Override
	public Contact get(long id) {
		// TODO Auto-generated method stub
		logger.info("ContactMysql.get() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String query="select a.CON_N_CONTACT_ID,a.CON_S_CONTACT,a.CON_S_ENTITY_NAME,a.CON_N_ENTITY_ID,a.CTY_N_TYPE_ID,b.CTY_S_NAME";
		query+=" from BSST_CON_CONTACT a";
		query+=" join BSST_CTY_CONTACT_TYPE b on a.CTY_N_TYPE_ID=b.CTY_N_TYPE_ID";
		query+=" where a.CON_N_CONTACT_ID=?";
		
		logger.info(query+"["+id+"]");
		Contact ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },
					new RowMapperContact());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("ContactMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		logger.info("ContactMysql.get() <<<");
		return ret;
	}
}

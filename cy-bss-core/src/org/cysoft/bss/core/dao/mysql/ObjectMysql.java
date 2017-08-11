package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.cysoft.bss.core.dao.ObjectDao;
import org.cysoft.bss.core.model.Attribute;
import org.cysoft.bss.core.model.AttributeType;
import org.cysoft.bss.core.model.CyBssObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ObjectMysql extends CyBssMysqlDao
implements ObjectDao{

	private static final Logger logger = LoggerFactory.getLogger(ObjectMysql.class);
	
	@Override
	public List<CyBssObject> getObjectAll() {
		// TODO Auto-generated method stub
		
		String query="select OBJ_N_OBJECT_ID,OBJ_S_NAME,OBJ_S_ENTITY_NAME from BSST_OBJ_OBJECT";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query);
		
		List<CyBssObject> ret = jdbcTemplate.query(
                query, 
                new RowMapperObject());
		
		return ret;
	}

	private class RowMapperObject implements RowMapper<CyBssObject>{
		
		@Override
		public CyBssObject mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			CyBssObject obj=new CyBssObject();
            
            obj.setId(rs.getLong("OBJ_N_OBJECT_ID"));
            obj.setName(rs.getString("OBJ_S_NAME"));
            obj.setEntityName(rs.getString("OBJ_S_ENTITY_NAME"));
			
            return obj;
		}
		
	}
	
	@Override
	public CyBssObject getByEntity(String entityName) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());

		String query="select OBJ_N_OBJECT_ID,OBJ_S_NAME,OBJ_S_ENTITY_NAME from BSST_OBJ_OBJECT where OBJ_S_ENTITY_NAME=?";
		
		logger.info(query+"["+entityName+"]");
		CyBssObject ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { entityName},new RowMapperObject());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("ObjectMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		}
		return ret;
	}

	@Override
	public CyBssObject getByName(String objectName) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());

		String query="select OBJ_N_OBJECT_ID,OBJ_S_NAME,OBJ_S_ENTITY_NAME from BSST_OBJ_OBJECT where OBJ_S_NAME=?";
		
		logger.info(query+"["+objectName+"]");
		CyBssObject ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] {objectName},new RowMapperObject());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("ObjectMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		}
		return ret;
	}

	@Override
	public List<AttributeType> getAttributeTypeAll() {
		// TODO Auto-generated method stub
		String query="select ATY_N_ATTR_TYPE_ID,ATY_S_NAME,ATY_S_DESCRIPTION from BSST_ATY_ATTR_TYPE";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query);
		
		List<AttributeType> ret = jdbcTemplate.query(
                query, 
                new RowMapperAttributeType());
		return ret;
	}

	private class RowMapperAttributeType implements RowMapper<AttributeType>{
		
		@Override
		public AttributeType mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			AttributeType attrType=new AttributeType();
           
			attrType.setId(rs.getLong("ATY_N_ATTR_TYPE_ID"));
	        attrType.setName(rs.getString("ATY_S_NAME"));
	        attrType.setDescription(rs.getString("ATY_S_DESCRIPTION"));
			
            return attrType;
		}
		
	}
	
	@Override
	public long addAttribute(Attribute attr) {
		// TODO Auto-generated method stub
		
		String cmd="insert into BSST_ATT_ATTRIBUTE(ATT_S_NAME,ATY_N_ATTR_TYPE_ID,OBJ_N_OBJECT_ID) ";
		cmd+=" values (?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+attr+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
				attr.getName(),attr.getTypeId(),attr.getObjectId()		
			});
		
		return getLastInsertId(jdbcTemplate);
	}

	@Override
	public List<Attribute> getAttributes(long objectId) {
		// TODO Auto-generated method stub
		String query="select ID,NAME,TYPE_ID,TYPE,OBJ_ID,OBJECT,ENTITY from BSSV_ATTRIBUTE where OBJ_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query);
		
		List<Attribute> ret = jdbcTemplate.query(
                query,new Object[]{objectId}, 
                new RowMapperAttribute());
		return ret;

	}

	private class RowMapperAttribute implements RowMapper<Attribute>{
		
		@Override
		public Attribute mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			Attribute attribute=new Attribute();
           
			attribute.setId(rs.getLong("ID"));
	        attribute.setName(rs.getString("NAME"));
	        attribute.setTypeId(rs.getLong("TYPE_ID"));
	        attribute.setTypeName(rs.getString("TYPE"));
	        attribute.setObjectId(rs.getLong("OBJ_ID"));
	        attribute.setObjectName(rs.getString("OBJECT"));
	        attribute.setEntityName(rs.getString("ENTITY"));
		        
	        
            return attribute;
		}
		
	}
	
	@Override
	public Attribute getAttribute(long attrId) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
				
		String query="select ID,NAME,TYPE_ID,TYPE,OBJ_ID,OBJECT,ENTITY from BSSV_ATTRIBUTE where ID=?";
		logger.info(query+"["+attrId+"]");
	
		Attribute ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { attrId },
					new RowMapperAttribute());
		}
			catch(IncorrectResultSizeDataAccessException e){
					logger.info("ObjectMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
				
		}
		return ret;
	}


	@Override
	public Attribute getAttributeByName(long objectId, String attrName) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String query="select ID,NAME,TYPE_ID,TYPE,OBJ_ID,OBJECT,ENTITY from BSSV_ATTRIBUTE where OBJ_ID=? and NAME=?";
		logger.info(query+"["+objectId+","+attrName+"]");
	
		Attribute ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { objectId, attrName },
					new RowMapperAttribute());
		}
			catch(IncorrectResultSizeDataAccessException e){
					logger.info("ObjectMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
				
		}
		return ret;
	}

	@Override
	public void updateAttribute(Attribute attr) {
		// TODO Auto-generated method stub
		String cmd="update BSST_ATT_ATTRIBUTE set ATT_S_NAME=?,ATY_N_ATTR_TYPE_ID=?,OBJ_N_OBJECT_ID=? ";
		cmd+=" where ATT_N_ATTRIBUTE_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+attr+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
				attr.getName(),attr.getTypeId(),attr.getObjectId(),attr.getId()		
			});

	}

	@Override
	public void removeAttribute(long attrId) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		String cmd="delete from BSST_ATT_ATTRIBUTE where ATT_N_ATTRIBUTE_ID=?";
		logger.info(cmd+"["+attrId+"]");
				
		jdbcTemplate.update(cmd, new Object[]{
				attrId		
		});
	}

	@Override
	public void setAttributeValue(long id, long attrId, String value) {
		// TODO Auto-generated method stub
		Attribute attr=this.getAttributeValue(id, attrId);
		String cmd=null;
		if (attr==null){
			cmd="insert into BSST_ATV_ATTR_VALUE(ATV_S_VALUE,ATV_N_OBJ_INST_ID,ATT_N_ATTRIBUTE_ID) ";
			cmd+=" values (?,?,?)";
			}
		else
			{
			cmd="update BSST_ATV_ATTR_VALUE set ATV_S_VALUE=? where ATV_N_OBJ_INST_ID=? and ATT_N_ATTRIBUTE_ID=?";
			}
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+value+","+id+","+attrId+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
				value,id,attrId		
			});
	}

	
	@Override
	public Attribute getAttributeValue(long id, long attrId) {
		// TODO Auto-generated method stub
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String query="select ID,NAME,TYPE_ID,TYPE,OBJ_ID,OBJECT,ENTITY,OBJINST_ID,VALUE from BSSV_ATTRIBUTE_VALUE where ID=? and OBJINST_ID=?";
		logger.info(query+"["+attrId+","+id+"]");
	
		Attribute ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { attrId, id },
					new RowMapperAttributeValue());
		}
			catch(IncorrectResultSizeDataAccessException e){
					logger.info("ObjectMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
				
		}
		return ret;
	}

	
	private class RowMapperAttributeValue implements RowMapper<Attribute>{
		
		@Override
		public Attribute mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			Attribute attribute=new Attribute();
           
			attribute.setId(rs.getLong("ID"));
	        attribute.setName(rs.getString("NAME"));
	        attribute.setTypeId(rs.getLong("TYPE_ID"));
	        attribute.setTypeName(rs.getString("TYPE"));
	        attribute.setObjectId(rs.getLong("OBJ_ID"));
	        attribute.setObjectName(rs.getString("OBJECT"));
	        attribute.setEntityName(rs.getString("ENTITY"));
	        attribute.setObjInstId(rs.getLong("OBJINST_ID"));
	        attribute.setValue(rs.getString("VALUE"));
	        
            return attribute;
		}
		
	}
	
	@Override
	public List<Attribute> getAttributeValues(long id,long objectId) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String query="select ID,NAME,TYPE_ID,TYPE,OBJ_ID,OBJECT,ENTITY,OBJINST_ID,VALUE from BSSV_ATTRIBUTE_VALUE where OBJINST_ID=? and OBJ_ID=?";
		logger.info(query+"["+id+","+objectId+"]");
		
		List<Attribute> ret = jdbcTemplate.query(
                query,new Object[]{id,objectId}, 
                new RowMapperAttributeValue());
		return ret;
	}

	@Override
	public void removeAttributeValue(long id, long attrId) {
		// TODO Auto-generated method stub
		String cmd="delete from BSST_ATV_ATTR_VALUE where ATV_N_OBJ_INST_ID=? and ATT_N_ATTRIBUTE_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+","+attrId+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
				id,attrId		
			});
	}
	
	@Override
	public void removeAttributeValues(long id,String entityName){
		String cmd="delete from BSST_ATV_ATTR_VALUE where ATV_N_OBJ_INST_ID=? and ATT_N_ATTRIBUTE_ID in ";
		cmd+="(select ATT_N_ATTRIBUTE_ID from BSST_ATT_ATTRIBUTE where OBJ_N_OBJECT_ID in (select OBJ_N_OBJECT_ID from BSST_OBJ_OBJECT where OBJ_S_ENTITY_NAME=?))";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		jdbcTemplate.update(cmd, new Object[]{
					id,entityName
				});
	}
	
	@Override
	public void removeAttributeValues(long attrId) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		String cmd="delete from BSST_ATV_ATTR_VALUE where ATT_N_ATTRIBUTE_ID=?";
		logger.info(cmd+"["+attrId+"]");
		jdbcTemplate.update(cmd, new Object[]{
				attrId		
			});
	}
	
	@Override
	public CyBssObject get(long id) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());

		String query="select OBJ_N_OBJECT_ID,OBJ_S_NAME,OBJ_S_ENTITY_NAME from BSST_OBJ_OBJECT where OBJ_N_OBJECT_ID=?";
		
		logger.info(query+"["+id+"]");
		CyBssObject ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] {id},new RowMapperObject());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("ObjectMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		}
		return ret;
	}

	

}

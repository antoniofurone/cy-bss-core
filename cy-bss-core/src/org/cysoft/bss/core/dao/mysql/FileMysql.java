package org.cysoft.bss.core.dao.mysql;

import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.FileDao;
import org.cysoft.bss.core.model.CyBssFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.multipart.MultipartFile;

public class FileMysql extends CyBssMysqlDao
	implements FileDao{
	
	private static final Logger logger = LoggerFactory.getLogger(FileMysql.class);
	
	@Override
	public void upload(String name, MultipartFile file, String fileType,
			String entityName, long entityId, 
			String note,String visibility) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("FileMysql.upload() >>>");
		
		String cmd="insert into BSST_FIL_FILE(FILE_S_NAME,FILE_S_TYPE,FILE_N_SIZE,FILE_S_CONTENT_TYPE,FILE_B_CONTENT,FILE_S_ENTITY_NAME,FILE_N_ENTITY_ID,FILE_S_NOTE,FILE_S_VISIBILITY) ";
		cmd+=" values (?,?,?,?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"[name="+name+",fileType="+fileType+",entityName="+entityName+",entityId="+entityId+",note="+note+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					name, fileType, file.getSize(), file.getContentType(),file.getInputStream(),entityName,entityId==0?null:entityId,note,visibility	
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		}
		
		logger.info("FileMysql.upload() <<<");
	}

	@Override
	public CyBssFile download(long fileId) {
		// TODO Auto-generated method stub
		logger.info("FileMysql.download() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());

		String query="select  FILE_S_NAME, FILE_S_CONTENT_TYPE, FILE_S_VISIBILITY, FILE_B_CONTENT from BSST_FIL_FILE where FILE_N_FILE_ID=? ";
		logger.info(query+"["+fileId+"]");
		
		CyBssFile ret=jdbcTemplate.queryForObject(query, new Object[] { fileId },new RowMapper<CyBssFile>() {
            @Override
            public CyBssFile mapRow(ResultSet rs, int rowNum) throws SQLException {
            	CyBssFile file=new CyBssFile();
                
            	file.setName(rs.getString("FILE_S_NAME"));
            	file.setContentType(rs.getString("FILE_S_CONTENT_TYPE"));
            	file.setVisibility(rs.getString("FILE_S_VISIBILITY"));
                Blob blob=rs.getBlob("FILE_B_CONTENT");
                file.setContent(blob.getBinaryStream());
                
                return file;
            }
        });
		
		
		logger.info("FileMysql.download() <<<");
		
		
		return ret;
	}

	@Override
	public void remove(long fileId) {
		// TODO Auto-generated method stub
		logger.info("FileMysql.remove() >>>");
		
		String cmd="delete from BSST_FIL_FILE where FILE_N_FILE_ID=? ";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+fileId+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
				fileId	
			});
		
		logger.info("FileMysql.remove() <<<");
				
	}

	@Override
	public List<CyBssFile> getByEntity(String entityName, long id) {
		// TODO Auto-generated method stub
		logger.info("FileMysql.getByEntity >>>");
		
		String query="select  FILE_N_FILE_ID, FILE_S_NAME, FILE_N_SIZE, FILE_S_CONTENT_TYPE,";
		query+="FILE_S_TYPE, FILE_S_ENTITY_NAME, FILE_N_ENTITY_ID, FILE_S_NOTE,FILE_S_VISIBILITY  ";
		query+="from BSST_FIL_FILE ";
		query+="where FILE_S_ENTITY_NAME=? and FILE_N_ENTITY_ID=?";
		
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"[entityName="+entityName+";entityId="+id+"]");
		
		List<CyBssFile> ret=jdbcTemplate.query(query, new Object[] { entityName, id },new RowMapperFile());
			
		logger.info("FileMysql.getByEntity <<<");
		return ret;

	}
	
	private class RowMapperFile implements RowMapper<CyBssFile>{

		@Override
		public CyBssFile mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			CyBssFile file=new CyBssFile();
            
			file.setId(rs.getLong("FILE_N_FILE_ID"));
			file.setName(rs.getString("FILE_S_NAME"));
		    file.setLength(rs.getInt("FILE_N_SIZE"));
			file.setContentType(rs.getString("FILE_S_CONTENT_TYPE"));
			file.setFileType(rs.getString("FILE_S_TYPE"));
			file.setEntityName(rs.getString("FILE_S_ENTITY_NAME"));
			file.setEntityId(rs.getLong("FILE_N_ENTITY_ID"));
			file.setNote(rs.getString("FILE_S_NOTE"));
			file.setVisibility(rs.getString("FILE_S_VISIBILITY"));
		    
            return file;
		}
		
	}

	@Override
	public void makePublic(long fileId) {
		// TODO Auto-generated method stub
		String cmd="update BSST_FIL_FILE set FILE_S_VISIBILITY=? where FILE_N_FILE_ID=? ";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+fileId+","+CyBssFile.VISIBILITY_PUBLIC+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
				CyBssFile.VISIBILITY_PUBLIC,fileId	
			});
		
	}

	@Override
	public void makeReserved(long fileId) {
		// TODO Auto-generated method stub
		String cmd="update BSST_FIL_FILE set FILE_S_VISIBILITY=? where FILE_N_FILE_ID=? ";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+fileId+","+CyBssFile.VISIBILITY_RESERVED+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
				CyBssFile.VISIBILITY_RESERVED,fileId	
			});
	}

	@Override
	public CyBssFile get(long fileId) {
		// TODO Auto-generated method stub
		logger.info("FileMysql.get >>>");
		
		String query="select  FILE_N_FILE_ID, FILE_S_NAME, FILE_N_SIZE, FILE_S_CONTENT_TYPE,";
		query+="FILE_S_TYPE, FILE_S_ENTITY_NAME, FILE_N_ENTITY_ID, FILE_S_NOTE,FILE_S_VISIBILITY  ";
		query+="from BSST_FIL_FILE ";
		query+="where FILE_N_FILE_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		logger.info(query+"["+fileId+"]");
		CyBssFile ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { fileId },new RowMapperFile());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("LocationMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		
		logger.info("FileMysql.get <<<");
		return ret;
	}

	@Override
	public List<CyBssFile> find(String name, String type, String entityName, String visibility) {
		// TODO Auto-generated method stub
		logger.info("FileMysql.find >>>");
		
		String query="select  FILE_N_FILE_ID, FILE_S_NAME, FILE_N_SIZE, FILE_S_CONTENT_TYPE,";
		query+="FILE_S_TYPE, FILE_S_ENTITY_NAME, FILE_N_ENTITY_ID, FILE_S_NOTE,FILE_S_VISIBILITY  ";
		query+="from BSST_FIL_FILE";
		if (!name.equals("") || !type.equals("") || !entityName.equals("") || !visibility.equals(""))
			query+=" WHERE ";
		
		boolean insAnd=false;
		List<Object> parms=new ArrayList<Object>();
		if (!name.equals("")){
			if (!name.contains("%"))
				query+=" FILE_S_NAME=?";
			else
				query+=" FILE_S_NAME like ?";
			insAnd=true;
			parms.add(name);
		}
		if (!type.equals("")){
			if (!type.contains("%"))
				query+=(insAnd?" AND":"")+" FILE_S_TYPE=?";
			else
				query+=(insAnd?" AND":"")+" FILE_S_TYPE like ?";
			insAnd=true;
			parms.add(type);
		}
		if (!entityName.equals("")){
			if (!entityName.contains("%"))
				query+=(insAnd?" AND":"")+" FILE_S_ENTITY_NAME=?";
			else
				query+=(insAnd?" AND":"")+" FILE_S_ENTITY_NAME like ?";
			insAnd=true;
			parms.add(entityName);
		}
		if (!visibility.equals("")){
			query+=(insAnd?" AND":"")+" FILE_S_VISIBILITY=?";
			insAnd=true;
			parms.add(visibility);
		}
		query+=" order by FILE_N_FILE_ID";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"[name="+name+";type="+type+";entityName="+entityName+";visibility="+visibility+"]");
		
		List<CyBssFile> ret=jdbcTemplate.query(query, parms.toArray(),new RowMapperFile());
		
		logger.info("FileMysql.find <<<");
		return ret;
	}
	

}

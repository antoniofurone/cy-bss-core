package org.cysoft.bss.core.dao.mysql;

import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.FileDao;
import org.cysoft.bss.core.model.CyBssFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.multipart.MultipartFile;

public class FileMysql extends CyBssMysqlDao
	implements FileDao{
	
	private static final Logger logger = LoggerFactory.getLogger(FileMysql.class);
	
	@Override
	public void upload(String name, MultipartFile file, String fileType,
			String entityName, long entityId, String note) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("FileMysql.upload() >>>");
		
		String cmd="insert into BSST_FIL_FILE(FILE_S_NAME,FILE_S_TYPE,FILE_N_SIZE,FILE_S_CONTENT_TYPE,FILE_B_CONTENT,FILE_S_ENTITY_NAME,FILE_N_ENTITY_ID,FILE_S_NOTE) ";
		cmd+=" values (?,?,?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(cmd+"[name="+name+",fileType="+fileType+",entityName="+entityName+",entityId="+entityId+",note="+note+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					name, fileType, file.getSize(), file.getContentType(),file.getInputStream(),entityName,entityId==0?null:entityId,note	
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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

		String query="select  FILE_S_NAME, FILE_S_CONTENT_TYPE, FILE_B_CONTENT from BSST_FIL_FILE where FILE_N_FILE_ID=? ";
		logger.info(query+"["+fileId+"]");
		
		CyBssFile ret=jdbcTemplate.queryForObject(query, new Object[] { fileId },new RowMapper<CyBssFile>() {
            @Override
            public CyBssFile mapRow(ResultSet rs, int rowNum) throws SQLException {
            	CyBssFile file=new CyBssFile();
                
            	file.setName(rs.getString("FILE_S_NAME"));
            	file.setContentType(rs.getString("FILE_S_CONTENT_TYPE"));
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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(cmd+"["+fileId+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
				fileId	
			});
		
		logger.info("FileMysql.remove() <<<");
				
	}

}

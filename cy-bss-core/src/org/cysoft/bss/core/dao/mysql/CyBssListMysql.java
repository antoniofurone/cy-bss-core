package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.cysoft.bss.core.dao.CyBssListDao;
import org.cysoft.bss.core.model.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class CyBssListMysql extends CyBssMysqlDao
	implements CyBssListDao
{
	
	private static final Logger logger = LoggerFactory.getLogger(CyBssListMysql.class);
	
	@Override
	public List<Language> getLanguageAll() {
		// TODO Auto-generated method stub
		logger.info("CyBssListMysql.getLanguageAll() >>>");
		
		String query="select LAN_N_LANG_ID,LAN_S_CODE,LAN_S_NAME from BSST_LAN_LANGUAGE";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info("query="+query);
		
		List<Language> ret = jdbcTemplate.query(
                query, 
                new RowMapper<Language>() {
                    @Override
                    public Language mapRow(ResultSet rs, int rowNum) throws SQLException {
                    	Language lang=new Language();
                        
                        lang.setId(rs.getInt("LAN_N_LANG_ID"));
                        lang.setCode(rs.getString("LAN_S_CODE"));
                        lang.setName(rs.getString("LAN_S_NAME"));
                        
                        return lang;
		            }
                });
		
		
        
		logger.info("CyBssListMysql.getLanguageAll() <<<");
		
		return ret;

	}

}

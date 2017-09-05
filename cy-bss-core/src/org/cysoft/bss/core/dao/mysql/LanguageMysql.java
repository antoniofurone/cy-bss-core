package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.cysoft.bss.core.dao.LanguageDao;
import org.cysoft.bss.core.model.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class LanguageMysql extends CyBssMysqlDao
	implements LanguageDao
{
	
	private static final Logger logger = LoggerFactory.getLogger(LanguageMysql.class);
	
	@Override
	public List<Language> getLanguageAll() {
		// TODO Auto-generated method stub
		logger.info("LanguageMysql.getLanguageAll() >>>");
		
		String query="select LAN_N_LANG_ID,LAN_S_CODE,LAN_S_NAME from BSST_LAN_LANGUAGE";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query);
		
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
		
		
        
		logger.info("LanguageMysql.getLanguageAll() <<<");
		
		return ret;

	}

	@Override
	public Language getLanguage(String languageCode) {
		// TODO Auto-generated method stub
		logger.info("LanguageMysql.getLanguage() >>>");
	
		String query="select LAN_N_LANG_ID,LAN_S_CODE,LAN_S_NAME from BSST_LAN_LANGUAGE where LAN_S_CODE=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"["+languageCode+"]");
		
		Language ret=jdbcTemplate.queryForObject(query, new Object[] { languageCode },new RowMapper<Language>() {
            @Override
            public Language mapRow(ResultSet rs, int rowNum) throws SQLException {
            	Language lang=new Language();
            	
            	lang.setId(rs.getInt("LAN_N_LANG_ID"));
                lang.setCode(rs.getString("LAN_S_CODE"));
                lang.setName(rs.getString("LAN_S_NAME"));
                
                return lang;
            }
        });
		
		
		logger.info("LanguageMysql.getLanguage() <<<");
		return ret;
		
	}

}

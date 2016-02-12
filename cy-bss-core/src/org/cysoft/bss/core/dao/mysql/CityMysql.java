package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.cysoft.bss.core.dao.CityDao;
import org.cysoft.bss.core.model.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class CityMysql extends CyBssMysqlDao
	implements CityDao{

	private static final Logger logger = LoggerFactory.getLogger(CityMysql.class);
	
	@Override
	public List<City> getCityAll() {
		// TODO Auto-generated method stub
		logger.info("CityMysql.getCityAll() >>>");
		
		String query="select ID,NAME,CODE,STATE_REGION,LATITUDE,LONGITUDE,COUNTRY_ID,COUNTRY_CODE,COUNTRY_NAME from BSSV_CITY";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(query);
		
		List<City> ret = jdbcTemplate.query(
                query, 
                new RowMapper<City>() {
                    @Override
                    public City mapRow(ResultSet rs, int rowNum) throws SQLException {
                    	City city=new City();
                        
                        city.setId(rs.getLong("ID"));
                        city.setName(rs.getString("NAME"));
                        city.setCode(rs.getString("STATE_REGION"));
                        city.setLatitude(rs.getDouble("LATITUDE"));
                        city.setLongitude(rs.getDouble("LONGITUDE"));
                        city.setCountryId(rs.getLong("COUNTRY_ID"));
                        city.setCountryCode(rs.getString("COUNTRY_CODE"));
                        city.setCountryName(rs.getString("COUNTRY_NAME"));
                        
                        return city;
		            }
                });
		
		
        
		logger.info("CityMysql.getCityAll() <<<");
		
		return ret;

	}

}

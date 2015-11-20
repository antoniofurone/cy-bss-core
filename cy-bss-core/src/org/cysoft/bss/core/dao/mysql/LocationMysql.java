package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.LocationDao;
import org.cysoft.bss.core.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class LocationMysql extends CyBssMysqlDao
	implements LocationDao{

	private static final Logger logger = LoggerFactory.getLogger(LocationMysql.class);
	
	
	@Override
	public synchronized long add(Location location) throws CyBssException {
		// TODO Auto-generated method stub
		String cmd="insert into BSST_LOC_LOCATION(LOC_S_NAME,LOC_S_TYPE,LOC_D_LAT,LOC_D_LNG,CIT_N_CITY_ID)";
		cmd+=" values ";
		cmd+=" (?,?,?,?,?)";
	
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(cmd+"["+location+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					location.getName(), 
					location.getLocationType()==null || location.getLocationType().equals("") ?null:location.getLocationType(),  
					location.getLatitude(),location.getLongitude(),
					location.getCityId()==0?null:location.getCityId()
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		}
		
		String query="SELECT LAST_INSERT_ID()";
		Long ret=jdbcTemplate.queryForObject(query, new Object[] { },new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
            	return rs.getLong(1);
            }
        });
		
		return ret;

	}


	@Override
	public Location get(long id) {
		// TODO Auto-generated method stub
		logger.info("LocationMysql.get() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		
		
		String query="select  LOC_N_LOCATION_ID,LOC_S_NAME,LOC_S_TYPE,LOC_D_LAT,LOC_D_LNG,CIT_N_CITY_ID";
		query+=" from BSST_LOC_LOCATION";
		query+=" where LOC_N_LOCATION_ID=?";
		
		logger.info(query+"["+id+"]");
		Location ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },new RowMapperLocation());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("UserMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		logger.info("LocationMysql.get() <<<");
		return ret;

	}

	private class RowMapperLocation implements RowMapper<Location>{

		@Override
		public Location mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			Location location=new Location();
            
			location.setId(rs.getLong("LOC_N_LOCATION_ID"));
			location.setName(rs.getString("LOC_S_NAME"));
			location.setLocationType(rs.getString("LOC_S_TYPE"));
			location.setLatitude(rs.getDouble("LOC_D_LAT"));
			location.setLongitude(rs.getDouble("LOC_D_LNG"));
			location.setCityId(rs.getLong("CIT_N_CITY_ID"));
			
            return location;
		}
		
	}

	
	
}

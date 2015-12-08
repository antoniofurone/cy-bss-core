package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
		String cmd="insert into BSST_LOC_LOCATION(LOC_S_NAME,LOC_S_DESC,LOC_S_TYPE,LOC_D_LAT,LOC_D_LNG,CIT_N_CITY_ID)";
		cmd+=" values ";
		cmd+=" (?,?,?,?,?,?)";
	
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(cmd+"["+location+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					location.getName(),
					location.getDescription()==null || location.getDescription().equals("") ?null:location.getDescription(),  
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
	public void addLang(Location location, long langId) throws CyBssException {
		// TODO Auto-generated method stub
		String cmd="insert into BSST_LLA_LOCATION_LANG(LOC_N_LOCATION_ID,LAN_N_LANG_ID,LLA_S_NAME,LLA_S_DESC)";
		cmd+=" values ";
		cmd+=" (?,?,?,?)";
	
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(cmd+"["+location+","+langId+"]");
	
		try {
			jdbcTemplate.update(cmd, new Object[]{
					location.getCityId(),
					langId,
					location.getName(),
					location.getDescription()==null || location.getDescription().equals("") ?null:location.getDescription()  
					});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		}
	}

	@Override
	public Location get(long id,long langId) {
		// TODO Auto-generated method stub
		logger.info("LocationMysql.get() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		
		
		String query="select  a.LOC_N_LOCATION_ID,IFNULL(b.LLA_S_NAME,a.LOC_S_NAME) as LOC_S_NAME,IFNULL(b.LLA_S_DESC,a.LOC_S_DESC) as LOC_S_DESC,LOC_S_TYPE,LOC_D_LAT,LOC_D_LNG,CIT_N_CITY_ID";
		query+=" from BSST_LOC_LOCATION a";
		query+=" left join BSST_LLA_LOCATION_LANG b on b.LOC_N_LOCATION_ID=a.LOC_N_LOCATION_ID and b.LAN_N_LANG_ID=?";
		query+=" where LOC_N_LOCATION_ID=?";
		
		logger.info(query+"["+id+"]");
		Location ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },new RowMapperLocation());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("LocationMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		return ret;

	}

	private class RowMapperLocation implements RowMapper<Location>{

		@Override
		public Location mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			Location location=new Location();
            
			location.setId(rs.getLong("LOC_N_LOCATION_ID"));
			location.setName(rs.getString("LOC_S_NAME"));
			location.setDescription(rs.getString("LOC_S_DESC"));
			location.setLocationType(rs.getString("LOC_S_TYPE"));
			location.setLatitude(rs.getDouble("LOC_D_LAT"));
			location.setLongitude(rs.getDouble("LOC_D_LNG"));
			location.setCityId(rs.getLong("CIT_N_CITY_ID"));
			
            return location;
		}
		
	}

	@Override
	public void remove(long id) throws CyBssException {
		// TODO Auto-generated method stub
		
		String cmd="delete from BSST_LLA_LOCATION_LANG where LOC_N_LOCATION_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
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
		
		cmd="delete from BSST_LOC_LOCATION where LOC_N_LOCATION_ID=?";
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
		
		
	}


	@Override
	public void removeLang(long id, long langId) throws CyBssException {
		// TODO Auto-generated method stub
		String cmd="delete from BSST_LLA_LOCATION_LANG where LOC_N_LOCATION_ID=? and LAN_N_LANG_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(cmd+"["+id+","+langId+"]");
		try {
			jdbcTemplate.update(cmd, new Object[]{
					id,langId
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		}
	}

	@Override
	public List<Location> find(String name,long cityId, long langId) throws CyBssException {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		
		
		String query="select  a.LOC_N_LOCATION_ID,IFNULL(b.LLA_S_NAME,a.LOC_S_NAME) as LOC_S_NAME,IFNULL(b.LLA_S_DESC,a.LOC_S_DESC) as LOC_S_DESC,LOC_S_TYPE,LOC_D_LAT,LOC_D_LNG,CIT_N_CITY_ID";
		query+=" from BSST_LOC_LOCATION a";
		query+=" left join BSST_LLA_LOCATION_LANG b on b.LOC_N_LOCATION_ID=a.LOC_N_LOCATION_ID and b.LAN_N_LANG_ID=?";
		
		if (!name.equals(""))
			query+=" WHERE ";
		
		List<Object> parms=new ArrayList<Object>();
		parms.add(langId);
		
		boolean insAnd=false;
		
		if (!name.equals("")){
			if (!name.contains("%"))
				query+=" a.LOC_S_NAME=?";
			else
				query+=" a.LOC_S_NAME like ?";
			parms.add(name);
			insAnd=true;
		}
		
		if (cityId!=0){
			query+=(insAnd?" AND":"")+" CIT_N_CITY_ID=?";
			insAnd=true;
			parms.add(cityId);
		}
		
		logger.info(query+"["+langId+","+name+"]");
		
		List<Location> ret=jdbcTemplate.query(query, parms.toArray(),new RowMapperLocation());
		
		return ret;
	}
	
	
}

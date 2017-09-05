package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.CityDao;
import org.cysoft.bss.core.model.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query);
		
		List<City> ret = jdbcTemplate.query(
                query, 
                new RowMapperCity());
		
		logger.info("CityMysql.getCityAll() <<<");
		
		return ret;

	}
	
	private class RowMapperCity implements RowMapper<City>{

		@Override
		public City mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			City city=new City();
			
			city.setId(rs.getLong("ID"));
            city.setName(rs.getString("NAME"));
            city.setCode(rs.getString("CODE"));
            city.setStateRegion(rs.getString("STATE_REGION"));
            city.setLatitude(rs.getDouble("LATITUDE"));
            city.setLongitude(rs.getDouble("LONGITUDE"));
            city.setCountryId(rs.getLong("COUNTRY_ID"));
            city.setCountryCode(rs.getString("COUNTRY_CODE"));
            city.setCountryName(rs.getString("COUNTRY_NAME"));
            
	        return city;
		}
		
	}


	@Override
	public long add(City city) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("CityMysql.add() >>>");
		
		String cmd="insert into BSST_CIT_CITY(CIT_S_NAME,CIT_S_CODE,CIT_S_STATE_REGION,CIT_D_LOC_LAT,CIT_D_LOC_LNG,CON_N_COUNTRY_ID) ";
		cmd+=" values (?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+city+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					city.getName(), 
					(city.getCode()==null || city.getCode().equals(""))?null:city.getCode(),
					(city.getStateRegion()==null || city.getStateRegion().equals(""))?null:city.getStateRegion(),
					city.getLatitude(),
					city.getLongitude(),
					city.getCountryId()
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("CityMysql.add() <<<");
		
		return getLastInsertId(jdbcTemplate);
	}

	@Override
	public void update(long id, City city) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("CityMysql.update() >>>");
		
		String cmd="update BSST_CIT_CITY set CIT_S_NAME=?,CIT_S_CODE=?,CIT_S_STATE_REGION=?,CIT_D_LOC_LAT=?,CIT_D_LOC_LNG=?,CON_N_COUNTRY_ID=? ";
		cmd+="where CIT_N_CITY_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+city+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					city.getName(), 
					(city.getCode()==null || city.getCode().equals(""))?null:city.getCode(),
					(city.getStateRegion()==null || city.getStateRegion().equals(""))?null:city.getStateRegion(),
					city.getLatitude(),
					city.getLongitude(),
					city.getCountryId(),
					id
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("CityMysql.update() <<<");
		
	}

	@Override
	public City get(long id) {
		// TODO Auto-generated method stub
		logger.info("CityMysql.get() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String query="select ID,NAME,CODE,STATE_REGION,LATITUDE,LONGITUDE,COUNTRY_ID,COUNTRY_CODE,COUNTRY_NAME from BSSV_CITY";
		query+=" where ID=?";
		
		logger.info(query+"["+id+"]");
		City ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },
					new RowMapperCity());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("CityMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		logger.info("CityMysql.get() <<<");
		return ret;
	}

	@Override
	public void remove(long id) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("CityMysql.remove() >>>");
		
		String cmd="delete from BSST_CIT_CITY where CIT_N_CITY_ID=?";
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
	public List<City> find(String name, String stateRegion, long countryId) {
		// TODO Auto-generated method stub
		String query="select ID,NAME,CODE,STATE_REGION,LATITUDE,LONGITUDE,COUNTRY_ID,COUNTRY_CODE,COUNTRY_NAME from BSSV_CITY";
		if (!name.equals("") || !stateRegion.equals("") || countryId!=0)
			query+=" WHERE ";
		boolean insAnd=false;
		
		List<Object> parms=new ArrayList<Object>();
		
		if (!name.equals("")){
			if (!name.contains("%"))
				query+=" NAME=?";
			else
				query+=" NAME like ?";
			insAnd=true;
			parms.add(name);
		}
		if (!stateRegion.equals("")){
			if (!stateRegion.contains("%"))
				query+=(insAnd?" AND":"")+" STATE_REGION=?";
			else
				query+=(insAnd?" AND":"")+" STATE_REGION like ?";
			insAnd=true;
			parms.add(stateRegion);
		}
		if (countryId!=0){
			query+=(insAnd?" AND":"")+" COUNTRY_ID=?";
			parms.add(countryId);
		}
		query+=" order by NAME";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"[name="+name+";stateRegion="+stateRegion+";countryId="+countryId+"]");
		
		List<City> ret=jdbcTemplate.query(query, parms.toArray(),new RowMapperCity());
			
		logger.info("CompanyMysql.find() <<<");
		return ret;
	}

}

package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.CountryDao;
import org.cysoft.bss.core.model.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class CountryMysql extends CyBssMysqlDao
	implements CountryDao{

	private static final Logger logger = LoggerFactory.getLogger(CountryMysql.class);
	
	@Override
	public List<Country> getCountryAll() {
		// TODO Auto-generated method stub
		logger.info("CountryMysql.getCountryAll() >>>");
		
		String query="select CON_N_COUNTRY_ID,CON_S_CODE,CON_S_NAME from BSST_CON_COUNTRY";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query);
		
		List<Country> ret = jdbcTemplate.query(
                query, 
                new RowMapperCountry());
		
		logger.info("CountryMysql.getCountryAll() <<<");
		
		return ret;

	}
	
	private class RowMapperCountry implements RowMapper<Country>{

		@Override
		public Country mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			Country country=new Country();
			
			country.setId(rs.getLong("CON_N_COUNTRY_ID"));
            country.setCode(rs.getString("CON_S_CODE"));
            country.setName(rs.getString("CON_S_NAME"));
            
	        return country;
		}
		
	}

	@Override
	public long add(Country country) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("CountryMysql.add() >>>");
		
		String cmd="insert into BSST_CON_COUNTRY(CON_S_CODE,CON_S_NAME) ";
		cmd+=" values (?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+country+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					country.getCode(),country.getName()
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("CountryMysql.add() <<<");
		
		return getLastInsertId(jdbcTemplate);
	}

	@Override
	public void update(long id, Country country) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("CountryMysql.update() >>>");
		
		String cmd="update BSST_CON_COUNTRY set CON_S_CODE=?,CON_S_NAME=? ";
		cmd+="where CON_N_COUNTRY_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+country+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					country.getCode(),country.getName(),id
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("CountryMysql.update() <<<");
		
	}

	@Override
	public Country get(long id) {
		// TODO Auto-generated method stub
		logger.info("CountryMysql.get() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String query="select CON_N_COUNTRY_ID,CON_S_CODE,CON_S_NAME from BSST_CON_COUNTRY";
		query+=" where CON_N_COUNTRY_ID=?";
		
		logger.info(query+"["+id+"]");
		Country ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },
					new RowMapperCountry());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("CountryMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		logger.info("CountryMysql.get() <<<");
		return ret;
	}

	@Override
	public void remove(long id) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("CountryMysql.remove() >>>");
		
		String cmd="delete from BSST_CON_COUNTRY where CON_N_COUNTRY_ID=?";
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
		
		logger.info("CountryMysql.remove() <<<");

	}

	@Override
	public List<Country> find(String name) {
		// TODO Auto-generated method stub
		String query="select CON_N_COUNTRY_ID,CON_S_CODE,CON_S_NAME from BSST_CON_COUNTRY";
		if (!name.equals(""))
			query+=" WHERE ";
		
		List<Object> parms=new ArrayList<Object>();
		
		if (!name.equals("")){
			if (!name.contains("%"))
				query+=" CON_S_NAME=?";
			else
				query+=" CON_S_NAME like ?";
			parms.add(name);
		}
		query+=" order by CON_S_NAME";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"[name="+name+"]");
		
		List<Country> ret=jdbcTemplate.query(query, parms.toArray(),new RowMapperCountry());
			
		logger.info("CountryMysql.find() <<<");
		return ret;
	}

}

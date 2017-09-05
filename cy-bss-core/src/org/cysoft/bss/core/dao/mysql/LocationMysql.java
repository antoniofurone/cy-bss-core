package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.dao.FileDao;
import org.cysoft.bss.core.dao.LocationDao;
import org.cysoft.bss.core.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class LocationMysql extends CyBssMysqlDao
	implements LocationDao{

	private static final Logger logger = LoggerFactory.getLogger(LocationMysql.class);
	
	protected  FileDao fileDao=null;
	@Autowired
	public void setFileDao(FileDao fileDao){
			this.fileDao=fileDao;
	}
	
	@Override
	public long add(Location location) throws CyBssException {
		// TODO Auto-generated method stub
		String cmd="insert into BSST_LOC_LOCATION(LOC_S_NAME,LOC_D_CREATION_DATE,LOC_S_DESC,LOC_S_TYPE,LOC_D_LAT,LOC_D_LNG,LOC_S_ADDRESS,LOC_S_ZIP,CIT_N_CITY_ID,USR_N_USER_ID,PER_N_PERSON_ID)";
		cmd+=" values ";
		cmd+=" (?,now(),?,?,?,?,?,?,?,?,?)";
	
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+location+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					location.getName(),
					location.getDescription()==null || location.getDescription().equals("") ?null:location.getDescription(),  
					location.getLocationType()==null || location.getLocationType().equals("") ?null:location.getLocationType(),  
					location.getLatitude(),location.getLongitude(),
					location.getAddress()==null || location.getAddress().equals("") ?null:location.getAddress(),  
					location.getZipCode()==null || location.getZipCode().equals("") ?null:location.getZipCode(),  
					location.getCityId()==0?null:location.getCityId(),
					location.getUserId(),
					location.getPersonId()==0?null:location.getPersonId()
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		}
		
		return getLastInsertId(jdbcTemplate);
	}

	@Override
	public void addUpdLang(final Location location) throws CyBssException {
		// TODO Auto-generated method stub
		Location loc0=get(location.getId(),location.getLangId()); 
		if (loc0==null)
			throw new RuntimeException("Loc<"+location.getId()+"> not found !");
	
		logger.info("l0.getLangId:"+loc0.getLangId()+";"+location.getLangId());
		
		if (loc0.getLangId()!=location.getLangId()){
			String cmd="insert into BSST_LLA_LOCATION_LANG(LOC_N_LOCATION_ID,LAN_N_LANG_ID,LLA_S_NAME,LLA_S_DESC)";
			cmd+=" values ";
			cmd+=" (?,?,?,?)";
		
			JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
			logger.info(cmd+"["+location+"]");
		
			try {
				jdbcTemplate.update(cmd, new Object[]{
						location.getId(),
						location.getLangId(),
						location.getName(),
						location.getDescription()==null || location.getDescription().equals("") ?null:location.getDescription()  
						});
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				logger.error(e.toString());
				throw new RuntimeException(e);
			}

		}
		else
		{
			String cmd="update BSST_LLA_LOCATION_LANG set LAN_N_LANG_ID=?,LLA_S_NAME=?,LLA_S_DESC=? where LOC_N_LOCATION_ID=? and LAN_N_LANG_ID=?";
			
			JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
			logger.info(cmd+"["+location+"]");
		
			try {
				jdbcTemplate.update(cmd, new Object[]{
						location.getLangId(),
						location.getName(),
						location.getDescription()==null || location.getDescription().equals("") ?null:location.getDescription(),
						location.getId(), 
						location.getLangId()
						});
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				logger.error(e.toString());
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public Location get(long id,long langId) {
		// TODO Auto-generated method stub
		logger.info("LocationMysql.get() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		
		String query="select  a.LOC_N_LOCATION_ID,IFNULL(b.LLA_S_NAME,a.LOC_S_NAME) as LOC_S_NAME,LOC_D_CREATION_DATE,IFNULL(b.LLA_S_DESC,a.LOC_S_DESC) as LOC_S_DESC,LOC_S_TYPE,LOC_D_LAT,LOC_D_LNG,";
		query+="a.LOC_S_ADDRESS,a.LOC_S_ZIP,a.CIT_N_CITY_ID,a.PER_N_PERSON_ID,PER_S_FIRST_NAME, PER_S_SECOND_NAME,a.USR_N_USER_ID,USR_S_USER_ID,IFNULL(b.LAN_N_LANG_ID,0) as LANG_ID";
		query+=" from BSST_LOC_LOCATION a";
		query+=" left join BSST_LLA_LOCATION_LANG b on b.LOC_N_LOCATION_ID=a.LOC_N_LOCATION_ID and b.LAN_N_LANG_ID=?";
		query+=" left join BSST_USR_USER c on c.USR_N_USER_ID=a.USR_N_USER_ID";
		query+=" left join BSST_PER_PERSON d on d.PER_N_PERSON_ID=a.PER_N_PERSON_ID";
		query+=" where a.LOC_N_LOCATION_ID=?";
		
		logger.info(query+"["+id+","+langId+"]");
		Location ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { langId,id },new RowMapperLocation());
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
			location.setCreationDate(rs.getString("LOC_D_CREATION_DATE"));
			location.setDescription(rs.getString("LOC_S_DESC"));
			location.setLocationType(rs.getString("LOC_S_TYPE"));
			location.setLatitude(rs.getDouble("LOC_D_LAT"));
			location.setLongitude(rs.getDouble("LOC_D_LNG"));
			location.setAddress(rs.getString("LOC_S_ADDRESS"));
			location.setZipCode(rs.getString("LOC_S_ZIP"));
			location.setCityId(rs.getLong("CIT_N_CITY_ID"));
			location.setPersonId(rs.getLong("PER_N_PERSON_ID"));
			location.setPersonFirstName(rs.getString("PER_S_FIRST_NAME"));
			location.setPersonSecondName(rs.getString("PER_S_SECOND_NAME"));
			location.setUserId(rs.getLong("USR_N_USER_ID"));
			location.setUserName(rs.getString("USR_S_USER_ID"));
			location.setLangId(rs.getLong("LANG_ID"));
			
			
            return location;
		}
		
	}

	@Override
	public void remove(final long id) throws CyBssException {
		// TODO Auto-generated method stub
		String cmd="delete from BSST_LOC_LOCATION where LOC_N_LOCATION_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+"]");
		try {
			jdbcTemplate.update(cmd, new Object[]{
					id
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new RuntimeException(e);
			}
		}


	@Override
	public void removeLang(long id, long langId) throws CyBssException {
		// TODO Auto-generated method stub
		String cmd="delete from BSST_LLA_LOCATION_LANG where LOC_N_LOCATION_ID=? and LAN_N_LANG_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
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
	public void removeLang(long id) throws CyBssException {
		// TODO Auto-generated method stub
		String cmd="delete from BSST_LLA_LOCATION_LANG where LOC_N_LOCATION_ID=?";
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
	}
	
	
	@Override
	public List<Location> find(String name,String description,String locationType,long cityId,long personId,
			String fromDate,String toDate,long langId) throws CyBssException {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String query="select  a.LOC_N_LOCATION_ID,IFNULL(b.LLA_S_NAME,a.LOC_S_NAME) as LOC_S_NAME,LOC_D_CREATION_DATE,IFNULL(b.LLA_S_DESC,a.LOC_S_DESC) as LOC_S_DESC,LOC_S_TYPE,LOC_D_LAT,LOC_D_LNG,";
		query+="a.LOC_S_ADDRESS,a.LOC_S_ZIP,a.CIT_N_CITY_ID,a.PER_N_PERSON_ID,PER_S_FIRST_NAME, PER_S_SECOND_NAME,a.USR_N_USER_ID,USR_S_USER_ID,IFNULL(b.LAN_N_LANG_ID,0) as LANG_ID";
		query+=" from BSST_LOC_LOCATION a";
		query+=" left join BSST_LLA_LOCATION_LANG b on b.LOC_N_LOCATION_ID=a.LOC_N_LOCATION_ID and b.LAN_N_LANG_ID=?";
		query+=" left join BSST_USR_USER c on c.USR_N_USER_ID=a.USR_N_USER_ID";
		query+=" left join BSST_PER_PERSON d on d.PER_N_PERSON_ID=a.PER_N_PERSON_ID";
		
		if (!name.equals("") || !description.equals("") || cityId!=0 || personId!=0 || !locationType.equals("") || !fromDate.equals("") || !toDate.equals(""))
			query+=" WHERE ";
		
		List<Object> parms=new ArrayList<Object>();
		parms.add(langId);
		
		boolean insAnd=false;
		
		if (!name.equals("")){
			if (!name.contains("%"))
				query+=" IFNULL(b.LLA_S_NAME,a.LOC_S_NAME)=?";
			else
				query+=" IFNULL(b.LLA_S_NAME,a.LOC_S_NAME) like ?";
			parms.add(name);
			insAnd=true;
		}
		
		if (!description.equals("")){
			if (!description.contains("%"))
				query+=(insAnd?" AND":"")+" IFNULL(b.LLA_S_DESC,a.LOC_S_DESC)=?";
			else
				query+=(insAnd?" AND":"")+" IFNULL(b.LLA_S_DESC,a.LOC_S_DESC) like ?";
			parms.add(description);
			insAnd=true;
		}
		
		
		if (cityId!=0){
			query+=(insAnd?" AND":"")+" a.CIT_N_CITY_ID=?";
			insAnd=true;
			parms.add(cityId);
		}
		
		if (personId!=0){
			query+=(insAnd?" AND":"")+" a.PER_N_PERSON_ID=?";
			insAnd=true;
			parms.add(personId);
		}
		
		if (!locationType.equals("")){
			query+=(insAnd?" AND":"")+" a.LOC_S_TYPE=?";
			insAnd=true;
			parms.add(locationType);
		}
		
		if (!fromDate.equals("")){
			query+=(insAnd?" AND":"")+" a.LOC_D_CREATION_DATE>=?";
			insAnd=true;
			try {
				parms.add(CyBssUtility.dateChangeFormat(fromDate, CyBssUtility.DATE_yyyy_MM_dd));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				throw new CyBssException(e);
			}
		}

		if (!toDate.equals("")){
			query+=(insAnd?" AND":"")+" DATE_SUB(a.LOC_D_CREATION_DATE,INTERVAL 1 DAY)<=?";
			insAnd=true;
			try {
				parms.add(CyBssUtility.dateChangeFormat(toDate, CyBssUtility.DATE_yyyy_MM_dd));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				throw new CyBssException(e);
			}
		}
		query+=" order by LOC_D_CREATION_DATE desc";
		
		logger.info(query+"[parms="+parms+"]");
		
		List<Location> ret=jdbcTemplate.query(query, parms.toArray(),new RowMapperLocation());
		
		return ret;
	}

	@Override
	public void update(long id, Location location) throws CyBssException {
		// TODO Auto-generated method stub
		
		String cmd="update BSST_LOC_LOCATION set LOC_S_NAME=?,LOC_S_DESC=?,LOC_S_TYPE=?,LOC_D_LAT=?,LOC_D_LNG=?,LOC_S_ADDRESS=?,LOC_S_ZIP=?,CIT_N_CITY_ID=? ";
		cmd+="where LOC_N_LOCATION_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+","+location+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					location.getName(),
					location.getDescription()==null || location.getDescription().equals("") ?null:location.getDescription(),  
					location.getLocationType()==null || location.getLocationType().equals("") ?null:location.getLocationType(),  
					location.getLatitude(),location.getLongitude(),
					location.getAddress()==null || location.getAddress().equals("") ?null:location.getAddress(),  
					location.getZipCode()==null || location.getZipCode().equals("") ?null:location.getZipCode(),  
					location.getCityId()==0?null:location.getCityId(),
					id		
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		}
	}
	
}

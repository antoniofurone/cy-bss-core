package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.dao.PersonDao;
import org.cysoft.bss.core.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class PersonMysql extends CyBssMysqlDao
implements PersonDao{

	private static final Logger logger = LoggerFactory.getLogger(PersonMysql.class);
	
	
	@Override
	public void add(Person person) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("PersonMysql.add() >>>");
		
		String cmd="insert into BSST_PER_PERSON(PER_S_CODE,PER_S_FIRST_NAME,PER_S_SECOND_NAME,PER_C_GENDER,PER_S_ADDRESS,PER_S_ZIP,CIT_N_CITY_ID,PER_S_FISCAL_CODE,PER_D_BIRTH_DAY,CIT_N_BIRTH_CITY_ID) ";
		cmd+=" values (?,?,?,?,?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+person+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					person.getCode(), person.getFirstName(), person.getSecondName(), 
					(person.getGender()==null || person.getGender().equals(""))?null:person.getGender(),
					(person.getAddress()==null || person.getAddress().equals(""))?null:person.getAddress(),
					(person.getZipCode()==null || person.getZipCode().equals(""))?null:person.getZipCode(),
					(person.getCityId()==0)?null:person.getCityId(),
					(person.getFiscalCode()==null || person.getFiscalCode().equals(""))?null:person.getFiscalCode(),
					(person.getBirthDay()==null || person.getBirthDay().equals(""))?null:CyBssUtility.tryStringToDate(person.getBirthDay()),
					(person.getBirthCityId()==0)?null:person.getBirthCityId()
				});
		} catch (DataAccessException | ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("PersonMysql.add() <<<");

	}
	
	@Override
	public Person getByCode(String code) {
		// TODO Auto-generated method stub
		return this.getBy(code, true);
	}
	
	private Person getBy(String id,boolean bCode) {
		// TODO Auto-generated method stub
		
		logger.info("PersonMysql.getBy() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		
		String query="select  ID,CODE,FIRST_NAME,SECOND_NAME,GENDER,ADDRESS,ZIP,";
		query+="CITY_ID,CITY,COUNTRY,FISCAL_CODE,BIRTH_DAY,BIRTH_CITY_ID,BIRTH_CITY ";
		query+="from BSSV_PERSON";
		if (bCode)
			query+=" where CODE=?";
		else
			query+=" where ID=?";
		
		logger.info(query+"["+id+",****]");
		Person ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { bCode?id:Long.parseLong(id) },new RowMapperPerson());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("PersonMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		logger.info("PersonMysql.getBy() <<<");
		return ret;
	}

	@Override
	public Person get(long id) {
		// TODO Auto-generated method stub
		return this.getBy((new Long(id)).toString(), false);
	}

	@Override
	public void update(long id, Person person) throws CyBssException {
		// TODO Auto-generated method stub
		
		logger.info("PersonMysql.update() >>>");
		
		String cmd="update BSST_PER_PERSON set PER_S_CODE=?,PER_S_FIRST_NAME=?,PER_S_SECOND_NAME=?,PER_C_GENDER=?,";
		cmd+="PER_S_ADDRESS=?,PER_S_ZIP=?,CIT_N_CITY_ID=?,PER_S_FISCAL_CODE=?,PER_D_BIRTH_DAY=?,CIT_N_BIRTH_CITY_ID=?";
		cmd+=" where PER_N_PERSON_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+","+person+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					person.getCode(), person.getFirstName(), person.getSecondName(), 
					(person.getGender()==null || person.getGender().equals(""))?null:person.getGender(),
					(person.getAddress()==null || person.getAddress().equals(""))?null:person.getAddress(),
					(person.getZipCode()==null || person.getZipCode().equals(""))?null:person.getZipCode(),
					(person.getCityId()==0)?null:person.getCityId(),
					(person.getFiscalCode()==null || person.getFiscalCode().equals(""))?null:person.getFiscalCode(),
					(person.getBirthDay()==null || person.getBirthDay().equals(""))?null:CyBssUtility.tryStringToDate(person.getBirthDay()),
					(person.getBirthCityId()==0)?null:person.getBirthCityId(),
					id		
				});
		} catch (DataAccessException | ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		}
		
		logger.info("PersonMysql.update() <<<");
		
	}

	@Override
	public void remove(final long id) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("PersonMysql.remove() >>>");
		
		String cmd="delete from BSST_PER_PERSON where PER_N_PERSON_ID=?";
		logger.info(cmd+"["+id+"]");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());		
		jdbcTemplate.update(cmd, new Object[]{
				id
		});		
		logger.info("PersonMysql.remove() <<<");
	}

	@Override
	public List<Person> find(String code, String firstName, String secondName) {
		// TODO Auto-generated method stub
		logger.info("PersonMysql.find() >>> code="+code+";firstName="+firstName+";secondName="+secondName);
		
		String query="select  ID,CODE,FIRST_NAME,SECOND_NAME,GENDER,ADDRESS,ZIP,";
		query+="CITY_ID,CITY,COUNTRY,FISCAL_CODE,BIRTH_DAY,BIRTH_CITY_ID,BIRTH_CITY ";
		query+="from BSSV_PERSON";
		if (!code.equals("") || !firstName.equals("") || !secondName.equals(""))
			query+=" WHERE ";
		boolean insAnd=false;
		
		List<Object> parms=new ArrayList<Object>();
		
		if (!code.equals("")){
			if (!code.contains("%"))
				query+=" CODE=?";
			else
				query+=" CODE like ?";
			insAnd=true;
			parms.add(code);
		}
		if (!firstName.equals("")){
			if (!firstName.contains("%"))
				query+=(insAnd?" AND":"")+" FIRST_NAME=?";
			else
				query+=(insAnd?" AND":"")+" FIRST_NAME like ?";
			insAnd=true;
			parms.add(firstName);
		}
		if (!secondName.equals("")){
			if (!secondName.contains("%"))
				query+=(insAnd?" AND":"")+" SECOND_NAME=?";
			else
				query+=(insAnd?" AND":"")+" SECOND_NAME like ?";
			parms.add(secondName);
		}
		query+=" order by ID";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"[code="+code+";firstName="+firstName+";secondName="+secondName+"]");
		
		List<Person> ret=jdbcTemplate.query(query, parms.toArray(),new RowMapperPerson());
			
		logger.info("PersonMysql.find() <<<");
		return ret;
	}
	
	private class RowMapperPerson implements RowMapper<Person>{

		@Override
		public Person mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			Person person=new Person();
            
			
            person.setId(rs.getLong("ID"));
            person.setCode(rs.getString("CODE"));
            person.setFirstName(rs.getString("FIRST_NAME"));
            person.setSecondName(rs.getString("SECOND_NAME"));
            person.setGender(rs.getString("GENDER"));
            person.setAddress(rs.getString("ADDRESS")==null?"":rs.getString("ADDRESS"));
            person.setZipCode(rs.getString("ZIP")==null?"":rs.getString("ZIP"));
            person.setCityId(rs.getLong("CITY_ID"));
            person.setCity(rs.getString("CITY")==null?"":rs.getString("CITY"));
            person.setCountry(rs.getString("COUNTRY")==null?"":rs.getString("COUNTRY"));
            person.setFiscalCode(rs.getString("FISCAL_CODE")); 
            person.setBirthDay(rs.getString("BIRTH_DAY")); 
            person.setBirthCityId(rs.getLong("BIRTH_CITY_ID"));
            person.setBirthCity(rs.getString("BIRTH_CITY"));
            
            return person;
		}
		
	}
}	

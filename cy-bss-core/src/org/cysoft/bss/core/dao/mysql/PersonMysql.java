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
		
		String cmd="insert into BSST_PER_PERSON(PER_S_CODE,PER_S_FIRST_NAME,PER_S_SECOND_NAME,PER_C_GENDER,PER_S_ADDRESS,CIT_N_CITY_ID,PER_S_FISCAL_CODE,PER_D_BIRTH_DAY,CIT_N_BIRTH_CITY_ID) ";
		cmd+=" values (?,?,?,?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(cmd+"["+person+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					person.getCode(), person.getFirstName(), person.getSecondName(), 
					(person.getGender()==null || person.getGender().equals(""))?null:person.getGender(),
					(person.getAddress()==null || person.getAddress().equals(""))?null:person.getAddress(),
					(person.getCityId()==0)?null:person.getCityId(),
					(person.getFiscalCode()==null || person.getFiscalCode().equals(""))?null:person.getFiscalCode(),
					(person.getBirtyDay()==null || person.getBirtyDay().equals(""))?null:CyBssUtility.tryStringToDate(person.getBirtyDay()),
					(person.getBirtyCityId()==0)?null:person.getBirtyCityId()
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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		
		
		String query="select  PER_N_PERSON_ID, PER_S_CODE, PER_S_FIRST_NAME, PER_S_SECOND_NAME, PER_C_GENDER,";
		query+="PER_S_ADDRESS, CIT_N_CITY_ID, PER_S_FISCAL_CODE, PER_D_BIRTH_DAY,CIT_N_BIRTH_CITY_ID ";
		query+="from BSST_PER_PERSON";
		if (bCode)
			query+=" where PER_S_CODE=?";
		else
			query+=" where PER_N_PERSON_ID=?";
		
		logger.info(query+"["+id+",****]");
		Person ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { bCode?id:Long.parseLong(id) },new RowMapperPerson());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("UserMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
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
		cmd+="PER_S_ADDRESS=?,CIT_N_CITY_ID=?,PER_S_FISCAL_CODE=?,PER_D_BIRTH_DAY=?,CIT_N_BIRTH_CITY_ID=?";
		cmd+=" where PER_N_PERSON_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(cmd+"["+id+","+person+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					person.getCode(), person.getFirstName(), person.getSecondName(), 
					(person.getGender()==null || person.getGender().equals(""))?null:person.getGender(),
					(person.getAddress()==null || person.getAddress().equals(""))?null:person.getAddress(),
					(person.getCityId()==0)?null:person.getCityId(),
					(person.getFiscalCode()==null || person.getFiscalCode().equals(""))?null:person.getFiscalCode(),
					(person.getBirtyDay()==null || person.getBirtyDay().equals(""))?null:CyBssUtility.tryStringToDate(person.getBirtyDay()),
					(person.getBirtyCityId()==0)?null:person.getBirtyCityId(),
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
	public void remove(long id) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("PersonMysql.remove() >>>");
		
		String cmd="delete from BSST_PER_PERSON where PER_N_PERSON_ID=?";
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
		
		logger.info("PersonMysql.remove() <<<");

	}

	@Override
	public List<Person> find(String code, String firstName, String secondName) {
		// TODO Auto-generated method stub
		logger.info("PersonMysql.find() >>> code="+code+";firstName="+firstName+";secondName="+secondName);
		
		String query="select  PER_N_PERSON_ID, PER_S_CODE, PER_S_FIRST_NAME, PER_S_SECOND_NAME, PER_C_GENDER,";
		query+="PER_S_ADDRESS, CIT_N_CITY_ID, PER_S_FISCAL_CODE, PER_D_BIRTH_DAY,CIT_N_BIRTH_CITY_ID ";
		query+="from BSST_PER_PERSON";
		if (!code.equals("") || !firstName.equals("") || !secondName.equals(""))
			query+=" WHERE ";
		boolean insAnd=false;
		
		List<Object> parms=new ArrayList<Object>();
		
		if (!code.equals("")){
			if (!code.contains("%"))
				query+=" PER_S_CODE=?";
			else
				query+=" PER_S_CODE like ?";
			insAnd=true;
			parms.add(code);
		}
		if (!firstName.equals("")){
			if (!firstName.contains("%"))
				query+=(insAnd?" AND":"")+" PER_S_FIRST_NAME=?";
			else
				query+=(insAnd?" AND":"")+" PER_S_FIRST_NAME like ?";
			insAnd=true;
			parms.add(firstName);
		}
		if (!secondName.equals("")){
			if (!secondName.contains("%"))
				query+=(insAnd?" AND":"")+" PER_S_SECOND_NAME=?";
			else
				query+=(insAnd?" AND":"")+" PER_S_SECOND_NAME like ?";
			parms.add(secondName);
		}
		
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
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
            
            person.setId(rs.getLong("PER_N_PERSON_ID"));
            person.setCode(rs.getString("PER_S_CODE"));
            person.setFirstName(rs.getString("PER_S_FIRST_NAME"));
            person.setSecondName(rs.getString("PER_S_SECOND_NAME"));
            person.setGender(rs.getString("PER_C_GENDER"));
            person.setAddress(rs.getString("PER_S_ADDRESS"));
            person.setCityId(rs.getLong("CIT_N_CITY_ID"));
            person.setFiscalCode(rs.getString("PER_S_FISCAL_CODE")); 
            person.setBirtyDay(rs.getString("PER_D_BIRTH_DAY")); 
            person.setBirtyCityId(rs.getLong("CIT_N_BIRTH_CITY_ID"));
            
            return person;
		}
		
	}
}	

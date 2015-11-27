package org.cysoft.bss.core.dao.mysql;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.dao.TicketDao;
import org.cysoft.bss.core.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class TicketMysql extends CyBssMysqlDao
	implements TicketDao{

	private static final Logger logger = LoggerFactory.getLogger(TicketMysql.class);
	
	
	@Override
	public synchronized long add(Ticket ticket) throws CyBssException {
		// TODO Auto-generated method stub
		
		String cmd="insert into BSST_TIC_TICKET(TIC_S_TEXT,TIC_D_CREATION_DATE,USR_N_USER_ID,TCA_N_CATEGORY_ID,PER_N_PERSON_ID,LOC_N_LOCATION_ID)";
		cmd+=" values ";
		cmd+=" (?,now(),?,?,?,?)";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(cmd+"["+ticket+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					ticket.getText(), ticket.getUserId(),  
					(ticket.getCategoryId()==0)?null:ticket.getCategoryId(),
					(ticket.getPersonId()==0)?null:ticket.getPersonId(),
					(ticket.getLocationId()==0)?null:ticket.getLocationId()
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
	public Ticket get(long id) {
		// TODO Auto-generated method stub
		logger.info("TicketMysql.get() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		
		
		String query="select  ID,TEXT,CREATION_DATE,STATUS_ID,STATUS_NAME,USER_ID,USER_NAME,CATEGORY_ID,CATEGORY_NAME,";
		query+="PERSON_ID,PERSON_FIRST_NAME,PERSON_SECOND_NAME,LOCATION_ID";
		query+=" from BSSV_TICKET";
		query+=" where ID=?";
		
		logger.info(query+"["+id+"]");
		Ticket ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },new RowMapperTicket());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("UserMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		logger.info("TicketMysql.get() <<<");
		return ret;

	}
	
	@Override
	public void update(long id, Ticket ticket) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("TicketMysql.update() >>>");
		
		String cmd="update BSST_TIC_TICKET set TIC_S_TEXT=?,TCA_N_CATEGORY_ID=?,PER_N_PERSON_ID=?,";
		cmd+="LOC_N_LOCATION_ID=? where TIC_N_TICKET_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(cmd+"["+id+","+ticket+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					ticket.getText(), 
					(ticket.getCategoryId()==0)?null:ticket.getCategoryId(),
					(ticket.getPersonId()==0)?null:ticket.getPersonId(),
					(ticket.getLocationId()==0)?null:ticket.getLocationId(),id
				});
			} catch (DataAccessException  e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		}
		
		logger.info("TicketMysql.update() <<<");

	}
	
	@Override
	public List<Ticket> find(String text, long categoryId, long statusId,
			long personId,String fromDate,String toDate) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("TicketMysql.find() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
				
				
		String query="select  ID,TEXT,CREATION_DATE,STATUS_ID,STATUS_NAME,USER_ID,USER_NAME,CATEGORY_ID,CATEGORY_NAME,";
		query+="PERSON_ID,PERSON_FIRST_NAME,PERSON_SECOND_NAME,LOCATION_ID";
		query+=" from BSSV_TICKET";
		
		if (!text.equals("") || categoryId!=0 || statusId!=0 || personId!=0 || !fromDate.equals("") || !toDate.equals("") )
			query+=" WHERE ";
		boolean insAnd=false;
		
		List<Object> parms=new ArrayList<Object>();
		if (!text.equals("")){
			if (!text.contains("%"))
				query+=" TEXT=?";
			else
				query+=" TEXT like ?";
			insAnd=true;
			parms.add(text);
		}
		
		if (categoryId!=0){
			query+=(insAnd?" AND":"")+" CATEGORY_ID=?";
			insAnd=true;
			parms.add(categoryId);
		}
		
		if (statusId!=0){
			query+=(insAnd?" AND":"")+" STATUS_ID=?";
			insAnd=true;
			parms.add(statusId);
		}
		
		if (personId!=0){
			query+=(insAnd?" AND":"")+" PERSON_ID=?";
			insAnd=true;
			parms.add(personId);
		}
		
		if (!fromDate.equals("")){
			query+=(insAnd?" AND":"")+" CREATION_DATE>=?";
			insAnd=true;
			try {
				parms.add(CyBssUtility.dateChangeFormat(fromDate, CyBssUtility.DATE_yyyy_MM_dd));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				throw new CyBssException(e);
			}
		}

		if (!toDate.equals("")){
			query+=(insAnd?" AND":"")+" DATE_SUB(CREATION_DATE,INTERVAL 1 DAY)<=?";
			insAnd=true;
			try {
				parms.add(CyBssUtility.dateChangeFormat(toDate, CyBssUtility.DATE_yyyy_MM_dd));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				throw new CyBssException(e);
			}
		}
			
		logger.info(query+"[text="+text+";categoryId="+categoryId+";statusId="+statusId
				+";personId="+personId+";fromDate="+fromDate+";toDate="+toDate
				+"]");
		
		List<Ticket> ret=jdbcTemplate.query(query, parms.toArray(),new RowMapperTicket());
		
		
		logger.info("TicketMysql.find() <<<");
		
		return ret;
	}

	
	
	
	private class RowMapperTicket implements RowMapper<Ticket>{

		@Override
		public Ticket mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			Ticket ticket=new Ticket();
            
			ticket.setId(rs.getLong("ID"));
			ticket.setText(rs.getString("TEXT"));
			ticket.setCreationDate(rs.getString("CREATION_DATE"));
			
			ticket.setStatusId(rs.getLong("STATUS_ID"));
			ticket.setStatusName(rs.getString("STATUS_NAME"));
			
			ticket.setUserId(rs.getLong("USER_ID"));
			ticket.setUserName(rs.getString("USER_NAME"));
			
			ticket.setCategoryId(rs.getLong("CATEGORY_ID"));
			ticket.setCategoryName(rs.getString("CATEGORY_NAME"));
			
			ticket.setPersonId(rs.getLong("PERSON_ID"));
			ticket.setPersonFirstName(rs.getString("PERSON_FIRST_NAME"));
			ticket.setPersonSecondName(rs.getString("PERSON_SECOND_NAME"));
			
			ticket.setLocationId(rs.getLong("LOCATION_ID"));
			
            return ticket;
		}
		
	}

	


	
} 

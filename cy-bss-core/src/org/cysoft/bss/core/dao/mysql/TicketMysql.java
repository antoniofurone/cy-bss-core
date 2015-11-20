package org.cysoft.bss.core.dao.mysql;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.cysoft.bss.core.common.CyBssException;
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
		
		
		String query="select  TIC_N_TICKET_ID,TIC_S_TEXT,TIC_D_CREATION_DATE,USR_N_USER_ID,TCA_N_CATEGORY_ID,PER_N_PERSON_ID,LOC_N_LOCATION_ID,TST_N_STATUS_ID";
		query+=" from BSST_TIC_TICKET";
		query+=" where TIC_N_TICKET_ID=?";
		
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
	
	private class RowMapperTicket implements RowMapper<Ticket>{

		@Override
		public Ticket mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			Ticket ticket=new Ticket();
            
			ticket.setId(rs.getLong("TIC_N_TICKET_ID"));
			ticket.setText(rs.getString("TIC_S_TEXT"));
			ticket.setCreationDate(rs.getString("TIC_D_CREATION_DATE"));
			ticket.setUserId(rs.getLong("USR_N_USER_ID"));
			ticket.setCategoryId(rs.getLong("TCA_N_CATEGORY_ID"));
			ticket.setPersonId(rs.getLong("PER_N_PERSON_ID"));
			ticket.setLocationId(rs.getLong("LOC_N_LOCATION_ID"));
			ticket.setStatusId(rs.getLong("TST_N_STATUS_ID"));
			
            return ticket;
		}
		
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

} 

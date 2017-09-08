package org.cysoft.bss.core.dao.mysql;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.dao.InvoiceDao;
import org.cysoft.bss.core.model.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class InvoiceMysql extends CyBssMysqlDao
	implements InvoiceDao{

	private static final Logger logger = LoggerFactory.getLogger(InvoiceMysql.class);

	@Override
	public long add(Invoice invoice) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("InvoiceMysql.add() >>>");

		String cmd="insert into BSST_INV_INVOICE(INV_D_DATE,INV_N_YEAR,";
		cmd+="MNC_N_COMPANY_ID,COM_N_COMPANY_ID,PER_N_PERSON_ID,CUR_N_CURRENCY_ID,";
		cmd+="INV_S_NOTE) ";
		cmd+="values (?,?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+invoice+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					CyBssUtility.tryStringToDate(invoice.getDate()),
					CyBssUtility.getYear(CyBssUtility.tryStringToDate(invoice.getDate())),
					invoice.getCompanyId(),
					invoice.getTpCompanyId()==0?null:invoice.getTpCompanyId(),
					invoice.getPersonId()==0?null:invoice.getPersonId(),
					invoice.getCurrencyId(),		
					(invoice.getNote()==null || invoice.getNote().equals(""))?null:invoice.getNote()
				});
		} catch (DataAccessException | ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("InvoiceMysql.add() <<<");
		return getLastInsertId(jdbcTemplate);
	}

	@Override
	public List<Invoice> find(int number,int year,long companyId, long customerId, String customerCode, String customerName, long personId,
			String personCode, String personName, String fromDate, String toDate)
					throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("InvoiceMysql.find() >>> companyId="+companyId
				+";customerId="+customerId+";customerCode="+customerCode+";customerName="+customerName
				+";personId="+personId+";personCode="+personCode+";personName="+personName
				+";fromDate="+fromDate+";toDate="+toDate
				);
		
		
		String query="select a.ID,a.DATE,a.YEAR,a.NUMBER,a.COMPANY_ID,a.COMPANY_CODE,a.COMPANY_NAME,";
		query+="a.CUSTOMER_ID,a.CUSTOMER_CODE,a.CUSTOMER_NAME,";
		query+="a.PERSON_ID,a.PERSON_CODE,a.PERSON_FIRST_NAME,a.PERSON_SECOND_NAME,";
		query+="a.CURRENCY_ID,a.CURRENCY_CODE,a.CURRENCY_NAME,";
		query+="a.AMOUNT,a.VAT_AMOUNT,a.TOT_AMOUNT,";
		query+="a.NOTE,a.CANCELLED,a.STRING_NUMBER";
		query+=" from BSSV_INVOICE a";
		if (companyId!=0 || number!=0 || year!=0 || 
			customerId!=0 || !customerCode.equals("") || !customerName.equals("") ||
			personId!=0 || !personCode.equals("") || !personName.equals("") ||
			!fromDate.equals("") || !toDate.equals(""))
		query+=" WHERE ";
		
		boolean insAnd=false;
		List<Object> parms=new ArrayList<Object>();
		if (number!=0){
			query+=(insAnd?" AND":"")+" a.NUMBER=?";
			insAnd=true;
			parms.add(number);
		}
		if (year!=0){
			query+=(insAnd?" AND":"")+" a.YEAR=?";
			insAnd=true;
			parms.add(year);
		}
		if (companyId!=0){
			query+=(insAnd?" AND":"")+" a.COMPANY_ID=?";
			insAnd=true;
			parms.add(companyId);
		}
		if (customerId!=0){
			query+=(insAnd?" AND":"")+" a.CUSTOMER_ID=?";
			insAnd=true;
			parms.add(customerId);
		}
		if (!customerCode.equals("")){
			if (!customerCode.contains("%"))
				query+=(insAnd?" AND":"")+" a.CUSTOMER_CODE=?";
			else
				query+=(insAnd?" AND":"")+" a.CUSTOMER_CODE like ?";
			insAnd=true;
			parms.add(customerCode);
		}
		if (!customerName.equals("")){
			if (!customerName.contains("%"))
				query+=(insAnd?" AND":"")+" a.CUSTOMER_NAME=?";
			else
				query+=(insAnd?" AND":"")+" a.CUSTOMER_NAME like ?";
			insAnd=true;
			parms.add(customerName);
		}
		if (personId!=0){
			query+=(insAnd?" AND":"")+" a.PERSON_ID=?";
			insAnd=true;
			parms.add(personId);
		}
		if (!personCode.equals("")){
			if (!personCode.contains("%"))
				query+=(insAnd?" AND":"")+" a.PERSON_CODE=?";
			else
				query+=(insAnd?" AND":"")+" a.PERSON_CODE like ?";
			insAnd=true;
			parms.add(personCode);
		}
		if (!personName.equals("")){
			if (!personName.contains("%"))
				query+=(insAnd?" AND":"")+" (a.PERSON_FIRST_NAME=? or a.PERSON_SECOND_NAME=?)";
			else
				query+=(insAnd?" AND":"")+" (a.PERSON_FIRST_NAME like ? or a.PERSON_SECOND_NAME like ?)";
			insAnd=true;
			parms.add(personName);
			parms.add(personName);
		}
		
		if (!fromDate.equals("")){
			query+=(insAnd?" AND":"")+" DATE>=?";
			insAnd=true;
			try {
				parms.add(CyBssUtility.dateChangeFormat(fromDate, CyBssUtility.DATE_yyyy_MM_dd));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				throw new CyBssException(e);
			}
		}

		if (!toDate.equals("")){
			query+=(insAnd?" AND":"")+" DATE<=?";
			insAnd=true;
			try {
				parms.add(CyBssUtility.dateChangeFormat(toDate, CyBssUtility.DATE_yyyy_MM_dd));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				throw new CyBssException(e);
			}
		}
		query+=" order by DATE desc";

		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"[number="+number+";year="+year+"companyId="+companyId
				+";customerId="+customerId+";customerCode="+customerCode+";supplierName="+customerName
				+";personId="+personId+";personCode="+personCode+";personName="+personName
				+";fromDate="+fromDate+";toDate="+toDate
				+"]");
		
		List<Invoice> ret=jdbcTemplate.query(query, parms.toArray(),new RowMapperInvoice());
		
		logger.info("InvoiceMysql.find() <<<");
		
		return ret;
	}

	
	private class RowMapperInvoice implements RowMapper<Invoice>{

		@Override
		public Invoice mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			Invoice invoice=new Invoice();
		
			invoice.setInvoiceType(Invoice.TYPE_ACTIVE);
			invoice.setId(rs.getLong("ID"));
			invoice.setDate(rs.getString("DATE"));
			invoice.setYear(rs.getInt("YEAR"));
			invoice.setNumber(rs.getInt("NUMBER"));
			
			invoice.setCompanyId(rs.getLong("COMPANY_ID"));
			invoice.setCompanyCode(rs.getString("COMPANY_CODE"));
			invoice.setCompanyName(rs.getString("COMPANY_NAME"));
			
			invoice.setTpCompanyId(rs.getLong("CUSTOMER_ID"));
			invoice.setTpCompanyCode(rs.getString("CUSTOMER_CODE"));
			invoice.setTpCompanyName(rs.getString("CUSTOMER_NAME"));
			
			invoice.setPersonId(rs.getLong("PERSON_ID"));
			invoice.setPersonCode(rs.getString("PERSON_CODE"));
			invoice.setPersonFirstName(rs.getString("PERSON_FIRST_NAME"));
			invoice.setPersonSecondName(rs.getString("PERSON_SECOND_NAME"));
		
			invoice.setCurrencyId(rs.getLong("CURRENCY_ID"));
			invoice.setCurrencyCode(rs.getString("CURRENCY_CODE"));
			invoice.setCurrencyName(rs.getString("CURRENCY_NAME"));
			invoice.setAmount(rs.getDouble("AMOUNT"));
			invoice.setVatAmount(rs.getDouble("VAT_AMOUNT"));
			invoice.setTotAmount(rs.getDouble("TOT_AMOUNT"));
			
			invoice.setNote(rs.getString("NOTE"));
			invoice.setCancelled(rs.getString("CANCELLED"));
			invoice.setStringNumber(rs.getString("STRING_NUMBER"));
			
			
			return invoice;
		}
	}

	
	@Override
	public Invoice get(long id) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String query="select a.ID,a.DATE,a.YEAR,a.NUMBER,a.COMPANY_ID,a.COMPANY_CODE,a.COMPANY_NAME,";
		query+="a.CUSTOMER_ID,a.CUSTOMER_CODE,a.CUSTOMER_NAME,";
		query+="a.PERSON_ID,a.PERSON_CODE,a.PERSON_FIRST_NAME,a.PERSON_SECOND_NAME,";
		query+="a.CURRENCY_ID,a.CURRENCY_CODE,a.CURRENCY_NAME,";
		query+="a.AMOUNT,a.VAT_AMOUNT,a.TOT_AMOUNT,";
		query+="a.NOTE,a.CANCELLED,a.STRING_NUMBER";
		query+=" from BSSV_INVOICE a";
		query+=" where ID=?";
				
		logger.info(query+"["+id+"]");
		Invoice ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },
					new RowMapperInvoice());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("InvoiceMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		
		return ret;
	}

	@Override
	public void remove(long id) throws CyBssException{
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		String cmd="delete from BSST_INV_INVOICE where INV_N_INVOICE_ID=? and INV_N_NUMBER IS NULL";
		logger.info(cmd+"["+id+"]");
				
		long rowUpdated=jdbcTemplate.update(cmd, new Object[]{
				id		
		});
		
		if (rowUpdated==0){
			throw new CyBssException("Invoice <"+id+"> not updated in remove().");
		}
	}

	@Override
	public void cancel(long id) throws CyBssException{
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		String cmd="update BSST_INV_INVOICE set INV_C_CANCELLED='Y' where INV_N_INVOICE_ID=?";
		logger.info(cmd+"["+id+"]");
				
		long rowUpdated=jdbcTemplate.update(cmd, new Object[]{
				id		
		});
		
		if (rowUpdated==0){
			throw new CyBssException("Invoice <"+id+"> not updated in cancel().");
		}
	}

	private int getInvoiceMaxNumber(int year){
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		int maxNumber=0;
		
		String query="select MAX(INV_N_NUMBER) from BSST_INV_INVOICE where INV_N_YEAR=?";
				
		logger.info(query+"["+year+"]");
		maxNumber=jdbcTemplate.queryForObject(query, new Object[] { year },new RowMapper<Integer>() {
			            @Override
			            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			            	int  ret=rs.getInt(1);
			                return ret;
			            }
			        });
		return maxNumber;
	}
	
	@Override
	public void lock(long id) throws CyBssException{
		// TODO Auto-generated method stub
		Invoice invoice=get(id);
		
		int invoiceMaxNumber=this.getInvoiceMaxNumber(invoice.getYear());
		invoiceMaxNumber++;
		
		String stringNumber=invoice.getYear()+"/"+invoiceMaxNumber;
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String cmd="update BSST_INV_INVOICE set INV_N_NUMBER=?,INV_S_NUMBER=?";
		cmd+=" where INV_N_INVOICE_ID=? and INV_N_NUMBER IS NULL";
		
		logger.info(cmd+"["+id+","+invoiceMaxNumber+","+stringNumber+"]");
				
		long rowUpdated=jdbcTemplate.update(cmd, new Object[]{
				invoiceMaxNumber,stringNumber,id		
		});
		
		if (rowUpdated==0){
			throw new CyBssException("Invoice <"+id+"> not updated in lock().");
		}
	}
	
	@Override
	public void unlock(long id) throws CyBssException{
		// TODO Auto-generated method stub
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String cmd="update BSST_INV_INVOICE set INV_N_NUMBER=NULL,INV_S_NUMBER=NULL";
		cmd+=" where INV_N_INVOICE_ID=?";
		
		logger.info(cmd+"["+id+"]");
				
		long rowUpdated=jdbcTemplate.update(cmd, new Object[]{
				id		
		});
		
		if (rowUpdated==0){
			throw new CyBssException("Invoice <"+id+"> not updated in unlock().");
		}
		
	}

	@Override
	public void update(Invoice invoice) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("InvoiceMysql.update() >>>");
		
		String cmd="update BSST_INV_INVOICE set INV_N_AMOUNT=?,INV_N_VAT_AMOUNT=?,INV_N_TOT_AMOUNT=?,";
		cmd+="INV_D_DATE=?,INV_N_YEAR=?,INV_S_NOTE=?";
		cmd+=" where INV_N_INVOICE_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+invoice+"]");
	
		try {
			jdbcTemplate.update(cmd, new Object[]{
					invoice.getAmount(),
					invoice.getVatAmount(),
					invoice.getTotAmount(),
					CyBssUtility.tryStringToDate(invoice.getDate()),
					CyBssUtility.getYear(CyBssUtility.tryStringToDate(invoice.getDate())),
					(invoice.getNote()==null || invoice.getNote().equals(""))?null:invoice.getNote(),
					invoice.getId()
				});
		} catch (DataAccessException | ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("InvoiceMysql.update() <<<");
	}

	@Override
	public void updateNumber(long id, int invoiceNumber) throws CyBssException {
		// TODO Auto-generated method stub
		Invoice invoice=this.get(id);
		
		String stringNumber=invoice.getYear()+"/"+invoiceNumber;
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		String cmd="update BSST_INV_INVOICE set INV_N_NUMBER=?,INV_S_NUMBER=?";
		cmd+=" where INV_N_INVOICE_ID=? AND INV_N_NUMBER IS NOT NULL";
		
		logger.info(cmd+"["+id+","+invoiceNumber+","+stringNumber+"]");
		
		long rowUpdated=jdbcTemplate.update(cmd, new Object[]{
				invoiceNumber,stringNumber,id		
		});
		
		if (rowUpdated==0){
			throw new CyBssException("Invoice <"+id+"> not updated in updateNumber().");
		}
	}
	
		
	
		
}

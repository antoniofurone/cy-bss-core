package org.cysoft.bss.core.dao.mysql;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.dao.PassiveInvoiceDao;
import org.cysoft.bss.core.model.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class PassiveInvoiceMysql extends CyBssMysqlDao
	implements PassiveInvoiceDao{

	private static final Logger logger = LoggerFactory.getLogger(PassiveInvoiceMysql.class);

	@Override
	public long add(Invoice invoice) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("PassiveInvoiceMysql.add() >>>");

		String cmd="insert into BSST_PIN_PASSIVE_INVOICE(PIN_D_DATE,PIN_N_YEAR,";
		cmd+="MNC_N_COMPANY_ID,COM_N_COMPANY_ID,PER_N_PERSON_ID,CUR_N_CURRENCY_ID,";
		cmd+="PIN_S_NOTE) ";
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
		
		logger.info("PassiveInvoiceMysql.add() <<<");
		return getLastInsertId(jdbcTemplate);
	}

	@Override
	public List<Invoice> find(long companyId, long supplierId, String supplierCode, String supplierName, long personId,
			String personCode, String personName,  String fromDate, String toDate)
					throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("PassiveInvoiceMysql.find() >>> companyId="+companyId
				+";supplierId="+supplierId+";supplierCode="+supplierCode+";supplierName="+supplierName
				+";personId="+personId+";personCode="+personCode+";personName="+personName
				+";fromDate="+fromDate+";toDate="+toDate
				);
		
		
		String query="select a.ID,a.DATE,a.YEAR,a.NUMBER,a.COMPANY_ID,a.COMPANY_CODE,a.COMPANY_NAME,";
		query+="a.SUPPLIER_ID,a.SUPPLIER_CODE,a.SUPPLIER_NAME,";
		query+="a.PERSON_ID,a.PERSON_CODE,a.PERSON_FIRST_NAME,a.PERSON_SECOND_NAME,";
		query+="a.CURRENCY_ID,a.CURRENCY_CODE,a.CURRENCY_NAME,";
		query+="a.AMOUNT,a.VAT_AMOUNT,a.TOT_AMOUNT,";
		query+="a.NOTE,a.CANCELLED,a.STRING_NUMBER";
		query+=" from BSSV_PASSIVE_INVOICE a";
		if (companyId!=0 || 
			supplierId!=0 || !supplierCode.equals("") || !supplierName.equals("") ||
			personId!=0 || !personCode.equals("") || !personName.equals("") ||
			!fromDate.equals("") || !toDate.equals(""))
		query+=" WHERE ";
		
		boolean insAnd=false;
		List<Object> parms=new ArrayList<Object>();
		if (companyId!=0){
			query+=(insAnd?" AND":"")+" a.COMPANY_ID=?";
			insAnd=true;
			parms.add(companyId);
		}
		if (supplierId!=0){
			query+=(insAnd?" AND":"")+" a.SUPPLIER_ID=?";
			insAnd=true;
			parms.add(supplierId);
		}
		if (!supplierCode.equals("")){
			if (!supplierCode.contains("%"))
				query+=(insAnd?" AND":"")+" a.SUPPLIER_CODE=?";
			else
				query+=(insAnd?" AND":"")+" a.SUPPLIER_CODE like ?";
			insAnd=true;
			parms.add(supplierCode);
		}
		if (!supplierName.equals("")){
			if (!supplierName.contains("%"))
				query+=(insAnd?" AND":"")+" a.SUPPLIER_NAME=?";
			else
				query+=(insAnd?" AND":"")+" a.SUPPLIER_NAME like ?";
			insAnd=true;
			parms.add(supplierName);
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
		logger.info(query+"[companyId="+companyId
				+";supplierId="+supplierId+";supplierCode="+supplierCode+";supplierName="+supplierName
				+";personId="+personId+";personCode="+personCode+";personName="+personName
				+";fromDate="+fromDate+";toDate="+toDate
				+"]");
		
		List<Invoice> ret=jdbcTemplate.query(query, parms.toArray(),new RowMapperInvoice());
		
		logger.info("PassiveInvoiceMysql.find() <<<");
		
		return ret;
	}
	
	private class RowMapperInvoice implements RowMapper<Invoice>{

		@Override
		public Invoice mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			Invoice invoice=new Invoice();
		
			invoice.setId(rs.getLong("ID"));
			invoice.setDate(rs.getString("DATE"));
			invoice.setYear(rs.getInt("YEAR"));
			invoice.setNumber(rs.getInt("NUMBER"));
			
			invoice.setCompanyId(rs.getLong("COMPANY_ID"));
			invoice.setCompanyCode(rs.getString("COMPANY_CODE"));
			invoice.setCompanyName(rs.getString("COMPANY_NAME"));
			
			invoice.setTpCompanyId(rs.getLong("SUPPLIER_ID"));
			invoice.setTpCompanyCode(rs.getString("SUPPLIER_CODE"));
			invoice.setTpCompanyName(rs.getString("SUPPLIER_NAME"));
			
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
			
			return invoice;
		}
	}
	
}

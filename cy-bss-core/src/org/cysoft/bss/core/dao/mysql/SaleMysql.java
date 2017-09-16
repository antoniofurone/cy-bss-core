package org.cysoft.bss.core.dao.mysql;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.dao.SaleDao;
import org.cysoft.bss.core.model.Sale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class SaleMysql extends CyBssMysqlDao
	implements SaleDao{

	private static final Logger logger = LoggerFactory.getLogger(SaleMysql.class);
	
	@Override
	public long add(Sale sale) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("SaleMysql.add() >>>");
		
		
		String cmd="insert into BSST_SAL_SALE(MNC_N_COMPANY_ID,PRO_N_PRODUCT_ID,COM_N_COMPANY_ID,";
		cmd+="PER_N_PERSON_ID,SAL_N_QUANTITY,MES_N_METRIC_SCALE_ID,SAL_N_PRICE,SAL_N_AMOUNT,CUR_N_CURRENCY_ID,";
		cmd+="SAL_N_VAT,SAL_N_VAT_AMOUNT,SAL_D_DATE_START,SAL_D_DATE_END,PRC_N_PRICE_COMPONENT_ID,";
		cmd+="SAL_N_FREQUENCY,SAL_D_DATE,SAL_C_TACIT_RENEWAL,SAL_C_TYPE)";
		cmd+=" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+sale+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					sale.getCompanyId(),
					sale.getProductId(),
					sale.getCustomerId()==0?null:sale.getCustomerId(),
					sale.getPersonId()==0?null:sale.getPersonId(),
					sale.getQty()==0?null:sale.getQty(),
					sale.getQtyUmId()==0?null:sale.getQtyUmId(),
					sale.getPrice(),
					sale.getAmount(),
					sale.getCurrencyId(),
					sale.getVat(),
					sale.getVatAmount(),
					(sale.getDateStart()==null || sale.getDateStart().equals(""))?null:CyBssUtility.tryStringToDate(sale.getDateStart()),
					(sale.getDateEnd()==null || sale.getDateEnd().equals(""))?null:CyBssUtility.tryStringToDate(sale.getDateEnd()),
					sale.getComponentId(),
					sale.getFrequencyId()==0?null:sale.getFrequencyId(),
					CyBssUtility.tryStringToDate(sale.getDate()),
					sale.getTacitRenewal(),
					sale.getTransactionType()
				});
		} catch (DataAccessException | ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("SaleMysql.add() <<<");
		
		return getLastInsertId(jdbcTemplate);
	}

	
	@Override
	public List<Sale> find(long companyId, long productId, String productName, long customerId, String customerCode,
			String customerName, long personId, String personCode, String personName, String attrName, String attrValue,
			String fromDate, String toDate) throws CyBssException {
		// TODO Auto-generated method stub
		
		logger.info("SaleMysql.find() >>> companyId="+companyId+";productId="+productId+";productName="+productName
				+";customerId="+customerId+";customerCode="+customerCode+";customerName="+customerName
				+";personId="+personId+";personCode="+personCode+";personName="+personName
				+";fromDate="+fromDate+";toDate="+toDate
				+";attrName="+attrName+";attrValue="+attrValue);
		
		String query="select a.ID,a.COMPANY_ID,a.COMPANY_CODE,a.COMPANY_NAME,";
		query+="a.PRODUCT_ID,a.PRODUCT_NAME,";
		query+="a.CUSTOMER_ID,a.CUSTOMER_CODE,a.CUSTOMER_NAME,";
		query+="a.PERSON_ID,a.PERSON_CODE,a.PERSON_FIRST_NAME,a.PERSON_SECOND_NAME,";
		query+="a.COMPONENT_ID,a.COMPONENT_CODE,a.COMPONENT_NAME,";
		query+="a.COMPONENT_TYPE_CODE,a.COMPONENT_TYPE_NAME,";
		query+="a.QTY_UM_ID,a.QTY_UM_SIMBOL,a.QTY,";
		query+="a.FREQUENCY_ID,a.FREQUENCY_NAME,";
		query+="a.CURRENCY_ID,a.CURRENCY_CODE,a.CURRENCY_NAME,";
		query+="a.PRICE, a.AMOUNT,a.VAT,a.VAT_AMOUNT,";
		query+="a.DATE,a.DATE_START,a.DATE_END,";
		query+="a.TACIT_RENEWAL,a.TYPE,a.UPDATE_DATE,a.NOBILLED";
		query+=" from BSSV_SALE a";
		if (attrName!=null && !attrName.equals("")){
			query+=" join BSSV_ATTRIBUTE_VALUE b on b.OBJINST_ID=a.ID and b.NAME='"+attrName+"' and b.ENTITY='"+Sale.ENTITY_NAME+"'";
		}
		if (companyId!=0 || productId!=0 || !productName.equals("") || 
			customerId!=0 || !customerCode.equals("") || !customerName.equals("") ||
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
		if (productId!=0){
			query+=(insAnd?" AND":"")+" a.PRODUCT_ID=?";
			insAnd=true;
			parms.add(productId);
		}
		if (!productName.equals("")){
			if (!productName.contains("%"))
				query+=(insAnd?" AND":"")+" a.PRODUCT_NAME=?";
			else
				query+=(insAnd?" AND":"")+" a.PRODUCT_NAME like ?";
			insAnd=true;
			parms.add(productName);
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
		logger.info(query+"[companyId="+companyId+";productId="+productId+";productName="+productName
				+";customerId="+customerId+";customerCode="+customerCode+";customerName="+customerName
				+";personId="+personId+";personCode="+personCode+";personName="+personName
				+";fromDate="+fromDate+";toDate="+toDate
				+";attrName="+attrName+";attrValue="+attrValue+"]");
		
		List<Sale> ret=jdbcTemplate.query(query, parms.toArray(),new RowMapperSale());
		
		logger.info("SaleMysql.find() <<<");
		
		
		return ret;
	}
	
	private class RowMapperSale implements RowMapper<Sale>{

		@Override
		public Sale mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			Sale sale=new Sale();
			
			sale.setId(rs.getLong("ID"));
			sale.setCompanyId(rs.getLong("COMPANY_ID"));
			sale.setCompanyCode(rs.getString("COMPANY_CODE"));
			sale.setCompanyName(rs.getString("COMPANY_NAME"));
			sale.setProductId(rs.getLong("PRODUCT_ID"));
			sale.setProductName(rs.getString("PRODUCT_NAME"));
			sale.setCustomerId(rs.getLong("CUSTOMER_ID"));
			sale.setCustomerCode(rs.getString("CUSTOMER_CODE"));
			sale.setCustomerName(rs.getString("CUSTOMER_NAME"));
			sale.setPersonId(rs.getLong("PERSON_ID"));
			sale.setPersonCode(rs.getString("PERSON_CODE"));
			sale.setPersonFirstName(rs.getString("PERSON_FIRST_NAME"));
			sale.setPersonSecondName(rs.getString("PERSON_SECOND_NAME"));
			sale.setComponentId(rs.getLong("COMPONENT_ID"));
			sale.setComponentCode(rs.getString("COMPONENT_CODE"));
			sale.setComponentName(rs.getString("COMPONENT_NAME"));
			sale.setComponentTypeCode(rs.getString("COMPONENT_TYPE_CODE"));
			sale.setComponentTypeName(rs.getString("COMPONENT_TYPE_NAME"));
			sale.setQtyUmId(rs.getLong("QTY_UM_ID"));
			sale.setQtyUmSimbol(rs.getString("QTY_UM_SIMBOL"));
			sale.setQty(rs.getDouble("QTY"));
			sale.setFrequencyId(rs.getLong("FREQUENCY_ID"));
			sale.setFrequencyName(rs.getString("FREQUENCY_NAME"));
			sale.setCurrencyId(rs.getLong("CURRENCY_ID"));
			sale.setCurrencyCode(rs.getString("CURRENCY_CODE"));
			sale.setCurrencyName(rs.getString("CURRENCY_NAME"));
			sale.setPrice(rs.getDouble("PRICE"));
			sale.setAmount(rs.getDouble("AMOUNT"));
			sale.setVat(rs.getDouble("VAT"));
			sale.setVatAmount(rs.getDouble("VAT_AMOUNT"));
			sale.setDate(rs.getString("DATE"));
			sale.setDateStart(rs.getString("DATE_START"));
			sale.setDateEnd(rs.getString("DATE_END"));
			sale.setTacitRenewal(rs.getString("TACIT_RENEWAL"));
			sale.setTransactionType(rs.getString("TYPE"));
			sale.setUpdateDate(rs.getString("UPDATE_DATE"));
			sale.setNoBilled(rs.getInt("NOBILLED"));
			sale.setPriceTot(sale.getPrice()+sale.getPrice()*sale.getVat()/100);
			return sale;
		}
	}

	
	@Override
	public void update(long id, Sale sale) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("SaleMysql.update() >>>");
		
		String cmd="update BSST_SAL_SALE set MNC_N_COMPANY_ID=?,PRO_N_PRODUCT_ID=?,COM_N_COMPANY_ID=?,";
		cmd+="PER_N_PERSON_ID=?,SAL_N_QUANTITY=?,MES_N_METRIC_SCALE_ID=?,SAL_N_PRICE=?,SAL_N_AMOUNT=?,CUR_N_CURRENCY_ID=?,";
		cmd+="SAL_N_VAT=?,SAL_N_VAT_AMOUNT=?,SAL_D_DATE_START=?,SAL_D_DATE_END=?,PRC_N_PRICE_COMPONENT_ID=?,";
		cmd+="SAL_N_FREQUENCY=?,SAL_D_DATE=?,SAL_C_TACIT_RENEWAL=?,SAL_C_TYPE=? ";
		cmd+="where SAL_N_SALE_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+sale+"]");
	
		try {
			jdbcTemplate.update(cmd, new Object[]{
					sale.getCompanyId(),
					sale.getProductId(),
					sale.getCustomerId()==0?null:sale.getCustomerId(),
					sale.getPersonId()==0?null:sale.getPersonId(),
					sale.getQty()==0?null:sale.getQty(),
					sale.getQtyUmId()==0?null:sale.getQtyUmId(),
					sale.getPrice(),
					sale.getAmount(),
					sale.getCurrencyId(),
					sale.getVat(),
					sale.getVatAmount(),
					(sale.getDateStart()==null || sale.getDateStart().equals(""))?null:CyBssUtility.tryStringToDate(sale.getDateStart()),
					(sale.getDateEnd()==null || sale.getDateEnd().equals(""))?null:CyBssUtility.tryStringToDate(sale.getDateEnd()),
					sale.getComponentId(),
					sale.getFrequencyId()==0?null:sale.getFrequencyId(),
					CyBssUtility.tryStringToDate(sale.getDate()),
					sale.getTacitRenewal(),
					sale.getTransactionType(),
					id
				});
		} catch (DataAccessException | ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("SaleMysql.update() <<<");
	}
	
	
	@Override
	public Sale get(long id) {
		// TODO Auto-generated method stub
		logger.info("SaleMysql.get() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String query="select a.ID,a.COMPANY_ID,a.COMPANY_CODE,a.COMPANY_NAME,";
		query+="a.PRODUCT_ID,a.PRODUCT_NAME,";
		query+="a.CUSTOMER_ID,a.CUSTOMER_CODE,a.CUSTOMER_NAME,";
		query+="a.PERSON_ID,a.PERSON_CODE,a.PERSON_FIRST_NAME,a.PERSON_SECOND_NAME,";
		query+="a.COMPONENT_ID,a.COMPONENT_CODE,a.COMPONENT_NAME,";
		query+="a.COMPONENT_TYPE_CODE,a.COMPONENT_TYPE_NAME,";
		query+="a.QTY_UM_ID,a.QTY_UM_SIMBOL,a.QTY,";
		query+="a.FREQUENCY_ID,a.FREQUENCY_NAME,";
		query+="a.CURRENCY_ID,a.CURRENCY_CODE,a.CURRENCY_NAME,";
		query+="a.PRICE, a.AMOUNT,a.VAT,a.VAT_AMOUNT,";
		query+="a.DATE,a.DATE_START,a.DATE_END,";
		query+="a.TACIT_RENEWAL,a.TYPE,a.UPDATE_DATE,a.NOBILLED";
		query+=" from BSSV_SALE a ";
		query+="where ID=?";
		
		logger.info(query+"["+id+"]");
		Sale ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },
					new RowMapperSale());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("SaleMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		logger.info("SaleMysql.get() <<<");
		return ret;

	}

	@Override
	public void remove(final long id) {
		// TODO Auto-generated method stub
		logger.info("SaleMysql.remove() >>>");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		String cmd="delete from BSST_SAL_SALE where SAL_N_SALE_ID=?";
		logger.info(cmd+"["+id+"]");
		jdbcTemplate.update(cmd, new Object[]{id});
		
		logger.info("SaleMysql.remove() <<<");
		
	}
	
}

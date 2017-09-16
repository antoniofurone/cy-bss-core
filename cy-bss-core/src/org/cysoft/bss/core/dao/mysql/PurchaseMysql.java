package org.cysoft.bss.core.dao.mysql;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.dao.PurchaseDao;
import org.cysoft.bss.core.model.Purchase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class PurchaseMysql extends CyBssMysqlDao
	implements PurchaseDao{

	private static final Logger logger = LoggerFactory.getLogger(PurchaseMysql.class);
	
	@Override
	public long add(Purchase purchase) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("PurchaseMysql.add() >>>");
		
		
		String cmd="insert into BSST_PUR_PURCHASE(MNC_N_COMPANY_ID,PRO_N_PRODUCT_ID,COM_N_COMPANY_ID,";
		cmd+="PER_N_PERSON_ID,PUR_N_QUANTITY,MES_N_METRIC_SCALE_ID,PUR_N_PRICE,PUR_N_AMOUNT,CUR_N_CURRENCY_ID,";
		cmd+="PUR_N_VAT,PUR_N_VAT_AMOUNT,PUR_D_DATE_START,PUR_D_DATE_END,PRC_N_PRICE_COMPONENT_ID,";
		cmd+="PUR_N_FREQUENCY,PUR_D_DATE,PUR_C_TACIT_RENEWAL,PUR_C_TYPE)";
		cmd+=" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+purchase+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					purchase.getCompanyId(),
					purchase.getProductId(),
					purchase.getSupplierId()==0?null:purchase.getSupplierId(),
					purchase.getPersonId()==0?null:purchase.getPersonId(),
					purchase.getQty()==0?null:purchase.getQty(),
					purchase.getQtyUmId()==0?null:purchase.getQtyUmId(),
					purchase.getPrice(),
					purchase.getAmount(),
					purchase.getCurrencyId(),
					purchase.getVat(),
					purchase.getVatAmount(),
					(purchase.getDateStart()==null || purchase.getDateStart().equals(""))?null:CyBssUtility.tryStringToDate(purchase.getDateStart()),
					(purchase.getDateEnd()==null || purchase.getDateEnd().equals(""))?null:CyBssUtility.tryStringToDate(purchase.getDateEnd()),
					purchase.getComponentId(),
					purchase.getFrequencyId()==0?null:purchase.getFrequencyId(),
					CyBssUtility.tryStringToDate(purchase.getDate()),
					purchase.getTacitRenewal(),
					purchase.getTransactionType()
				});
		} catch (DataAccessException | ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("PurchaseMysql.add() <<<");
		
		return getLastInsertId(jdbcTemplate);
	}

	@Override
	public List<Purchase> find(long companyId, long productId, String productName, long supplierId, String supplierCode,
			String supplierName, long personId, String personCode, String personName, String attrName, String attrValue,
			String fromDate, String toDate) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("PurchaseMysql.find() >>> companyId="+companyId+";productId="+productId+";productName="+productName
				+";supplierId="+supplierId+";supplierCode="+supplierCode+";supplierName="+supplierName
				+";personId="+personId+";personCode="+personCode+";personName="+personName
				+";fromDate="+fromDate+";toDate="+toDate
				+";attrName="+attrName+";attrValue="+attrValue);
		
		String query="select a.ID,a.COMPANY_ID,a.COMPANY_CODE,a.COMPANY_NAME,";
		query+="a.PRODUCT_ID,a.PRODUCT_NAME,";
		query+="a.SUPPLIER_ID,a.SUPPLIER_CODE,a.SUPPLIER_NAME,";
		query+="a.PERSON_ID,a.PERSON_CODE,a.PERSON_FIRST_NAME,a.PERSON_SECOND_NAME,";
		query+="a.COMPONENT_ID,a.COMPONENT_CODE,a.COMPONENT_NAME,";
		query+="a.COMPONENT_TYPE_CODE,a.COMPONENT_TYPE_NAME,";
		query+="a.QTY_UM_ID,a.QTY_UM_SIMBOL,a.QTY,";
		query+="a.FREQUENCY_ID,a.FREQUENCY_NAME,";
		query+="a.CURRENCY_ID,a.CURRENCY_CODE,a.CURRENCY_NAME,";
		query+="a.PRICE, a.AMOUNT,a.VAT,a.VAT_AMOUNT,";
		query+="a.DATE,a.DATE_START,a.DATE_END,";
		query+="a.TACIT_RENEWAL,a.TYPE,a.UPDATE_DATE,a.NOBILLED";
		query+=" from BSSV_PURCHASE a";
		if (attrName!=null && !attrName.equals("")){
			query+=" join BSSV_ATTRIBUTE_VALUE b on b.OBJINST_ID=a.ID and b.NAME='"+attrName+"' and b.ENTITY='"+Purchase.ENTITY_NAME+"'";
		}
		if (companyId!=0 || productId!=0 || !productName.equals("") || 
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
		logger.info(query+"[companyId="+companyId+";productId="+productId+";productName="+productName
				+";supplierId="+supplierId+";supplierCode="+supplierCode+";supplierName="+supplierName
				+";personId="+personId+";personCode="+personCode+";personName="+personName
				+";fromDate="+fromDate+";toDate="+toDate
				+";attrName="+attrName+";attrValue="+attrValue+"]");
		
		List<Purchase> ret=jdbcTemplate.query(query, parms.toArray(),new RowMapperPurchase());
		
		logger.info("PurchaseMysql.find() <<<");
		
		
		return ret;
	}
	
	private class RowMapperPurchase implements RowMapper<Purchase>{

		@Override
		public Purchase mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			Purchase purchase=new Purchase();
			
			purchase.setId(rs.getLong("ID"));
			purchase.setCompanyId(rs.getLong("COMPANY_ID"));
			purchase.setCompanyCode(rs.getString("COMPANY_CODE"));
			purchase.setCompanyName(rs.getString("COMPANY_NAME"));
			purchase.setProductId(rs.getLong("PRODUCT_ID"));
			purchase.setProductName(rs.getString("PRODUCT_NAME"));
			purchase.setSupplierId(rs.getLong("SUPPLIER_ID"));
			purchase.setSupplierCode(rs.getString("SUPPLIER_CODE"));
			purchase.setSupplierName(rs.getString("SUPPLIER_NAME"));
			purchase.setPersonId(rs.getLong("PERSON_ID"));
			purchase.setPersonCode(rs.getString("PERSON_CODE"));
			purchase.setPersonFirstName(rs.getString("PERSON_FIRST_NAME"));
			purchase.setPersonSecondName(rs.getString("PERSON_SECOND_NAME"));
			purchase.setComponentId(rs.getLong("COMPONENT_ID"));
			purchase.setComponentCode(rs.getString("COMPONENT_CODE"));
			purchase.setComponentName(rs.getString("COMPONENT_NAME"));
			purchase.setComponentTypeCode(rs.getString("COMPONENT_TYPE_CODE"));
			purchase.setComponentTypeName(rs.getString("COMPONENT_TYPE_NAME"));
			purchase.setQtyUmId(rs.getLong("QTY_UM_ID"));
			purchase.setQtyUmSimbol(rs.getString("QTY_UM_SIMBOL"));
			purchase.setQty(rs.getDouble("QTY"));
			purchase.setFrequencyId(rs.getLong("FREQUENCY_ID"));
			purchase.setFrequencyName(rs.getString("FREQUENCY_NAME"));
			purchase.setCurrencyId(rs.getLong("CURRENCY_ID"));
			purchase.setCurrencyCode(rs.getString("CURRENCY_CODE"));
			purchase.setCurrencyName(rs.getString("CURRENCY_NAME"));
			purchase.setPrice(rs.getDouble("PRICE"));
			purchase.setAmount(rs.getDouble("AMOUNT"));
			purchase.setVat(rs.getDouble("VAT"));
			purchase.setVatAmount(rs.getDouble("VAT_AMOUNT"));
			purchase.setDate(rs.getString("DATE"));
			purchase.setDateStart(rs.getString("DATE_START"));
			purchase.setDateEnd(rs.getString("DATE_END"));
			purchase.setTacitRenewal(rs.getString("TACIT_RENEWAL"));
			purchase.setTransactionType(rs.getString("TYPE"));
			purchase.setUpdateDate(rs.getString("UPDATE_DATE"));
			purchase.setNoBilled(rs.getInt("NOBILLED"));
			purchase.setPriceTot(purchase.getPrice()+purchase.getPrice()*purchase.getVat()/100);
			return purchase;
		}
	}

	@Override
	public void update(long id, Purchase purchase) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("PurchaseMysql.update() >>>");
		
		String cmd="update BSST_PUR_PURCHASE set MNC_N_COMPANY_ID=?,PRO_N_PRODUCT_ID=?,COM_N_COMPANY_ID=?,";
		cmd+="PER_N_PERSON_ID=?,PUR_N_QUANTITY=?,MES_N_METRIC_SCALE_ID=?,PUR_N_PRICE=?,PUR_N_AMOUNT=?,CUR_N_CURRENCY_ID=?,";
		cmd+="PUR_N_VAT=?,PUR_N_VAT_AMOUNT=?,PUR_D_DATE_START=?,PUR_D_DATE_END=?,PRC_N_PRICE_COMPONENT_ID=?,";
		cmd+="PUR_N_FREQUENCY=?,PUR_D_DATE=?,PUR_C_TACIT_RENEWAL=?,PUR_C_TYPE=? ";
		cmd+="where PUR_N_PURCHASE_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+purchase+"]");
	
		try {
			jdbcTemplate.update(cmd, new Object[]{
					purchase.getCompanyId(),
					purchase.getProductId(),
					purchase.getSupplierId()==0?null:purchase.getSupplierId(),
					purchase.getPersonId()==0?null:purchase.getPersonId(),
					purchase.getQty()==0?null:purchase.getQty(),
					purchase.getQtyUmId()==0?null:purchase.getQtyUmId(),
					purchase.getPrice(),
					purchase.getAmount(),
					purchase.getCurrencyId(),
					purchase.getVat(),
					purchase.getVatAmount(),
					(purchase.getDateStart()==null || purchase.getDateStart().equals(""))?null:CyBssUtility.tryStringToDate(purchase.getDateStart()),
					(purchase.getDateEnd()==null || purchase.getDateEnd().equals(""))?null:CyBssUtility.tryStringToDate(purchase.getDateEnd()),
					purchase.getComponentId(),
					purchase.getFrequencyId()==0?null:purchase.getFrequencyId(),
					CyBssUtility.tryStringToDate(purchase.getDate()),
					purchase.getTacitRenewal(),
					purchase.getTransactionType(),
					id
				});
		} catch (DataAccessException | ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("PurchaseMysql.update() <<<");
	}

	@Override
	public Purchase get(long id) {
		// TODO Auto-generated method stub
		logger.info("PurchaseMysql.get() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String query="select a.ID,a.COMPANY_ID,a.COMPANY_CODE,a.COMPANY_NAME,";
		query+="a.PRODUCT_ID,a.PRODUCT_NAME,";
		query+="a.SUPPLIER_ID,a.SUPPLIER_CODE,a.SUPPLIER_NAME,";
		query+="a.PERSON_ID,a.PERSON_CODE,a.PERSON_FIRST_NAME,a.PERSON_SECOND_NAME,";
		query+="a.COMPONENT_ID,a.COMPONENT_CODE,a.COMPONENT_NAME,";
		query+="a.COMPONENT_TYPE_CODE,a.COMPONENT_TYPE_NAME,";
		query+="a.QTY_UM_ID,a.QTY_UM_SIMBOL,a.QTY,";
		query+="a.FREQUENCY_ID,a.FREQUENCY_NAME,";
		query+="a.CURRENCY_ID,a.CURRENCY_CODE,a.CURRENCY_NAME,";
		query+="a.PRICE, a.AMOUNT,a.VAT,a.VAT_AMOUNT,";
		query+="a.DATE,a.DATE_START,a.DATE_END,";
		query+="a.TACIT_RENEWAL,a.TYPE,a.UPDATE_DATE,a.NOBILLED";
		query+=" from BSSV_PURCHASE a ";
		query+="where ID=?";
		
		logger.info(query+"["+id+"]");
		Purchase ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },
					new RowMapperPurchase());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("PurchaseMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		logger.info("PurchaseMysql.get() <<<");
		return ret;

	}

	@Override
	public void remove(final long id) {
		// TODO Auto-generated method stub
		logger.info("PurchaseMysql.remove() >>>");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		String cmd="delete from BSST_PUR_PURCHASE where PUR_N_PURCHASE_ID=?";
		logger.info(cmd+"["+id+"]");
		jdbcTemplate.update(cmd, new Object[]{id});
		logger.info("PurchaseMysql.remove() <<<");
		
	}
	
}

package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.dao.BillableDao;
import org.cysoft.bss.core.model.Billable;
import org.cysoft.bss.core.model.BillableCost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class BillableCostMysql extends CyBssMysqlDao
	implements BillableDao{

	private static final Logger logger = LoggerFactory.getLogger(BillableCostMysql.class);

	@Override
	public long add(Billable billable) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("BillableCostMysql.add() >>>");
		
		String cmd="insert into BSST_BIC_BILLABLE_COST(PUR_N_PURCHASE_ID,MNC_N_COMPANY_ID,PRO_N_PRODUCT_ID,COM_N_COMPANY_ID,";
		cmd+="PER_N_PERSON_ID,BIC_N_QUANTITY,MES_N_METRIC_SCALE_ID,BIC_N_PRICE,BIC_N_AMOUNT,CUR_N_CURRENCY_ID,";
		cmd+="BIC_N_VAT,BIC_N_VAT_AMOUNT,BIC_N_TOT_AMOUNT,BIC_D_DATE_START,BIC_D_DATE_END,PRC_N_PRICE_COMPONENT_ID,";
		cmd+="BIC_D_DATE,BIC_C_TYPE)";
		cmd+=" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+billable+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					billable.getParentId(),
					billable.getCompanyId(),
					billable.getProductId(),
					billable.getTpCompanyId()==0?null:billable.getTpCompanyId(),
					billable.getPersonId()==0?null:billable.getPersonId(),
					billable.getQty()==0?null:billable.getQty(),
					billable.getQtyUmId()==0?null:billable.getQtyUmId(),
					billable.getPrice(),
					billable.getAmount(),
					billable.getCurrencyId(),
					billable.getVat(),
					billable.getVatAmount(),
					billable.getTotAmount(),
					(billable.getDateStart()==null || billable.getDateStart().equals(""))?null:CyBssUtility.tryStringToDate(billable.getDateStart()),
					(billable.getDateEnd()==null || billable.getDateEnd().equals(""))?null:CyBssUtility.tryStringToDate(billable.getDateEnd()),
					billable.getComponentId(),
					CyBssUtility.tryStringToDate(billable.getDate()),
					billable.getBillableType()
				});
		} catch (DataAccessException | ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("BillableCostMysql.add() <<<");
		
		return getLastInsertId(jdbcTemplate);

	}
	
	@Override
	public void removeByParent(long purchaseId) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		String cmd="delete from BSST_BIC_BILLABLE_COST where PUR_N_PURCHASE_ID=? and BIC_C_BILLED='N'";
		logger.info(cmd+"["+purchaseId+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
				purchaseId		
		});
	}

	
	@Override
	public List<Billable> getByInvoice(long invoiceId) {
		// TODO Auto-generated method stub
	
		String query="select ID,PURCHASE_ID,INVOICE_ID,";
		query+="COMPANY_ID,COMPANY_CODE,COMPANY_NAME,";
		query+="PRODUCT_ID,PRODUCT_NAME,";
		query+="SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,";
		query+="PERSON_ID,PERSON_CODE,PERSON_FIRST_NAME,PERSON_SECOND_NAME,";
		query+="COMPONENT_ID,COMPONENT_CODE,COMPONENT_NAME,";
		query+="COMPONENT_TYPE_CODE,COMPONENT_TYPE_NAME,";
		query+="QTY_UM_ID,QTY_UM_SIMBOL,QTY,";
		query+="CURRENCY_ID,CURRENCY_CODE,CURRENCY_NAME,";
		query+="PRICE, AMOUNT,VAT,VAT_AMOUNT,TOT_AMOUNT,";
		query+="DATE,DATE_START,DATE_END,TYPE,BILLED,UPDATE_DATE";
		
		query+=" from BSSV_BILLABLE_COST";
		query+=" where INVOICE_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"["+invoiceId+"]");
		List<Billable> ret=jdbcTemplate.query(query, new Object[]{invoiceId},new RowMapperBillableCost());
				
		return ret;
	}

	private class RowMapperBillableCost implements RowMapper<Billable>{

		@Override
		public Billable mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			BillableCost billable=new BillableCost();
		
			billable.setId(rs.getLong("ID"));
			billable.setParentId(rs.getLong("PURCHASE_ID"));
			billable.setInvoiceId(rs.getLong("INVOICE_ID"));
			
			billable.setCompanyId(rs.getLong("COMPANY_ID"));
			billable.setCompanyCode(rs.getString("COMPANY_CODE"));
			billable.setCompanyName(rs.getString("COMPANY_NAME"));
			
			billable.setProductId(rs.getLong("PRODUCT_ID"));
			billable.setProductName(rs.getString("PRODUCT_NAME"));
			
			billable.setSupplierId(rs.getLong("SUPPLIER_ID"));
			billable.setSupplierCode(rs.getString("SUPPLIER_CODE"));
			billable.setSupplierName(rs.getString("SUPPLIER_NAME"));
		
			billable.setPersonId(rs.getLong("PERSON_ID"));
			billable.setPersonCode(rs.getString("PERSON_CODE"));
			billable.setPersonFirstName(rs.getString("PERSON_FIRST_NAME"));
			billable.setPersonSecondName(rs.getString("PERSON_SECOND_NAME"));
			
			billable.setComponentId(rs.getLong("COMPONENT_ID"));
			billable.setComponentCode(rs.getString("COMPONENT_CODE"));
			billable.setComponentName(rs.getString("COMPONENT_NAME"));
			billable.setComponentTypeCode(rs.getString("COMPONENT_TYPE_CODE"));
			billable.setComponentTypeName(rs.getString("COMPONENT_TYPE_NAME"));
			billable.setQtyUmId(rs.getLong("QTY_UM_ID"));
			billable.setQtyUmSimbol(rs.getString("QTY_UM_SIMBOL"));
			billable.setQty(rs.getDouble("QTY"));
			
			billable.setCurrencyId(rs.getLong("CURRENCY_ID"));
			billable.setCurrencyCode(rs.getString("CURRENCY_CODE"));
			billable.setCurrencyName(rs.getString("CURRENCY_NAME"));
			
			billable.setPrice(rs.getDouble("PRICE"));
			billable.setAmount(rs.getDouble("AMOUNT"));
			billable.setVat(rs.getDouble("VAT"));
			billable.setVatAmount(rs.getDouble("VAT_AMOUNT"));
			billable.setTotAmount(rs.getDouble("TOT_AMOUNT"));
			
			billable.setDate(rs.getString("DATE"));
			billable.setDateStart(rs.getString("DATE_START"));
			billable.setDateEnd(rs.getString("DATE_END"));
			
			billable.setBillableType(rs.getString("TYPE"));
			billable.setBilled(rs.getString("BILLED"));
			billable.setUpdateDate(rs.getString("UPDATE_DATE"));
				
			return billable;
		}
	}

	@Override
	public void unbill(long invoiceId, boolean unlink) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String cmd="";
		if (unlink)
			cmd+="update BSST_BIC_BILLABLE_COST set PIN_N_INVOICE_ID=NULL,BIC_C_BILLED='N' where PIN_N_INVOICE_ID=?";
		else
			cmd+="update BSST_BIC_BILLABLE_COST set BIC_C_BILLED='N' where PIN_N_INVOICE_ID=?";

		logger.info(cmd+"["+invoiceId+"]");
				
		jdbcTemplate.update(cmd, new Object[]{
				invoiceId		
		});
	}

	@Override
	public void bill(long invoiceId) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		String cmd="update BSST_BIC_BILLABLE_COST set BIC_C_BILLED='Y' where PIN_N_INVOICE_ID=?";
		logger.info(cmd+"["+invoiceId+"]");
				
		jdbcTemplate.update(cmd, new Object[]{
				invoiceId		
		});
	}

	@Override
	public List<Billable> getNotLinked(long companyId, long tpCompanyId, long personId, long currencyId) {
		// TODO Auto-generated method stub
		logger.info("BillableCostMysql.getNotLinked() >>>");
		
		String query="select ID,PURCHASE_ID,INVOICE_ID,";
		query+="COMPANY_ID,COMPANY_CODE,COMPANY_NAME,";
		query+="PRODUCT_ID,PRODUCT_NAME,";
		query+="SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,";
		query+="PERSON_ID,PERSON_CODE,PERSON_FIRST_NAME,PERSON_SECOND_NAME,";
		query+="COMPONENT_ID,COMPONENT_CODE,COMPONENT_NAME,";
		query+="COMPONENT_TYPE_CODE,COMPONENT_TYPE_NAME,";
		query+="QTY_UM_ID,QTY_UM_SIMBOL,QTY,";
		query+="CURRENCY_ID,CURRENCY_CODE,CURRENCY_NAME,";
		query+="PRICE, AMOUNT,VAT,VAT_AMOUNT,TOT_AMOUNT,";
		query+="DATE,DATE_START,DATE_END,TYPE,BILLED,UPDATE_DATE";
		query+=" from BSSV_BILLABLE_COST";
		query+=" where INVOICE_ID IS NULL";
		
		List<Object> parms=new ArrayList<Object>();
		if (companyId!=0){
			query+=" AND COMPANY_ID=?";
			parms.add(companyId);
		}
		if (tpCompanyId!=0){
			query+=" AND SUPPLIER_ID=?";
			parms.add(tpCompanyId);
		}
		if (personId!=0){
			query+=" AND PERSON_ID=?";
			parms.add(personId);
		}
		if (currencyId!=0){
			query+=" AND CURRENCY_ID=?";
			parms.add(currencyId);
		}
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"[companyId="+companyId			
				+";supplierId="+tpCompanyId
				+";personId="+personId
				+";currencyId="+currencyId
				+"]");
		
		List<Billable> ret=jdbcTemplate.query(query, parms.toArray(),new RowMapperBillableCost());
		
		logger.info("BillableCostMysql.getNotLinked() <<<");
		return ret;
	}

	@Override
	public void link(long invoiceId, long billableId) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		String cmd="update BSST_BIC_BILLABLE_COST set PIN_N_INVOICE_ID=? where BIC_N_BILLABLE_ID=? and PIN_N_INVOICE_ID IS NULL and BIC_C_BILLED='N'";
		logger.info(cmd+"["+invoiceId+";"+billableId+"]");
				
		jdbcTemplate.update(cmd, new Object[]{
				invoiceId,billableId		
		});
	}

	@Override
	public void unlink(long invoiceId, long billableId) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		String cmd="update BSST_BIC_BILLABLE_COST set PIN_N_INVOICE_ID=NULL where BIC_N_BILLABLE_ID=? and PIN_N_INVOICE_ID=? and BIC_C_BILLED='N'";
		logger.info(cmd+"["+invoiceId+";"+billableId+"]");
				
		jdbcTemplate.update(cmd, new Object[]{
				billableId,invoiceId		
		});
	}

	@Override
	public List<Billable> getBilledByParent(long purchaseId) {
		// TODO Auto-generated method stub
		logger.info("BillableCostMysql.getBilledByParent() >>>");
		
		String query="select ";
		query+="PURCHASE_ID,COMPANY_ID,PRODUCT_ID,SUPPLIER_ID,";
		query+="PERSON_ID,COMPONENT_ID,QTY_UM_ID,CURRENCY_ID,PRICE,VAT,";
		query+="sum(QTY) as QTY,sum(AMOUNT) as AMOUNT,sum(VAT_AMOUNT) as VAT_AMOUNT,sum(TOT_AMOUNT) as TOT_AMOUNT";
		query+=" from BSSV_BILLABLE_COST";
		query+=" where BILLED='Y' and PURCHASE_ID=?";
		query+=" group by PURCHASE_ID,COMPANY_ID,PRODUCT_ID,SUPPLIER_ID,";
		query+="PERSON_ID,COMPONENT_ID,QTY_UM_ID,CURRENCY_ID,PRICE,VAT";
		query+=" having abs(sum(TOT_AMOUNT))>0.1";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"["+purchaseId+"]");
		List<Billable> ret=jdbcTemplate.query(query, new Object[]{purchaseId},new RowMapperBillableCostAggr());
		
		
		logger.info("BillableCostMysql.getBilledByParent() <<<");
		
		return ret;
	}
	
	private class RowMapperBillableCostAggr implements RowMapper<Billable>{

		@Override
		public Billable mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			BillableCost billable=new BillableCost();
		
			billable.setParentId(rs.getLong("PURCHASE_ID"));
			billable.setCompanyId(rs.getLong("COMPANY_ID"));
			billable.setProductId(rs.getLong("PRODUCT_ID"));
			billable.setSupplierId(rs.getLong("SUPPLIER_ID"));
			billable.setPersonId(rs.getLong("PERSON_ID"));
			billable.setComponentId(rs.getLong("COMPONENT_ID"));
			billable.setQtyUmId(rs.getLong("QTY_UM_ID"));
			billable.setCurrencyId(rs.getLong("CURRENCY_ID"));
			billable.setPrice(rs.getDouble("PRICE"));
			billable.setVat(rs.getDouble("VAT"));
			
			billable.setQty(rs.getDouble("QTY"));
			billable.setAmount(rs.getDouble("AMOUNT"));
			billable.setVatAmount(rs.getDouble("VAT_AMOUNT"));
			billable.setTotAmount(rs.getDouble("TOT_AMOUNT"));
				
			return billable;
		}
	}

	@Override
	public List<Billable> getByParent(long purchaseId) {
		// TODO Auto-generated method stub
		String query="select ID,PURCHASE_ID,INVOICE_ID,";
		query+="COMPANY_ID,COMPANY_CODE,COMPANY_NAME,";
		query+="PRODUCT_ID,PRODUCT_NAME,";
		query+="SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,";
		query+="PERSON_ID,PERSON_CODE,PERSON_FIRST_NAME,PERSON_SECOND_NAME,";
		query+="COMPONENT_ID,COMPONENT_CODE,COMPONENT_NAME,";
		query+="COMPONENT_TYPE_CODE,COMPONENT_TYPE_NAME,";
		query+="QTY_UM_ID,QTY_UM_SIMBOL,QTY,";
		query+="CURRENCY_ID,CURRENCY_CODE,CURRENCY_NAME,";
		query+="PRICE, AMOUNT,VAT,VAT_AMOUNT,TOT_AMOUNT,";
		query+="DATE,DATE_START,DATE_END,TYPE,BILLED,UPDATE_DATE";
		
		query+=" from BSSV_BILLABLE_COST";
		query+=" where PURCHASE_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"["+purchaseId+"]");
		List<Billable> ret=jdbcTemplate.query(query, new Object[]{purchaseId},new RowMapperBillableCost());
				
		return ret;
	}

}

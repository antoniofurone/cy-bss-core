package org.cysoft.bss.core.dao.mysql;

import java.text.ParseException;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.dao.BillableCostDao;
import org.cysoft.bss.core.model.BillableCost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class BillableCostMysql extends CyBssMysqlDao
	implements BillableCostDao{

	private static final Logger logger = LoggerFactory.getLogger(BillableCostMysql.class);

	@Override
	public long add(BillableCost billableCost) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("BillableCostMysql.add() >>>");
		
		String cmd="insert into BSST_BIC_BILLABLE_COST(PUR_N_PURCHASE_ID,MNC_N_COMPANY_ID,PRO_N_PRODUCT_ID,COM_N_COMPANY_ID,";
		cmd+="PER_N_PERSON_ID,BIC_N_QUANTITY,MES_N_METRIC_SCALE_ID,BIC_N_PRICE,BIC_N_AMOUNT,CUR_N_CURRENCY_ID,";
		cmd+="BIC_N_VAT,BIC_N_VAT_AMOUNT,BIC_N_TOT_AMOUNT,BIC_D_DATE_START,BIC_D_DATE_END,PRC_N_PRICE_COMPONENT_ID,";
		cmd+="BIC_D_DATE,BIC_C_TYPE)";
		cmd+=" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+billableCost+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					billableCost.getParentId(),
					billableCost.getCompanyId(),
					billableCost.getProductId(),
					billableCost.getSupplierId()==0?null:billableCost.getSupplierId(),
					billableCost.getPersonId()==0?null:billableCost.getPersonId(),
					billableCost.getQty()==0?null:billableCost.getQty(),
					billableCost.getQtyUmId()==0?null:billableCost.getQtyUmId(),
					billableCost.getPrice(),
					billableCost.getAmount(),
					billableCost.getCurrencyId(),
					billableCost.getVat(),
					billableCost.getVatAmount(),
					billableCost.getTotAmount(),
					(billableCost.getDateStart()==null || billableCost.getDateStart().equals(""))?null:CyBssUtility.tryStringToDate(billableCost.getDateStart()),
					(billableCost.getDateEnd()==null || billableCost.getDateEnd().equals(""))?null:CyBssUtility.tryStringToDate(billableCost.getDateEnd()),
					billableCost.getComponentId(),
					CyBssUtility.tryStringToDate(billableCost.getDate()),
					billableCost.getBillableType()
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
	public List<BillableCost> getUnbilledBySupplier(long supplierId) throws CyBssException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BillableCost> getUnbilledByPerson(long personId) throws CyBssException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BillableCost> getBilled(long purchaseId) throws CyBssException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BillableCost get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeByPurchase(long purchaseId) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		String cmd="delete from BSST_BIC_BILLABLE_COST where PUR_N_PURCHASE_ID=? and BIC_C_BILLED='N'";
		logger.info(cmd+"["+purchaseId+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
				purchaseId		
		});
	}
			
}

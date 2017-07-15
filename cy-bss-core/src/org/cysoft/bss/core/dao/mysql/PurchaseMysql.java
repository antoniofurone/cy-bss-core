package org.cysoft.bss.core.dao.mysql;


import java.text.ParseException;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.dao.PurchaseDao;
import org.cysoft.bss.core.model.Purchase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
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
					purchase.getPurchaseType()
				});
		} catch (DataAccessException | ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("PurchaseMysql.add() <<<");
		
		return getLastInsertId(jdbcTemplate);
	}

	
}

package org.cysoft.bss.core.web.service.rest;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.CompanyDao;
import org.cysoft.bss.core.model.Company;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.cysoft.bss.core.web.response.rest.CompanyResponse;
import org.cysoft.bss.core.web.service.CyBssWebServiceAdapter;
import org.cysoft.bss.core.web.service.ICyBssWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@CyBssService(name = "Company")
public class CompanyWs extends CyBssWebServiceAdapter
implements ICyBssWebService{
	
	private static final Logger logger = LoggerFactory.getLogger(CompanyWs.class);
	
	protected CompanyDao companyDao=null;
	@Autowired
	public void setPersonDao(CompanyDao companyDao){
			this.companyDao=companyDao;
	}
	
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@CyBssOperation(name = "add")

	public CompanyResponse add(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody Company company
			) throws CyBssException
	{
		CompanyResponse response=new CompanyResponse();
		
		logger.info("CompanyWs.add() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"add",String.class,Company.class))
			return response;
		// end checkGrant 
				
		//logger.info(company.toString());
		
		if (companyDao.getByCode(company.getCode())!=null){
			setResult(response, ICyBssResultConst.RESULT_COMPANYCODE_USED, 
					ICyBssResultConst.RESULT_D_COMPANYCODE_USED,response.getLanguageCode());
			return response;
		}
		
		long id=companyDao.add(company);
		company.setId(id);
		response.setCompany(company);
		
		logger.info("CompanyWs.add() <<<");
		
		return response;
	}
	

}

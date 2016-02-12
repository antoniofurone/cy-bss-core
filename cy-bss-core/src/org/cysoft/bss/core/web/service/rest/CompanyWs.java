package org.cysoft.bss.core.web.service.rest;


import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.CompanyDao;
import org.cysoft.bss.core.model.Company;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.cysoft.bss.core.web.response.rest.CompanyListResponse;
import org.cysoft.bss.core.web.response.rest.CompanyResponse;
import org.cysoft.bss.core.web.service.CyBssWebServiceAdapter;
import org.cysoft.bss.core.web.service.ICyBssWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@CyBssService(name = "Company")
public class CompanyWs extends CyBssWebServiceAdapter
implements ICyBssWebService{
	
	private static final Logger logger = LoggerFactory.getLogger(CompanyWs.class);
	
	protected CompanyDao companyDao=null;
	@Autowired
	public void setCompanyDao(CompanyDao companyDao){
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
	
	@RequestMapping(value = "/{id}/get",method = RequestMethod.GET)
	@CyBssOperation(name = "get")
	public CompanyResponse get(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("CompanyWs.get() >>> id="+id);
		CompanyResponse response=new CompanyResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"get",String.class,Long.class))
			return response;
		// end checkGrant 
				
		Company company=companyDao.get(id);
		if (company!=null)
			response.setCompany(company);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		
		logger.info("CompanyWs.get() <<< ");
		
		return response;
	}
	
	@RequestMapping(value = "/getByCode",method = RequestMethod.GET)
	@CyBssOperation(name = "getByCode")
	public CompanyResponse getByCode(
			@RequestHeader("Security-Token") String securityToken,
			@RequestParam(value="code", required=true) String code
			) throws CyBssException{
		
		logger.info("CompanyWs.getByCode() >>> personId="+code);
		CompanyResponse response=new CompanyResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getByCode",String.class,String.class))
			return response;
		// end checkGrant 
				
		Company company=companyDao.getByCode(code);
		if (company!=null)
			response.setCompany(company);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		
		logger.info("CompanyWs.getByCode() <<< ");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/update",method = RequestMethod.POST)
	@CyBssOperation(name = "update")
	public CompanyResponse update(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody Company company
			) throws CyBssException
	{
		CompanyResponse response=new CompanyResponse();
		
		logger.info("CompanyWs.update() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"update",String.class,Long.class,Company.class))
			return response;
		// end checkGrant 
		
		if (companyDao.get(id)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		
		companyDao.update(id, company);
		response.setCompany(companyDao.get(id));
		
		logger.info("CompanyWs.update() <<<");
		
		return response;
	}
		
	@RequestMapping(value = "/{id}/remove",method = RequestMethod.GET)
	@CyBssOperation(name = "remove")
	public CompanyResponse remove(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("CompanyWs.remove() >>> id="+id);
		CompanyResponse response=new CompanyResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",String.class,Long.class))
			return response;
		// end checkGrant 
		companyDao.remove(id);
		
		logger.info("CompanyWs.remove() <<< ");
		return response;
	}
	
	
	@RequestMapping(value = "/find",method = RequestMethod.GET)
	@CyBssOperation(name = "find")
	public CompanyListResponse find(
			@RequestHeader("Security-Token") String securityToken,
			@RequestParam(value="code", required=false, defaultValue="") String code,
			@RequestParam(value="name", required=false, defaultValue="") String name,
			@RequestParam(value="offset", required=false, defaultValue="0") Integer offset,
			@RequestParam(value="size", required=false, defaultValue="100") Integer size
			) throws CyBssException{
		
		logger.info("CompanyWs.find() >>> code="+code+";name="+name);
		CompanyListResponse response=new CompanyListResponse();
		
		
		// checkGrant
		if (!checkGrant(response,securityToken,"find",String.class,String.class,String.class,Integer.class,Integer.class))
			return response;
		// end checkGrant 
		
		
		List<Company> companies=companyDao.find(code,name);
		int lsize=companies.size();
		if (offset!=0)
			response.setCompanies(companies.subList(offset-1, (offset-1)+(size>lsize?lsize:size)));
		else
			response.setCompanies(companies);
		
		logger.info("CompanyWs.find() <<< ");
		
		return response;
	}


}

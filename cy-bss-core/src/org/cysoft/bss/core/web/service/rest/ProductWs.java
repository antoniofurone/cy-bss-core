package org.cysoft.bss.core.web.service.rest;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.ProductDao;
import org.cysoft.bss.core.model.ProductCategory;
import org.cysoft.bss.core.model.ProductType;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.cysoft.bss.core.web.response.rest.ProductCategoryListResponse;
import org.cysoft.bss.core.web.response.rest.ProductCategoryResponse;
import org.cysoft.bss.core.web.response.rest.ProductTypeListResponse;
import org.cysoft.bss.core.web.response.rest.ProductTypeResponse;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@CyBssService(name = "Product")
public class ProductWs extends CyBssWebServiceAdapter
	implements ICyBssWebService{

	private static final Logger logger = LoggerFactory.getLogger(ProductWs.class);
	
	protected ProductDao productDao=null;
	@Autowired
	public void setProductDao(ProductDao productDao){
			this.productDao=productDao;
	}
	
	// Category
	
	@RequestMapping(value = "/addCategory",method = RequestMethod.POST)
	@CyBssOperation(name = "addCategory")
	public ProductCategoryResponse addCategory(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody ProductCategory category
			) throws CyBssException
	{
		ProductCategoryResponse response=new ProductCategoryResponse();
		
		logger.info("ProductWs.addCategory() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"addCategory",String.class,ProductCategory.class))
				return response;
		// end checkGrant 
		
		logger.info(category.toString());
		long id=productDao.addCategory(category);
		category.setId(id);
		response.setCategory(category);
		
		logger.info("ProductWs.addCategory() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/getCategoryAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getCategoryAll")
	public ProductCategoryListResponse getCategoryAll(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken
			) throws CyBssException{
		
		logger.info("ProductWs.getCategoryAll() >>>");
		ProductCategoryListResponse response=new ProductCategoryListResponse(); 
		response.setCategories(productDao.getCategoryAll());
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/getCategory",method = RequestMethod.GET)
	@CyBssOperation(name = "getCategory")
	public ProductCategoryResponse getCategory(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("ProductWs.getCategory() >>> id="+id);
		ProductCategoryResponse response=new ProductCategoryResponse();
		
		ProductCategory category=productDao.getCategory(id);
		if (category!=null)
			response.setCategory(category);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
	
		logger.info("ProductWs.getCategory() <<< ");
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/updateCategory",method = RequestMethod.POST)
	@CyBssOperation(name = "updateCategory")
	public ProductCategoryResponse updateCategory(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody ProductCategory category
			) throws CyBssException
	{
		ProductCategoryResponse response=new ProductCategoryResponse();
		
		logger.info("ProductWs.updateCategory() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"updateCategory",String.class,Long.class,ProductCategory.class))
			return response;
		// end checkGrant 
		
		if (productDao.getCategory(id)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		productDao.updateCategory(id, category);
		response.setCategory(productDao.getCategory(id));
		
		logger.info("ProductWs.updateCategory() <<<");
		return response;
	}
	
	@RequestMapping(value = "/{id}/removeCategory",method = RequestMethod.GET)
	@CyBssOperation(name = "removeCategory")
	public ProductCategoryResponse removeCategory(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("ProductWs.removeCategory() >>> id="+id);
		ProductCategoryResponse response=new ProductCategoryResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"removeCategory",String.class,Long.class))
			return response;
		// end checkGrant 
		
		productDao.removeCategory(id);
	
		logger.info("ProductWs.removeCategory() <<< ");
	
		return response;
	}

	// Type
	
	@RequestMapping(value = "/addType",method = RequestMethod.POST)
	@CyBssOperation(name = "addType")
	public ProductTypeResponse addType(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody ProductType type
			) throws CyBssException
	{
		ProductTypeResponse response=new ProductTypeResponse();
		
		logger.info("ProductWs.addType() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"addType",String.class,ProductType.class))
				return response;
		// end checkGrant 
		
		logger.info(type.toString());
		long id=productDao.addType(type);
		type.setId(id);
		response.setType(type);
		
		logger.info("ProductWs.addType() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/getTypeAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getTypeAll")
	public ProductTypeListResponse getTypeAll(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken
			) throws CyBssException{
		
		logger.info("ProductWs.getCategoryAll() >>>");
		ProductTypeListResponse response=new ProductTypeListResponse(); 
		response.setTypes(productDao.getTypeAll());
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/getType",method = RequestMethod.GET)
	@CyBssOperation(name = "getType")
	public ProductTypeResponse getType(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("ProductWs.getType() >>> id="+id);
		ProductTypeResponse response=new ProductTypeResponse();
		
		ProductType type=productDao.getType(id);
		if (type!=null)
			response.setType(type);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
	
		logger.info("ProductWs.getType() <<< ");
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/updateType",method = RequestMethod.POST)
	@CyBssOperation(name = "updateType")
	public ProductTypeResponse updateType(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody ProductType type
			) throws CyBssException
	{
		ProductTypeResponse response=new ProductTypeResponse();
		
		logger.info("ProductWs.updateType() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"updateType",String.class,Long.class,ProductType.class))
			return response;
		// end checkGrant 
		
		if (productDao.getCategory(id)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		productDao.updateType(id, type);
		response.setType(productDao.getType(id));
		
		logger.info("ProductWs.updateType() <<<");
		return response;
	}
	
	@RequestMapping(value = "/{id}/removeType",method = RequestMethod.GET)
	@CyBssOperation(name = "removeType")
	public ProductTypeResponse removeType(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("ProductWs.removeType() >>> id="+id);
		ProductTypeResponse response=new ProductTypeResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"removeType",String.class,Long.class))
			return response;
		// end checkGrant 
		
		productDao.removeType(id);
	
		logger.info("ProductWs.removeType() <<< ");
	
		return response;
	}

	// Product
	
}

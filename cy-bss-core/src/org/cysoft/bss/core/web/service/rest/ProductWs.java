package org.cysoft.bss.core.web.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.message.ICyBssMessageConst;
import org.cysoft.bss.core.model.Product;
import org.cysoft.bss.core.model.ProductCategory;
import org.cysoft.bss.core.model.ProductType;
import org.cysoft.bss.core.service.ProductService;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.rest.product.ProductCategoryListResponse;
import org.cysoft.bss.core.web.response.rest.product.ProductCategoryResponse;
import org.cysoft.bss.core.web.response.rest.product.ProductListResponse;
import org.cysoft.bss.core.web.response.rest.product.ProductResponse;
import org.cysoft.bss.core.web.response.rest.product.ProductTypeListResponse;
import org.cysoft.bss.core.web.response.rest.product.ProductTypeResponse;
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
@RequestMapping("/product")
@CyBssService(name = "Product")
public class ProductWs extends CyBssWebServiceAdapter
	implements ICyBssWebService{

	private static final Logger logger = LoggerFactory.getLogger(ProductWs.class);
	
	protected ProductService productService=null;
	@Autowired
	public void setProductService(ProductService productService){
			this.productService=productService;
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
		long id=productService.addCategory(category);
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
		response.setCategories(productService.getCategoryAll());
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
		
		ProductCategory category=productService.getCategory(id);
		if (category!=null)
			response.setCategory(category);
		else
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
	
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
		
		if (productService.getCategory(id)==null){
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		productService.updateCategory(id, category);
		response.setCategory(productService.getCategory(id));
		
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
		
		productService.removeCategory(id);
	
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
		long id=productService.addType(type);
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
		response.setTypes(productService.getTypeAll());
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
		
		ProductType type=productService.getType(id);
		if (type!=null)
			response.setType(type);
		else
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
	
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
		
		if (productService.getType(id)==null){
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		productService.updateType(id, type);
		response.setType(productService.getType(id));
		
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
		
		productService.removeType(id);
	
		logger.info("ProductWs.removeType() <<< ");
	
		return response;
	}

	// Product

	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@CyBssOperation(name = "add")
	public ProductResponse add(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody Product product
			) throws CyBssException
	{
		ProductResponse response=new ProductResponse();
		
		logger.info("ProductWs.add() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"add",String.class,Product.class))
				return response;
		// end checkGrant 
		
		logger.info(product.toString());
		long id=productService.add(product);
		product.setId(id);
		response.setProduct(product);
		
		logger.info("ProductWs.add() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/find",method = RequestMethod.GET)
	@CyBssOperation(name = "find")
	public ProductListResponse find(
			@RequestHeader("Security-Token") String securityToken,
			@RequestParam(value="name", required=false, defaultValue="") String name,
			@RequestParam(value="categoryId", required=false, defaultValue="0") Integer categoryId,
			@RequestParam(value="typeId", required=false, defaultValue="0") Integer productTypeId,
			@RequestParam(value="attrName", required=false, defaultValue="") String attrName,
			@RequestParam(value="attrValue", required=false, defaultValue="") String attrValue,
			@RequestParam(value="offset", required=false, defaultValue="0") Integer offset,
			@RequestParam(value="size", required=false, defaultValue="100") Integer size
			) throws CyBssException{
		
		logger.info("ProductWs.find() >>> name="+name+";categoryId="+categoryId
				+";productTypeId="+productTypeId+";attributeName="+attrName+";attributeValue="+attrValue);
		
		ProductListResponse response=new ProductListResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"find",String.class,
			String.class,Integer.class,Integer.class,
			String.class,String.class,
			Integer.class,Integer.class))
			return response;
		// end checkGrant 
		
		
		List<Product> products=productService.find(name,categoryId,productTypeId,attrName,attrValue);
		int lsize=products.size();
		
		if (offset!=0){
			if (offset<=lsize)
				response.setProducts(products.subList(offset-1, ((offset-1)+size)>lsize?lsize:(offset-1)+size));
			else
				response.setProducts(new ArrayList<Product>());
			}
		else
			response.setProducts(products);
		
		logger.info("ProductWs.find() <<< ");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/get",method = RequestMethod.GET)
	@CyBssOperation(name = "get")
	public ProductResponse get(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("Product.get() >>> id="+id);
		ProductResponse response=new ProductResponse();
		
		Product product=productService.get(id);
		if (product!=null)
			response.setProduct(product);
		else
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
	
		logger.info("ProductWs.get() <<< ");
		return response;
	}
	
	@RequestMapping(value = "/{id}/update",method = RequestMethod.POST)
	@CyBssOperation(name = "update")
	public ProductResponse update(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody Product product
			) throws CyBssException
	{
		ProductResponse response=new ProductResponse();
		
		logger.info("ProductWs.update() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"update",String.class,Long.class,Product.class))
			return response;
		// end checkGrant 
		
		if (productService.get(id)==null){
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		productService.update(id, product);
		response.setProduct(productService.get(id));
		
		logger.info("ProductWs.update() <<<");
		return response;
	}

	@RequestMapping(value = "/{id}/remove",method = RequestMethod.GET)
	@CyBssOperation(name = "remove")
	public ProductResponse remove(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("ProductWs.remove() >>> id="+id);
		ProductResponse response=new ProductResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",String.class,Long.class))
			return response;
		// end checkGrant 
		
		productService.remove(id);
	
		logger.info("ProductWs.remove() <<< ");
	
		return response;
	}

}

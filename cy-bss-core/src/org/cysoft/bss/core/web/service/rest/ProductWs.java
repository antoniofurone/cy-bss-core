package org.cysoft.bss.core.web.service.rest;

import org.cysoft.bss.core.dao.MetricDao;
import org.cysoft.bss.core.dao.ProductDao;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.service.CyBssWebServiceAdapter;
import org.cysoft.bss.core.web.service.ICyBssWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	
	
	
	
}

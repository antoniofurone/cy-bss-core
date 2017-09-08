package org.cysoft.bss.core.service.impl;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.ObjectDao;
import org.cysoft.bss.core.dao.ProductDao;
import org.cysoft.bss.core.model.Product;
import org.cysoft.bss.core.model.ProductCategory;
import org.cysoft.bss.core.model.ProductType;
import org.cysoft.bss.core.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class ProductServiceImpl extends CyBssServiceBase 
implements ProductService{

	protected ProductDao productDao=null;
	@Autowired
	public void setProductDao(ProductDao productDao){
			this.productDao=productDao;
	}
	
	protected ObjectDao objectDao=null;
	@Autowired
	public void setObjectDao(ObjectDao objectDao){
			this.objectDao=objectDao;
	}

	
	@Override
	public long addCategory(ProductCategory productCategory) throws CyBssException {
		// TODO Auto-generated method stub
		return productDao.addCategory(productCategory);
	}

	@Override
	public void removeCategory(long id) throws CyBssException {
		// TODO Auto-generated method stub
		productDao.removeCategory(id);
	}

	@Override
	public void updateCategory(long id, ProductCategory category) throws CyBssException {
		// TODO Auto-generated method stub
		productDao.updateCategory(id, category);
	}

	@Override
	public List<ProductCategory> getCategoryAll() {
		// TODO Auto-generated method stub
		return productDao.getCategoryAll();
	}

	@Override
	public ProductCategory getCategory(long id) {
		// TODO Auto-generated method stub
		return productDao.getCategory(id);
	}

	@Override
	public long addType(ProductType productType) throws CyBssException {
		// TODO Auto-generated method stub
		return productDao.addType(productType);
	}

	@Override
	public void removeType(long id) throws CyBssException {
		// TODO Auto-generated method stub
		productDao.removeType(id);
	}

	@Override
	public void updateType(long id, ProductType type) throws CyBssException {
		// TODO Auto-generated method stub
		productDao.updateType(id, type);
	}

	@Override
	public List<ProductType> getTypeAll() {
		// TODO Auto-generated method stub
		return productDao.getTypeAll();
	}

	@Override
	public ProductType getType(long id) {
		// TODO Auto-generated method stub
		return productDao.getType(id);
	}

	@Override
	public long add(Product product) throws CyBssException {
		// TODO Auto-generated method stub
		return productDao.add(product);
	}

	@Override
	public void update(long id, Product product) throws CyBssException {
		// TODO Auto-generated method stub
		productDao.update(id, product);
	}

	@Override
	public List<Product> find(String name, long categoryId, long typeId, String attrName, String attrValue) {
		// TODO Auto-generated method stub
		return productDao.find(name, categoryId, typeId, attrName, attrValue);
	}

	@Override
	public Product get(long id) {
		// TODO Auto-generated method stub
		return productDao.get(id);
	}

	@Override
	public void remove(final long id) throws CyBssException {
		// TODO Auto-generated method stub
		
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		txTemplate.execute(new TransactionCallbackWithoutResult(){

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				// TODO Auto-generated method stub
				
				try {
					objectDao.removeAttributeValues(id,Product.ENTITY_NAME);
					productDao.remove(id);
				} catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				
			}
		});	
		
	}

}

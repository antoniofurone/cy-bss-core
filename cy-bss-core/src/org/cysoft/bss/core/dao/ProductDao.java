package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Product;
import org.cysoft.bss.core.model.ProductCategory;
import org.cysoft.bss.core.model.ProductType;

public interface ProductDao {
	
	public long addCategory(ProductCategory productCategory) throws CyBssException;
	public void removeCategory(long id) throws CyBssException;
	public void updateCategory(long id,ProductCategory category) throws CyBssException;
	public List<ProductCategory> getCategoryAll();
	public ProductCategory getCategory(long id);
	

	public long addType(ProductType productType) throws CyBssException;
	public void removeType(long id) throws CyBssException;
	public void updateType(long id,ProductType type) throws CyBssException;
	public List<ProductType> getTypeAll();
	public ProductType getType(long id);
	
	public long add(Product product) throws CyBssException;
	public void update(long id,Product product) throws CyBssException;
	public List<Product> find(String name, long categoryId, long typeId,String attrName, String attrValue);
	public Product get(long id);
	public void remove(long id) throws CyBssException;
	
}


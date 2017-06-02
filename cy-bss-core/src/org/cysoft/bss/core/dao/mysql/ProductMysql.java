package org.cysoft.bss.core.dao.mysql;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.ProductDao;
import org.cysoft.bss.core.model.Product;
import org.cysoft.bss.core.model.ProductCategory;
import org.cysoft.bss.core.model.ProductType;

public class ProductMysql extends CyBssMysqlDao
	implements ProductDao{

	@Override
	public long addCategory(ProductCategory productCategory) throws CyBssException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeCategory(long id) throws CyBssException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ProductCategory> getCategoryAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductCategory getCategory(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long addType(ProductType productType) throws CyBssException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeType(long id) throws CyBssException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ProductType> getTypeAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductType getType(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long add(Product product) throws CyBssException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(long id, Product product) throws CyBssException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Product> find(String name, long categoryId, long typeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(long id) throws CyBssException {
		// TODO Auto-generated method stub
		
	}

}

package org.cysoft.bss.core.service.impl;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.PriceDao;
import org.cysoft.bss.core.model.PriceComponent;
import org.cysoft.bss.core.model.PriceType;
import org.cysoft.bss.core.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl extends CyBssServiceBase 
implements PriceService {

	protected PriceDao priceDao=null;
	@Autowired
	public void setPriceDao(PriceDao priceDao){
			this.priceDao=priceDao;
	}
	
	@Override
	public List<PriceType> getPriceTypeAll() {
		// TODO Auto-generated method stub
		return priceDao.getPriceTypeAll();
	}

	@Override
	public PriceType getPriceType(long id) {
		// TODO Auto-generated method stub
		return priceDao.getPriceType(id);
	}

	@Override
	public List<PriceComponent> getComponentAll() {
		// TODO Auto-generated method stub
		return priceDao.getComponentAll();
	}

	@Override
	public PriceComponent getPriceComponent(long id) {
		// TODO Auto-generated method stub
		return priceDao.getPriceComponent(id);
	}

	@Override
	public void updateComponent(long id, PriceComponent product) throws CyBssException {
		// TODO Auto-generated method stub
		priceDao.updateComponent(id, product);
	}

}

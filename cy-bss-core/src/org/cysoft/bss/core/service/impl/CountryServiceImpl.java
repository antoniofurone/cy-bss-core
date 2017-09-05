package org.cysoft.bss.core.service.impl;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.CountryDao;
import org.cysoft.bss.core.model.Country;
import org.cysoft.bss.core.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl extends CyBssServiceBase 
implements CountryService{

	protected CountryDao countryDao=null;
	@Autowired
	public void setCountryDao(CountryDao countryDao){
			this.countryDao=countryDao;
	}
	
	@Override
	public long add(Country country) throws CyBssException {
		// TODO Auto-generated method stub
		return countryDao.add(country);
	}

	@Override
	public List<Country> getCountryAll() {
		// TODO Auto-generated method stub
		return countryDao.getCountryAll();
	}

	@Override
	public List<Country> find(String name) {
		// TODO Auto-generated method stub
		return countryDao.find(name);
	}

	@Override
	public void update(long id, Country country) throws CyBssException {
		// TODO Auto-generated method stub
		countryDao.update(id, country);
	}

	@Override
	public Country get(long id) {
		// TODO Auto-generated method stub
		return countryDao.get(id);
	}

	@Override
	public void remove(long id) throws CyBssException {
		// TODO Auto-generated method stub
		countryDao.remove(id);
	}
	
}

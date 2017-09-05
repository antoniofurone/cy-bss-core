package org.cysoft.bss.core.service.impl;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.CityDao;
import org.cysoft.bss.core.model.City;
import org.cysoft.bss.core.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl extends CyBssServiceBase 
implements CityService{
	
	protected CityDao cityDao=null;
	@Autowired
	public void setCityDao(CityDao cityDao){
			this.cityDao=cityDao;
	}
	@Override
	public long add(City city) throws CyBssException {
		// TODO Auto-generated method stub
		return cityDao.add(city);
	}
	@Override
	public List<City> getCityAll() {
		// TODO Auto-generated method stub
		return cityDao.getCityAll();
	}
	@Override
	public List<City> find(String name, String stateRegion, long countryId) {
		// TODO Auto-generated method stub
		return cityDao.find(name, stateRegion, countryId);
	}
	@Override
	public void update(long id, City city) throws CyBssException {
		// TODO Auto-generated method stub
		cityDao.update(id, city);
	}
	@Override
	public City get(long id) {
		// TODO Auto-generated method stub
		return cityDao.get(id);
	}
	@Override
	public void remove(long id) throws CyBssException {
		// TODO Auto-generated method stub
		cityDao.remove(id);
	}

}

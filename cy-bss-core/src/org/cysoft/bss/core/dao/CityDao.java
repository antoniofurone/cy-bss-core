package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.City;

public interface CityDao {
	public long add(City city) throws CyBssException;
	public List<City> getCityAll();
	
	public List<City> find(String name,String stateRegion,long countryId);
	
	public void update(long id,City city) throws CyBssException;
	public City get(long id);
	public void remove(long id) throws CyBssException;
}

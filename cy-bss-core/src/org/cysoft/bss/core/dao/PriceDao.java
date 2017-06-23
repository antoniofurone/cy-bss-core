package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.PriceComponent;
import org.cysoft.bss.core.model.PriceType;

public interface PriceDao {
	
	public List<PriceType> getPriceTypeAll();
	public PriceType getPriceType(long id);
	
	public List<PriceComponent> getComponentAll();
	public PriceComponent getPriceComponent(long id);
	public void updateComponent(long id,PriceComponent product) throws CyBssException;
	
}


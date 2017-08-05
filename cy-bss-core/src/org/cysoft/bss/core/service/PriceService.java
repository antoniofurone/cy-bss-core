package org.cysoft.bss.core.service;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.PriceComponent;
import org.cysoft.bss.core.model.PriceType;

public interface PriceService {
	
	public List<PriceType> getPriceTypeAll();
	public PriceType getPriceType(long id);
	
	public List<PriceComponent> getComponentAll();
	public PriceComponent getPriceComponent(long id);
	public void updateComponent(long id,PriceComponent product) throws CyBssException;

}

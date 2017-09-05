package org.cysoft.bss.core.service.impl;

import java.util.List;

import org.cysoft.bss.core.dao.CyBssServiceDao;
import org.cysoft.bss.core.model.CyBssOperationParam;
import org.cysoft.bss.core.model.CyBssServOperation;
import org.cysoft.bss.core.model.CyBssService;
import org.cysoft.bss.core.service.CyBssServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CyBssServiceServiceImpl extends CyBssServiceBase 
implements CyBssServiceService{

	
	private CyBssServiceDao serviceDao=null;
	@Autowired
	public void setServiceDao(CyBssServiceDao serviceDao){
			this.serviceDao=serviceDao;
	}
	
	@Override
	public List<CyBssService> getAll() {
		// TODO Auto-generated method stub
		return serviceDao.getAll();
	}

	@Override
	public CyBssService get(String id) {
		// TODO Auto-generated method stub
		return serviceDao.get(id);
	}

	@Override
	public List<CyBssServOperation> getServOperations(String id) {
		// TODO Auto-generated method stub
		return serviceDao.getServOperations(id);
	}

	@Override
	public CyBssServOperation getOperation(String id) {
		// TODO Auto-generated method stub
		return serviceDao.getOperation(id);
	}

	@Override
	public List<CyBssOperationParam> getOpParams(String idOp) {
		// TODO Auto-generated method stub
		return serviceDao.getOpParams(idOp);
	}

}

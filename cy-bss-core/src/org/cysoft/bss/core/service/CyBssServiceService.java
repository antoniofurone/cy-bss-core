package org.cysoft.bss.core.service;

import java.util.List;

import org.cysoft.bss.core.model.CyBssOperationParam;
import org.cysoft.bss.core.model.CyBssServOperation;
import org.cysoft.bss.core.model.CyBssService;

public interface CyBssServiceService {
	
	public List<CyBssService> getAll();
	public CyBssService get(String id);
	public List<CyBssServOperation> getServOperations(String id);
	public CyBssServOperation getOperation(String id);
	public List<CyBssOperationParam> getOpParams(String idOp);

}

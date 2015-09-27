package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.BssOperationParam;
import org.cysoft.bss.core.model.BssServOperation;
import org.cysoft.bss.core.model.BssService;

public interface BssServiceDao {
	
	public List<BssService> getAll();
	public BssService get(String id);
	public List<BssServOperation> getServOperations(String id);
	public BssServOperation getOperation(String id);
	public List<BssOperationParam> getOpParams(String idOp);
}

package org.cysoft.bss.core.web.response.rest.server;

import java.util.List;

import org.cysoft.bss.core.model.ServerQueueItem;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class ServerQueueItemListResponse extends CyBssResponseAdapter
implements ICyBssResponse{

	private List<ServerQueueItem> items=null;

	public List<ServerQueueItem> getItems() {
		return items;
	}

	public void setItems(List<ServerQueueItem> items) {
		this.items = items;
	}

	
	
}

package org.cysoft.bss.core.web.response.file;

import java.util.List;

import org.cysoft.bss.core.model.CyBssFile;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class FileListResponse extends CyBssResponseAdapter
implements ICyBssResponse{

	private List<CyBssFile> files=null;

	public List<CyBssFile> getFiles() {
		return files;
	}

	public void setFiles(List<CyBssFile> files) {
		this.files = files;
	}

	
}

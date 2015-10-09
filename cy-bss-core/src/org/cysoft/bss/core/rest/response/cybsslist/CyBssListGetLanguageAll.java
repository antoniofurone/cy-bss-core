package org.cysoft.bss.core.rest.response.cybsslist;

import java.util.List;

import org.cysoft.bss.core.model.Language;
import org.cysoft.bss.core.rest.response.CyBssRestResponseAdapter;
import org.cysoft.bss.core.rest.response.ICyBssRestResponse;

public class CyBssListGetLanguageAll extends CyBssRestResponseAdapter
implements ICyBssRestResponse{
	
	private List<Language> languages=null;
	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}
	

}

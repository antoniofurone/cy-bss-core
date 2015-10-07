package org.cysoft.bss.core.model.rest.response;

import java.util.List;

import org.cysoft.bss.core.model.Language;

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

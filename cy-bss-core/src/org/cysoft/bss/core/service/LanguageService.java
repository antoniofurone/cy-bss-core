package org.cysoft.bss.core.service;

import java.util.List;

import org.cysoft.bss.core.model.Language;

public interface LanguageService {
	public List<Language> getLanguageAll();
	public Language getLanguage(String languageCode);

}

package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.model.Language;

public interface LanguageDao {
	public List<Language> getLanguageAll();
	public Language getLanguage(String languageCode);

}

package org.cysoft.bss.core.service.impl;

import java.util.List;

import org.cysoft.bss.core.dao.LanguageDao;
import org.cysoft.bss.core.model.Language;
import org.cysoft.bss.core.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanguageServiceImpl extends CyBssServiceBase 
implements LanguageService{

	protected LanguageDao languageDao=null;
	@Autowired
	public void setLanguageDao(LanguageDao languageDao){
			this.languageDao=languageDao;
	}
	
	@Override
	public List<Language> getLanguageAll() {
		// TODO Auto-generated method stub
		return languageDao.getLanguageAll();
	}

	@Override
	public Language getLanguage(String languageCode) {
		// TODO Auto-generated method stub
		return languageDao.getLanguage(languageCode);
	}

}

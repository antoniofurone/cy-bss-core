package org.cysoft.bss.core.service.impl;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.ContactDao;
import org.cysoft.bss.core.dao.ObjectDao;
import org.cysoft.bss.core.dao.PersonDao;
import org.cysoft.bss.core.model.Person;
import org.cysoft.bss.core.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class PersonServiceImpl extends CyBssServiceBase 
implements PersonService{

	protected PersonDao personDao=null;
	@Autowired
	public void setPersonDao(PersonDao personDao){
			this.personDao=personDao;
	}
	
	protected ContactDao contactDao=null;
	@Autowired
	public void setContactDao(ContactDao contactDao){
			this.contactDao=contactDao;
	}
	
	protected ObjectDao objectDao=null;
	@Autowired
	public void setObjectDao(ObjectDao objectDao){
			this.objectDao=objectDao;
	}
	
	@Override
	public void add(Person person) throws CyBssException {
		// TODO Auto-generated method stub
		personDao.add(person);
	}

	@Override
	public Person getByCode(String code) {
		// TODO Auto-generated method stub
		return personDao.getByCode(code);
	}

	@Override
	public Person get(long id) {
		// TODO Auto-generated method stub
		return personDao.get(id);
	}

	@Override
	public void update(long id, Person person) throws CyBssException {
		// TODO Auto-generated method stub
		personDao.update(id, person);
	}

	@Override
	public void remove(final long id) throws CyBssException {
		// TODO Auto-generated method stub
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		txTemplate.execute(new TransactionCallbackWithoutResult(){

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
		
				try {
					contactDao.removeByEntityId(id, Person.ENTITY_NAME);
					objectDao.removeAttributeValues(id, Person.ENTITY_NAME);
					personDao.remove(id);
				} catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		
		});
			
	}

	@Override
	public List<Person> find(String code, String firstName, String secondName) {
		// TODO Auto-generated method stub
		return personDao.find(code, firstName, secondName);
	}

}

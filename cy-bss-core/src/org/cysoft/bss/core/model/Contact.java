package org.cysoft.bss.core.model;

public class Contact {
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	private long contactTypeId;
	public long getContactTypeId() {
		return contactTypeId;
	}
	public void setContactTypeId(long contactTypeId) {
		this.contactTypeId = contactTypeId;
	}
	
	private String contactTypeName="";
	public String getContactTypeName() {
		return contactTypeName;
	}
	public void setContactTypeName(String contactTypeName) {
		this.contactTypeName = contactTypeName;
	}
	
	private String entityName="";
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	private long entityId;
	public long getEntityId() {
		return entityId;
	}
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}
	
	private String contact="";
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	@Override
	public String toString() {
		return "Contact [id=" + id + ", contactTypeId=" + contactTypeId + ", contactTypeName=" + contactTypeName
				+ ", entityName=" + entityName + ", entityId=" + entityId + ", contact=" + contact + "]";
	}
	
	

}

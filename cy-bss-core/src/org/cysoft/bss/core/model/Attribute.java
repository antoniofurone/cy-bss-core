package org.cysoft.bss.core.model;

public class Attribute {
	private long id;
	private String name="";
	private long objectId;
	private String objectName="";
	private String entityName="";
	private long typeId;
	private String typeName="";
	private long objInstId;
	private String value="";
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getObjectId() {
		return objectId;
	}

	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	
	public long getObjInstId() {
		return objInstId;
	}

	public void setObjInstId(long objInstId) {
		this.objInstId = objInstId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Attribute [id=" + id + ", name=" + name + ", objectId=" + objectId + ", objectName=" + objectName
				+ ", entityName=" + entityName + ", typeId=" + typeId + ", typeName=" + typeName + ", objInstId="
				+ objInstId + ", value=" + value + "]";
	}
		
}
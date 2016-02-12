package org.cysoft.bss.core.model;

import java.io.InputStream;

public class CyBssFile {
	
	public final static String VISIBILITY_PUBLIC="P";
	public final static String VISIBILITY_RESERVED="R";
	
	long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	private String name="";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	private int length=0;
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
	private String contentType="";
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	private String fileType="";
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
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
	
	private String note="";
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	private String visibility="";
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	
	@Override
	public String toString() {
		return "CyBssFile [id=" + id + ", name=" + name + ", length=" + length + ", contentType=" + contentType
				+ ", fileType=" + fileType + ", entityName=" + entityName + ", entityId=" + entityId + ", note=" + note
				+ ", visibility=" + visibility + "]";
	}

	private InputStream content=null;
	public InputStream getContent() {
		return content;
	}
	public void setContent(InputStream content) {
		this.content = content;
	}
	
}

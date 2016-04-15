package org.cysoft.bss.core.model;

public class AttributeValue {
	
	private Attribute attribute=null;
	private String value="";
	
	public Attribute getAttribute() {
		return attribute;
	}
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "AttributeValue [attribute=" + attribute + ", value=" + value + "]";
	}
	
	
}

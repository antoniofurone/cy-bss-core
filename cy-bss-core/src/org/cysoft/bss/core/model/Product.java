package org.cysoft.bss.core.model;


public class Product {
	public static final String ENTITY_NAME="Product";
	
	private long id;
	private String name;
	private String description;
	private String code;
	private long categoryId;
	private long typeId;
	private long parentId;
	private String parentName;
	private long producerId;
	private String producerCode;
	private String producerName;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public long getProducerId() {
		return producerId;
	}
	public void setProducerId(long producerId) {
		this.producerId = producerId;
	}
	
	public String getProducerCode() {
		return producerCode;
	}
	public void setProducerCode(String producerCode) {
		this.producerCode = producerCode;
	}
	public String getProducerName() {
		return producerName;
	}
	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	private ProductCategory category=new ProductCategory();
	public ProductCategory getCategory() {
		return category;
	}
	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	private ProductType productType=new ProductType();

	public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", code=" + code
				+ ", categoryId=" + categoryId + ", typeId=" + typeId + ", parentId=" + parentId + ", parentName="
				+ parentName + ", producerId=" + producerId + ", producerCode=" + producerCode + ", producerName="
				+ producerName + ", category=" + category + ", productType=" + productType + "]";
	}
	
	
}

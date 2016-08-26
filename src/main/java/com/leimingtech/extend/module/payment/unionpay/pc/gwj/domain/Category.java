package com.leimingtech.extend.module.payment.unionpay.pc.gwj.domain;

public class Category {
	
	// 业务类型代码
	private String code;
	
	// 业务类型名称
	private String name;
	
	// 业务类型
	private String type;
	
	// 业务列表
	private Bussiness[] categories;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Bussiness[] getCategories() {
		return categories;
	}

	public void setCategories(Bussiness[] categories) {
		this.categories = categories;
	}
	
}

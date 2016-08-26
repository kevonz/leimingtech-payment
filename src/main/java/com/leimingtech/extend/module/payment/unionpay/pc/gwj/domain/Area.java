package com.leimingtech.extend.module.payment.unionpay.pc.gwj.domain;

public class Area {
	
	// 地区代码
	private String code;
	
	// 地区名称
	private String name;
	
	// 次级地区列表
	private Area[] areas;

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

	public Area[] getAreas() {
		return areas;
	}

	public void setAreas(Area[] areas) {
		this.areas = areas;
	}
	
}

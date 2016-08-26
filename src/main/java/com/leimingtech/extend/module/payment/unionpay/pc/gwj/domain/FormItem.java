package com.leimingtech.extend.module.payment.unionpay.pc.gwj.domain;

import java.util.Map;

public class FormItem {
	
	private String type;
	
	private String label;
	
	private String rule;
	
	private String name;
	
	private String value;
	
	private String action;
	
	private Map<String, String>[] options;
	
	private String vid;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Map<String, String>[] getOptions() {
		return options;
	}
	public void setOptions(Map<String, String>[] options) {
		this.options = options;
	}
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
}

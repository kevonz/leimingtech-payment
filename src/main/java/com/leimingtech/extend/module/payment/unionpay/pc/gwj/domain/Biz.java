package com.leimingtech.extend.module.payment.unionpay.pc.gwj.domain;

public class Biz {
	
	private String code;
	
	private String action;
	
	private String title;
	
	private FormItem[] form;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public FormItem[] getForm() {
		return form;
	}
	public void setForm(FormItem[] form) {
		this.form = form;
	}
}

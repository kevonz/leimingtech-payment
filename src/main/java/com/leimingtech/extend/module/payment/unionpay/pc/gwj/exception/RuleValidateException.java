package com.leimingtech.extend.module.payment.unionpay.pc.gwj.exception;

public class RuleValidateException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public static final String REQUIRED_ERROR = "01";
	public static final String MAXLENGTH_ERROR = "02";
	public static final String MINLENGTH_ERROR = "03";
	public static final String LENGTH_ERROR = "04";
	public static final String EQUALS_ERROR = "05";
	public static final String ISDATE_ERROR = "06";
	public static final String DECIMAL_ERROR = "07";
	
	public String name;
	public String code;
	public String message;

	public RuleValidateException(String name, String code, String message){
		super(message);
		this.name = name;
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

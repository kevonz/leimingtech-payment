package com.leimingtech.extend.module.payment.unionpay.pc.gwj.domain;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.leimingtech.extend.module.payment.unionpay.pc.gwj.exception.RuleValidateException;
import com.leimingtech.extend.module.payment.unionpay.pc.gwj.util.DateUtil;

/**
 * "biz"中form里的formItem元素校验规则类.
 * 全量规则例子：
 * 	{
 *     "required": true,
 *     "maxLength": 10, 
 *     "minLength": 4,
 *     "length": 8,
 *     "equals": "${usr_num1}", // 注意为了错误消息能够正确显示，规则一般配在第一个formItem中。
 *     "isDate": "yyMM", // 为yyyyMMddHHmmssSSS中的一部分
 *     "decimal": 2 // 表示最多只有两个小数
 * 	}    
 */
public class Rule {
	
	/** 是否必填 */
	private boolean required = true; // 默认为必填，没有显示指定required为false的都为必填
	
	/** 最长长度 */
	private int maxLength;
	
	/** 最小长度 */
	private int minLength;

	/** 等长 */
	private int length;
	
	/** 等值 */
	private String equals;
	
	/** 是否时间格式 yyyyMMddHHmmssSSS格式的一部分 */
	private String isDate;
	
	/** 精度，123.46精度为2 */
	private int decimal;
	
	/**
	 * 校验用户上传的formItem值
	 * 
	 * @param label formItem的label，如"用户名"
	 * @param value 用户上送的值
	 * @param requestMap 用户传上来的qString转换的requestMap
	 */
	public void validate(String name, String label, String value, Map<String, Object> requestMap){
		validateRequired(name, label, value);
		validateMaxLength(name, label, value);
		validateMinLength(name, label, value);
		validateLength(name, label, value);
		validateEquals(name, label, value, requestMap);
		validateIsDate(name, label, value);
		validateDecimal(name, label, value);
	}
	
	/**
	 * 校验是否必填
	 */
	private void validateRequired(String name, String label, String value){
		if(!required){
			return;
		}
		
		if(StringUtils.isEmpty(value)){
			throw new RuleValidateException(name, RuleValidateException.REQUIRED_ERROR, label + "不能为空");
		}
	}
	
	/**
	 * 校验最长长度
	 */
	private void validateMaxLength(String name, String label, String value){
		if(maxLength > 0){
			if(value.length() > maxLength){
				throw new RuleValidateException(name, RuleValidateException.MAXLENGTH_ERROR, label + "输入长度不能大于" + maxLength);
			}
		}
	}
	
	
	/**
	 * 校验最小长度
	 */
	private void validateMinLength(String name, String label, String value){
		if(minLength > 0){
			if(value.length() < minLength){
				throw new RuleValidateException(name, RuleValidateException.MINLENGTH_ERROR, label + "输入长度不能小于" + minLength);
			}
		}
	}
	
	/**
	 * 校验等长
	 */
	private void validateLength(String name, String label, String value){
		if(length > 0){
			if(value.length() != length){
				throw new RuleValidateException(name, RuleValidateException.LENGTH_ERROR, label + "输入长度须等于" + length);
			}
		}
	}
	
	/**
	 * 校验等值
	 */
	private void validateEquals(String name, String label, String value,  Map<String, Object> contextMap){
		if(!StringUtils.isEmpty(equals)){
			String expectEqualItemName = equals.substring("${".length(), equals.length() -1);
			String expectValue = (String)contextMap.get(expectEqualItemName);
			if(!value.equals(expectValue)){
				throw new RuleValidateException(name, RuleValidateException.EQUALS_ERROR, label + "两次输入的值不相同");
			}
		}
	}
	
	/**
	 * 校验时间格式
	 */
	private void validateIsDate(String name, String label, String value){
		if(!StringUtils.isEmpty(isDate)){
			if(DateUtil.parseDate(value, isDate) ==null ){
				throw new RuleValidateException(name, RuleValidateException.ISDATE_ERROR, label + "时间格式非法");
			}
		}
	}
	
	/**
	 * 校验精度
	 */
	private void validateDecimal(String name, String label, String value){
		if(decimal > 0){
			
			if(StringUtils.isEmpty(value)){
				throw new RuleValidateException(name, RuleValidateException.DECIMAL_ERROR, label + "不能为空");
	    	}
			
			BigDecimal bd = new BigDecimal(value);
			if (bd.doubleValue() > 9999999999.99) {
				throw new RuleValidateException(name, RuleValidateException.DECIMAL_ERROR, label + "的值非法");
			}
	    	
	    	int index = value.indexOf('.');
	    	if(index > 0 && (value.length() - index) > (decimal + 1)){
	    		throw new RuleValidateException(name, RuleValidateException.DECIMAL_ERROR, label + "不能超过" + decimal + "位小数");
	    	}

		}
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getEquals() {
		return equals;
	}

	public void setEquals(String equals) {
		this.equals = equals;
	}

	public String getIsDate() {
		return isDate;
	}

	public void setIsDate(String isDate) {
		this.isDate = isDate;
	}

	public int getDecimal() {
		return decimal;
	}

	public void setDecimal(int decimal) {
		this.decimal = decimal;
	}
}

/**
 * 
 */
package com.leimingtech.extend.module.payment.wechat.mobile.util;

/**
 * @company 雷铭智信
 * @author 谢进伟
 * @DateTime 2015-3-24 上午10:02:29
 */
public class BasicNameValuePair extends NameValuePair {
	
	private final String name;
	private final String value;
	
	public BasicNameValuePair (String name , String value ){
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public String getValue() {
		return this.value;
	}
}

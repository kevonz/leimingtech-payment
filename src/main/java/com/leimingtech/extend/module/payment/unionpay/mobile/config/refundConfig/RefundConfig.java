/**
 * 
 */
package com.leimingtech.extend.module.payment.unionpay.mobile.config.refundConfig;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import com.leimingtech.extend.module.payment.unionpay.mobile.config.base.UnionpayBaseConfig;

/**
 * 手机支付 ——手机控件支付产品参数
 * 
 * @company 雷铭智信
 * @author 谢进伟
 * @DateTime 2015-3-29 下午12:39:38
 */
public class RefundConfig extends UnionpayBaseConfig {
	
	/**
	 * 充值后台通知地址
	 */
	private String notifyUrl_topUp;
	
	public static RefundConfig getConfig() {
		return loadConfig();
	}
	
	private static RefundConfig loadConfig() {
		// 读取参数
		ResourceBundle rb = ResourceBundle.getBundle("com.leimingtech.pinche.pay.mobile.unionpay.config.refundConfig.refundConfig");
		Enumeration<String> keys = rb.getKeys();
		Class<RefundConfig> cls = RefundConfig.class;
		Field fields[] = cls.getDeclaredFields();
		Field superFields[] = cls.getSuperclass().getDeclaredFields();
		List<Field> allField = new ArrayList<Field>();
		List<Field> asList = Arrays.asList(fields);
		List<Field> asList2 = Arrays.asList(superFields);
		allField.addAll(asList);
		allField.addAll(asList2);
		String key;
		RefundConfig wc = null;
		try {
			wc = cls.newInstance();
			while (keys.hasMoreElements()) {
				key = keys.nextElement();
				for(Field f : allField) {
					if(f.getName().toUpperCase().equals(key.toUpperCase())) {
						if(f.getType().getSimpleName() == "boolean") {
							f.set(wc , Boolean.parseBoolean(new String(rb.getString(key).getBytes("ISO8859-1") , "UTF-8")));
							break;
						} else {
							f.set(wc , new String(rb.getString(key).getBytes("ISO8859-1") , "UTF-8"));
							break;
						}
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return wc;
	}
	
	/**
	 * @return 得到 充值后台通知地址的值
	 */
	public String getNotifyUrl_topUp() {
		return notifyUrl_topUp;
	}
	
	/**
	 * @param notifyUrl_topUp
	 *            设置 充值后台通知地址值
	 */
	public void setNotifyUrl_topUp(String notifyUrl_topUp) {
		this.notifyUrl_topUp = notifyUrl_topUp;
	}
	
}

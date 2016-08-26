///**
// * 
// */
//package com.leimingtech.extend.module.payment.unionpay.mobile.config.appConsumeConfig;
//
//import java.io.UnsupportedEncodingException;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Enumeration;
//import java.util.List;
//import java.util.ResourceBundle;
//
//import com.leimingtech.extend.module.payment.unionpay.mobile.config.base.UnionpayBaseConfig;
//
///**
// * 手机支付 ——手机控件支付产品参数
// * 
// * @company 雷铭智信
// * @author 谢进伟
// * @DateTime 2015-3-29 下午12:39:38
// */
//public class AppConsumeConfig extends UnionpayBaseConfig {
//	/**
//	 * 签名证书路径 （联系运营获取两码，在CFCA网站下载后配置，自行设置证书密码并配置）
//	 */
//	private String certPath;
//	/**
//	 * #签名证书密码
//	 */
//	private String certPwd;
//	/**
//	 * 充值后台通知地址
//	 */
//	private String notifyUrl_topUp;
//	
//	public static AppConsumeConfig getConfig() {
//		return loadConfig();
//	}
//	
//	private static AppConsumeConfig loadConfig() {
//		// 读取参数
//		ResourceBundle rb = ResourceBundle.getBundle("com.leimingtech.front.pay.mobile.unionpay.config.appConsumeConfig.appConsumeConfig");
//		Enumeration<String> keys = rb.getKeys();
//		Class<AppConsumeConfig> cls = AppConsumeConfig.class;
//		Field fields[] = cls.getDeclaredFields();
//		Field superFields[] = cls.getSuperclass().getDeclaredFields();
//		List<Field> allField = new ArrayList<Field>();
//		List<Field> asList = Arrays.asList(fields);
//		List<Field> asList2 = Arrays.asList(superFields);
//		allField.addAll(asList);
//		allField.addAll(asList2);
//		String key;
//		AppConsumeConfig wc = null;
//		try {
//			wc = cls.newInstance();
//			while (keys.hasMoreElements()) {
//				key = keys.nextElement();
//				for(Field f : allField) {
//					if(f.getName().toUpperCase().equals(key.toUpperCase())) {
//						if(f.getType().getSimpleName() == "boolean") {
//							f.set(wc , Boolean.parseBoolean(new String(rb.getString(key).getBytes("ISO8859-1") , "UTF-8")));
//							break;
//						} else {
//							f.set(wc , new String(rb.getString(key).getBytes("ISO8859-1") , "UTF-8"));
//							break;
//						}
//					}
//				}
//			}
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		return wc;
//	}
//	
//	/**
//	 * @return 得到 充值后台通知地址的值
//	 */
//	public String getNotifyUrl_topUp() {
//		return notifyUrl_topUp;
//	}
//	
//	/**
//	 * @param notifyUrl_topUp
//	 *            设置 充值后台通知地址值
//	 */
//	public void setNotifyUrl_topUp(String notifyUrl_topUp) {
//		this.notifyUrl_topUp = notifyUrl_topUp;
//	}
//
//	
//	/**
//	 * @return 得到  certPath 的值
//	 */
//	public String getCertPath() {
//		return certPath;
//	}
//
//	
//	/**
//	 * @param 设置属性 certPath 的值 为 certPath
//	 */
//	public void setCertPath(String certPath) {
//		this.certPath = certPath;
//	}
//
//	
//	/**
//	 * @return 得到  certPwd 的值
//	 */
//	public String getCertPwd() {
//		return certPwd;
//	}
//
//	
//	/**
//	 * @param 设置属性 certPwd 的值 为 certPwd
//	 */
//	public void setCertPwd(String certPwd) {
//		this.certPwd = certPwd;
//	}
//	
//}

package com.leimingtech.extend.module.payment.alipay.pc.china.config;

import com.leimingtech.core.common.PropertiesLoader;

/**
 * 支付宝
 */
public class AlipayContents {
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("com/leimingtech/extend/module/payment/alipay/pc/china/config/alipay.properties");
    
    public static final String partner= propertiesLoader.getProperty("partner");//合作者身份id以2088开头由16位纯数字组成的字符串
    public static final String sellerid= propertiesLoader.getProperty("sellerid");//收款支付宝账号
    public static final String key= propertiesLoader.getProperty("key");//商户的私钥
    public static final String aplipaybackUrl= propertiesLoader.getProperty("aplipaybackUrl");//商户/收单后台接收地址 必送
    public static final String aplipayfrontUrl= propertiesLoader.getProperty("aplipayfrontUrl");//商户/收单前台接收地址 必送
    
    
}

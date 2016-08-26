package com.leimingtech.extend.module.payment.alipay.h5.config;

import com.leimingtech.core.common.PropertiesLoader;

/**
 * 支付宝h5
 */
public class Alipayh5Contents {
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("com/leimingtech/extend/module/payment/alipay/h5/config/alipayh5.properties");
    
    public static final String partner= propertiesLoader.getProperty("partner");//合作者身份id以2088开头由16位纯数字组成的字符串
    public static final String sellerid= propertiesLoader.getProperty("sellerid");//收款支付宝账号
    public static final String privatekey= propertiesLoader.getProperty("privatekey");//商户的私钥
    public static final String alipublickey= propertiesLoader.getProperty("alipublickey");//支付宝的公钥，无需修改该值
    public static final String alipayh5backUrl= propertiesLoader.getProperty("alipayh5backUrl");//商户/收单后台接收地址 必送
    public static final String alipayh5frontUrl= propertiesLoader.getProperty("alipayh5frontUrl");//商户/收单前台接收地址 必送
    
    
}

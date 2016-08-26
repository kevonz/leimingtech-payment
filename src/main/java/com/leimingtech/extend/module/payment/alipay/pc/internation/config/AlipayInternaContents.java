package com.leimingtech.extend.module.payment.alipay.pc.internation.config;

import com.leimingtech.core.common.PropertiesLoader;

/**
 * 国际支付宝
 */
public class AlipayInternaContents {
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("com/leimingtech/extend/module/payment/alipay/pc/internation/config/alipayinterna.properties");
    public static final String partner= propertiesLoader.getProperty("partner");//合作者身份id以2088开头由16位纯数字组成的字符串
    public static final String sellerid= propertiesLoader.getProperty("sellerid");//收款支付宝账号
    public static final String key= propertiesLoader.getProperty("key");//商户的私钥
    public static final String aplipayinternabackUrl= propertiesLoader.getProperty("aplipayinternabackUrl");//商户/收单后台接收地址 必送
    public static final String aplipayinternafrontUrl= propertiesLoader.getProperty("aplipayinternafrontUrl");//商户/收单前台接收地址 
    public static final String aplipayratefiledir= propertiesLoader.getProperty("aplipayratefiledir");//支付宝汇率文件存放目录
    public static final String aplipayratefilename= propertiesLoader.getProperty("aplipayratefilename");//汇率文件名称
    
    
}

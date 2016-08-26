package com.leimingtech.extend.module.payment.wechat.mobile.config;

import com.leimingtech.core.common.PropertiesLoader;

/**
 * 
 * @author ihui
 * 微信开放平台配置
 */
public class WachatMobileContent {
	    private static PropertiesLoader propertiesLoader = new PropertiesLoader("com/leimingtech/extend/module/payment/wechat/mobile/config/wxpaymobileConfig.properties");
	   
	    public static final String appid= propertiesLoader.getProperty("app_id");//API密钥，在商户平台设置
	    public static final String mchid= propertiesLoader.getProperty("mch_id");//商户号
	    public static final String appsecret= propertiesLoader.getProperty("appsecret");//应用密钥
	    public static final String apikey= propertiesLoader.getProperty("api_key");//API密钥，在商户平台设置
	    public static final String goodsName= propertiesLoader.getProperty("goodsName");//商品名称
	    public static final String goodsDetail= propertiesLoader.getProperty("goodsDetail");//商品详情描述
	    public static final String notifyUrl= propertiesLoader.getProperty("notifyUrl");//微信异步通知路径
	    public static final String notifyUrl_topUp= propertiesLoader.getProperty("notifyUrl_topUp");//微信充值异步通知路径
	    public static final String signCertpath= propertiesLoader.getProperty("signCert.path");//操作证书
	    
	    
	    
	  
	    
	    
}

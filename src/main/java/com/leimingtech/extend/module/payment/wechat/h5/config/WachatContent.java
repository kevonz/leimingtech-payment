package com.leimingtech.extend.module.payment.wechat.h5.config;

import com.leimingtech.core.common.PropertiesLoader;

/**
 * 
 * @author ihui
 * 微信公共号配置
 */
public class WachatContent {
	    private static PropertiesLoader propertiesLoader = new PropertiesLoader("com/leimingtech/extend/module/payment/wechat/h5/config/wachatcontent.properties");
	   
	    public static final String appid= propertiesLoader.getProperty("appid");//公众账号ID
	    public static final String appsecret= propertiesLoader.getProperty("appsecret");//应用密钥
	    public static final String partner= propertiesLoader.getProperty("partner");//微信支付商户号
	    public static final String apikey= propertiesLoader.getProperty("api_key");//API密钥，在商户平台设置
	    public static final String h5index= propertiesLoader.getProperty("h5index");//微商城首页
	    public static final String Oauth2back= propertiesLoader.getProperty("Oauth2back");//授权成功后回调页面
	    public static final String oauth2= propertiesLoader.getProperty("oauth2");//跳转到授权页面
	    public static final String getaccesstoken= propertiesLoader.getProperty("getaccesstoken");//获取access_token跳转
	    public static final String refreshtoken= propertiesLoader.getProperty("refreshtoken");//刷新access_token
	    public static final String getuserifo= propertiesLoader.getProperty("getuserifo");//获取用户信息
	    public static final String istoken= propertiesLoader.getProperty("istoken");//判断授权是否有效
	    public static final String backUri= propertiesLoader.getProperty("backUri");
	    public static final String updateorderstate= propertiesLoader.getProperty("updateorderstate");//修改订单状态
	    
	    public static final String signCertpath= propertiesLoader.getProperty("signCert.path");//操作证书
	    
	    
}

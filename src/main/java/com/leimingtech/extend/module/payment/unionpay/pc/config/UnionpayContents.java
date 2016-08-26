package com.leimingtech.extend.module.payment.unionpay.pc.config;

import com.leimingtech.core.common.PropertiesLoader;

public class UnionpayContents {
	/**
	 * 银联
	 */
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("com/leimingtech/extend/module/payment/unionpay/pc/config/unionpay.properties");
    public static final String MEM_ID = propertiesLoader.getProperty("acpsdk.signCert.merId");//商户号码 
    public static final String version= propertiesLoader.getProperty("version");//版本号
    public static final String encoding= propertiesLoader.getProperty("encoding");//字符集编码 默认"UTF-8"
    public static final String signMethod= propertiesLoader.getProperty("signMethod");//签名方法 01
    public static final String txnType= propertiesLoader.getProperty("txnType");//交易类型 01-消费
    public static final String txnSubType= propertiesLoader.getProperty("txnSubType");//交易子类型 01:自助消费 02:订购 03:分期付款
    public static final String bizType= propertiesLoader.getProperty("bizType");//业务类型 000201 B2C网关支付
    public static final String channelType= propertiesLoader.getProperty("channelType");//渠道类型 07-互联网渠道
    public static final String backUrl= propertiesLoader.getProperty("backUrl");//商户/收单后台接收地址 必送
    public static final String frontUrl= propertiesLoader.getProperty("frontUrl");//商户/收单前台接收地址 必送
    public static final String accessType= propertiesLoader.getProperty("accessType");//接入类型:商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
    public static final String currencyCode= propertiesLoader.getProperty("currencyCode");//交易币种
}

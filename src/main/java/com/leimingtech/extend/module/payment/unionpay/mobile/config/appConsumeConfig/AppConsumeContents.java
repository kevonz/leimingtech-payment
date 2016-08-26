package com.leimingtech.extend.module.payment.unionpay.mobile.config.appConsumeConfig;

import com.leimingtech.core.common.PropertiesLoader;

public class AppConsumeContents {
	/**
	 * 银联
	 */
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("com/leimingtech/extend/module/payment/unionpay/mobile/config/appConsumeConfig/appConsumeConfig.properties");
	public static final String currencyCode= propertiesLoader.getProperty("currencyCode");//交易币种
	public static final String channelType= propertiesLoader.getProperty("channelType");//渠道类型，07-PC，08-手机
	public static final String orderDesc= propertiesLoader.getProperty("orderDesc");//订单描述，可不上送，上送时控件中会显示该信息
	
	public static final String merId = propertiesLoader.getProperty("merId");//商户号码，请改成自己的商户号
    public static final String txnSubType = propertiesLoader.getProperty("txnSubType");////交易子类型 01:自助消费 02:订购 03:分期付款
    public static final String version= propertiesLoader.getProperty("version");//版本号
    public static final String signMethod= propertiesLoader.getProperty("signMethod");//签名方法 01
    public static final String backUrl= propertiesLoader.getProperty("backUrl");//商户/收单后台接收地址 必送
    public static final String frontUrl= propertiesLoader.getProperty("frontUrl");//商户/收单前台接收地址 必送
    public static final String notifyUrl_topUp= propertiesLoader.getProperty("notifyUrl_topUp");//充值后台通知地址
    public static final String encoding= propertiesLoader.getProperty("encoding");//字符集编码 默认"UTF-8"
    public static final String reqReserved= propertiesLoader.getProperty("reqReserved");//字符集编码 默认"UTF-8"
    
    public static final String txnType= propertiesLoader.getProperty("txnType");//请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
    public static final String bizType= propertiesLoader.getProperty("bizType");//业务类型 000201 B2C网关支付
    public static final String accessType= propertiesLoader.getProperty("accessType");//接入类型:商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
    public static final String certPath= propertiesLoader.getProperty("certPath");//签名证书路径 （联系运营获取两码，在CFCA网站下载后配置，自行设置证书密码并配置）
    public static final String certPwd= propertiesLoader.getProperty("certPwd");//签名证书密码
    
    
    
}

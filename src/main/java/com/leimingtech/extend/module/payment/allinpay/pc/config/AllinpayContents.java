package com.leimingtech.extend.module.payment.allinpay.pc.config;

import com.leimingtech.core.common.PropertiesLoader;

public class AllinpayContents {
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("com/leimingtech/extend/module/payment/allinpay/pc/config/allinpay.properties");
    public static final String inputCharset = propertiesLoader.getProperty("inputCharset");//
    public static final String pickupUrl= propertiesLoader.getProperty("pickupUrl");//
    public static final String receiveUrl= propertiesLoader.getProperty("receiveUrl");//
    public static final String version= propertiesLoader.getProperty("version");//
    public static final String language= propertiesLoader.getProperty("language");//
    public static final String signType= propertiesLoader.getProperty("signType");//
    public static final String merchantId= propertiesLoader.getProperty("merchantId");//
    public static final String orderCurrency= propertiesLoader.getProperty("orderCurrency");//
    public static final String payType= propertiesLoader.getProperty("payType");//
    public static final String tradeNature= propertiesLoader.getProperty("tradeNature");//
//   public static final String orderDatetime= propertiesLoader.getProperty("orderDatetime");//
    public static final String IssuerId= propertiesLoader.getProperty("IssuerId");//
    public static final String PayerName= propertiesLoader.getProperty("PayerName");//
    public static final String PayerEmail= propertiesLoader.getProperty("PayerEmail");//
    public static final String PayerTelephone= propertiesLoader.getProperty("PayerTelephone");//
    public static final String PayerIDCard= propertiesLoader.getProperty("PayerIDCard");//
    public static final String Pid= propertiesLoader.getProperty("Pid");//
    public static final String OrderExpireDatetime= propertiesLoader.getProperty("OrderExpireDatetime");//
    public static final String ProductName= propertiesLoader.getProperty("ProductName");//
}

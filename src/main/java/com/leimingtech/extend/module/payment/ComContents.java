package com.leimingtech.extend.module.payment;

import com.leimingtech.core.common.PropertiesLoader;

/**
 * 工程url
 */
public class ComContents {
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("com/leimingtech/extend/module/payment/comment.properties");
    public static final String payserviceurl= propertiesLoader.getProperty("payserviceurl");//初始路径url
    public static final String refundserviceurl= propertiesLoader.getProperty("refundserviceurl");//平台退款回调专用地址
}

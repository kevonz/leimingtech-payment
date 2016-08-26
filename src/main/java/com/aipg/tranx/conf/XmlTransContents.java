package com.aipg.tranx.conf;

import com.leimingtech.core.common.PropertiesLoader;

public class XmlTransContents {
	
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("com/aipg/tranx/conf/XmlTransProperties.properties");
	
	public static final String ACCOUNT_NAME = propertiesLoader.getProperty("ACCOUNT_NAME");
	public static final String  ACCOUNT_NO = propertiesLoader.getProperty("ACCOUNT_NO");
	public static final String  ACCOUNT_PROP= propertiesLoader.getProperty("ACCOUNT_PROP");
	public static final String AMOUNT = propertiesLoader.getProperty("AMOUNT");
	public static final String  BANK_CODE= propertiesLoader.getProperty("BANK_CODE");
	public static final String CURRENCY = propertiesLoader.getProperty("CURRENCY");
	public static final String CUST_USERID = propertiesLoader.getProperty("CUST_USERID");
	public static final String  TEL = propertiesLoader.getProperty("TEL");

}

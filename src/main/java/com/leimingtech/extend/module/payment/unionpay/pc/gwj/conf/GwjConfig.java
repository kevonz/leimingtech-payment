package com.leimingtech.extend.module.payment.unionpay.pc.gwj.conf;

import java.io.InputStream;
import java.util.PropertyResourceBundle;


public class GwjConfig {
	
	// 缴费应用访问地址
	public static String GWJ_URL;
	
	// UPMP分配的client id
	public static String GWJ_CID;
	
	// UPMP分配的授权码
	public static String GWJ_CODE;
	
	private static final String KEY_GWJ_URL = "gwj.url";
	private static final String KEY_CID = "gwj-cid";
	private static final String KEY_CODE = "gwj-code";
	
	private static final String CONF_FILE_NAME = "acp_sdk.properties";//
	
	static {
		
        try {
			InputStream fis = GwjConfig.class.getClassLoader().getResourceAsStream(CONF_FILE_NAME);
			PropertyResourceBundle props = new PropertyResourceBundle(fis);
			GWJ_URL = props.getString(KEY_GWJ_URL);
			GWJ_CID = props.getString(KEY_CID);
			GWJ_CODE = props.getString(KEY_CODE);
        } catch(Exception e) {
        	e.printStackTrace();
        }
    }
	
	public static void setConfig(String gwjUrl, String cid, String code){
		GWJ_URL = gwjUrl;
		GWJ_CID = cid;
		GWJ_CODE = code;
	}
}

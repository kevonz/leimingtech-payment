package com.leimingtech.extend.module.payment.unionpay.pc.sdksample.back;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKConstants;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKUtil;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SecureUtil;

public class Common {

	/**
	 * 持卡人信息域操作
	 * 
	 * @param encoding
	 *            编码方式
	 * @return base64后的持卡人信息域字段
	 */
	public static String getCustomer(Map<String, ?> contentData,
			String encoding) {

		String pin = "123456"; //密码明文请从读卡器让用户输入采集。
		String expired = "1249"; //有效期明文从读卡器读取，IC卡送，磁条卡不送。

		pin = SDKUtil.encryptPin(contentData.get("accNo").toString(), pin, encoding);
		expired = SDKUtil.encryptAvailable(expired, encoding);
		
		Map<String, String> customerInfoMap = new HashMap<String, String>(); 
		customerInfoMap.put("pin", pin);
//		customerInfoMap.put("expired", expired); //IC卡送，磁条卡不送。
		
		StringBuffer sf = new StringBuffer();
		String customerInfo = sf.append(SDKConstants.LEFT_BRACE)
		.append(SDKUtil.coverMap2String(customerInfoMap))
		.append(SDKConstants.RIGHT_BRACE).toString();
		
		try {
			return new String(SecureUtil.base64Encode(sf.toString().getBytes(
					encoding)), encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return customerInfo;
	}

}

package com.leimingtech.extend.module.payment.unionpay.pc.sdksample.ic;

import java.util.HashMap;
import java.util.Map;

import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKConfig;

/**
 * 名称：有卡支付产品<br>
 * 功能：6.2.2　银行卡余额查询交易<br>
 * 版本：5.0<br>
 * 日期：2014-03<br>
 * 作者： 中国银联ACP团队<br>
 * 版权： 中国银联<br>
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考。<br>
 */
public class Form12_6_2_2 extends DemoBase {

	public static Map<String, Object> setFormDate() {

		Map<String, Object> contentData = new HashMap<String, Object>();

		contentData.put("version", "5.0.0");
		contentData.put("encoding", "UTF-8");
		contentData.put("signMethod", "01");
		contentData.put("txnType", "71");
		contentData.put("txnSubType", "00");
		contentData.put("bizType", "000000");
		contentData.put("channelType", "08");
		contentData.put("accessType", "0"); 
		contentData.put("merId", "802290049000180");
		contentData.put("orderId", getOrderId());
		contentData.put("txnTime", getCurrentTime());
		contentData.put("currencyCode", "156"); //币种
		contentData.put("accNo", "6225000000000253");
		contentData.put("reqReserved", "透传信息"); //请求方保留域，相关通知和查询接口原样返回，内容符合规范即可，如出现&、=等字符可能会影响报文解析，尽量不要出现。
		contentData.put("customerInfo", getCustomer(contentData, encoding)); //请参考getCustomer方法内部，相关要素从读卡器采集。
		contentData.put("cardTransData", getCardTransData(contentData, encoding)); //请参考getCardTransData方法内部，相关要素从读卡器采集。

		return contentData;
	}

	public static void main(String[] args) {

		/**
		 * <pre>
		 * 参数初始化 
		 * 1.以java main方法运行时,必须每次都执行加载配置文件 
		 * 2.以java web应用程序运行时,配置文件初始化代码可以写在listener中
		 * </pre>
		 */
		SDKConfig.getConfig().loadPropertiesFromSrc();

		/**
		 * 交易请求url 从配置文件读取
		 */
		String requestBackUrl = SDKConfig.getConfig().getCardRequestUrl();
		Map<String, String> resmap = submitDate(setFormDate(), requestBackUrl);
		System.out.println(resmap.toString());
	}}

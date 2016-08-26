package com.leimingtech.extend.module.payment.unionpay.pc.sdksample.multicert;

import java.util.HashMap;
import java.util.Map;

import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKConfig;

/**
 * 本示例只是用来说明如何使用多张私钥证书进行交易开发<br>
 * 
 * <pre>
 * 商户如果需要使用到多张私钥证书(.pfx)来进行交易开发，
 * 其实质与单张私钥证书并无差别， 只是在签名方法的调用处使用如下API方法
 * SDKUtil.signByCertInfo(submitFromData, encoding, certPath, certPwd);
 * 这样程序就不依赖于配置文件中所指定的私钥证书，
 * 而是完全依赖于商户自己传入的证书路径进行报文签名
 * 其中:submitFromData--待签名的报文对象
 *      encoding--报文编码格式
 *      certPath--证书绝对路径
 *      certPwd--证书密码
 * </pre>
 * 
 * @author cm.he
 * 
 */
public class MultiCertExample extends DemoBase {

	/**
	 * 消费交易表单填写
	 * 
	 * @return
	 */

	public static Map<String, Object> setFormDate() {

		Map<String, Object> contentData = new HashMap<String, Object>();

		// 固定填写
		contentData.put("version", "5.0.0");// M
		// 默认取值：UTF-8
		contentData.put("encoding", "UTF-8");// M
		// 取值：01（表示采用的是RSA）
		contentData.put("signMethod", "01");// M
		// 取值：01
		contentData.put("txnType", "01");// M
		// 01：自助消费，通过地址的方式区分前台消费和后台消费（含无跳转支付）03：分期付款
		contentData.put("txnSubType", "01");// M
		// 业务类型 000201 -
		contentData.put("bizType", "000201");// M
		// 渠道类型 07
		contentData.put("channelType", "07");// M
		// 后台返回商户结果时使用，如上送，则发送商户后台交易结果通知
		contentData.put("backUrl", backUrl);// M
		// 0：普通商户直连接入2：平台类商户接入
		contentData.put("accessType", "0");// M
		// 　商户代码
		contentData.put("merId", "802290049000180");// M
		// 商户类型为平台类商户接入时必须上送
		contentData.put("subMerId", "");// C
		// 商户类型为平台类商户接入时必须上送
		contentData.put("subMerName", "");// C
		// 商户类型为平台类商户接入时必须上送
		contentData.put("subMerAbbr", "");// C
		// 商户端生成
		contentData.put("orderId", getOrderId());// M
		// 订单发送时间
		contentData.put("txnTime", getCurrentTime());// M
		// 帐号类型 01：银行卡 02：存折 03：IC卡 默认取值：01
		// 取值“03”表示以IC终端发起的IC卡交易，IC作为普通银行卡进行支付时，此域填写为“01”
		contentData.put("accType", "");// C
		// 1、 后台类消费交易时上送全卡号或卡号后4位 2、 跨行收单且收单机构收集银行卡信息时上送、 3、前台类交易可通过配置后返回，卡号可选上送
		contentData.put("accNo", "");// C
		// 交易单位为分
		contentData.put("txnAmt", "1");// M
		// 默认为156
		contentData.put("currencyCode", "156");// M
		// 1、后台类消费交易时上送2、跨行收单且收单机构收集银行卡信息时上送3、认证支付2.0，后台交易时可选Key=value格式（具体填写参考数据字典）
		contentData.put("customerInfo", getCustomer(encoding));// C
		// PC1、前台类消费交易时上送2、认证支付2.0，后台交易时可选
		contentData.put("orderTimeout", "");// O
		// PC超过此时间用户支付成功的交易，不通知商户，系统自动退款，大约5个工作日金额返还到用户账户
		contentData.put("payTimeout", "");// O
		// 　
		contentData.put("termId", "");// O
		// 商户自定义保留域，交易应答时会原样返回
		contentData.put("reqReserved", "");// O
		contentData.put("reserved", "");// O
		// 格式如下：{子域名1=值&子域名2=值&子域名3=值}
		contentData.put("riskRateInfo", "");// O
		// 当使用银联公钥加密密码等信息时，需上送加密证书的CertID；说明一下？目前商户、机构、页面统一套
		contentData.put("encryptCertId", "");// C
		// 分期付款交易，商户端选择分期信息时，需上送 组合域，填法见数据元说明
		contentData.put("instalTransInfo", "");// C
		// C 取值参考数据字典
		contentData.put("defaultPayType", "");// O
		// C当帐号类型为02-存折时需填写在前台类交易时填写默认银行代码，支持直接跳转到网银商户发卡银行控制系统应答返回
		contentData.put("issInsCode", "");// O
		// 仅仅pc使用，使用哪种支付方式 由收单机构填写，取值为以下内容的一种或多种，通过逗号（，）分割。取值参考数据字典
		contentData.put("supPayType", "");// O
		// 移动支付业务需要上送
		contentData.put("userMac", "");// O
		// 前台交易，有IP防钓鱼要求的商户上送
		contentData.put("customerIp", "");// C
		// 绑定标识号
		contentData.put("bindId", "");// C
		// 支付卡类型
		contentData.put("payCardType", "");// C
		// 有卡交易必填有卡交易信息域
		contentData.put("cardTransData",
				getCardTransData(contentData, encoding));// C
		// 移动支付上送
		contentData.put("orderDesc", "");// C

		return contentData;
	}

	public static void main(String[] args) {

		/**
		 * 参数初始化 在java main 方式运行时必须每次都执行加载<br>
		 * 如果是在web应用程序里,这个方法可在监听器中初始化到缓存中
		 */
		SDKConfig.getConfig().loadPropertiesFromSrc();

		/**
		 * 交易请求url 从配置文件读取
		 */
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl();
		/**
		 * 商户在使用多证书的环境下，每笔交易所使用的证书由商户自行决定，而不依赖acp_sdk.properties中的配置
		 */
		// 第一笔交易请求,传入第一个私钥证书
		Map<String, String> resmap1 = submitDate(setFormDate(), requestBackUrl,
				"d:\\certs\\106660149170027_000000.pfx", "000000");
		System.out.println(resmap1.toString());
		// 第二笔交易请求,传入第二个私钥证书
		Map<String, String> resmap2 = submitDate(setFormDate(), requestBackUrl,
				"d:\\certs\\00201000_111111.pfx", "111111");
		System.out.println(resmap2.toString());
		// 第二笔交易请求,传入第二个私钥证书
		//Map<String, String> resmap3 = submitDate(setFormDate(), requestBackUrl,
		//		"d:\\certs\\PM_700000000000001_acp.pfx", "000000");
		System.out.println(resmap2.toString());
		Map<String, String> resmap4 = submitDate(setFormDate(), requestBackUrl,
				"d:\\certs\\106660149170027_000000.pfx", "000000");
		System.out.println(resmap4.toString());
		
		
		
		// 第二笔交易请求,传入第二个私钥证书
		//Map<String, String> resmap8 = submitDate(setFormDate(), requestBackUrl,
		//		"d:\\certs\\PM_700000000000001_acp.pfx", "000000");
		System.out.println(resmap2.toString());
		
		// 第二笔交易请求,传入第二个私钥证书
		//Map<String, String> resmap9 = submitDate(setFormDate(), requestBackUrl,
		//		"d:\\certs\\PM_700000000000001_acp.pfx", "000000");
		System.out.println(resmap2.toString());

	}
}

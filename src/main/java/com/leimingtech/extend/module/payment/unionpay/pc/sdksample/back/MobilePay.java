package com.leimingtech.extend.module.payment.unionpay.pc.sdksample.back;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.leimingtech.extend.module.payment.unionpay.pc.sdk.HttpClient;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKConfig;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKConstants;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKUtil;

public class MobilePay {
	public static void main(String[] args) {
		// 字符集编码
		String encoding = "UTF-8";
		// 通信状态应答码
		int status = 0;
		// 返回结果
		String result = "";

		/**
		 * 初始化acp_sdk.properties配置文件
		 */
		SDKConfig.getConfig().loadPropertiesFromSrc();
		// 请求url
		String requestUrl = SDKConfig.getConfig().getBackRequestUrl();

		/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", "5.0.0");
		// 字符集编码
		data.put("encoding", encoding);
		// 证书ID
		// data.put("certId", "31692114440333550101559775639582427889");
		// 签名
		// data.put("signature", "");
		// 签名方法 RSA
		data.put("signMethod", "01");
		// 交易类型
		data.put("txnType", "01");
		// 交易子类型 01:自助消费 02:订购 03:分期付款
		data.put("txnSubType", "01");
		// 业务类型
		data.put("bizType", "000000");
		// 渠道类型
		data.put("channelType", "08");
		// 前台通知地址
		// data.put("frontUrl", "");
		// 后台通知地址
		data.put("backUrl", "http://localhost:8080/ACPTest/acp_back_url.do");
		// 接入类型 商户:0 收单:1
		data.put("accessType", "0");
		// 收单机构代码
		data.put("acqInsCode", "");
		// 商户类别
		data.put("merCatCode", "");
		// 商户代码
		data.put("merId", "898340183980105");
		// 商户名称
		data.put("merName", "");
		// 商户简称
		data.put("merAbbr", "");
		// 二级商户代码
		data.put("subMerId", "");
		// 二级商户名称
		data.put("subMerName", "");
		// 二级商户简称
		data.put("subMerAbbr", "");
		// 订单号 <注：订单号如果自己手动生成时,请每次执行之前，稍做更改以防订单号重复导致交易失败>
		data.put("orderId", "34010105078112");
		// 订单发送时间 <注：订单发送时间如果自己手动生成时,请每次执行之前，稍做更改以防与当前时间差距较大导致交易失败>
		data.put("txnTime", "20140714120021");
		// 账号类型 01
		data.put("accType", "");
		// 账号不加密 9555542160000001
		data.put("accNo", "");
		// 账号加密
		// data.put("accNo",MpiUtil.encrptPan("9555542160000001", encoding));
		// 交易金额 分
		data.put("txnAmt", "1");
		// 交易币种
		data.put("currencyCode", "156");
		// 此卡人身份信息
		data.put("customerInfo", Common.getCustomer(data,encoding));
		// 订单接收超时时间（防钓鱼使用）
		data.put("orderTimeout", "");
		// 订单支付超时时间
		data.put("payTimeout", "");
		// 终端号
		data.put("termId", "");
		// 请求方保留域
		data.put("reqReserved", "");
		// 保留域
		data.put("reserved", "");
		// 风险信息域
		data.put("riskRateInfo", "");
		// 加密证书ID
		data.put("encryptCertId", "");
		// 失败交易前台跳转地址
		// data.put("frontFailUrl", "");
		// 分期付款信息域
		data.put("instalTransInfo", "");
		// 默认支付方式
		data.put("defaultPayType", "");
		// 发卡机构代码
		data.put("issInsCode", "");
		// 支持支付方式
		data.put("supPayType", "");
		// 终端信息域
		data.put("userMac", "");
		// 持卡人IP
		data.put("customerIp", "");
		// 绑定标识号
		data.put("bindId", "");
		// 支付卡类型
		data.put("payCardType", "0");
		// 安全类型
		data.put("securityType", "");
		// 有卡交易信息域
		data.put("cardTransData", "");
		// VPC交易信息域
		data.put("vpcTransData", "");
		// 订单描述
		data.put("orderDesc", "");

		// 若报文中的数据元标识的key对应的value为空，不上送该报文域
		Map<String, String> request = new HashMap<String, String>();
		request.putAll(data);
		Set<String> set = data.keySet();
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (null == data.get(key) || "".equals(data.get(key))) {
				request.remove(key);
			}
		}
		/**
		 * 对请求报文进行签名
		 */
		SDKUtil.sign(request, encoding);

		/**
		 * HTTP通信发送请求报文
		 */
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);

		try {
			status = hc.send(request, encoding);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (200 == status) {
			// 通信成功,获取返回报文
			result = hc.getResult();
			/**
			 * 打印返回报文
			 */
			System.out.println("返回报文=[" + result + "]");

			if (null != result && !"".equals(result)) {
				// 将返回结果转换为map
				Map<String, String> resData = SDKUtil
						.convertResultStringToMap(result);
				String respcode = resData.get(SDKConstants.param_respCode);
				System.out.println("返回报文中应答码respCode=[" + respcode + "]");
				// 返回报文中不包含UPOG,表示Server端正确接收交易请求,则需要验证Server端返回报文的签名
				/**
				 * 验证返回报文签名
				 */
				if (SDKUtil.validate(resData, encoding)) {
					System.out.println("商户端验证签名成功");
					/**
					 * 商户端根据返回报文内容处理自己的业务逻辑 
					 */

					// 获取 移动支付的 tn

					//String tn = resData.get("tn");
					/**
					 * 根据 tn 发起推送订单交易流程
					 */
				} else {
					System.out.println("商户端验证签名失败");
				}

			}
		} else {
			// 通信失败,获取返回报文
			result = hc.getResult();
			/**
			 * 打印返回报文
			 */
			System.out.println("通信失败.");
			System.out.println("返回报文=[" + result + "]");
		}

	}
}

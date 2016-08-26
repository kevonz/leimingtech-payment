package com.leimingtech.extend.module.payment.unionpay.pc.sdksample.back;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.leimingtech.extend.module.payment.unionpay.pc.sdk.CertUtil;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.HttpClient;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKConfig;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKUtil;

/**
 * 名称：同步商户后续类交易（包括余额查询、实名认证等后台交易） 功能： 类属性： 方法调用 版本：5.0 日期：2014-07 作者：中国银联ACP团队
 * 版权：中国银联 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
 * */

public class SyncBackTrans{

	public static void main(String[] args) {
		// 字符集编码
		String encoding = "UTF-8";
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
		data.put("txnType", "71");
		// 交易子类型
		data.put("txnSubType", "00");
		// 业务类型
		data.put("bizType", "000000");
		// 渠道类型
		data.put("channelType", "08");
		// 前台通知地址
		data.put("frontUrl", "http://localhost:8080/ACPTest/acp_front_url.do");
		// 接入类型 商户:0 收单:1
		data.put("accessType", "0");
		// 商户代码
		data.put("merId", "898340183980105");
		// 收单机构代码
		data.put("acqInsCode", "");
		// 商户类别
		data.put("merCatCode", "");
		// 商户名称
		data.put("merName", "");
		// 商户简称
		data.put("merAbbr", "");
		// 二级商户代码
		data.put("subMerId", "");
		// 二级商户名称
		data.put("subMerName", "");
		// 订单号
		data.put("orderId", "34010105078112");// 订单号如果自己手动生成时,请每次执行之前，稍做更改以防订单号重复导致交易失败
		// 订单发送时间
		data.put("txnTime", "201407150101240984378");// 订单发送时间如果自己手动生成时,请每次执行之前，稍做更改以防与当前时间差距较大导致交易失败
		// 交易币种
		data.put("currencyCode", "156");
		// 账号类型 03
		data.put("accType", "01");
		// 账号不加密
		data.put("accNo", "9555542160000001");
		// 账号加密
		// data.put("accNo",MpiUtil.encryptPan("9555542160000001", encoding));
		// 银行卡验证信息及身份信息
		data.put("customerInfo", Common.getCustomer(data,encoding));
		// 请求方保留域
		data.put("reqReserved", "");
		// 保留域
		data.put("reserved", "");
		// 加密证书ID
		data.put("encryptCertId", CertUtil.getEncryptCertId());
		// 终端信息域
		data.put("userMac", "");
		// 安全类型
		data.put("securityType", "");
		// 有卡交易信息域
		data.put("cardTransData", "");

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
		 * 签名
		 */
		SDKUtil.sign(request, encoding);
		/**
		 * 发送
		 */
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		try {
			int status = hc.send(request, encoding);
			if (200 == status) {
				result = hc.getResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * 验证签名
		 */
		if (null != result && !"".equals(result)) {
			// 将返回结果转换为map
			Map<String, String> resData = SDKUtil
					.convertResultStringToMap(result);
			if (SDKUtil.validate(resData, encoding)) {
				System.out.println("验证签名成功");
			} else {
				System.out.println("验证签名失败");
			}
			// 打印返回报文
			System.out.println(result);
		}
	}
}

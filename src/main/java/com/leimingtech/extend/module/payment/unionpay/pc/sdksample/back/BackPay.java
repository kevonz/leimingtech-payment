package com.leimingtech.extend.module.payment.unionpay.pc.sdksample.back;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.leimingtech.extend.module.payment.unionpay.pc.sdk.HttpClient;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKConfig;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKConstants;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKUtil;

/**
 * 名称：后台支付类 (消费) 功能： 类属性： 方法调用 版本：5.0 日期：2014-07 作者：中国银联ACP团队 版权：中国银联
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
 * */

public class BackPay{

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
		// 签名方法 RSA
		data.put("signMethod", "01");
		// 交易类型
		data.put("txnType", "01");
		// 交易子类型 01:自助消费 02:订购 03:分期付款
		data.put("txnSubType", "01");
		// 业务类型
		data.put("bizType", "000000");
		// 渠道类型
		data.put("channelType", "07");
		// 后台通知地址
		data.put("backUrl", "http://localhost:8080/ACPTest/acp_back_url.do");
		// 接入类型 商户:0 收单:1
		data.put("accessType", "0");
		// 商户代码
		data.put("merId", "802290049000180");
		// 订单号 <注：订单号如果自己手动生成时,请每次执行之前，稍做更改以防订单号重复导致交易失败>
		data.put("orderId", SDKUtil.generateOrderId());
		// 订单发送时间 <注：订单发送时间如果自己手动生成时,请每次执行之前，稍做更改以防与当前时间差距较大导致交易失败>
		data.put("txnTime", SDKUtil.generateTxnTime());
		// 账号
		data.put("accNo", "6225000000000253");
		// 交易金额 分
		data.put("txnAmt", "1");
		// 交易币种
		data.put("currencyCode", "156");
		// 此卡人身份信息
		data.put("customerInfo", Common.getCustomer(data,encoding));

		// 若报文中的数据元标识的key对应的value为空，不上送该报文域
		Map<String, String> request = new HashMap<String, String>();
		request.putAll(data);
		Set<String> set = data.keySet();
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (data.get(key) == null || "".equals(data.get(key))) {
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
				if (!respcode.startsWith("UPOG")) {
					// 返回报文中不包含UPOG,表示Server端正确接收交易请求,则需要验证Server端返回报文的签名
					/**
					 * 验证返回报文签名
					 */
					if (SDKUtil.validate(resData, encoding)) {
						System.out.println("商户端验证签名成功");
						/**
						 * 商户端根据返回报文内容处理自己的业务逻辑
						 */
					} else {
						System.out.println("商户端验证签名失败");
					}
				} else {
					// 返回报文中包含UPOG,表示统一前置正确接收,但Server端未正确接收交易请求
					System.out.println("交易失败.");
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

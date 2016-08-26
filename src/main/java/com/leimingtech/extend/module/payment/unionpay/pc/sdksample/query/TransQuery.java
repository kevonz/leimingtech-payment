package com.leimingtech.extend.module.payment.unionpay.pc.sdksample.query;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.leimingtech.extend.module.payment.unionpay.pc.sdk.HttpClient;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKConfig;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKUtil;

/**
 * 名称：商户查询类 功能： 类属性： 方法调用 版本：5.0 日期：2014-07 作者：中国银联ACP团队 版权：中国银联
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
 * */

public class TransQuery {
	
	public static void main(String[] args) {
		// 字符集编码
		String encoding = "UTF-8";
		// 返回结果
		String result = "";
		/**
		 * 初始化mpi.properties配置文件
		 */
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
		// 请求url			
		String requestUrl =  SDKConfig.getConfig().getBackRequestUrl();
		/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", "5.0.0");
		// 字符集编码
		data.put("encoding", encoding);
		// 证书ID
//		data.put("certId", "31692114440333550101559775639582427889");
		// 签名
//		data.put("signature", "");
		// 签名方法
		data.put("signMethod", "01");
		// 交易类型 取值:同原始交易
		data.put("txnType", "01");
		// 交易子类型 同原交易
		data.put("txnSubType", "01");
		// 产品类型
		data.put("bizType", "000000");
		// 收单机构代码 ，直连商户不需要填写
		data.put("acqInsCode", "");
		// 接入类型 商户:0 收单:1
		data.put("accessType", "0");
		// 商户代码
		data.put("merId", "210440383989420");
		// 订单发送时间
		data.put("txnTime", "20140715052503");// 请填入原交易订单发送时间
		// 订单号
		data.put("orderId", "20140712152503abcde");// 请填入原交易订单号
		// 交易查询流水号
		data.put("queryId", "");
		// 保留域
		data.put("reserved", "");

		//若报文中的数据元标识的key对应的value为空，不上送该报文域
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
		
		System.out.println("报文发送后的响应信息：  "+result);
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

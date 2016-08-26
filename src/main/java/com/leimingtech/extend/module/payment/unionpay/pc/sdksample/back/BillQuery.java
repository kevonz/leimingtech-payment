package com.leimingtech.extend.module.payment.unionpay.pc.sdksample.back;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.leimingtech.extend.module.payment.unionpay.pc.sdk.HttpClient;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKConfig;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKUtil;

/**
 * 名称： 账单查询类交易    功能： 类属性： 方法调用 版本：5.0 日期：2014-07 作者：中国银联ACP团队
 * 版权：中国银联 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
 * */
public class BillQuery {

	public static void main(String[] args) throws IOException {
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
		//data.put("certId", "31692114440333550101559775639582427889");
		// 签名
		//data.put("signature", "");
		// 签名方法 RSA
		data.put("signMethod", "01");
		// 交易类型
		data.put("txnType", "73");
		// 交易子类型 默认:00
		data.put("txnSubType", "01");
		// 业务类型
		data.put("bizType", "000000");
		// 渠道类型
		data.put("channelType", "08");
		// 接入类型 商户:0 收单:1
		data.put("accessType", "0");
		// 收单机构代码
		data.put("acqInsCode", "");
		// 商户代码
		data.put("merId", "898340183980105");//105550149170027
		// 商户类别
		data.put("merCatCode", "");
		// 商户名称
		data.put("merName", "");
		// 商户简称
		data.put("merAbbr", "");
		// 二级商户代码
		data.put("subMerId", "");
		// 二级商户全称
		data.put("subMerName", "");
		// 订单号 <注：订单号如果自己手动生成时,请每次执行之前，稍做更改以防订单号重复导致交易失败>
		data.put("orderId", "34010105078112");//c94c679ed6c42dfb66bbe8dc8dd2cd7
		// 订单发送时间 <注：订单发送时间如果自己手动生成时,请每次执行之前，稍做更改以防与当前时间差距较大导致交易失败>
		data.put("txnTime", "20140717120021");
		
		// 账单类型
		data.put("billType", "0100");
		// 账单号码
		data.put("billNo", "123456789012");
		// 地区代码
		data.put("districtCode", "2900");
		// 附加地区代码
		data.put("additionalDistrictCode", "");
		// 账单月份
		data.put("billMonth", "");
		
		String a = "a";
		String[] b = new String[]{"b1","b2","b3"};
		
		JSONArray jsonArr = new JSONArray();
		for (String obj : b) {
			JSONObject json = new JSONObject();
			json.put("key1", a);
			json.put("key2", obj);
			jsonArr.add(json);
		}
		
		// 账单查询要素	
		data.put("billQueryInfo", jsonArr.toString());
		
//		String bill = data.get("billQueryInfo");
//		//将字符串的值转换为map结构
//		Map<String, String> map = SDKUtil.convertResultStringToMap(bill);
//		//将map结构的数据转换为JSON结构
//		String billQueryInfo = new ObjectMapper().writeValueAsString(map);
//		// 重新给账单查询要素赋值（JSON报文结构）
//		data.put("billQueryInfo", billQueryInfo);
		
		System.out.println("== Json格式的数据 ==" + data.get("billQueryInfo"));
		// 请求方保留域
		data.put("reqReserved", "");
		// 保留域
		data.put("reserved", "");

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
			System.out.println("打印返回报文：" + result);
		}
	}
}

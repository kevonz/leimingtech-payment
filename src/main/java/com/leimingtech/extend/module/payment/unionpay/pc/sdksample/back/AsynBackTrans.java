package com.leimingtech.extend.module.payment.unionpay.pc.sdksample.back;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.leimingtech.extend.module.payment.unionpay.pc.sdk.HttpClient;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.LogUtil;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKConfig;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKUtil;

/**
 * 名称：异步商户后续类交易（包括退货、消费撤销等后台资金交易） 功能： 类属性： 方法调用 版本：5.0 日期：2014-07 作者：中国银联ACP团队
 * 版权：中国银联 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
 * */

public class AsynBackTrans {

	public Map<String, String> process() {
		// 字符集编码
		String encoding = "UTF-8";
		// 返回结果
		String resString = "";
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
		data.put("txnType", "04");
		// 交易子类型 默认:00
		data.put("txnSubType", "00");
		// 业务类型
		data.put("bizType", "000000");
		// 渠道类型
		data.put("channelType", "07");
		// 后台通知地址
		data.put("backUrl", "http://localhost:8080/ACPTest/acp_back_url.do");
		// 接入类型 商户:0 收单:1
		data.put("accessType", "0");
		// 收单机构代码
		data.put("acqInsCode", "");
		// 商户类别
		data.put("merCatCode", "");
		// 商户号码
		data.put("merId", "940410759997007");// 105550149170027
		// 商户名称
		data.put("merName", "");
		// 商户简称
		data.put("merAbbr", "");
		// 二级商户代码
		data.put("subMerId", "");
		// 二级商户全称
		data.put("subMerName", "");
		// 二级商户简称
		data.put("subMerAbbr", "");
		// 订单号 <注：订单号如果自己手动生成时,请每次执行之前，稍做更改以防订单号重复导致交易失败>
		data.put("orderId", "355847812026");
		// 原始交易流水号 同原始消费交易的queryId
		data.put("origQryId", "201410170924561000408");// 201407090101240984378
		// 订单发送时间 <注：订单发送时间如果自己手动生成时,请每次执行之前，稍做更改以防与当前时间差距较大导致交易失败>
		data.put("txnTime", "20141017092456");
		// 交易金额 分
		data.put("txnAmt", "1");
		// 终端号
		data.put("termId", "");
		// 请求方保留域
		data.put("reqReserved", "");
		// 保留域
		data.put("reserved", "");
		// VPC交易信息域
		data.put("vpcTransData", "");

		// 若报文中的数据元标识的key对应的value为空，不上送该报文域
		Map<String, String> request = new HashMap<String, String>();
		request.putAll(data);
		Set<String> set = data.keySet();
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if ("".equals(data.get(key))) {
				request.remove(key);
			}
		}
		/**
		 * 签名
		 */
		SDKUtil.sign(request, encoding);
		LogUtil.writeLog("交易请求报文如下：");
		LogUtil.printRequestLog(request);
		/**
		 * 发送
		 */
		LogUtil.writeLog("后台交易地址== ：" + requestUrl);
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		try {
			int status = hc.send(request, encoding);
			if (200 == status) {
				resString = hc.getResult();
			} else {
				LogUtil.writeLog("发送请求报文失败(系统未开放或暂时关闭);请稍后再试");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.writeLog("发送请求报文失败(系统未开放或暂时关闭);请稍后再试");
		}

		Map<String, String> resultMap = new HashMap<String, String>();
		String respcode = "";
		// 将返回结果转换为map
		Map<String, String> resData = null;
		try {
			resData = SDKUtil.convertResultStringToMap(resString);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("respCode", "10");// 响应码 --M
			resultMap.put("respMsg", "报文格式错误");// 应答信息 --M
			LogUtil.printResponseLog("返回报文： " + resultMap);
			return resultMap;
		}
		respcode = resData.get("respCode");
		LogUtil.writeLog("接收返回报文中应答码respCode=[" + respcode + "]");

		/**
		 * 验证签名
		 */
		if (null != resString && !"".equals(resString)) {
			if (SDKUtil.validate(resData, encoding)) {
				System.out.println("验证签名成功");
			} else {
				System.out.println("验证签名失败");
			}
		}

		// 异步转同步
		if (!"00".equals(respcode)) {// 如果服务器返回不成功，结束流程，返回错误信息
			resultMap.put("respCode", resData.get("respCode"));// 响应码 --M
			resultMap.put("respMsg", resData.get("respMsg"));// 应答信息 --M
			LogUtil.printResponseLog("应答报文打印： " + resultMap);
			return resultMap;
		}

		LogUtil.printResponseLog("应答报文打印： " + resString);
		// 查询
		Map<String, String> queryResultMap = this.queryProcess();

		String origRespcode = queryResultMap.get("origRespCode");
		LogUtil.writeLog("第==1==次查询返回 ： OrigRespcode:" + origRespcode
				+ ",RespCode:" + queryResultMap.get("respCode"));
		if (StringUtils.isNotBlank(origRespcode)
				&& ("05".equals(origRespcode) || "34".equals(origRespcode))) {
			int queryTimes = 5;
			for (int i = 1; i < queryTimes; i++) {
				Integer sleepTime = (int) Math.pow(2, i);
				try {
					Thread.sleep(sleepTime * 1000);
					// 发起交易状态查询交易
					LogUtil.writeLog("发起第===" + (i + 1) + "===交易状查询");
					queryResultMap = this.queryProcess();
					LogUtil.writeLog("origRespcode:" + origRespcode);
					if (null != queryResultMap
							&& (StringUtils.isNotBlank(origRespcode) && ("00"
									.equals(origRespcode) || "01"
									.equals(origRespcode)))) {
						if ("00".equals(origRespcode)) {
							LogUtil.writeLog("查询成功，退出查询");
						} else if ("01".equals(origRespcode)) {
							LogUtil.writeLog("交易失败，退出查询");
						}
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
					resultMap.put("respCode", "02");// 响应码 --M
					resultMap.put("respMsg", "查询出错！");// 应答信息 --M
					LogUtil.writeLog("返回报文=" + resultMap);
					return resultMap;
				}
			}
		}
		queryResultMap.put("respTime", resData.get("respTime"));// 响应信息 --M
		// 交易处理结果等于查询结果
		resultMap = queryResultMap;
		if ("00".equals(queryResultMap.get("respCode"))) {
			// query.setRespCode(origRespcode);
			resultMap.put("respCode", origRespcode);
		}
		LogUtil.writeLog("打印返回报文如下");
		LogUtil.printRequestLog(resultMap);
		return resultMap;
	}

	/**
	 * 交易状态查询方法
	 * 
	 * @return 查询返回的结果map
	 */
	public Map<String, String> queryProcess() {

		// 从classpath加载acp_sdk.properties文件
		// 查询的url
		SDKConfig.getConfig().loadPropertiesFromSrc();
		String queryUrl = SDKConfig.getConfig().getSingleQueryUrl();
		String encoding = "UTF-8";
		/**
		 * 组装查询的请求报文
		 */
		Map<String, String> queryData = new HashMap<String, String>();
		// 版本号
		queryData.put("version", "5.0.0");
		// 字符集编码
		queryData.put("encoding", encoding);
		// 签名方法 RSA
		queryData.put("signMethod", "01");
		// 交易类型
		queryData.put("txnType", "00");
		// 交易子类型 默认:00
		queryData.put("txnSubType", "00");
		// 业务类型
		queryData.put("bizType", "000000");
		// 收单机构代码
		queryData.put("acqInsCode", "");// 88888888
		// 接入类型 商户:0 收单:1
		queryData.put("accessType", "0");
		// 商户号码
		queryData.put("merId", "898340183980105");
		// 订单发送时间 <注：订单发送时间如果自己手动生成时,请每次执行之前，稍做更改以防与当前时间差距较大导致交易失败>
		queryData.put("txnTime", "20141017102920");
		// 订单号
		queryData.put("orderId", "508aa0a8b414e01042e93b92862f5ac");// 请填入原交易订单号
		// 交易查询流水号
		queryData.put("queryId", "201410171029201000508");
		// 保留域
		queryData.put("reserved", "");

		// 若报文中的数据元标识的key对应的value为空，不上送该报文域
		Map<String, String> request = new HashMap<String, String>();
		request.putAll(queryData);
		Set<String> set = queryData.keySet();
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (null == queryData.get(key) || "".equals(queryData.get(key))) {
				request.remove(key);
			}
		}

		/**
		 * 签名
		 */
		SDKUtil.sign(request, encoding);
		LogUtil.writeLog("查询的queryUrl==  " + queryUrl);
		LogUtil.writeLog("查询请求报文如下");
		LogUtil.printRequestLog(request);
		/**
		 * 发送
		 */
		HttpClient hc = new HttpClient(queryUrl, 30000, 30000);
		// 查询的返回结果
		String queryResultString = "";
		try {
			int status = hc.send(request, encoding);
			if (200 == status) {
				queryResultString = hc.getResult();
			} else {
				LogUtil.writeLog("发送查询请求报文失败（系统未开放或暂时关闭）");
				LogUtil.writeLog("  返回信息为空，请稍后再试  ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.writeLog("发送查询请求报文失败（系统未开放或暂时关闭）");
			LogUtil.writeLog("  返回信息为空，请稍后再试  ");
		}

		Map<String, String> resData = null;
		/**
		 * 验证签名
		 */
		if (null != queryResultString && !"".equals(queryResultString)) {
			// 将返回结果转换为map
			resData = SDKUtil.convertResultStringToMap(queryResultString);
			LogUtil.writeLog("接收返回报文中应答码respCode=[" + resData.get("respCode")
					+ "]");
			// LogUtil.writeLog("接收返回报文中查询结果码 queryResult=["+

			if (SDKUtil.validate(resData, encoding)) {
				System.out.println("验证签名成功");
			} else {
				System.out.println("验证签名失败");
			}
			// 打印返回报文
			LogUtil.writeLog("查询返回报文如下");
			LogUtil.printResponseLog(queryResultString);
		}
		return resData;
	}

	public static void main(String[] args) {
		AsynBackTrans test = new AsynBackTrans();
		test.process();
		// test.queryProcess();
	}

}
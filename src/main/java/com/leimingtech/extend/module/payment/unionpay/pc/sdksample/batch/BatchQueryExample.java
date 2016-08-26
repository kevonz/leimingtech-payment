package com.leimingtech.extend.module.payment.unionpay.pc.sdksample.batch;

import java.io.FileInputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.leimingtech.extend.module.payment.unionpay.pc.sdk.HttpClient;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.LogUtil;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKConfig;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKUtil;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SecureUtil;

/**
 * 指量查询
 * 
 * @author xuyaowen
 * 
 */
public class BatchQueryExample {

	public static void main(String[] args) {
		// Map<String, String> map = new HashMap<String, String>();
		// fieldUpload(map,"5_0_cx.txt");

		String fileName = "TH00000000700000000000001201407101000I.txt";
		send(fileName);
		// System.out.println(map);

	}

	/**
	 * "5_0_cx.txt"
	 * 
	 * @param map
	 * @param fileName
	 */
	public static void fieldUpload(Map<String, String> map, String fileName) {

		try {
			URL dd = BatchQueryExample.class.getClassLoader().getResource(
					fileName);

			FileInputStream ins = new FileInputStream(dd.getFile());
			byte[] b = new byte[ins.available()];
			ins.read(b);
			ins.close();
			String string = new String(b);
			string = new String(SecureUtil.base64Encode(SecureUtil.deflater(b)));
			//
			// byte[] strings = SecureUtil.base64Decode(string.getBytes());
			//
			// string = new String(SecureUtil.inflater(strings));
			System.out.println(string);
			map.put("fileContent", string);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void send(String fileName) {
		/**
		 * 初始化acp_sdk.properties配置文件
		 */
		SDKConfig.getConfig().loadPropertiesFromSrc();

		String type = fileName.substring(0, 2);
		String acqInsCode = fileName.substring(2, 10);
		String merId = fileName.substring(10, 25);
		String txnTime = fileName.substring(25, 33);
		String batchNo = fileName.substring(33, 37);

		String version = "5.0.0";// --版本号
		String encoding = "UTF-8";// --编码方式
		String certId = "999999999999";// --证书ID
		// String signature=signature;//--签名
		String signMethod = "02";// --签名方法
		String txnType = "22";// --交易类型取值: 22：批量查询

		String txnSubType = "01";// --交易子类
		if ("TH".equals(type)) {
			txnSubType = "01";// --交易子类
			//
			// 01：退货
			// 02：代收
			// 03：代付

		} else if ("DK".equals(type)) {
			txnSubType = "02";// --交易子类

		} else if ("DF".equals(type)) {
			txnSubType = "03";// --交易子类
		}
		String bizType = "000000";// --产品类型
		String channelType = "08";// --渠道类型
		String accessType = "0";// --接入类型
		// 0：普通商户直连接入
		// 1：收单机构接入
		// 2：平台类商户接入

		String reqReserved = "";// 受理方和发卡方自选填写的域[O]--请求方保留域
		String reserved = "";// 受理方和发卡方自选填写的域[O]--保留域

		Map<String, String> req = new HashMap<String, String>();

		req.put("version", version);// 版本号
		req.put("encoding", encoding);// 编码方式
		req.put("certId", certId);// 证书ID
		// req.put("signature",signature);//签名
		req.put("signMethod", signMethod);// 签名方法
		req.put("txnType", txnType);// 交易类型
		req.put("txnSubType", txnSubType);// 交易子类
		req.put("bizType", bizType);// 产品类型
		req.put("channelType", channelType);// 渠道类型
		req.put("accessType", accessType);// 接入类型
		if (StringUtils.isNotBlank(acqInsCode)) {// 某条件成立时必须填写的域[C]--收单机构代码
			req.put("acqInsCode", acqInsCode);// 收单机构代码
		}
		if (StringUtils.isNotBlank(merId)) {// 某条件成立时必须填写的域[C]--商户代码
			req.put("merId", merId);// 商户代码
		}
		req.put("batchNo", batchNo);// 批次号
		   SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
					req.put("txnTime", txnTime+sdf.format(new Date()));// 总金额
		if (StringUtils.isNotBlank(reqReserved)) {// 受理方和发卡方自选填写的域[O]--请求方保留域
			req.put("reqReserved", reqReserved);// 请求方保留域
		}
		if (StringUtils.isNotBlank(reserved)) {// 受理方和发卡方自选填写的域[O]--保留域
			req.put("reserved", reserved);// 保留域
		}

		/**
		 * 签名
		 */
		SDKUtil.sign(req, encoding);

		LogUtil.writeLog("BackRequest组装请求报文表单如下");
		LogUtil.printRequestLog(req);

		// 请求url
		String queryUrl = SDKConfig.getConfig().getBatchQueryUrl();

		LogUtil.writeLog("发送报文到后台");
		LogUtil.writeLog("请求地址queryUrl=[" + queryUrl + "]");
		String result = null;
		/**
		 * 发送
		 */
		HttpClient hc = new HttpClient(queryUrl, 30000, 30000);
		try {
			int status = hc.send(req, encoding);
			if (200 == status) {
				result = hc.getResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("报文发送后的响应信息：  " + result);

	}
}

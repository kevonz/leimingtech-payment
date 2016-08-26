package com.leimingtech.extend.module.payment.unionpay.pc.sdksample.batch;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
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
 * 批量代收、 批量代付、 批量退货等
 * 
 * @author xuyaowen
 * 
 */
public class BatchTransExample {

	public static String FILENAME = "TH00000000700000000000001201407101000I.txt";

	public static String type;
	public static String acqInsCode;
	public static String merId;
	public static String txnTime;
	public static String batchNo;

	public static String totalQty;// --总笔数
	public static String totalAmt;// --总金额

	public static void main(String[] args) {
		// Map<String, String> map = new HashMap<String, String>();
		// fieldUpload(map,"5_0_cx.txt");
		send();
//		 Calendar calendar=null;
//         calendar =new GregorianCalendar();//子类实例化
//        System.out.println("年： "+calendar.get(Calendar.YEAR));
//        System.out.println("月 "+(calendar.get(Calendar.MONTH)+1));
//        System.out.println("日： "+calendar.get(Calendar.DAY_OF_MONTH));
//        System.out.println("时： "+calendar.get(Calendar.HOUR_OF_DAY));
//        System.out.println("分： "+calendar.get(Calendar.MINUTE));
//        System.out.println("秒： "+calendar.get(Calendar.SECOND));
//        System.out.println("毫秒 "+calendar.get(Calendar.MILLISECOND));
		// decode();
		// System.out.println(map);
	}

	public static void decode() {
		// /字符1-2位 TH
		// 字符3-10位 8位收单机构号，如非收单机构交易，可填00000000
		// 字符11-25位 15位商户号
		// 字符26-33位 上传日期, 与当前日期相同（8位的日期，如：20110401）
		// 字符34-37位 批次号, 与填写的批次号一致，当天唯一(0001-9999)
		// 字符38位 方向标志“I”，代表请求
		// 字符39-42位 固定”.txt”

		// type = FILENAME.substring(0, 2);
		// acqInsCode = FILENAME.substring(2, 10);
		// merId = FILENAME.substring(10, 25);
		// txnTime = FILENAME.substring(25, 33);
		// batchNo = FILENAME.substring(33, 37);

		//
		// 商户或机构代码 非空，商户号（15位数字），或8位收单机构代码
		// 批次号 非空，当天唯一(0001-9999)
		// 上传日期 非空，YYYYMMDD,与报文中的交易日期前八位相同
		// 总金额 非空，以分为单位，最长12位
		// 总笔数 非空，数字长度不大于6
		// 请求方保留域 1 可选，字节长度必须小于30，1个汉字2个字节
		// 请求方保留域 2 可选，字节长度必须小于30，1个汉字2个字节
//		System.out.println(type);
//		System.out.println(acqInsCode);
//		System.out.println(merId);
//		System.out.println(txnTime);
//		System.out.println(batchNo);

	}

	/**
	 * 读取文件信息
	 * 
	 * @param map
	 * @param fileName
	 */
	public static void fieldUpload(Map<String, String> map, String fileName) {

		try {
			URL dd = BatchTransExample.class.getClassLoader().getResource(
					fileName);

			if(null == dd){
				LogUtil.writeLog("找不到文件"+ fileName);
				return;
			}
			FileInputStream ins = new FileInputStream(dd.getFile());
			byte[] b = new byte[ins.available()];
			ins.read(b);
			ins.close();
			FileReader fr = new FileReader(dd.getFile());
			// FileReader fr=new FileReader(new Fil);
			// 可以换成工程目录下的其他文本文件
			BufferedReader br = new BufferedReader(fr);
			String ss = br.readLine();
			br.close();

			String[] arr = ss.split("[|]");
			type = fileName.substring(0, 2);
			acqInsCode = fileName.substring(2, 10);
			merId = fileName.substring(10, 25);
			txnTime = fileName.substring(25, 33);
			batchNo = fileName.substring(33, 37);
			
			merId = arr[0];
			batchNo = arr[1];
			txnTime = arr[2];
			totalAmt = arr[3];
			totalQty = arr[4];

			String string = new String(b);
			string = new String(SecureUtil.base64Encode(SecureUtil.deflater(b)));
			//
			// byte[] strings = SecureUtil.base64Decode(string.getBytes());
			//
			// string = new String(SecureUtil.inflater(strings));
			map.put("fileContent", string);
			String txnSubType=null;
			 if("TH".equals(type)){
				  txnSubType="01";//--交易子类
//				  
//				  01：退货
//				  02：代收
//				  03：代付

			 }else if("DK".equals(type)){
				 txnSubType="02";//--交易子类
				 
			 }else if("DF".equals(type)){
				 txnSubType="03";//--交易子类
			 }

			 
			 map.put("txnSubType", txnSubType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void send() {
		/**
		 * 初始化acp_sdk.properties配置文件
		 */
		SDKConfig.getConfig().loadPropertiesFromSrc();

		String version = "5.0.0";// --版本号
		String encoding = "UTF-8";// --编码方式
		String certId = "999999999999";// --证书ID
		// String signature=signature;//--签名
		String signMethod = "02";// --签名方法
		String txnType = "21";// --交易类型21 批量交易
		
		//String txnSubType = "01";// --交易子类
		String bizType = "000000";// --产品类型
		String channelType = "08";// --渠道类型
		String accessType = "1";// --接入类型
		// String acqInsCode="";//某条件成立时必须填写的域[C]--收单机构代码
		// String merId="";//某条件成立时必须填写的域[C]--商户代码
		String subMerId = "";// 某条件成立时必须填写的域[C]--二级商户代码
		String subMerName = "";// 某条件成立时必须填写的域[C]--二级商户名称
		// String batchNo="";//--批次号
		// String txnTime="";//--订单发送时间
		// String totalQty="";//--总笔数
		// String totalAmt="";//--总金额
		// String fileContent="";//--文件内容
		String reqReserved = "";// 受理方和发卡方自选填写的域[O]--请求方保留域
		String reserved = "";// 受理方和发卡方自选填写的域[O]--保留域

		Map<String, String> req = new HashMap<String, String>();
		fieldUpload(req, FILENAME);
		req.put("version", version);// 版本号
		req.put("encoding", encoding);// 编码方式
		req.put("certId", certId);// 证书ID
		// req.put("signature",signature);//签名
		req.put("signMethod", signMethod);// 签名方法
		req.put("txnType", txnType);// 交易类型
		//req.put("txnSubType", txnSubType);// 交易子类
		req.put("bizType", bizType);// 产品类型
		req.put("channelType", channelType);// 渠道类型
		req.put("accessType", accessType);// 接入类型
		if (StringUtils.isNotBlank(acqInsCode)) {// 某条件成立时必须填写的域[C]--收单机构代码
			req.put("acqInsCode", acqInsCode);// 收单机构代码
		}
		if (StringUtils.isNotBlank(merId)) {// 某条件成立时必须填写的域[C]--商户代码
			req.put("merId", merId);// 商户代码
		}
		if (StringUtils.isNotBlank(subMerId)) {// 某条件成立时必须填写的域[C]--二级商户代码
			req.put("subMerId", subMerId);// 二级商户代码
		}
		if (StringUtils.isNotBlank(subMerName)) {// 某条件成立时必须填写的域[C]--二级商户名称
			req.put("subMerName", subMerName);// 二级商户名称
		}
		req.put("batchNo", batchNo);// 批次号
		   SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
			req.put("txnTime", txnTime+sdf.format(new Date()));// 总金额
	
		
		
		
		req.put("totalQty", totalQty);// 总笔数
	
		req.put("totalAmt", totalAmt);// 总金额
		// req.put("fileContent",fileContent);//文件内容
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
		String tradUrl = SDKConfig.getConfig().getBatchTransUrl();

		LogUtil.writeLog("发送报文到后台");
		LogUtil.writeLog("请求地址tradUrl=[" + tradUrl + "]");
		String result = null;
		/**
		 * 发送
		 */
		HttpClient hc = new HttpClient(tradUrl, 30000, 30000);
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

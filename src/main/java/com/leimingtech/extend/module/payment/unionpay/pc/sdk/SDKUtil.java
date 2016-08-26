/**
 *
 * Licensed Property to China UnionPay Co., Ltd.
 * 
 * (C) Copyright of China UnionPay Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * 
 * Modification History:
 * =============================================================================
 *   Author         Date          Description
 *   ------------ ---------- ---------------------------------------------------
 *   xshu       2014-05-28      MPI工具类
 * =============================================================================
 */
package com.leimingtech.extend.module.payment.unionpay.pc.sdk;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

public class SDKUtil {

	protected static char[] letter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z' };

	protected static final Random random = new Random();

	/**
	 * 发送后台交易
	 * 
	 * @param url
	 *            请求的URL
	 * @param data
	 *            发送的数据
	 * @param httpHeads
	 *            HTTP头信息
	 * @param encoding
	 *            字符集编码
	 * @return 结果
	 */
	public static String send(String url, Map<String, String> data,
			String encoding, int connectionTimeout, int readTimeout) {
		HttpClient hc = new HttpClient(url, connectionTimeout, readTimeout);
		String res = "";
		try {
			int status = hc.send(data, encoding);
			if (200 == status) {
				res = hc.getResult();
			}
		} catch (Exception e) {
			LogUtil.writeErrorLog("通信异常", e);
		}
		return res;
	}


	/**
	 * 生成签名值(SHA1摘要算法),供商户调用
	 * 
	 * @param data
	 *            待签名数据Map键值对形式
	 * @param encoding
	 *            编码
	 * @return 签名是否成功
	 */
	public static boolean sign(Map<String, String> data, String encoding) {
		if (isEmpty(encoding)) {
			encoding = "UTF-8";
		}
		// 设置签名证书序列号
		data.put(SDKConstants.param_certId, CertUtil.getSignCertId());
		// 将Map信息转换成key1=value1&key2=value2的形式
		String stringData = coverMap2String(data);
		/**
		 * 签名\base64编码
		 */
		byte[] byteSign = null;
		String stringSign = null;
		try {
			// 通过SHA1进行摘要并转16进制
			byte[] signDigest = SecureUtil.sha1X16(stringData, encoding);
			byteSign = SecureUtil.base64Encode(SecureUtil.signBySoft(
					CertUtil.getSignCertPrivateKey(), signDigest));
			stringSign = new String(byteSign);
			// 设置签名域值
			data.put(SDKConstants.param_signature, stringSign);
			return true;
		} catch (Exception e) {
			LogUtil.writeErrorLog("签名异常", e);
			return false;
		}
	}

	/**
	 * 通过传入的证书绝对路径和证书密码读取签名证书进行签名并返回签名值<br>
	 * 此为信用卡应用提供的特殊化处理方法
	 * 
	 * @param data
	 *            待签名数据Map键值对形式
	 * @param encoding
	 *            编码
	 * @param certPath
	 *            证书绝对路径
	 * @param certPwd
	 *            证书密码
	 * @return 签名值
	 */
	public static boolean signByCertInfo(Map<String, String> data,
			String encoding, String certPath, String certPwd) {
		if (isEmpty(encoding)) {
			encoding = "UTF-8";
		}
		if (isEmpty(certPath) || isEmpty(certPwd)) {
			LogUtil.writeLog("Invalid Parameter:CertPath=[" + certPath
					+ "],CertPwd=[" + certPwd + "]");
			return false;
		}
		// 设置签名证书序列号
		data.put(SDKConstants.param_certId,
				CertUtil.getCertIdByKeyStoreMap(certPath, certPwd));
		// 将Map信息转换成key1=value1&key2=value2的形式
		String stringData = coverMap2String(data);
		/**
		 * 签名\base64编码
		 */
		byte[] byteSign = null;
		String stringSign = null;
		try {
			byte[] signDigest = SecureUtil.sha1X16(stringData, encoding);
			byteSign = SecureUtil.base64Encode(SecureUtil.signBySoft(
					CertUtil.getSignCertPrivateKeyByStoreMap(certPath, certPwd),
					signDigest));
			stringSign = new String(byteSign);
			// 设置签名域值
			data.put(SDKConstants.param_signature, stringSign);
			return true;
		} catch (Exception e) {
			LogUtil.writeErrorLog("签名异常", e);
			return false;
		}
	}


	/**
	 * 验证签名(SHA-1摘要算法)
	 * 
	 * @param resData
	 *            返回报文数据
	 * @param encoding
	 *            编码格式
	 * @return
	 */
	public static boolean validate(Map<String, String> resData, String encoding) {
		LogUtil.writeLog("验签处理开始.");
		if (isEmpty(encoding)) {
			encoding = "UTF-8";
		}
		String stringSign = resData.get(SDKConstants.param_signature);

		// 从返回报文中获取certId ，然后去证书静态Map中查询对应验签证书对象
		String certId = resData.get(SDKConstants.param_certId);

		// 将Map信息转换成key1=value1&key2=value2的形式
		String stringData = coverMap2String(resData);

		try {
			// 验证签名需要用银联发给商户的公钥证书.
			return SecureUtil.validateSignBySoft(CertUtil
					.getValidateKey(certId), SecureUtil.base64Decode(stringSign
					.getBytes(encoding)), SecureUtil.sha1X16(stringData,
					encoding));
		} catch (UnsupportedEncodingException e) {
			LogUtil.writeErrorLog(e.getMessage(), e);
		} catch (Exception e) {
			LogUtil.writeErrorLog(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * 将Map中的数据转换成key1=value1&key2=value2的形式 不包含签名域signature
	 * 
	 * @param data
	 *            待拼接的Map数据
	 * @return 拼接好后的字符串
	 */
	public static String coverMap2String(Map<String, String> data) {
		TreeMap<String, String> tree = new TreeMap<String, String>();
		Iterator<Entry<String, String>> it = data.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> en = it.next();
			if (SDKConstants.param_signature.equals(en.getKey().trim())) {
				continue;
			}
			tree.put(en.getKey(), en.getValue());
		}
		it = tree.entrySet().iterator();
		StringBuffer sf = new StringBuffer();
		while (it.hasNext()) {
			Entry<String, String> en = it.next();
			sf.append(en.getKey() + SDKConstants.EQUAL + en.getValue()
					+ SDKConstants.AMPERSAND);
		}
		return sf.substring(0, sf.length() - 1);
	}


	/**
	 * 兼容老方法 将形如key=value&key=value的字符串转换为相应的Map对象
	 * 
	 * @param result
	 * @return
	 */
	public static Map<String, String> coverResultString2Map(String result) {
		return convertResultStringToMap(result);
	}

	/**
	 * 将形如key=value&key=value的字符串转换为相应的Map对象
	 * 
	 * @param result
	 * @return
	 */
	public static Map<String, String> convertResultStringToMap(String result) {
		Map<String, String> map =null;
		try {
			
			if(StringUtils.isNotBlank(result)){
				if(result.startsWith("{") && result.endsWith("}")){
					System.out.println(result.length());
					result = result.substring(1, result.length()-1);
				}
				 map = parseQString(result);
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return map;
	}

	
	/**
	 * 解析应答字符串，生成应答要素
	 * 
	 * @param str
	 *            需要解析的字符串
	 * @return 解析的结果map
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> parseQString(String str)
			throws UnsupportedEncodingException {

		Map<String, String> map = new HashMap<String, String>();
		int len = str.length();
		StringBuilder temp = new StringBuilder();
		char curChar;
		String key = null;
		boolean isKey = true;
		boolean isOpen = false;//值里有嵌套
		char openName = 0;
		if(len>0){
			for (int i = 0; i < len; i++) {// 遍历整个带解析的字符串
				curChar = str.charAt(i);// 取当前字符
				if (isKey) {// 如果当前生成的是key
					
					if (curChar == '=') {// 如果读取到=分隔符 
						key = temp.toString();
						temp.setLength(0);
						isKey = false;
					} else {
						temp.append(curChar);
					}
				} else  {// 如果当前生成的是value
					if(isOpen){
						if(curChar == openName){
							isOpen = false;
						}
						
					}else{//如果没开启嵌套
						if(curChar == '{'){//如果碰到，就开启嵌套
							isOpen = true;
							openName ='}';
						}
						if(curChar == '['){
							isOpen = true;
							openName =']';
						}
					}
					if (curChar == '&' && !isOpen) {// 如果读取到&分割符,同时这个分割符不是值域，这时将map里添加
						putKeyValueToMap(temp, isKey, key, map);
						temp.setLength(0);
						isKey = true;
					}else{
						temp.append(curChar);
					}
				}
				
			}
			putKeyValueToMap(temp, isKey, key, map);
		}
		return map;
	}

	private static void putKeyValueToMap(StringBuilder temp, boolean isKey,
			String key, Map<String, String> map)
			throws UnsupportedEncodingException {
		if (isKey) {
			key = temp.toString();
			if (key.length() == 0) {
				throw new RuntimeException("QString format illegal");
			}
			map.put(key, "");
		} else {
			if (key.length() == 0) {
				throw new RuntimeException("QString format illegal");
			}
			map.put(key, temp.toString());
		}
	}
	/**
	 * 密码加密.
	 * 
	 * @param card
	 *            卡号
	 * @param pwd
	 *            密码
	 * @param encoding
	 *            字符集编码
	 * @return 加密后的字符串
	 */
	public static String encryptPin(String card, String pwd, String encoding) {
		return SecureUtil.EncryptPin(pwd, card, encoding, CertUtil
				.getEncryptCertPublicKey());
	}
	
	/**
	 * CVN2加密.
	 * 
	 * @param cvn2
	 *            CVN2值
	 * @param encoding
	 *            字符编码
	 * @return 加密后的数据
	 */
	public static String encryptCvn2(String cvn2, String encoding) {
		return SecureUtil.EncryptData(cvn2, encoding, CertUtil
				.getEncryptCertPublicKey());
	}

	/**
	 * CVN2解密
	 * 
	 * @param base64cvn2
	 *            加密后的CVN2值
	 * @param encoding
	 *            字符编码
	 * @return 解密后的数据
	 */
	public static String decryptCvn2(String base64cvn2, String encoding) {
		return SecureUtil.DecryptedData(base64cvn2, encoding, CertUtil
				.getSignCertPrivateKey());
	}

	/**
	 * 有效期加密.
	 * 
	 * @param date
	 *            有效期
	 * @param encoding
	 *            字符编码
	 * @return 加密后的数据
	 */
	public static String encryptAvailable(String date, String encoding) {
		return SecureUtil.EncryptData(date, encoding, CertUtil
				.getEncryptCertPublicKey());
	}

	/**
	 * 有效期解密.
	 * 
	 * @param base64Date
	 *            有效期值
	 * @param encoding
	 *            字符编码
	 * @return 加密后的数据
	 */
	public static String decryptAvailable(String base64Date, String encoding) {
		return SecureUtil.DecryptedData(base64Date, encoding, CertUtil
				.getSignCertPrivateKey());
	}

	/**
	 * 卡号加密.
	 * 
	 * @param pan
	 *            卡号
	 * @param encoding
	 *            字符编码
	 * @return 加密后的卡号值
	 */
	public static String encryptPan(String pan, String encoding) {
		return SecureUtil.EncryptData(pan, encoding, CertUtil
				.getEncryptCertPublicKey());
	}

	/**
	 * 卡号解密.
	 * 
	 * @param base64Pan
	 *            如果卡号加密，传入返回报文中的卡号字段
	 * @param encoding
	 * @return
	 */
	public static String decryptPan(String base64Pan, String encoding) {
		return SecureUtil.DecryptedData(base64Pan, encoding, CertUtil
				.getSignCertPrivateKey());
	}
	

	/**
	 * 加密磁道信息
	 * 
	 * @param trackData
	 *            待加密磁道数据
	 * @param encoding
	 *            编码格式
	 * @return String
	 */
	public static String encryptTrack(String trackData, String encoding) {
		return SecureUtil.EncryptData(trackData, encoding,
				CertUtil.getEncryptTrackCertPublicKey());
	}
	
	/**
	 * 加密磁道信息
	 * 
	 * @param trackData
	 *            待加密磁道数据
	 * @param encoding
	 *            编码格式
	 * @param modulus
	 *            模
	 * @param exponent
	 *            指数
	 * @return
	 */
	public static String encryptTrack(String trackData, String encoding,
			String modulus, String exponent) {
		return SecureUtil.EncryptData(trackData, encoding,
				CertUtil.getEncryptTrackCertPublicKey(modulus, exponent));
	}


	/**
	 * 判断字符串是否为NULL或空
	 * 
	 * @param s
	 *            待判断的字符串数据
	 * @return 判断结果 true-是 false-否
	 */
	public static boolean isEmpty(String s) {
		return null == s || "".equals(s.trim());
	}

	/**
	 * 生成订单发送时间YYYYMMDDhhmmss
	 * 
	 * @return 返回YYYYMMDDhhmmss格式的时间戳
	 */
	public static String generateTxnTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	/**
	 * 生产商户订单号AN12..32
	 * 
	 * @return
	 */
	public static String generateOrderId() {
		StringBuilder sb = new StringBuilder();
		int len = random.nextInt(18);
		for (int i = 0; i < len; i++) {
			sb.append(letter[i]);
		}
		return generateTxnTime() + sb.toString();
	}

	/**
	 * 构建HTML页面,包含一个form表单(自动提交)
	 * 
	 * @param url
	 * @param data
	 * @return
	 */
	public static String createAutoSubmitForm(String url,
			Map<String, String> data) {
		StringBuffer sf = new StringBuffer();
		sf.append("<form id = \"sform\" action=\"" + url
				+ "\" method=\"post\">");
		if (null != data && 0 != data.size()) {
			Set<Entry<String, String>> set = data.entrySet();
			Iterator<Entry<String, String>> it = set.iterator();
			while (it.hasNext()) {
				Entry<String, String> ey = it.next();
				String key = ey.getKey();
				String value = ey.getValue();
				sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\""
						+ key + "\" value=\"" + value + "\"/>");
			}
		}
		sf.append("</form>");
		sf.append("</body>");
		sf.append("<script type=\"text/javascript\">");
		sf.append("document.getElementById(\"sform\").submit();\n");
		sf.append("</script>");
		return sf.toString();
	}

	public static void main(String[] args) {
		System.out.println(SDKUtil.encryptTrack("12", "utf-8"));
	}

}

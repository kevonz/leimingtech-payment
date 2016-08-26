package com.leimingtech.extend.module.payment.unionpay.pc.sdksample.jiaofei;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.StringUtils;

import com.leimingtech.extend.module.payment.unionpay.pc.sdk.HttpClient;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKConstants;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKUtil;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SecureUtil;

/**
 * 名称： 基础参数<br>
 * 功能： 提供基础数据<br>
 * 版本： 5.0<br>
 * 日期： 2014-07<br>
 * 作者： 中国银联ACP团队<br>
 * 版权： 中国银联<br>
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考。<br>
 */
public class DemoBase {

	public static String encoding = "UTF-8";

	/**
	 * 5.0.0
	 */
	public static String version = "5.0.0";

	/**
	 * 后台服务对应的写法参照 FrontRcvResponse.java
	 * http://localhost:8080/ACPTest/acp_front_url.do
	 */
	public static String frontUrl = "http://localhost:8080/ACPTest/acp_front_url.do";

	public DemoBase() {
		super();
	}

	/**
	 * http://localhost:8080/ACPTest/acp_back_url.do
	 */
	// 后台服务对应的写法参照 BackRcvResponse.java
	public static String backUrl = "http://localhost:8080/ACPTest/acp_back_url.do";// 受理方和发卡方自选填写的域[O]--后台通知地址

	/**
	 * 构造HTTP POST交易表单的方法示例
	 * 
	 * @param action
	 *            表单提交地址
	 * @param hiddens
	 *            以MAP形式存储的表单键值
	 * @return 构造好的HTTP POST交易表单
	 */
	public static String createHtml(String action, Map<String, String> hiddens) {
		StringBuffer sf = new StringBuffer();
		sf.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body>");
		sf.append("<form id = \"pay_form\" action=\"" + action
				+ "\" method=\"post\">");
		if (null != hiddens && 0 != hiddens.size()) {
			Set<Entry<String, String>> set = hiddens.entrySet();
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
		sf.append("document.all.pay_form.submit();");
		sf.append("</script>");
		sf.append("</html>");
		return sf.toString();
	}

	/**
	 * java main方法 数据提交 　　 对数据进行签名
	 * 
	 * @param contentData
	 * @return　签名后的map对象
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> signData(Map<String, ?> contentData) {
		Entry<String, String> obj = null;
		Map<String, String> submitFromData = new HashMap<String, String>();
		for (Iterator<?> it = contentData.entrySet().iterator(); it.hasNext();) {
			obj = (Entry<String, String>) it.next();
			String value = obj.getValue();
			if (StringUtils.isNotBlank(value)) {
				// 对value值进行去除前后空处理
				submitFromData.put(obj.getKey(), value.trim());
			}
		}
		/**
		 * 签名
		 */
		SDKUtil.sign(submitFromData, encoding);
		return submitFromData;
	}

	/**
	 * java main方法 数据提交 提交到后台
	 * 
	 * @param contentData
	 * @return 返回报文 map
	 */
	public static Map<String, String> submitUrl(
			Map<String, String> submitFromData, String requestUrl) {
		int status = -1;
		String resultString = "";
		System.out.println("requestUrl====" + requestUrl);
//		System.out.println("submitFromData====" + submitFromData.toString());
		/**
		 * 发送
		 */
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		try {
			status = hc.send(submitFromData, encoding);
			if (200 == status) {
				resultString = hc.getResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> resData = new HashMap<String, String>();
		/**
		 * 验证签名
		 */
		if (null != resultString && !"".equals(resultString)) {
			// 将返回结果转换为map
			resData = SDKUtil.convertResultStringToMap(resultString);
			if (SDKUtil.validate(resData, encoding)) {
				System.out.println("验证签名成功");
			} else {
				System.out.println("验证签名失败");
			}
			// 打印返回报文
			System.out.println("打印返回报文：" + resultString);
		} else {
			System.out.println("返回报文为空,HTTP STATUS：" + status);
		}
		return resData;
	}

	/**
	 * 解析返回文件
	 */
	public static void deCodeFileContent(Map<String, String> resData) {
		// 解析返回文件
		String fileContent = resData.get(SDKConstants.param_fileContent);
		if (null != fileContent && !"".equals(fileContent)) {
			try {
				byte[] fileArray = SecureUtil.inflater(SecureUtil
						.base64Decode(fileContent.getBytes(encoding)));
				String root = "D:\\";
				String filePath = null;
				if (SDKUtil.isEmpty(resData.get("fileName"))) {
					filePath = root + File.separator + resData.get("merId")
							+ "_" + resData.get("batchNo") + "_"
							+ resData.get("txnTime") + ".txt";
				} else {
					filePath = root + File.separator + resData.get("fileName");
				}
				File file = new File(filePath);
				if (file.exists()) {
					file.delete();
				}
				file.createNewFile();
				FileOutputStream out = new FileOutputStream(file);
				out.write(fileArray, 0, fileArray.length);
				out.flush();
				out.close();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * java main方法 数据提交　 数据组装进行提交 包含签名
	 * 
	 * @param contentData
	 * @return 返回报文 map
	 */
	public static Map<String, String> submitDate(Map<String, ?> contentData,
			String requestUrl) {
		Map<String, String> submitFromData = (Map<String, String>) signData(contentData);
		return submitUrl(submitFromData, requestUrl);
	}

	/**
	 * 持卡人信息域操作
	 * 
	 * @param encoding
	 *            编码方式
	 * @return base64后的持卡人信息域字段
	 */
	public static String getCustomer(String encoding) {
		StringBuffer sf = new StringBuffer("{");
		// 证件类型
		String certifTp = "01";
		// 证件号码
		String certifId = "1301212386859081945";
		// 姓名
		String customerNm = "测试";
		// 手机号
		String phoneNo = "18613958987";
		// 短信验证码
		String smsCode = "123311";
		// 持卡人密码
		String pin = "123213";
		// cvn2
		String cvn2 = "400";
		// 有效期
		String expired = "1212";
		sf.append("certifTp=" + certifTp + SDKConstants.AMPERSAND);
		sf.append("certifId=" + certifId + SDKConstants.AMPERSAND);
		sf.append("customerNm=" + customerNm + SDKConstants.AMPERSAND);
		sf.append("phoneNo=" + phoneNo + SDKConstants.AMPERSAND);
		sf.append("smsCode=" + smsCode + SDKConstants.AMPERSAND);
		// 密码加密
		// sf.append("pin=" + SDKUtil.encryptPin("622188123456789", pin, encoding) + SDKConstants.AMPERSAND);
		// 密码不加密
		sf.append("pin=" + pin + SDKConstants.AMPERSAND);
		// cvn2加密
		// sf.append(SDKUtil.encryptCvn2(cvn2, encoding) + SDKConstants.AMPERSAND);
		// cvn2不加密
		sf.append("cvn2=" + cvn2 + SDKConstants.AMPERSAND);
		// 有效期加密
		// sf.append(SDKUtil.encryptAvailable(expired, encoding));
		// 有效期不加密
		sf.append("expired=" + expired);
		sf.append("}");
		String customerInfo = sf.toString();
		try {
			return new String(SecureUtil.base64Encode(sf.toString().getBytes(
					encoding)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return customerInfo;
	}

	public static String getCurrentTime() {
		return DateUtil.formatDate(new Date(), "yyyyMMddHHmmss");
	}

	public static String getOrderId() {
		return DateUtil.formatDate(new Date(), "yyyyMMddHHmmss");
	}

	/**
	 * 有卡交易信息域(cardTransData)构造示例<br>
	 * 所有子域需用“{}”包含，子域间以“&”符号链接。格式如下：{子域名1=值&子域名2=值&子域名3=值}<br>
	 * 说明：本示例仅供参考，开发时请根据接口文档中的报文要素组装
	 * 
	 * @param contentData
	 * @param encoding
	 * @return
	 */
	public static String getCardTransData(Map<String, ?> contentData,
			String encoding) {

		StringBuffer cardTransDataBuffer = new StringBuffer();
		
		// 以下测试数据只是用来说明组装cardTransData域的基本步骤,真实数据请以实际业务为准
		String ICCardData = "uduiadniodaiooxnnxnnada";// IC卡数据
		String ICCardSeqNumber = "123";// IC卡的序列号
		String track2Data = "testtrack2Datauidanidnaidiadiada231";// 第二磁道数据
		String track3Data = "testtrack3Datadadaiiuiduiauiduia312117831";// 第三磁道数据
		String transSendMode = "b";// 交易发起方式

		// 第二磁道数据 加密格式如下：merId|orderId|txnTime|txnAmt|track2Data
		StringBuffer track2Buffer = new StringBuffer();
		track2Buffer.append(contentData.get("merId"))
				.append(SDKConstants.COLON).append(contentData.get("orderId"))
				.append(SDKConstants.COLON).append(contentData.get("txnTime"))
				.append(SDKConstants.COLON).append(contentData.get("txnAmt"))
				.append(SDKConstants.COLON).append(track2Data);

		String encryptedTrack2 = SDKUtil.encryptTrack(track2Buffer.toString(),
				encoding);

		// 第三磁道数据 加密格式如下：merId|orderId|txnTime|txnAmt|track3Data
		StringBuffer track3Buffer = new StringBuffer();
		track3Buffer.append(contentData.get("merId"))
				.append(SDKConstants.COLON).append(contentData.get("orderId"))
				.append(SDKConstants.COLON).append(contentData.get("txnTime"))
				.append(SDKConstants.COLON).append(contentData.get("txnAmt"))
				.append(SDKConstants.COLON).append(track3Data);

		String encryptedTrack3 = SDKUtil.encryptTrack(track3Buffer.toString(),
				encoding);

		// 将待组装的数据装入MAP,进行格式处理
		Map<String, String> cardTransDataMap = new HashMap<String, String>();
		cardTransDataMap.put("ICCardData", ICCardData);
		cardTransDataMap.put("ICCardSeqNumber", ICCardSeqNumber);
		cardTransDataMap.put("track2Data", encryptedTrack2);
		cardTransDataMap.put("track3Data", encryptedTrack3);
		cardTransDataMap.put("transSendMode", transSendMode);

		return cardTransDataBuffer.append(SDKConstants.LEFT_BRACE)
				.append(SDKUtil.coverMap2String(cardTransDataMap))
				.append(SDKConstants.RIGHT_BRACE).toString();
	}

}
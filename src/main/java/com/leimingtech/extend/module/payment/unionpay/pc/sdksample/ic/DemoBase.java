package com.leimingtech.extend.module.payment.unionpay.pc.sdksample.ic;

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

	public static String version = "5.0.0";

	// 前台通知服务对应的写法参照 FrontRcvResponse.java
	public static String frontUrl = "http://localhost:8080/ACPTest/acp_front_url.do";

	// 后台通知服务对应的写法参照 BackRcvResponse.java
	public static String backUrl = "http://localhost:8080/ACPTest/acp_back_url.do";// 受理方和发卡方自选填写的域[O]--后台通知地址

	public DemoBase() {
		super();
	}
	
	// 模
	public static final String modulus = "23648629510357402173669374843546537318532861396089478651610490265597426690711092692490012429464861104676801339474220894685964389750254240882066338437712341498313076007251358899488346743554156067576120095739341094220657657611893755799646325194641430110114613586989866468748149428464174345443169749235358776080247588710246733575431530477273705811466095207773188767974550742707293785661521305267533098997705930724499157184797236612324838287379798375903922360666026664942383548006246201656190964746068225967889145661249463716565124050082767382345820178584568857820200627919768134084891356188058390460707236118612628845159";

	// 指数
	public static final String exponent = "65537";


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
		String resultString = "";
		System.out.println("requestUrl=[" + requestUrl + "]");
		/**
		 * 发送
		 */
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		try {
			int status = hc.send(submitFromData, encoding);
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
	public static String getCustomer(Map<String, ?> contentData,
			String encoding) {

		String pin = "123456"; //密码明文请从读卡器让用户输入采集。
		String expired = "1249"; //有效期明文从读卡器读取，IC卡送，磁条卡不送。

		pin = SDKUtil.encryptPin(contentData.get("accNo").toString(), pin, encoding);
		expired = SDKUtil.encryptAvailable(expired, encoding);
		
		Map<String, String> customerInfoMap = new HashMap<String, String>(); 
		customerInfoMap.put("pin", pin);
//		customerInfoMap.put("expired", expired); //IC卡送，磁条卡不送。
		
		StringBuffer sf = new StringBuffer();
		String customerInfo = sf.append(SDKConstants.LEFT_BRACE)
		.append(SDKUtil.coverMap2String(customerInfoMap))
		.append(SDKConstants.RIGHT_BRACE).toString();
		
		try {
			return new String(SecureUtil.base64Encode(sf.toString().getBytes(
					encoding)), encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return customerInfo;
	}

	// 商户发送交易时间 格式:YYYYMMDDhhmmss
	public static String getCurrentTime() {
		return DateUtil.formatDate(new Date(), "yyyyMMddHHmmss");
	}

	// AN8..40 商户订单号，不能含"-"或"_"
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
		String iCCardData = "9F2608E949441859FB25309F2701809F101307000103A00002010A010000052000F26F86559F3704DFE09A299F360201A2950500800008009A031504089C01009F02060000000001005F2A02015682027D009F1A0201569F03060000000000009F330360F8C89F34031E03009F3501219F1E0831323334353637388410A00000033301010211223344556677889F090200019F410413"; //IC卡信息域，IC卡必送，必须为从IC卡里读取，写死值的话cups解析时会出错会拒交易。
		//String iCCardSeqNumber = "123"; //IC卡序列号，IC卡必送
		String track2Data = "6214441000010020=301020600000F";// 第二磁道数据
		String track3Data = "6214441000010020=301020600000F";// 第三磁道数据

		// 第二磁道数据 加密格式如下：merId|orderId|txnTime|txnAmt|track2Data
		StringBuffer track2Buffer = new StringBuffer();
		track2Buffer.append(contentData.get("merId"))
				.append(SDKConstants.COLON).append(contentData.get("orderId"))
				.append(SDKConstants.COLON).append(contentData.get("txnTime"))
				.append(SDKConstants.COLON).append(contentData.get("txnAmt"))
				.append(SDKConstants.COLON).append(track2Data);

		String encryptedTrack2 = SDKUtil.encryptTrack(track2Buffer.toString(),
				encoding,modulus, exponent);

		// 第三磁道数据 加密格式如下：merId|orderId|txnTime|txnAmt|track3Data
		StringBuffer track3Buffer = new StringBuffer();
		track3Buffer.append(contentData.get("merId"))
				.append(SDKConstants.COLON).append(contentData.get("orderId"))
				.append(SDKConstants.COLON).append(contentData.get("txnTime"))
				.append(SDKConstants.COLON).append(contentData.get("txnAmt"))
				.append(SDKConstants.COLON).append(track3Data);

		String encryptedTrack3 = SDKUtil.encryptTrack(track3Buffer.toString(),
				encoding, modulus, exponent);
		
		try {
			iCCardData = new String(SecureUtil.base64Encode(iCCardData.getBytes(encoding)), encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 将待组装的数据装入MAP,进行格式处理
		Map<String, String> cardTransDataMap = new HashMap<String, String>();
//		cardTransDataMap.put("ICCardData", iCCardData); //IC卡送，磁条卡不送。
//		cardTransDataMap.put("ICCardSeqNumber", iCCardSeqNumber); //IC卡送，磁条卡不送。
		cardTransDataMap.put("track2Data", encryptedTrack2);
		cardTransDataMap.put("track3Data", encryptedTrack3);
		cardTransDataMap.put("carrierAppTp", "3");
		cardTransDataMap.put("carrierTp", "0");

		return cardTransDataBuffer.append(SDKConstants.LEFT_BRACE)
				.append(SDKUtil.coverMap2String(cardTransDataMap))
				.append(SDKConstants.RIGHT_BRACE).toString();
	}

}
package com.leimingtech.extend.module.payment.unionpay.pc.sdksample.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.leimingtech.extend.module.payment.unionpay.pc.sdk.HttpClient;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKConfig;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKConstants;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKUtil;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SecureUtil;

/**
 * 名称：文件传输类交易（如对账文件下载交易） 功能： 类属性： 方法调用 版本：5.0 日期：2014-07 作者：中国银联ACP团队 版权：中国银联
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
 * */
public class FileTrans {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 字符集编码
		String encoding = "UTF-8";
		// 返回结果
		String result = "";
		/**
		 * 初始化sdk.properties配置文件
		 */
		SDKConfig.getConfig().loadPropertiesFromSrc();//
		// 请求url
		String requestUrl = SDKConfig.getConfig().getFileTransUrl();
		System.out.println("文件请求地址=[" + requestUrl + "]");
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
		data.put("txnType", "76");
		// 交易子类型 01:对账文件下载
		data.put("txnSubType", "01");
		// 业务类型
		data.put("bizType", "000000");

		// 渠道类型
		data.put("channelType", "07");
		// 接入类型 商户:0 收单:1 平台：2
		data.put("accessType", "0");
		// 商户号码
		data.put("merId", "700000000000001");// 210440383989420 700000000000001
		// 收单机构代码
		// data.put("acqInsCode", "");
		// 二级商户代码
		data.put("subMerId", "");
		// 二级商户名称
		// data.put("subMerName", "");
		// 二级商户简称
		// data.put("subMerAbbr", "");
		// 清算日期
		data.put("settleDate", "0502");
		// 订单发送时间
		data.put("txnTime", SDKUtil.generateTxnTime());// 订单发送时间如果自己手动生成时,请每次执行之前，稍做更改以防与当前时间差距较大导致交易失败
		// 文件类型 --依据实际业务情况定义，默认值为：00
		data.put("fileType", "00");
		// 请求方保留域
		// data.put("reqReserved", "");
		// 保留域
		// data.put("reserved", "");

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
		HttpClient hc = new HttpClient(requestUrl, 160000, 280000);
		try {
			int status = hc.send(request, encoding);
			if (200 == status) {
				result = hc.getResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/**
		 * 1.打印返回报文
		 */
		System.out.println("返回报文=[" + result + "]");

		/**
		 * 验证签名
		 */
		if (null != result && !"".equals(result)) {
			// 将返回结果转换为map
			Map<String, String> resData = SDKUtil
					.convertResultStringToMap(result);
			if (SDKUtil.validate(resData, encoding)) {
				System.out.println("验证签名成功");

				// 解析返回文件
				String fileContent = resData
						.get(SDKConstants.param_fileContent);
				if (null != fileContent && !"".equals(fileContent)) {
					try {
						byte[] fileArray = SecureUtil.inflater(SecureUtil
								.base64Decode(fileContent.getBytes(encoding)));
						String root = "D:\\";
						String filePath = null;
						if (SDKUtil.isEmpty(resData.get("fileName"))) {
							filePath = root + File.separator
									+ resData.get("merId") + "_"
									+ resData.get("batchNo") + "_"
									+ resData.get("txnTime") + ".txt";
						} else {
							filePath = root + File.separator
									+ resData.get("fileName");
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

			} else {
				System.out.println("验证签名失败");
			}
			// 打印返回报文
			System.out.println(result);
		}
	}

}

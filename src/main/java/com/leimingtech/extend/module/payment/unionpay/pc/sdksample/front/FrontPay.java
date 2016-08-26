package com.leimingtech.extend.module.payment.unionpay.pc.sdksample.front;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKConfig;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKUtil;
import com.leimingtech.extend.module.payment.unionpay.pc.sdksample.back.Common;

/**
 * 名称：支付请求类（如消费、预授权等） 功能： 类属性： 方法调用 版本：5.0 日期：2014-07 作者：中国银联ACP团队 版权：中国银联
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
 * */

public class FrontPay{

	public static void main(String[] args) {
		// 字符集编码
		String encoding = "UTF-8";
		/**
		 * 初始化证书
		 */
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
		// 请求url
		String requestUrl = SDKConfig.getConfig().getFrontRequestUrl();
		/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", "5.0.0");
		// 字符集编码
		data.put("encoding", encoding);
		// 证书ID
		// data.put("certId", "31692114440333550101559775639582427889");
		// 签名
		// data.put("signature", "");
		// 签名方法
		data.put("signMethod", "01");
		// 交易类型
		data.put("txnType", "01");
		// 交易子类型 01:自助消费 02:订购 03:分期付款
		data.put("txnSubType", "01");
		// 业务类型
		data.put("bizType", "000000");
		// 渠道类型
		data.put("channelType", "08");
		// 商户/收单前台接收地址
		data.put("frontUrl", "http://localhost:8080/ACPTest/acp_front_url.do");
		// 商户/收单后台接收地址
		data.put("backUrl", "http://localhost:8080/ACPTest/acp_back_url.do");
		// 接入类型 商户:0 收单:1
		data.put("accessType", "0");
		// 收单机构代码 ，直连商户不需要填写
		data.put("acqInsCode", "");
		// 商户类别 mcc码	1233
		data.put("merCatCode", "");
		// 商户号码
		data.put("merId", "105550149170027");
		// 商户名称
		data.put("merName", "");
		// 商户简称
		data.put("merAbbr", "");
		// 二级商户代码
		data.put("subMerId", "");
		// 二级商户名称
		data.put("subMerName", "");
		// 二级商户简称
		data.put("subMerAbbr", "");
		// 订单号
		//data.put("orderId", "123231412332132");
		// 订单发送时间
		//data.put("txnTime", "20140715015900");
		// 账号类型	01
		data.put("accType", "");
		// 账号 不加密	622188123456789
		data.put("accNo", "");
		// 账号加密
		// data.put("accNo",MpiUtil.encrptPan("622188123456789", encoding));
		// 交易金额 分
		data.put("txnAmt", "1");
		// 交易币种
		data.put("currencyCode", "156");
		// 银行卡验证信息及身份信息
		data.put("customerInfo", Common.getCustomer(data,encoding));
		// 订单超时时间 orderTimeoutInterval
		data.put("orderTimeout", "12");
		// 订单支付超时时间 payTimeoutTime
		data.put("payTimeout", "20120815135900");
		// 终端号
		data.put("termId", "");
		// 商户保留域
		data.put("reqReserved", "");
		// 保留域
		data.put("reserved", "");
		// 风险信息域
		data.put("riskRateInfo", "");
		// 加密证书ID
		data.put("encryptCertId", "");
		// 失败交易前台跳转地址
		data.put("frontFailUrl", "");
		// 分期付款信息域
		data.put("instalTransInfo", "");
		// 默认支付方式
		data.put("defaultPayType", "");
		// 发卡机构代码
		data.put("issInsCode", "");
		// 支持支付方式
		data.put("supPayType", "");
		// 终端信息域
		data.put("userMac", "");
		// 持卡人ip
		data.put("customerIp", "127.0.0.1");
		// 绑定标识号
		data.put("bindId", "");
		// 支付卡类型
		data.put("payCardType", "");
		// 安全类型
		data.put("securityType", "");
		// 有卡交易信息域
		data.put("cardTransData", "");
		// VPC交易信息域
		data.put("vpcTransData", "");
		// 订单描述
		data.put("orderDesc", "");
		
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
		 * 创建表单
		 */
		String html = createHtml(requestUrl, request);
		System.out.println(html);
	}

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
		sf.append("<form id = \"sform\" action=\"" + action
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
		sf.append("document.all.sform.submit();");
		sf.append("</script>");
		return sf.toString();
	}
}

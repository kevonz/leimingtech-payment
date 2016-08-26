package com.leimingtech.extend.module.payment.module.unionopay.pc.refund.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.leimingtech.core.common.SpringContextUtil;
import com.leimingtech.core.entity.UnionRefund;
import com.leimingtech.extend.module.payment.ComContents;
import com.leimingtech.extend.module.payment.module.unionopay.pc.refund.service.UnionRefundService;
import com.leimingtech.extend.module.payment.unionpay.pc.config.UnionpayBase;
import com.leimingtech.extend.module.payment.unionpay.pc.config.UnionpayContents;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.LogUtil;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKConfig;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKConstants;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKUtil;

@Service
@Slf4j
public class UnionRefundServiceImpl implements UnionRefundService {
	


	public String toUnionRefund(UnionRefund unionRefund) {
		/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", UnionpayContents.version);
		// 字符集编码 默认"UTF-8"
		data.put("encoding",  UnionpayContents.encoding);
		// 签名方法 01 RSA
		data.put("signMethod", UnionpayContents.signMethod);
		// 交易类型  04表示退货
		data.put("txnType","04");
		// 交易子类型 
		data.put("txnSubType", "00");
		// 业务类型
		data.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		data.put("channelType",unionRefund.getChannelType());
		// 前台通知地址 ，控件接入方式无作用
		data.put("frontUrl", ComContents.payserviceurl+unionRefund.getFrontUrl());
		// 后台通知地址
		data.put("backUrl", ComContents.payserviceurl+unionRefund.getBackUrl());
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId",UnionpayContents.MEM_ID);//生产环境UnionpayContents.MEM_ID
		//原消费的queryId，可以从查询接口或者通知接口中获取
		data.put("origQryId",unionRefund.getTradeNo());
		// 商户订单号，8-40位数字字母，重新产生，不同于原消费
		data.put("orderId",unionRefund.getOrderId());
		// 订单发送时间，取系统时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 交易金额
		Double amount = 1d; //退款总金额
		data.put("txnAmt",amount.intValue()+"");
		// 交易币种
		data.put("currencyCode", "156");
		// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		// data.put("reqReserved", "透传信息");
		String html = "";
		SDKConfig config = SDKConfig.getConfig();
		//获取本地商户私钥证书（签名），银联公钥证书（验签）等地址
		String relapath=SpringContextUtil.getResourceRootRealPath()+File.separator+"com/leimingtech/extend/module/payment/unionpay/pc";
		//加载配置acp_sdk.properties文件
		config.loadPropertiesFromPath(relapath);//loadPropertiesFromSrc();
		//加载订单后后进行拼接
	    html = UnionpayBase.createHtml(config.getFrontRequestUrl(),UnionpayBase.signData(data));
		//建立请求
 		// 银联订单号 tn 商户推送订单后银联移动支付系统返回该流水号，商户调用支付控件时使用
 		System.out.println("请求报文=["+data.toString()+"]");
 		System.out.println("应答报文=["+html+"]");
		return html;
		
	}
	@Override
	public Map<String,Object> UnionRefundfront(HttpServletRequest request,HttpServletResponse rep){
		//获取本地商户私钥证书（签名），银联公钥证书（验签）等地址
		String relapath=SpringContextUtil.getResourceRootRealPath()+File.separator+"com/leimingtech/extend/module/payment/unionpay/pc";
		//加载配置acp_sdk.properties文件
		SDKConfig.getConfig().loadPropertiesFromPath(relapath);//loadPropertiesFromSrc();
		LogUtil.writeLog("FrontRcvResponse前台接收报文返回开始");
		//PrintWriter out = null;
		Map<String,Object> payMap=new HashMap<String,Object>();
		payMap.put("paystate","failure");
//		try {
//			out= rep.getWriter();
//		}catch (IOException e2){
//			e2.printStackTrace();
//		}
//		try {
//			request.setCharacterEncoding("UTF-8");
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
		String encoding = request.getParameter(SDKConstants.param_encoding);
		log.debug("返回报文中encoding=[" + encoding + "]");
		/*String pageResult = "";
		if ("UTF-8".equalsIgnoreCase(encoding)) {
			pageResult = "/cart/pay_result";
		} else {
			pageResult = "/cart/pay_result";
		}*/
		Map<String, String> respParam = getAllRequestParam(request);
		// 打印请求报文
		LogUtil.printRequestLog(respParam);
		Map<String, String> valideData = null;
		StringBuffer page = new StringBuffer();
		if (null != respParam && !respParam.isEmpty()) {
			Iterator<Entry<String, String>> it = respParam.entrySet().iterator();
			valideData = new HashMap<String, String>(respParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				try {
					value = new String(value.getBytes("UTF-8"), encoding);
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				page.append("<tr><td width=\"30%\" align=\"right\">" + key
						+ "(" + key + ")</td><td>" + value + "</td></tr>");
				valideData.put(key, value);
			}
		}
		if (!SDKUtil.validate(valideData, encoding)) {
			page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>失败</td></tr>");
			log.debug("验证签名结果[失败].");
			//out.println("failure");
			payMap.put("paystate","failure");
			payMap.put("msg","验证签名结果[失败]");
		} else {
			System.out.println("ordersn:**********"+valideData.get("orderId"));
			page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>成功</td></tr>");
			log.debug("验证签名结果[成功].");
			//payService.updatePayFinish(valideData.get("orderId"));
			System.out.println(valideData.get("orderId")); //其他字段也可用类似方式获取
			
			//out.print("success");//向银行发送处理结果 
			payMap.put("paystate","success");
			payMap.put("out_trade_no",valideData.get("orderId"));
			payMap.put("msg","验证签名结果[成功]");
		}
		request.setAttribute("result", page.toString());
		log.debug("FrontRcvResponse前台接收报文返回结束");
		return payMap;
	}

	@Override
	public Map<String,Object> UnionRefundback(HttpServletRequest request,HttpServletResponse resp) {
		//获取本地商户私钥证书（签名），银联公钥证书（验签）等地址
		String relapath=SpringContextUtil.getResourceRootRealPath()+File.separator+"com/leimingtech/extend/module/payment/unionpay/pc";
		//加载配置acp_sdk.properties文件
		SDKConfig.getConfig().loadPropertiesFromPath(relapath);//loadPropertiesFromSrc();
		//PrintWriter out = null;
		Map<String,Object> payMap=new HashMap<String,Object>();
		payMap.put("paystate","failure");
//		try {
//			out= resp.getWriter();
//		} catch (IOException e2) {
//			e2.printStackTrace();
//		}
//		log.debug("BackRcvResponse接收后台通知开始");
//		try {
//			request.setCharacterEncoding("UTF-8");
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
		String encoding = request.getParameter(SDKConstants.param_encoding);
		// 获取请求参数中所有的信息
		Map<String, String> reqParam = getAllRequestParam(request);
		// 打印请求报文
		LogUtil.printRequestLog(reqParam);
		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap<String, String>(reqParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				try {
					value = new String(value.getBytes("UTF-8"), encoding);
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				valideData.put(key, value);
			}
		}
			// 验证签名
			if (!SDKUtil.validate(valideData, encoding)) {
				log.debug("验证签名结果[失败].");
				payMap.put("paystate","failure");
				payMap.put("msg","验证签名结果[失败]");
				//out.println("failure");
			}else {
				log.debug("验证签名结果[成功].");
				//out.println("success");
				payMap.put("paystate","success");
				payMap.put("out_trade_no",valideData.get("orderId"));
				payMap.put("msg","验证签名结果[成功]");
	     }	
			log.debug("BackRcvResponse接收后台通知结束");
			return payMap;
	}
	
	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(
			final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				// 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				if (res.get(en) == null || "".equals(res.get(en))) {
					// System.out.println("======为空的字段名===="+en);
					res.remove(en);
				}
			}
		}
		return res;
	}
	
	
	/**
	 * 拼接订单body
	 * @param orderId
	 * @return
	 */
//	public String getBody(int orderId){
//		//List<OrderCommon> orderCommon= orderCommonDao.selectByOrderId(Integer.valueOf(odrderId));
//		int[] ids = new int[]{orderId};
//		List<OrderGoods> orderGoods = orderGoodsDao.findByOrderId(ids);
//		String body = "无数据";
//		if(orderGoods.size()>0){
//			String name = "";
//			for(int i=0; i<orderGoods.size(); i++){
//				OrderGoods goods = orderGoods.get(i);
//				name += goods.getGoodsName()+",";
//			}
//			body = name;
//		}
//		return body;
//	}

}

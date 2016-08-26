package com.leimingtech.extend.module.payment.module.alipay.pc.china.pay.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.leimingtech.core.common.CommonConstants;
import com.leimingtech.core.entity.Order;
import com.leimingtech.core.entity.PayCommon;
import com.leimingtech.core.entity.base.OrderGoods;
import com.leimingtech.core.entity.base.Pay;
import com.leimingtech.core.entity.base.Payment;
import com.leimingtech.extend.module.payment.ComContents;
import com.leimingtech.extend.module.payment.alipay.pc.china.config.AlipayConfig;
import com.leimingtech.extend.module.payment.alipay.pc.china.config.AlipayContents;
import com.leimingtech.extend.module.payment.alipay.pc.china.util.AlipayNotify;
import com.leimingtech.extend.module.payment.alipay.pc.china.util.AlipaySubmit;
import com.leimingtech.extend.module.payment.module.alipay.pc.china.pay.service.AlipayService;
import com.leimingtech.service.module.trade.service.PayService;

@Service
@Slf4j
public class AlipayServiceImpl implements AlipayService {
	
	public String toPay(PayCommon payCommon) {
		
		//Pay pay = payService.findPayBySn(orderSn);
		//String bodyStr	 = pay.getBeWrite();
		String bodyStr="";
		String payment_type = "1";
		//必填，不能修改
		//服务器异步通知页面路径
		String notify_url =ComContents.payserviceurl+payCommon.getNotifyUrl();
		//需http://格式的完整路径，不能加?id=123这类自定义参数

		//页面跳转同步通知页面路径
		String return_url = ComContents.payserviceurl+payCommon.getReturnUrl();
		//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

		//商户订单号
		String out_trade_no =payCommon.getOutTradeNo();//new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//商户网站订单系统中唯一订单号，必填

		//订单名称
		//String subject = "世界高铁网订单";//new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
		String subject = "超级购物";
		//必填
		// 交易金额 分
//		Double amount = 0d; //订单总金额
//		amount = 100 * orderPay.getPayAmount().doubleValue();
        log.debug("total_fee = "+payCommon.getPayAmount()+"");
		//付款金额
		String total_fee = payCommon.getPayAmount()+"";//order.getOrderAmount().toString();//new String(request.getParameter("WIDtotal_fee").getBytes("ISO-8859-1"),"UTF-8");
		//必填
   
		//订单描述
		String body = "无描述";
		try {
			body = new String(bodyStr.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.error("",e);
		}
		//默认支付方式
		String paymethod = "bankPay";
		//必填
		//默认网银
		String defaultbank = "CMB";//new String(request.getParameter("WIDdefaultbank").getBytes("ISO-8859-1"),"UTF-8");
		//必填，银行简码请参考接口技术文档

		//商品展示地址
		String show_url = CommonConstants.FRONT_SERVER;//new String(request.getParameter("WIDshow_url").getBytes("ISO-8859-1"),"UTF-8");
		//需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.html

		//防钓鱼时间戳
		String anti_phishing_key = "";
		//若要使用请调用类文件submit中的query_timestamp函数

		//客户端的IP地址
		String exter_invoke_ip = "";
		//非局域网的外网IP地址，如：221.0.0.1
		
		//////////////////////////////////////////////////////////////////////////////////
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_email", AlipayConfig.seller_email);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("body", body);
		sParaTemp.put("paymethod", paymethod);
		sParaTemp.put("defaultbank", defaultbank);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("anti_phishing_key", anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
		return sHtmlText;
	}

	@SuppressWarnings("rawtypes")
	public Map<String,Object> payfront(HttpServletRequest request){
		String out_trade_no="";
		Map<String,Object> payMap=new HashMap<String,Object>();
		payMap.put("paystate","fail");
		try {
			//获取支付宝GET过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号
		    out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
	
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
	
			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
	
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			
			//计算得出通知验证结果
			boolean verify_result = AlipayNotify.verify(params);
			System.out.println("************front:"+params);
			System.out.println(verify_result);
			if(verify_result){//验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码
				//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				    //判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
					payMap.put("paystate","success");
					payMap.put("out_trade_no",out_trade_no);
					payMap.put("trade_no",trade_no);
					//payService.updatePayFinish(out_trade_no);
				}
				//该页面可做页面美工编辑
				//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
			}else{
				//该页面可做页面美工编辑
				//out.println("验证失败");
				log.error("支付宝验证失败");
				payMap.put("paystate","fail");
				payMap.put("msg","验证失败");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("出错啦，支付失败",e);
			payMap.put("paystate","fail");
			payMap.put("msg","出错啦，支付失败");
		}
		return payMap;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String,Object> payback(HttpServletRequest request) {
		Map<String,Object> payMap=new HashMap<String,Object>();
		payMap.put("paystate","fail");
		try{
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
	
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
	
			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
	
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			System.out.println("************back:"+params);
			System.out.println(AlipayNotify.verify(params));
			
			if(AlipayNotify.verify(params)){//验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码
				//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				if(trade_status.equals("TRADE_FINISHED")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序
					//注意：
					//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				} else if (trade_status.equals("TRADE_SUCCESS")){
					    //判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序
						payMap.put("paystate","success");
						payMap.put("out_trade_no",out_trade_no);
						payMap.put("trade_no",trade_no);
					   // payService.updatePayFinish(out_trade_no);
				}
				//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
				//////////////////////////////////////////////////////////////////////////////////////////
			}else{//验证失败
				payMap.put("paystate","fail");
				payMap.put("msg","验证失败");
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("",e);
//			log.error(e.toString());
			payMap.put("paystate","fail");
		}
		return payMap;
	}
	
	/**
	 * 拼接订单body
	 * @param orderId
	 * @return
	 */
	public String getBody(List<Order> orderlist){
		String body = "无数据";
		if(orderlist.size()>0){
			String name = "";
			for(Order order:orderlist){
				List<OrderGoods> goodslist=order.getOrderGoodsList();
				if(goodslist.size()>0){
					for(OrderGoods ordergoods:goodslist){
						name+=ordergoods.getGoodsName();
					}
				}
			}
			body = name;
		}
		return body;
	}

}

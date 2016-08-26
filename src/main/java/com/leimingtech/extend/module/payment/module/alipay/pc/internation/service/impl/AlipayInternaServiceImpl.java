package com.leimingtech.extend.module.payment.module.alipay.pc.internation.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.leimingtech.core.entity.Order;
import com.leimingtech.core.entity.base.OrderGoods;
import com.leimingtech.core.entity.base.Pay;
import com.leimingtech.extend.module.payment.ComContents;
import com.leimingtech.extend.module.payment.alipay.pc.internation.config.AlipayConfig;
import com.leimingtech.extend.module.payment.alipay.pc.internation.config.AlipayInternaContents;
import com.leimingtech.extend.module.payment.alipay.pc.internation.util.AlipayNotify;
import com.leimingtech.extend.module.payment.alipay.pc.internation.util.AlipaySubmit;
import com.leimingtech.extend.module.payment.module.alipay.pc.internation.service.AlipayInternaService;
import com.leimingtech.service.module.trade.service.PayService;

@Service
@Slf4j
public class AlipayInternaServiceImpl implements AlipayInternaService {
	
	@Resource
	private PayService payService;

	public String toPay(String ordersn) {
		try{ 
			Pay pay = payService.findPayBySn(ordersn);
			////////////////////////////////////请求参数//////////////////////////////////////
			//Return URL
			String return_url =ComContents.payserviceurl+AlipayInternaContents.aplipayinternafrontUrl;
			//After the payment transaction is done
			//Notification URL
			String notify_url = ComContents.payserviceurl+AlipayInternaContents.aplipayinternabackUrl;
			//The URL for receiving notifications after the payment process.
			//Goods name
			//String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
			String subject ="雷铭购物";//必填
			//required，The name of the goods.
			//Goods description
			//String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
			String body ="物美价廉";//非必填
			//A detailed description of the goods.
			//Outside trade ID
			//String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			String out_trade_no =pay.getPaySn();//必填
			//required，A numbered transaction ID （Unique inside the partner system）
			
			//Currency
			//String currency = new String(request.getParameter("WIDcurrency").getBytes("ISO-8859-1"),"UTF-8");
			//String currency="CNY";//必填 人民币国际符号
			 String currency="USD";//必填 美元国际符号
			//required，The settlement currency merchant named in contract.
			//Payment sum
			//String total_fee = new String(request.getParameter("WIDtotal_fee").getBytes("ISO-8859-1"),"UTF-8");
			//String total_fee="1";//必填  显示和支付是外币
			String rmb_fee=pay.getPayAmount()+"";;//显示和支付是人民币的
			//required，A floating number ranging 0.01～1000000.00
			//把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			
			sParaTemp.put("service", "create_forex_trade");
	        sParaTemp.put("partner", AlipayConfig.partner);
	        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
			sParaTemp.put("return_url", return_url);
			sParaTemp.put("notify_url", notify_url);
			sParaTemp.put("subject", subject);
			sParaTemp.put("body", body);
			sParaTemp.put("out_trade_no", out_trade_no);
			sParaTemp.put("currency", currency);
			//sParaTemp.put("total_fee", total_fee);
		    sParaTemp.put("rmb_fee", rmb_fee);
			//建立请求
			String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
			return sHtmlText;
		}catch(Exception e){
			e.printStackTrace();
			log.error("",e);
//			log.error(e.toString());
			return "fail";
		}
	}

	@SuppressWarnings("rawtypes")
	public String payfront(HttpServletRequest request){
		String out_trade_no="";
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
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
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
			System.out.println(verify_result);
			if(verify_result){//验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码
				//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				    //判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
					payService.updatePayFinish(out_trade_no,trade_no,"internaalipay");
				}
				//该页面可做页面美工编辑
				//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
			}else{
				//该页面可做页面美工编辑
				//out.println("验证失败");
				log.error("支付宝验证失败");
				return "支付宝验证失败";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("出错啦，支付失败",e);
			return "出错啦，支付失败";
		}
		return out_trade_no;
	}

	@SuppressWarnings("rawtypes")
	public String payback(HttpServletRequest request) {
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
					    payService.updatePayFinish(out_trade_no,trade_no,"internaalipay");
				}
				//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
				return "success";	//请不要修改或删除
			}else{//验证失败
				return "fail";
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("",e);
//			log.error(e.toString());
			return "fail";
		}
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

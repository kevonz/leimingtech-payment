package com.leimingtech.extend.module.payment.module.whchat.h5.pay.scan.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.entity.PayCommon;
import com.leimingtech.core.entity.base.Pay;
import com.leimingtech.core.entity.base.Store;
import com.leimingtech.extend.module.payment.module.whchat.h5.pay.scan.WechatScanService.WechatScanService;
import com.leimingtech.service.module.trade.service.PayService;

@Controller
@RequestMapping("/weChatScanpayment")
public class WechatScanAction {
	//微信进行首次链接验证确保正确的token
	@Resource
	private WechatScanService wechatScanService;
	@Resource
	private PayService payService;
	@RequestMapping("topay")
	public void toPay(PayCommon payCommon){
		String codeurl=wechatScanService.toPay(payCommon);
	}
	
	//修改订单状态
		@SuppressWarnings("unchecked")
		@RequestMapping("/updateorderstate")
		public void updateorderstate(HttpServletRequest request ,HttpServletResponse response,Model model)throws  IOException, DocumentException{
			PrintWriter out = null;
			int status = 0;//0失败 ，1 成功
			try {
				out= response.getWriter();
			}catch (IOException e2){
				e2.printStackTrace();
			}
			 // 解析结果存储在HashMap
		    Map<String, String> map = new HashMap<String, String>();
		    InputStream inputStream = request.getInputStream();
		    // 读取输入流
		    SAXReader reader = new SAXReader();
		    Document document = reader.read(inputStream);
		    // 得到xml根元素
		    Element root = document.getRootElement();
		    // 得到根元素的所有子节点
		    List<Element> elementList = root.elements();
		    // 遍历所有子节点
		    for (Element e : elementList)
		        map.put(e.getName(), e.getText());
		    // 释放资源
		    inputStream.close();
		    inputStream = null;
		    System.out.println(map);
		    //根据反过来支付信息修改订单状态
		    if(map.get("result_code")!=null&& map.get("result_code").equals("SUCCESS")){
		    	System.out.println("修改成功了"+map.get("out_trade_no"));//原商户订单
		    	System.out.println("修改成功了"+map.get("transaction_id"));//微信支付订单号
		    	//根据订单号修改订单信息
		    	payService.updatePayFinish(map.get("out_trade_no"),map.get("transaction_id"),"mp_weichatpay");//公共平台支付
		    	out.print("SUCCESS");
		    }else{
		    	out.print("FAIL");
		    }
		}
		
		@RequestMapping("/payfront")
		public String FrontRetrun(HttpServletRequest request , HttpServletResponse response, Model model){
	    	int status = 0;//0失败 ，1 成功
			String rebackurl="";
			String msg="";
			String ordersn=request.getParameter("ordersn");//订单号
			if(ordersn.toString().contains("R")){
				status = 1;
				rebackurl="/cart/topup_result";
				msg = "恭喜您，充值成功！";
			}else{
				status = 1;
				rebackurl="/cart/pay_result";
				msg = "恭喜您，支付成功！";
			}
			//这里是你的跳转页面
			model.addAttribute("status", status);
	    	model.addAttribute("msg", msg);
	        return rebackurl;
		}
		/**
		 * 检验订单是否支付成功
		 * 
		 * @param model
		 * @param id
		 * @return
		 */
		@RequestMapping("/checkorderstate")
		public @ResponseBody boolean checkStorename(@RequestParam String paysn) {
			Pay pay=payService.findPayBySn(paysn);
			if (pay!=null && pay.getPayState()==1) {
				return true;
			} else {
				return false;
			}
		}
}

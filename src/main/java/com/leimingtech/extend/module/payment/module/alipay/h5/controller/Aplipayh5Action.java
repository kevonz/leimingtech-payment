package com.leimingtech.extend.module.payment.module.alipay.h5.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.leimingtech.core.entity.PayCommon;
import com.leimingtech.core.entity.base.Pay;
import com.leimingtech.extend.module.payment.alipay.h5.config.Alipayh5Contents;
import com.leimingtech.extend.module.payment.module.alipay.h5.service.Alipayh5Service;
import com.leimingtech.service.module.trade.service.PayService;

@Slf4j
@Controller
@RequestMapping("/alipayh5ment")
public class Aplipayh5Action {
	
	@Resource
	private Alipayh5Service alipayh5Service;
	@Resource
    private PayService payService;
	
	/**
	 *功能：纯网关接口接入页
	 *版本：3.3
	 *日期：2012-08-14
	 *说明：
	 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
	 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

	 *************************注意*****************
	 *如果您在接口集成过程中遇到问题，可以按照下面的途径来解决
	 *1、商户服务中心（https://b.alipay.com/support/helperApply.htm?action=consultationApply），提交申请集成协助，我们会有专业的技术工程师主动联系您协助解决
	 *2、商户帮助中心（http://help.alipay.com/support/232511-16307/0-16307.htm?sh=Y&info_type=9）
	 *3、支付宝论坛（http://club.alipay.com/read-htm-tid-8681712.html）
	 *如果不想使用扩展功能请把扩展功能参数赋空值。
	 **********************************************
	 */
	@RequestMapping("topay")
	public void toPay(HttpServletRequest request ,HttpServletResponse response){
		String paySn = request.getParameter("paySn");
		Pay pay = payService.findPayBySn(paySn);
		PayCommon payCommon = new PayCommon();
		payCommon.setOutTradeNo(pay.getPaySn());
		payCommon.setPayAmount(pay.getPayAmount());
		payCommon.setNotifyUrl(Alipayh5Contents.alipayh5backUrl);
		payCommon.setReturnUrl(Alipayh5Contents.alipayh5frontUrl);
		try {
			String sHtmlText = "";
			if(StringUtils.isEmpty(paySn)){
				sHtmlText = "订单号不能为空";
			}else{
				sHtmlText = alipayh5Service.toPay(payCommon);//构造提交支付宝的表单
				System.out.println("****"+sHtmlText);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(sHtmlText);
		}catch(IOException e) {
			log.error("",e);
		}
	}
	
	/**
	 * 功能：支付宝页面跳转同步通知页面
	 * 版本：3.2
	 * 日期：2011-03-17
	 * 说明：
	 * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
	 * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

	 //***********页面功能说明***********
	 * 该页面可在本机电脑测试
	 * 可放入HTML等美化页面的代码、商户业务逻辑程序代码
	 * TRADE_FINISHED(表示交易已经成功结束，并不能再对该交易做后续操作);
	 * TRADE_SUCCESS(表示交易已经成功结束，可以对该交易做后续操作，如：分润、退款等);
	 //********************************
	 **/
	@RequestMapping("/payfront")
	public String FrontRetrun(HttpServletRequest request , HttpServletResponse response, Model model){
		int status = 0;//0失败 ，1 成功
		log.debug("===============payfront================");
		String rebackurl="";
		String msg="";
		Map<String,Object> payMap = alipayh5Service.payfront(request);
		if(payMap.size()!=0){
			if(payMap.get("paystate").equals("success")&&!payMap.get("out_trade_no").equals("")){
			    	payService.updatePayFinish(payMap.get("out_trade_no").toString(),payMap.get("trade_no").toString(),"alipay");
					if(payMap.get("out_trade_no").toString().contains("R")){
						status = 1;
						rebackurl="/html5/buyer/rechpay_success";
						msg = "恭喜您，充值成功！";
					}else{
						status = 1;
						rebackurl="/html5/buyer/orderpay_success";
						msg = "恭喜您，支付成功！";
					}
			}
		}
		//这里是你的跳转页面
		model.addAttribute("status", status);
    	model.addAttribute("msg", msg);
        return rebackurl;
	}
	
	/**
	* 功能：支付宝服务器异步通知页面
	* 版本：3.3
	* 日期：2012-08-17
	* 说明：
	* 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
	* 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

	 //***********页面功能说明***********
	* 创建该页面文件时，请留心该页面文件中无任何HTML代码及空格。
	* 该页面不能在本机电脑测试，请到服务器上做测试。请确保外部可以访问该页面。
	* 该页面调试工具请使用写文本函数logResult，该函数在com.alipay.util文件夹的AlipayNotify.java类文件中
	* 如果没有收到该页面返回的 success 信息，支付宝会在24小时内按一定的时间策略重发通知
	 //********************************
	 * */
	@RequestMapping("/payback")
	public void BackReturn(HttpServletRequest request ,HttpServletResponse response){
		log.debug("===============packback================");
		PrintWriter out;
		Map<String,Object> payMap=null;
		try {
			out = response.getWriter();
			payMap = alipayh5Service.payback(request);
			if(payMap.size()!=0){//out_trade_no返回过来的订单号
				if(payMap.get("paystate").equals("success")&&!payMap.get("out_trade_no").equals("")){
					payService.updatePayFinish(payMap.get("out_trade_no").toString(),payMap.get("trade_no").toString(),"alipay");
				}
			}
			//获取支付宝POST过来反馈信息 支付成功success 支付失败fail
			out.print(payMap.get("paystate"));
		} catch (IOException e) {
			log.error("/payback notify 支付失败",e);
		}
	}

}

package com.leimingtech.extend.module.payment.module.unionopay.pc.pay.controller;

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
import com.leimingtech.extend.module.payment.module.unionopay.pc.pay.service.UnionpayService;
import com.leimingtech.service.module.trade.service.PayService;

@Slf4j
@Controller
@RequestMapping("/Unionpayment")
public class UnionpayAction {
	
	@Resource 
	private UnionpayService Unionpayservice;
	
	@Resource
	private PayService payService;
	
	@RequestMapping("Unionpay")
	public void toPay(HttpServletRequest request ,HttpServletResponse response){
		String orderSn = request.getParameter("sn");
		//String orderSn ="20150120235242453";
		PayCommon paycommon=null;
		try {
			String sHtmlText = "";
			if(StringUtils.isEmpty(orderSn)){
				//sHtmlText = AlipaySubmit.errorRedirect(null, null);//构造错误跳转方法
			}else{
				sHtmlText = Unionpayservice.toUnionpay(paycommon);//构造提交银联的表单
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(sHtmlText);
		}catch(IOException e) {
			e.printStackTrace();
			log.error("",e);
		}
	}
	
	/**
	 * 功能：银联页面跳转同步通知页面
	 * 版本：3.2
	 * 日期：2015-07-02
	 * 说明：
	 * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
	 * 该代码仅供学习和研究银联接口使用，只是提供一个参考。

	 //***********页面功能说明***********
	 * 该页面可在本机电脑测试
	 * 可放入HTML等美化页面的代码、商户业务逻辑程序代码
	 * TRADE_FINISHED(表示交易已经成功结束，并不能再对该交易做后续操作);
	 * TRADE_SUCCESS(表示交易已经成功结束，可以对该交易做后续操作，如：分润、退款等);
	 //********************************
	 **/
	@RequestMapping("/Unionpayfront")
	public String FrontRetrun(HttpServletRequest request , HttpServletResponse response, Model model){
    	int status = 0;//0失败 ，1 成功
		log.debug("===============payfront================");
		//String orderpayCode = Unionpayservice.Unionpayfront(request,response);
		Map<String,Object> payMap=Unionpayservice.Unionpayfront(request,response);
		String rebackurl="";
		String msg="";
		if(payMap.size()!=0){
			if(payMap.get("paystate").equals("success")&&!payMap.get("out_trade_no").equals("")){
				   payService.updatePayFinish(payMap.get("out_trade_no").toString(),payMap.get("trade_no").toString(),"pc_unionpay");
					if(payMap.get("out_trade_no").toString().contains("R")){
						status = 1;
						rebackurl="/cart/topup_result";
						msg = "恭喜您，充值成功！";
					}else{
						status = 1;
						rebackurl="/cart/pay_result";
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
	* 功能：银联服务器异步通知页面
	* 版本：3.3
	* 日期：2015-07-02
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
	@RequestMapping("/Unionpayback")
	public void BackReturn(HttpServletRequest request ,HttpServletResponse response){
		log.debug("===============packback================");
		PrintWriter out;
		Map<String,Object> payMap=null;
		try {
			out = response.getWriter();
			payMap = Unionpayservice.Unionpayback(request,response);
			if(payMap.size()!=0){//out_trade_no返回过来的订单号 失败返回failure 成功返回success
				if(payMap.get("paystate").equals("success")&&!payMap.get("out_trade_no").equals("")){
					payService.updatePayFinish(payMap.get("out_trade_no").toString(),payMap.get("trade_no").toString(),"pc_unionpay");
				}
			}
			//获取银联POST过来反馈信息
			out.print(payMap.get("paystate"));
		} catch (IOException e) {
			log.error("/payback notify 支付失败",e);
		}
	}
	
	
}

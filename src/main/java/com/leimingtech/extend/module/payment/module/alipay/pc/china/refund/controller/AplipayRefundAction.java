package com.leimingtech.extend.module.payment.module.alipay.pc.china.refund.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.leimingtech.core.entity.AliPayRefund;
import com.leimingtech.extend.module.payment.module.alipay.pc.china.refund.service.AlipayRefundService;
import com.leimingtech.service.module.trade.service.PayService;
import com.leimingtech.service.module.trade.service.RefundReturnService;

@Slf4j
@Controller
@RequestMapping("/refundpayment")
public class AplipayRefundAction {
	
	@Resource
	private AlipayRefundService alipayRefundService;
	
	@Resource
	private PayService payService;
	
	@Resource
    private RefundReturnService refundReturnService;
	
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
	@RequestMapping("toRefundpay")
	public void toRefundpay(HttpServletRequest request ,HttpServletResponse response){
		AliPayRefund aliPayRefund=new AliPayRefund();
		aliPayRefund.setRefundAmountNum(1);
		aliPayRefund.setDetaildata("2015101421001004650090828422",BigDecimal.valueOf(0.01),"协商退款");
		try {
			String sHtmlText = "";
//			if(StringUtils.isEmpty(orderSn)){
//				sHtmlText = AlipaySubmit.errorRedirect(null, null);//构造错误跳转方法
//			}else{
			  sHtmlText=alipayRefundService.toRefund(aliPayRefund);//构造提交支付宝的表单
//			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(sHtmlText);
		}catch(IOException e) {
			log.error("",e);
		}
	}
	
//	/**
//	 * 功能：支付宝页面跳转同步通知页面
//	 * 版本：3.2
//	 * 日期：2011-03-17
//	 * 说明：
//	 * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
//	 * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
//
//	 //***********页面功能说明***********
//	 * 该页面可在本机电脑测试
//	 * 可放入HTML等美化页面的代码、商户业务逻辑程序代码
//	 * TRADE_FINISHED(表示交易已经成功结束，并不能再对该交易做后续操作);
//	 * TRADE_SUCCESS(表示交易已经成功结束，可以对该交易做后续操作，如：分润、退款等);
//	 //********************************
//	 **/
//	@RequestMapping("/payfront")
//	public String FrontRetrun(HttpServletRequest request , HttpServletResponse response, Model model){
//    	int status = 0;//0失败 ，1 成功
//		log.debug("===============payfront================");
//		String rebackurl="";
//		String msg="";
//		Map<String,Object> payMap=alipayService.payfront(request);
//		if(payMap.size()!=0){
//			if(payMap.get("paystate").equals("success")&&!payMap.get("out_trade_no").equals("")){
//			    	payService.updatePayFinish(payMap.get("out_trade_no").toString());
//					if(payMap.get("out_trade_no").toString().contains("R")){
//						status = 1;
//						rebackurl="/cart/topup_result";
//						msg = "恭喜您，充值成功！";
//					}else{
//						status = 1;
//						rebackurl="/cart/pay_result";
//						msg = "恭喜您，支付成功！";
//					}
//			}
//		}
//		//这里是你的跳转页面
//		model.addAttribute("status", status);
//    	model.addAttribute("msg", msg);
//        return rebackurl;
//	}
//	
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
	@RequestMapping("/payRefundback")
	public String BackReturn(HttpServletRequest request ,HttpServletResponse response,Model model){
		log.debug("===============packback================");
		PrintWriter out;
		Map<String,Object> payMap=null;
		String rebackurl="";
		String msg="";
		try {
			out = response.getWriter();
			payMap = alipayRefundService.Refundback(request);
			if(payMap.size()!=0){//out_trade_no返回过来的交易号
				if(payMap.get("refundstate").equals("success")&&!payMap.get("result_details").equals("")){
					//String id=request.getParameter("id");
					//String adminMessage=request.getParameter("adminMessage");
					String resultdetails=payMap.get("result_details")+"";//得到成功后的结果集
					String messaged="";//退款理由
					if(StringUtils.isNotEmpty(resultdetails)){
						messaged=resultdetails.substring(resultdetails.lastIndexOf("^")+1,resultdetails.length());
					}
					String bathno=(String) payMap.get("batch_no");//批次号
					if(StringUtils.isNotEmpty("bathno")){
						refundReturnService.updateRefundReturnByBatchNo(bathno,messaged);
					}
					rebackurl="/refund/refund_result";
					msg = "审核成功";
					model.addAttribute("msg",msg);
				}
			}
			//获取支付宝POST过来反馈信息 支付成功success 支付失败fail
			out.print(payMap.get("refundstate"));
		} catch (IOException e) {
			log.error("/payback notify 支付失败",e);
		}
		return rebackurl;
	}
	
	 public static void main(String[] args) {
		String df="2011011201037066^5.00^协商退款";
		System.out.println(df.lastIndexOf("^"));
		System.out.println(df.substring(df.lastIndexOf("^")+1,df.length()));
	}
}



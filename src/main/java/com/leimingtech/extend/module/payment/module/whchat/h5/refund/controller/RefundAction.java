package com.leimingtech.extend.module.payment.module.whchat.h5.refund.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.leimingtech.core.entity.WeiRefund;
import com.leimingtech.extend.module.payment.module.whchat.h5.refund.service.WechatRefundService;

@Controller
@RequestMapping("/weChatrefundpayment")
public class RefundAction {
	@Resource
	private WechatRefundService wechatRefundService;
	
	@RequestMapping("towechatRefund")
	public String wechatRefund(Model model,WeiRefund weiRefund) {
		String rebackurl="";
		String msg="";
		weiRefund.setOutrefundno("1003520632201508280727759639");//
		weiRefund.setOuttradeno("p20150828112922039");
		weiRefund.setRefundfee(1);
		weiRefund.setTotalfee(1);
		Map<String,Object> map=wechatRefundService.toRefund(weiRefund);
		//SUCCESS退款申请接收成功，结果通过退款查询接口查询 FAIL 提交业务失败
		if(map.size()!=0 && map.get("result_code").equals("SUCCESS")){
				String paytype="weimp";
				String out_refund_no=map.get("out_refund_no")+"";//微信订单号
				String out_trade_no=map.get("out_trade_no")+"";//商户退款单号
				System.out.println("out_refund_no"+out_refund_no);
				System.out.println("out_trade_no"+out_trade_no);
				/////////////
                   ////修改订单状态				
				///////////
				rebackurl="/cart/refund_result";
				msg = "退款成功！";
				model.addAttribute("msg",msg);
		}
		System.out.println("***********"+map);
		return rebackurl;
		
	}
	
	
	public static void main(String[] args) {
		RefundAction refund=new RefundAction();
		//refund.wechatRefund();
	}
}

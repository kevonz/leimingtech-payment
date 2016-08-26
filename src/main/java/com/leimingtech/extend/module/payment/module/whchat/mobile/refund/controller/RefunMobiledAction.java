package com.leimingtech.extend.module.payment.module.whchat.mobile.refund.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.leimingtech.core.entity.WeiRefund;
import com.leimingtech.extend.module.payment.module.whchat.mobile.refund.service.WechatMobileRefundService;

@Controller
@RequestMapping("/weChatreMobilefundpayment")
public class RefunMobiledAction {
	@Resource
	private WechatMobileRefundService wechatMobileRefundService;
	
	@RequestMapping("towechatMobileRefund")
	public void wechatRefund() {
		WeiRefund weiRefund=new WeiRefund();
		weiRefund.setOutrefundno("1004780170201509020770858827");
		weiRefund.setOuttradeno("P2015090215215488112");
		weiRefund.setRefundfee(1);
		weiRefund.setTotalfee(1);
		Map<String,Object> map=wechatMobileRefundService.toRefund(weiRefund);
		System.out.println("***********"+map);
	}
	public static void main(String[] args) {
		RefunMobiledAction refund=new RefunMobiledAction();
		refund.wechatRefund();
	}
}

package com.leimingtech.extend.module.payment.module.alipay.pc.china.refund.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.leimingtech.core.common.DateUtils;
import com.leimingtech.core.common.NumberUtils;
import com.leimingtech.core.entity.AliPayRefund;
import com.leimingtech.core.entity.Order;
import com.leimingtech.core.entity.base.OrderGoods;
import com.leimingtech.extend.module.payment.ComContents;
import com.leimingtech.extend.module.payment.alipay.pc.china.config.AlipayConfig;
import com.leimingtech.extend.module.payment.alipay.pc.china.util.AlipayNotify;
import com.leimingtech.extend.module.payment.alipay.pc.china.util.AlipaySubmit;
import com.leimingtech.extend.module.payment.module.alipay.pc.china.refund.service.AlipayRefundService;
@Service
@Slf4j
public class AlipayServiceRefundImpl implements AlipayRefundService {
	@Override
	public String toRefund(AliPayRefund aliPayRefund) {
		        //服务器异步通知页面路径
				String notify_url =ComContents.payserviceurl+aliPayRefund.getNotifyurl();
				//需http://格式的完整路径，不允许加?id=123这类自定义参数
				//退款当天日期
				String refund_date = DateUtils.getnowDate();
				//必填，格式：年[4位]-月[2位]-日[2位] 小时[2位 24小时制]:分[2位]:秒[2位]，如：2007-10-01 13:13:13
				//批次号
				String batch_no = DateUtils.getDateStr("yyyyMMddHHmmssSSS")+NumberUtils.getRandomNumber();
				//必填，格式：当天日期[8位]+序列号[3至24位]，如：201008010000001

				//退款笔数
				String batch_num = aliPayRefund.getRefundAmountNum()+"";
				//必填，参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的数量999个）
                
				//退款理由
//				String refundReason=aliPayRefund.getRRefundReason();
//				if(StringUtils.isEmpty(refundReason)){
//					refundReason="refundReason";
//				}
				//退款详细数据
				String detail_data =aliPayRefund.getDetaildata();
				//必填，具体格式请参见接口技术文档
				//////////////////////////////////////////////////////////////////////////////////
				//把请求参数打包成数组
				Map<String, String> sParaTemp = new HashMap<String, String>();
				sParaTemp.put("service", "refund_fastpay_by_platform_pwd");
		        sParaTemp.put("partner", AlipayConfig.partner);
		        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
				sParaTemp.put("notify_url", notify_url);
				sParaTemp.put("seller_email", AlipayConfig.seller_email);
				sParaTemp.put("refund_date", refund_date);
				sParaTemp.put("batch_no", batch_no);
				sParaTemp.put("batch_num", batch_num);
				sParaTemp.put("detail_data", detail_data);
				//建立请求
				String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
				return sHtmlText;
	}

	@Override
	public Map<String, Object> Refundback(HttpServletRequest request) {
		//获取支付宝POST过来反馈信息
		Map<String,Object> payMap=new HashMap<String,Object>();
		payMap.put("refundstate","fail");
		try {
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
			//批次号
			String batch_no = new String(request.getParameter("batch_no").getBytes("ISO-8859-1"),"UTF-8");
			//批量退款数据中转账成功的笔数
			String success_num = new String(request.getParameter("success_num").getBytes("ISO-8859-1"),"UTF-8");
			//批量退款数据中的详细信息
			String result_details = new String(request.getParameter("result_details").getBytes("ISO-8859-1"),"UTF-8");
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			if(AlipayNotify.verify(params)){//验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码
				//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				if(!"0".equals(success_num)){
					payMap.put("refundstate","success");
					payMap.put("batch_no","batch_no");
					payMap.put("result_details",result_details);
				}
				//判断是否在商户网站中已经做过了这次通知返回的处理
				//如果没有做过处理，那么执行商户的业务程序
				//如果有做过处理，那么不执行商户的业务程序
				//请不要修改或删除
				//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
				//////////////////////////////////////////////////////////////////////////////////////////
			}else{//验证失败
				payMap.put("refundstate","fail");
		   }
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("出错啦，支付失败",e);
			payMap.put("refundstate","fail");
			payMap.put("msg","出错啦，退款失败");
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

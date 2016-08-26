package com.leimingtech.extend.module.payment.module.alipay.mobile.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.extend.module.payment.alipay.mobile.config.AlipayConfig;
import com.leimingtech.extend.module.payment.alipay.mobile.util.AlipayNotify;
import com.leimingtech.service.module.trade.service.PayService;


/**
 * 支付宝手机端支付
 * 
 * @company 雷铭智信
 * @author 谢进伟
 * @DateTime 2015-2-12 下午12:43:43
 */
@Slf4j
@Controller
@RequestMapping(value = "/front/LeimingmobileAlipay/")
public class AlipayMobileAction {
	
	@Autowired
	private PayService payService;

	
	/**
	 * 获取支付宝相关参数
	 * 
	 * @param response
	 * @param request
	 * @param paramName
	 *            支付宝相关参数名称
	 * @return
	 */
	@RequestMapping(value = "getAlipayParam")
	@ResponseBody
	public JSONObject getAlipayParam(HttpServletResponse response , HttpServletRequest request , @RequestParam(required = false) Object paramName) {
		// 检查参数
		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("paramName" , paramName);
		JSONObject checkParams = APIResponseUtil.checkParams(paramMap , "paramName");
		List<Map<String , Object>> resultJsonList = new ArrayList<Map<String , Object>>();
		Map<String , Object> map = new HashMap<String , Object>();
		if(checkParams != null) {
			map.put("partner" , AlipayConfig.PARTNER);
			map.put("privateKey" , AlipayConfig.PRIVATE_KEY);
			map.put("pubicKey" , AlipayConfig.ALI_PUBLIC_KEY);
			map.put("sellerId" , AlipayConfig.SELLERID);
		} else {
			if(paramName.toString().toUpperCase().equals("PARTNER")) {// partner
				map.put(paramName.toString() , AlipayConfig.PARTNER);
			} else if(paramName.toString().toUpperCase().equals("PRIVATEKEY")) {// privateKey
				map.put(paramName.toString() , AlipayConfig.PRIVATE_KEY);
			} else if(paramName.toString().toUpperCase().equals("PUBLICKEY")) {// pubicKey
				map.put(paramName.toString() , AlipayConfig.ALI_PUBLIC_KEY);
			} else if(paramName.toString().toUpperCase().equals("SELLERID")) {// sellerId
				map.put(paramName.toString() , AlipayConfig.SELLERID);
			}
		}
		resultJsonList.add(map);
		APIResponseUtil result = new APIResponseUtil(resultJsonList);
		return result.converToJson();
	}
	
	/**
	 * 服务器端对订单信息进行签名
	 * 
	 * @param response
	 *            响应对象
	 * @param request
	 *            请求对象
	 * @param orderCode
	 *            订单编号
	 * @param payUserId
	 *            支付人Id
	 * @param isTopUp
	 *            时候充值
	 * @param topUpMoney
	 *            充值金额
	 * @param chitId
	 *            代金券Id
	 * @return
	 */
	//@RequestMapping(value = "alipaySignOrderInfo")
	//@ResponseBody
	/*public JSONObject alipaySignOrderInfo(HttpServletResponse response , HttpServletRequest request , @RequestParam(required = false) Object orderCode,@RequestParam(required = false) Object isTopUp , @RequestParam(required = false) Object topUpMoney , @RequestParam(required = false) Object chitId) {
		Map<String , Object> map = new HashMap<String , Object>();
		APIResponseUtil result = new APIResponseUtil();
		try {
			// 检查参数
			Map<String , Object> paramMap = new HashMap<String , Object>();
			// 支付
			paramMap.put("orderCode" , orderCode);// 支付时orderCode为必传参数
			JSONObject checkParams = APIResponseUtil.checkParams(paramMap , "orderCode");
			if(checkParams != null) {
				return checkParams;
			} else {
				Order ordert=orderService.findByOrderSn(orderCode.toString());
				//List<POrderEntity> porders = this.orderServiceI.findByProperty(POrderEntity.class , "ordercode" , orderCode);
				if(ordert != null) {
					//POrderEntity porder = null;
					// 订单状态：0:已取消;10:待付款;20:待发货;30:待收货;40:交易完成;50:已提交;60:已确认;
					if(ordert.getOrderState() == 10) {
						map.put("payInfo" , "");
						result.setResultCode("0");
						result.setResultMsg("待付款");
						return result.converToJson();
					} else if(ordert.getOrderState() == 0) {// 支付状态为1表示已支付
						map.put("payInfo" , "");
						result.setResultCode("0");
						result.setResultMsg("该订单已取消，无法进行付款操作,订单签名失败!");
						return result.converToJson();
					} else if(ordert.getOrderState() == 20) {// 支付状态为1表示已支付
						map.put("payInfo" , "");
						result.setResultCode("0");
						result.setResultMsg("该订单已支付完成，无法进行付款操作,订单签名失败!");
						return result.converToJson();
					} else{
						map.put("payInfo" , SignUtils.signOrderInfo(ordert , false));// 对订单进行签名
					}
				} else {
					map.put("payInfo" , "");
					result.setResultCode("0");
					result.setResultMsg("订单信息不存在，无法进行签名！");
				}
			}
		} catch (Exception e) {
			map.put("payInfo" , "");
			result.setResultCode("0");
			result.setResultMsg("服务器端订单签名异常！");
			e.printStackTrace();
		}
		return result.converToJson();
	}*/
	
	/**
	 * 支付宝支付异步通知处理
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "mobile/notify")
	public void notify(HttpServletResponse response , HttpServletRequest request) {
		alipayNotify(response , request , false);
	}
	
	/**
	 * 支付宝充值异步通知处理
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "mobile/notify_topUp")
	public void notify_topUp(HttpServletResponse response , HttpServletRequest request) {
		alipayNotify(response , request , true);
	}
	
	/**
	 * @param response
	 * @param request
	 */
	private void alipayNotify(HttpServletResponse response , HttpServletRequest request , boolean isTopUp) {
		PrintWriter out = null;
		String hit = isTopUp ? "充值" : "支付";
		try {
			out = response.getWriter();
			// 获取支付宝POST过来反馈信息
			Map<String , String> params = new HashMap<String , String>();
			@SuppressWarnings("rawtypes")
			Map requestParams = request.getParameterMap();
			for(@SuppressWarnings("unchecked")
			Iterator<String> iter = requestParams.keySet().iterator() ; iter.hasNext() ;) {
				String name = iter.next();
				String [] values = (String [])requestParams.get(name);
				String valueStr = "";
				for(int i = 0 ; i < values.length ; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				// valueStr = new String(valueStr.getBytes("ISO-8859-1"),
				// "gbk");
				params.put(name , valueStr);
			}
			
			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)
			// 商户订单号
			String out_trade_no_str = request.getParameter("out_trade_no");
			String trade_no_str = request.getParameter("trade_no");
			String trade_status_str = request.getParameter("trade_status");
			Timestamp nowTime = new Timestamp(new Date().getTime());
			log.info(nowTime + "接收到支付宝" + hit + "异步通知:out_trade_no:" + out_trade_no_str + " trade_no：" + trade_no_str + " trade_status_str:" + trade_status_str);
			if(StringUtils.isEmpty(out_trade_no_str)) {
				// 验证失败
				out.println("success"); // 验证失败成功,
				return;
			}
			// 支付宝交易号
			if(StringUtils.isEmpty(trade_no_str)) {
				// 验证失败
				out.println("success"); // 验证失败成功,
				return;
			}
			// 交易状态
			if(StringUtils.isEmpty(trade_status_str)) {
				// 验证失败
				out.println("success"); // 验证失败成功,
				return;
			}
			String out_trade_no = new String(out_trade_no_str.getBytes("ISO-8859-1") , "UTF-8");
			String trade_no = new String(trade_no_str.getBytes("ISO-8859-1") , "UTF-8");
			//String trade_status = new String(trade_status_str.getBytes("ISO-8859-1") , "UTF-8");
			//String total_fee = request.getParameter("total_fee");
			//String gmt_paymen = request.getParameter("gmt_create");
			// 获取支付宝的通知返回参数
			if(AlipayNotify.verify(params)) {// 验证成功
				payService.updatePayFinish(out_trade_no,trade_no,"alipay");//修改订单状态
				out.println("success");//向支付宝返回支付结果
			}
		} catch (Exception e) {
			out.println("fail");
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
}

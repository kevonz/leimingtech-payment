package com.leimingtech.extend.module.payment.module.whchat.mobile.pay.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import net.sf.json.JSONObject;
import jodd.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.extend.module.payment.module.alipay.mobile.controller.APIResponseUtil;
import com.leimingtech.extend.module.payment.wechat.mobile.config.WXpayConfig;
import com.leimingtech.extend.module.payment.wechat.mobile.util.component.ResponseHandler;
import com.leimingtech.service.module.trade.service.PayService;

import freemarker.log.Logger;

/**
 * @company 雷铭智信
 * @author 谢进伟
 * @DateTime 2015-3-4 下午2:09:05
 */
@Slf4j
@Controller
@RequestMapping(value = "/front/LeimingWXpay/")
public class WXpayMobileAction {
	@Resource
	private PayService payService;
	//Logger log = Logger.getLogger(WXpayMobileAction.class);
	
	/**
	 * 获取微信支付相关参数
	 * 
	 * @param response
	 * @param request
	 * @param paramName
	 * @return
	 */
	@RequestMapping(value = "getWXPayParam")
	@ResponseBody
	public JSONObject getWXPayParam(HttpServletResponse response , HttpServletRequest request , @RequestParam(required = false) Object paramName) {
		// 检查参数
		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("paramName" , paramName);
		JSONObject checkParams = APIResponseUtil.checkParams(paramMap , "paramName");
		List<Map<String , Object>> resultJsonList = new ArrayList<Map<String , Object>>();
		Map<String , Object> map = new HashMap<String , Object>();
		if(checkParams != null) {
			map.put("appId" , WXpayConfig.APP_ID);
			map.put("mchId" , WXpayConfig.MCH_ID);
			map.put("apiKey" , WXpayConfig.API_KEY);
		} else {
			if(paramName.toString().toUpperCase().equals("APPID")) {// APP_ID
				map.put(paramName.toString() , WXpayConfig.APP_ID);
			} else if(paramName.toString().toUpperCase().equals("MCHID")) {// 商户号
				map.put(paramName.toString() , WXpayConfig.MCH_ID);
			} else if(paramName.toString().toUpperCase().equals("APIKEY")) {// API密钥，在商户平台设置
				map.put(paramName.toString() , WXpayConfig.API_KEY);
			}
		}
		resultJsonList.add(map);
		APIResponseUtil result = new APIResponseUtil(resultJsonList);
		return result.converToJson();
	}
	
	public static void main(String [] args) {
		System.out.println("false".matches("^true|false$"));
	}
	
	/**
	 * 微信预支付
	 * 
	 * @param orderCode
	 *            订单编号
	 * @param payUserId
	 *            支付人Id
	 * @param isTopUp
	 *            是否是充值操作
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @param chitId
	 *            代金券Id
	 * @return
	 */
//	@RequestMapping(value = "advancePayment")
//	@ResponseBody
//	public JSONObject advancePayment(@RequestParam(required = false) Object orderCode , @RequestParam(required = false) Object payUserId , @RequestParam(required = false) Object isTopUp , @RequestParam(required = false) Object topUpMoney , HttpServletRequest request , HttpServletResponse response , @RequestParam(required = false) Object chitId) {
//		List<Map<String , Object>> resultJsonList = new ArrayList<Map<String , Object>>();
//		Map<String , Object> map = new HashMap<String , Object>();
//		APIResponseUtil result = new APIResponseUtil();
//		try {
//			// 检查参数
//			Map<String , Object> paramMap = new HashMap<String , Object>();
//			String isTopUpStr = null;
//			paramMap.put("orderCode" , orderCode);// 支付时orderCode为必传参数
//			final boolean isHadChit = chitId != null;
//			if(isHadChit) {
//				paramMap.put("chitId" , chitId);
//			}
//			paramMap.put("payUserId" , payUserId);
//			JSONObject checkParams = APIResponseUtil.checkParams(paramMap , "orderCode");
//			if(checkParams != null) {
//				return checkParams;
//			} else {
//				boolean isTopUp_boolean = BooleanUtils.toBoolean(isTopUpStr);
//				List<POrderEntity> porders = this.orderServiceI.findByProperty(POrderEntity.class , "ordercode" , orderCode);
//				if(porders != null && porders.size() > 0 && porders.size() < 2 || isTopUp_boolean) {
//					POrderEntity porder = null;
//					if(isTopUp_boolean) {
//						String topUpMoneyStr = topUpMoney.toString();
//						String regex = "^\\d+(\\.?\\d{0,2})?$";// 充值金额以元为单位，精确到分
//						if(!topUpMoneyStr.matches(regex)) {
//							map.put("payReq" , "");
//							result.setResultCode("0");
//							result.setResultMsg("参数：isTop传入且传入值为true时,充值金额（参数：topUpMoney）以元为单位，精确到分!");
//							return result.converToJson();
//						}
//						porder = new POrderEntity();
//						porder.setJtj(new BigDecimal(topUpMoneyStr));
//					} else {
//						porder = porders.get(0);
//						// 订单状态 订单状态 0:待司机确认接单; 已确认：1 ；待确认：2；已取消：3
//						if(porder.getOrderstate() == 0) {
//							map.put("payReq" , "");
//							result.setResultCode("0");
//							result.setResultMsg("司机还未确认是否接单，无法进行预支付操作,请等待司机确认接单后再进行支付!");
//							return result.converToJson();
//						} else if(porder.getOrderstate() == 3) {// 支付状态为1表示已支付
//							map.put("payReq" , "");
//							result.setResultCode("0");
//							result.setResultMsg("该订单已取消，无法进行预支付操作,订单预支付失败!");
//							return result.converToJson();
//						} else if(porder.getOrderstate() == 1) {// 支付状态为1表示已支付
//							map.put("payReq" , "");
//							result.setResultCode("0");
//							result.setResultMsg("该订单已支付完成，无法进行预支付操作,订单预支付失败!");
//							return result.converToJson();
//						}
//					}
//					if(payUser != null) {
//						if(payUser.getDj() == 0) {// 未冻结
//							// 有代金券参与支付
//							if(isHadChit) {
//										   BigDecimal realPayMoney = jtj.subtract(cash);// 实际支付金额
//											porder.setJtj(realPayMoney);
//											map.put("payReq" , new SignUtils().advancePayment(porder , BooleanUtils.toBoolean(isTopUpStr)));// 对订单进行预支付
//								}
//							} else {
//								// 无代金券参与支付
//								porder.setOrdercode(orderCode + "D" + payUserId);
//								map.put("payReq" , new SignUtils().advancePayment(porder , BooleanUtils.toBoolean(isTopUpStr)));// 对订单进行预支付
//							}
//						} else {
//							map.put("payReq" , "");
//							result.setResultCode("0");
//							result.setResultMsg("用户已被冻结，无法进行预支付操作,订单预支付失败!");
//						}
//					} else {
//						map.put("payReq" , "");
//						result.setResultCode("0");
//						result.setResultMsg("预支付用户不存在，无法进行预支付操作,订单预支付失败!");
//					}
//				} else if(porders.size() > 1) {
//					map.put("payReq" , "");
//					result.setResultCode("0");
//					result.setResultMsg("订单信息重复，无法进行预支付！");
//					this.log.error("编号为:" + orderCode + "的订单信息信息重复，无法进行预支付！");
//				} else {
//					map.put("payReq" , "");
//					result.setResultCode("0");
//					result.setResultMsg("订单信息不存在，无法进行预支付！");
//				}
//			}
//		} catch (Exception e) {
//			map.put("payReq" , "");
//			result.setResultCode("0");
//			result.setResultMsg("服务器端订单预支付异常！");
//			e.printStackTrace();
//		}
//		resultJsonList.add(map);
//		result.setList(resultJsonList);
//		return result.converToJson();
//		
//	}
//	
	/**
	 * 微信支付通知（后台通知）
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 */
	@RequestMapping(value = "notify")
	public void notify(HttpServletRequest request , HttpServletResponse response) {
		wxNotify(request , response , false);
	}
	
	/**
	 * 微信充值通知（后台通知）
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 */
	@RequestMapping(value = "notify_topUp")
	public void notify_topUp(HttpServletRequest request , HttpServletResponse response) {
		wxNotify(request , response , true);
	}
	
	/**
	 * @param request
	 * @param response
	 * @param b
	 */
	@SuppressWarnings("unused")
	private void wxNotify(HttpServletRequest request , HttpServletResponse response , boolean isTopUp) {
		Date nowDate = new Date();
		Timestamp nowTime = new Timestamp(nowDate.getTime());
		log.info(nowTime + "接收到微信" + (isTopUp ? "充值" : "支付") + "异步通知,服务器端开始处理通知...");
		// 创建支付应答对象
		ResponseHandler resHandler = new ResponseHandler(request , response);
		resHandler.setKey(WXpayConfig.API_KEY);
		resHandler.getAllParameters();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if(resHandler.isWechatSign()) {
				log.info("是微信V3签名,规则是:按参数名称a-z排序,遇到空值的参数不参加签名");
			} else {
				out.print("success");// 给微信系统发送成功信息，微信系统收到此结果后不再进行后续通知
				log.info("不是 微信V3签名,规则是:按参数名称a-z排序,遇到空值的参数不参加签名");
				return;
			}
			String return_code = resHandler.getSmap().get("return_code");// 通信标识
			String return_msg = resHandler.getSmap().get("return_msg");// 通知返回的信息，如非空，为错误，原因：签名失败，参数格式效验失败
			if(!StringUtil.isEmpty(return_code) && "success".toUpperCase().equals(return_code.toUpperCase())) {
				// String sign = resHandler.getSmap().get("sign"); // 签名
				// String nonce_str = resHandler.getSmap().get("nonce_str"); //
				// 随机字符串
				String mch_id = resHandler.getSmap().get("mch_id"); // 商户号
				String appid = resHandler.getSmap().get("appid"); // 公众账号id
				// String device_info = resHandler.getSmap().get("device_info");
				// //
				// 设备号
				String result_code = resHandler.getSmap().get("result_code");// 业务结果
				String err_code = resHandler.getSmap().get("err_code"); // 错误代码
				String err_code_des = resHandler.getSmap().get("err_code_des"); // 错误代码描述
				// log.error("业务结果：" + result_code);
				if(!StringUtil.isEmpty(result_code) && "success".toUpperCase().equals(result_code.toUpperCase())) {
					// 用户标识
					String openid = resHandler.getSmap().get("openid");
					// 是否关注公众账号
					String is_subscribe = resHandler.getSmap().get("is_subscribe");
					// 交易类型
					String trade_type = resHandler.getSmap().get("trade_type");
					// 付款银行
					String bank_type = resHandler.getSmap().get("bank_type");
					// 总金额
					String total_fee = resHandler.getSmap().get("total_fee");
					// 现金券金额
					String coupon_fee = resHandler.getSmap().get("coupon_fee");
					// 货比类型
					String fee_type = resHandler.getSmap().get("fee_type");
					// 微信支付订单号
					String transaction_id = resHandler.getSmap().get("transaction_id");
					// 商户订单号，与请求一致
					String out_trade_no = resHandler.getSmap().get("out_trade_no");
					// 支付完成时间
					String time_end = resHandler.getSmap().get("time_end");
					
					if(!StringUtil.isAllBlank(mch_id) && !StringUtil.isEmpty(appid) && WXpayConfig.MCH_ID.equals(mch_id) && WXpayConfig.APP_ID.equals(appid)) {// 只处理系统设置商户的异步同志请求
							if(!isTopUp) {
								log.info("微信支付异步处理成功！");
								System.out.println("支付成功了！"+out_trade_no);
								payService.updatePayFinish(out_trade_no,transaction_id,"open_weichatpay");//修改订单状态  开放平台支付
								out.print("success");// 给微信系统发送成功信息，微信系统收到此结果后不再进行后续通知
							} else {// 充值
//								Date nowDate_third = new Date();
//								if(!StringUtils.isEmpty(time_end)) {
//									nowDate_third = new SimpleDateFormat("yyyyMMddhhmmss").parse(time_end);
//								}
//								Serializable save = this.rechargehistoryServiceI.topUp(out_trade_no , transaction_id , total_fee , 2 , nowDate_third);
//								if(save != null) {
//									log.info("微信充值异步处理成功！");
//									out.print("success");// 给微信系统发送成功信息，微信系统收到此结果后不再进行后续通知
//								} else {
//									log.info("微信充值异步处理失败！");
//									out.print("success");// 给微信系统发送成功信息，微信系统收到此结果后不再进行后续通知
//								}
							}
					}
				} else {
					log.error("交易失败！错误代码(err_code)：" + err_code + " 错误代码描述：" + err_code_des);
					out.print("success");// 给微信系统发送成功信息，微信系统收到此结果后不再进行后续通知
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("fail");// 给微信系统发送成功信息，微信系统收到此结果后不再进行后续通知
		}
	}
}

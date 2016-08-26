package com.leimingtech.extend.module.payment.module.unionopay.mobile;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.entity.base.Pay;
import com.leimingtech.extend.module.payment.module.alipay.mobile.controller.APIResponseUtil;
import com.leimingtech.extend.module.payment.unionpay.mobile.acp.util.AppConsumeUtil;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKConstants;
import com.leimingtech.extend.module.payment.unionpay.pc.sdk.SDKUtil;
import com.leimingtech.service.module.trade.service.PayService;

/**
 * @company 雷铭智信
 * @author 谢进伟
 * @DateTime 2015-2-12 下午12:43:43
 */
@Slf4j
@Controller
@RequestMapping(value = "/front/pinCheUnionpay/")
public class UnionpayMobileAction {
	
	//Logger loger = Logger.getLogger(UnionpayMobileAction.class);
	
	@Autowired
	private PayService payService;
	
	/**
	 * 银联支付获取交易流水号
	 * 
	 * @param response
	 *            响应对象
	 * @param request
	 *            请求对象
	 * @param orderCode
	 *            订单编号
	 * @param isTopUp
	 *            是否是充值
	 * @param topUpMoney
	 *            充值金额
	 * @param chitId
	 *            代金券Id
	 * @return
	 */
	@RequestMapping(value = "getTn")
	@ResponseBody
	public JSONObject getTn(HttpServletResponse response , HttpServletRequest request , @RequestParam(required = false) Object orderCode , @RequestParam(required = false) Object payUserId , @RequestParam(required = false) Object isTopUp , @RequestParam(required = false) Object topUpMoney , @RequestParam(required = false) Object chitId) {
		List<Map<String , Object>> resultJsonList = new ArrayList<Map<String , Object>>();
		Map<String , Object> map = new HashMap<String , Object>();
		APIResponseUtil result = new APIResponseUtil();
		try {
			// 检查参数
			Map<String , Object> paramMap = new HashMap<String , Object>();
			String isTopUpStr = null;
			if(isTopUp != null) {// 充值
					boolean isTopUpBoolean = org.apache.commons.lang3.BooleanUtils.toBoolean(isTopUpStr);
					if(isTopUpBoolean) {
						orderCode = System.nanoTime() + "";// 生成充值单号
						paramMap.put("topUpMoney" , topUpMoney);
					}
			} else {// 支付
				paramMap.put("orderCode" , orderCode);// 支付时orderCode为必传参数
			}
			final boolean isHadChit = chitId != null;
			if(isHadChit) {
				paramMap.put("chitId" , chitId);
			}
			paramMap.put("payUserId" , payUserId);
				//List<POrderEntity> porders = this.orderServiceI.findByProperty(POrderEntity.class , "ordercode" , orderCode);
			    Pay ordert=payService.findPayBySn(orderCode.toString());
				if(ordert != null){
				     	Map<String , Object> resultMap = AppConsumeUtil.getTn(ordert,false);
				     	if(resultMap.containsKey("respCode")) {
							Object resCodeValue = resultMap.get("respCode");
							if(resCodeValue != null && "00".equals(resCodeValue.toString())) {
								if(resultMap.containsKey("tn")) {
									map.put("tn" , resultMap.get("tn"));// 从银联获取支付流水号
								}else {
									map.put("tn" , "");
									result.setResultCode("0");
									result.setResultMsg("请求异常,交易流水号获取失败!");
								}
							} else {
								map.put("tn" , "");
								result.setResultCode("0");
								result.setResultMsg("请求异常异常代码【" + resCodeValue + "】,交易流水号获取失败!");
							}
						}
			  }
			}catch (Exception e) {
			map.put("tn" , "");
			result.setResultCode("0");
			result.setResultMsg("服务器端异常,交易流水号获取失败!");
			e.printStackTrace();
		}
		resultJsonList.add(map);
		result.setList(resultJsonList);
		return result.converToJson();
	}
	
	/**
	 * 银联支付异步通知处理<br>
	 * <span style="color:red;">
	 * 返回任意应答均可，银联只验证http应答码为200时就认为通知成功。如果商户服务器繁忙，希望等一会再次通知的话，
	 * 可以故意抛出异常让http应答码为500。
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "notify")
	public void notify(HttpServletResponse response , HttpServletRequest request) throws Exception {
		unionPayNotify(request , response , false);
	}
	
	/**
	 * 银联充值异步通知<br>
	 * <span style="color:red;">
	 * 返回任意应答均可，银联只验证http应答码为200时就认为通知成功。如果商户服务器繁忙，希望等一会再次通知的话，
	 * 可以故意抛出异常让http应答码为500。
	 * 
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "notify_topUp")
	public void notify_topUp(HttpServletResponse response , HttpServletRequest request) throws Exception {
		unionPayNotify(request , response , true);
	}
	
	/**
	 * @param request
	 * @throws Exception
	 */
	private void unionPayNotify(HttpServletRequest request , HttpServletResponse response , boolean isTopUp) throws Exception {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			//Timestamp nowTime = new Timestamp(new Date().getTime());
			//LogUtil.writeLog(nowTime + "接收银联" + (isTopUp ? "充值" : "支付") + "后台通知...");
			request.setCharacterEncoding("UTF-8");
			String encoding = request.getParameter(SDKConstants.param_encoding);
			// 获取请求参数中所有的信息
			Map<String , String> reqParam = getAllRequestParam(request);
			// 打印请求报文
			//LogUtil.printRequestLog(reqParam);
			Map<String , String> valideData = null;
			if(null != reqParam && !reqParam.isEmpty()) {
				Iterator<Entry<String , String>> it = reqParam.entrySet().iterator();
				valideData = new HashMap<String , String>(reqParam.size());
				while (it.hasNext()) {
					Entry<String , String> e = it.next();
					String key = e.getKey();
					String value = e.getValue();
					if(!StringUtil.isEmpty(value)) {
						valideData.put(key , value);
					}
				}
			}
			// 验证签名
			if(!SDKUtil.validate(valideData , encoding)) {
				System.out.println("验证签名失败");
				out.println("failure");
				//LogUtil.writeLog("验证签名结果[失败]");
			} else {
				String out_trade_no = valideData.get("orderId");
				String trade_no=valideData.get("queryId");//交易流水号
				//LogUtil.writeLog("验证签名结果[成功].");
				System.out.println("验证签名成功99");
				System.out.println("out_trade_no:"+out_trade_no);
				payService.updatePayFinish(out_trade_no,trade_no,"mobile_unionpay");
				out.println("success"); // 验证失败成功
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(out != null) {
				out.close();
			}
		}
	}
	
	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String , String> getAllRequestParam(final HttpServletRequest request) {
		Map<String , String> res = new HashMap<String , String>();
		Enumeration<?> temp = request.getParameterNames();
		if(null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String)temp.nextElement();
				String value = request.getParameter(en);
				res.put(en , value);
				// 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				// System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
				if(null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}
}

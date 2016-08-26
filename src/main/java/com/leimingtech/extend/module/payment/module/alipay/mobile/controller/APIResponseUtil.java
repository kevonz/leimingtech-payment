/**
 * 
 */
package com.leimingtech.extend.module.payment.module.alipay.mobile.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

/**
 * 外部接口API响应工具
 * 
 * 该类用于统一API返回值格式
 * 
 * @company 雷铭智信
 * @author 谢进伟
 * @DateTime 2015-1-5 下午5:10:19
 */
public class APIResponseUtil {
	
	/**
	 * 返回值头部信息：返回请求提示信息
	 */
	private String resultMsg = "请求成功";
	/**
	 * 返回值头部信息：返回数据量统计
	 */
	private int total;
	/**
	 * 返回值头部信息：返回请求结果码 1成功 0失败
	 */
	private String resultCode = "1";
	/**
	 * 返回数据
	 */
	private List<? extends Object> list;
	/**
	 * 列表API中的当前页码
	 */
	private Integer pageNo;
	/**
	 * 列表API中每一页显示的记录数
	 */
	private Integer pageSize;
	/**
	 * 附加数据
	 */
	private Map<String , Object> other;
	
	/**
	 * 是否现实的设置total的值
	 */
	private boolean isSetTotal=false;
	
	public APIResponseUtil (){
		this.list = new ArrayList<Object>();
		this.other = new HashMap<String , Object>();
	}
	
	/**
	 * @param resultMsg
	 *            返回请求提示信息
	 * @param resultCode
	 *            返回请求结果码
	 * @param list
	 *            返回数据
	 * @param other
	 *            附加数据
	 */
	public APIResponseUtil (String resultMsg , String resultCode , List<? extends Object> list , Map<String , Object> other ){
		this();
		this.resultMsg = resultMsg;
		this.resultCode = resultCode;
		this.list = list;
		this.other = other;
	}
	
	/**
	 * @param resultMsg
	 *            返回请求提示信息
	 * @param resultCode
	 *            返回请求结果码
	 * @param list
	 *            返回数据
	 */
	public APIResponseUtil (String resultMsg , String resultCode , List<? extends Object> list ){
		this();
		this.resultMsg = resultMsg;
		this.resultCode = resultCode;
		this.list = list;
	}
	
	/**
	 * @param list
	 *            返回数据
	 */
	public APIResponseUtil (List<? extends Object> list ){
		this();
		this.list = list;
	}
	
	/**
	 * @param resultMsg
	 * @param resultCode
	 * @param list
	 * @param pageNo
	 * @param pageSize
	 * @param other
	 */
	public APIResponseUtil (String resultMsg , String resultCode , List<? extends Object> list , Integer pageNo , Integer pageSize , Map<String , Object> other ){
		this();
		this.resultMsg = resultMsg;
		this.resultCode = resultCode;
		this.list = list;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.other = other;
	}
	
	/**
	 * @param resultMsg
	 * @param resultCode
	 * @param list
	 * @param pageNo
	 * @param pageSize
	 */
	public APIResponseUtil (String resultMsg , String resultCode , List<? extends Object> list , Integer pageNo , Integer pageSize ){
		this();
		this.resultMsg = resultMsg;
		this.resultCode = resultCode;
		this.list = list;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	
	/**
	 * @param list
	 * @param pageNo
	 * @param pageSize
	 */
	public APIResponseUtil (List<? extends Object> list , Integer pageNo , Integer pageSize ){
		this();
		this.list = list;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	
	/**
	 * @param list
	 *            返回数据
	 * @param other
	 *            附加数据
	 */
	public APIResponseUtil (List<? extends Object> list , Map<String , Object> other ){
		this();
		this.list = list;
		this.other = other;
	}
	
	/**
	 * @return 得到 resultMsg的值
	 */
	public String getResultMsg() {
		return resultMsg;
	}
	
	/**
	 * @param resultMsg
	 *            设置 resultMsg值
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
	/**
	 * @return 得到 total的值
	 */
	public int getTotal() {
		if (!isSetTotal) {
			this.total = 0;
			if (this.getList().size() > 0) {
				this.total += this.getList().size();
			}
			if (this.getOther().size() > 0) {
				this.total += this.getOther().size();
			}
		}
		return this.total;
	}
	
	/**
	 * @return 得到 total的值
	 */
	public void setTotal(int total) {
		this.isSetTotal=true;
		this.total = total;
	}
	
	/**
	 * @return 得到 resultCode的值
	 */
	public String getResultCode() {
		return resultCode;
	}
	
	/**
	 * @param resultCode
	 *            设置 resultCode值
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	/**
	 * @return 得到 list的值
	 */
	public List<? extends Object> getList() {
		return list;
	}
	
	/**
	 * @return 得到 list的值
	 */
	public List<? extends Object> setList(List<? extends Object> parmlist) {
		return list = parmlist;
	}
	
	/**
	 * @return 得到 other的值
	 */
	public Map<String , Object> getOther() {
		return other;
	}
	
	/**
	 * @return 得到 pageNo的值
	 */
	public Integer getPageNo() {
		return pageNo;
	}
	
	/**
	 * @param pageNo
	 *            设置 pageNo值
	 */
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	
	/**
	 * @return 得到 pageSize的值
	 */
	public Integer getPageSize() {
		return pageSize;
	}
	
	/**
	 * @param pageSize
	 *            设置 pageSize值
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 不分页
	 * 
	 * @return
	 */
	public JSONObject converToJson() {
		JSONObject json = new JSONObject();
		JsonConfig jsonConfig = new JsonConfig();
		// 如果没有附加数据返回，则不将other字段及total字段纳入转换Json的规则中(除开other的字段其他字段一律纳入转换规则)
		jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
			
			@SuppressWarnings("unchecked")
			@Override
			public boolean apply(Object source , String name , Object value) {
				if(value instanceof Map) {
					return ((Map<String , Object>)value).size() == 0;
				}
				if(name.equals("total")) {
					return true;
				}
				return value == null;
			}
		});
		json = JSONObject.fromObject(this , jsonConfig);
		return json;
	}
	/**
	 * 不分页
	 * FIXME PIN@tanyao
	 * @return
	 */
	public JSONObject converToJsonTotal() {
		JSONObject json = new JSONObject();
		JsonConfig jsonConfig = new JsonConfig();
		// 如果没有附加数据返回，则不将other字段及total字段纳入转换Json的规则中(除开other的字段其他字段一律纳入转换规则)
		jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
			//属性过滤器，return true的时候过滤数据，否则return false
			@SuppressWarnings("unchecked")
			@Override
			public boolean apply(Object source , String name , Object value) {
				if(value instanceof Map) {
					return ((Map<String , Object>)value).size() == 0;
				}
				/*if(name.equals("total")) {
					return true;
				}*/
				return value == null;
			}
		});
		json = JSONObject.fromObject(this , jsonConfig);
		return json;
	}
	
	/**
	 * 分页
	 * 
	 * @return
	 */
	public JSONObject converToPageingJson() {
		JSONObject json = new JSONObject();
		JsonConfig jsonConfig = new JsonConfig();
		// 如果没有附加数据返回，则不将other字段纳入转换Json的规则中(除开other的字段其他字段一律纳入转换规则)
		jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
			
			@SuppressWarnings("unchecked")
			@Override
			public boolean apply(Object source , String name , Object value) {
				if(value instanceof Map) {
					return ((Map<String , Object>)value).size() == 0;
				}
				return value == null;
			}
		});
		json = JSONObject.fromObject(this , jsonConfig);
		return json;
	}
	
	/**
	 * 只提示响应码和响应信息
	 * 
	 * @return
	 */
	public JSONObject converToMessageJson() {
		JSONObject json = new JSONObject();
		Map<String , Object> resultMap = new HashMap<String , Object>();
		resultMap.put("resultCode" , this.getResultCode());
		resultMap.put("resultMsg" , this.getResultMsg());
		if(this.getOther() != null && this.getOther().size() > 0) {
			resultMap.put("other" , this.getOther());
		}
		json = JSONObject.fromObject(resultMap);
		return json;
	}
	
	/**
	 * 检查参数是否为空且是否为数值（整型或浮点）型类型(notNumericKey指定的key不接受数值型检测)
	 * 
	 * @param params
	 *            需要检查的参数键值对
	 * @param notNumericKey
	 *            map中不需要检查是否是数值（整型或浮点）类型的key
	 * @return null表示参数全部合法，否则将返回包含错误提示的JSONObject对象
	 */
	public static JSONObject checkParams(Map<String , Object> map , String ... notNumericKey) {
		APIResponseUtil result = null;
		StringBuffer msg_paramIsNull = new StringBuffer("参数: ");
		StringBuffer msg_notInteger = new StringBuffer(",");
		StringBuffer msg_Integer = new StringBuffer(",");
		if(map != null && map.size() > 0) {
			Iterator<String> iter = map.keySet().iterator();
			List<String> notIntegerKeyList = Arrays.asList(notNumericKey);
			String regex_Integer = "^(\\-)?\\d+$";
			String regex = "^(\\-)?\\d+(\\.?\\d+)?$";
			String key = "";
			Object value = null;
			boolean matches = false;
			while (iter.hasNext()) {
				key = iter.next();
				value = map.get(key);
				if(value == null) {
					msg_paramIsNull.append(key + "、");
				} else {
					if(!notIntegerKeyList.contains(key)) {// 需要检查是参数类型是否为数值（整型或浮点）类型
						if(key.toUpperCase().equals("pageNo".toUpperCase()) || key.toUpperCase().equals("pageSize".toUpperCase())) {
							matches = Pattern.matches(regex_Integer , value.toString());
							if(!matches) {
								msg_Integer.append(key + "、");
								continue;
							}
						}
						matches = Pattern.matches(regex , value.toString());
						if(!matches) {
							msg_notInteger.append(key + "、");
						}
					}
				}
			}
			msg_paramIsNull.append("$");
			msg_notInteger.append("$");
			msg_Integer.append("$");
			if(msg_paramIsNull.toString().contains("、$") || msg_notInteger.toString().contains("、$") || msg_Integer.toString().contains("、$")) {
				msg_notInteger = new StringBuffer(msg_notInteger.toString().replaceFirst(",\\$" , ""));
				msg_Integer = new StringBuffer(msg_Integer.toString().replaceFirst(",\\$" , ""));
				String resultMsg = msg_paramIsNull.toString().replaceFirst("、\\$" , "是必须的") + msg_Integer.toString().replaceFirst("、\\$" , "必须为整数") + msg_notInteger.toString().replaceFirst("、\\$" , "必须为数值型") + "!";
				result = new APIResponseUtil();
				result.setResultCode("0");
				result.setResultMsg(resultMsg.replaceFirst("\\$," , ""));
				return result.converToJson();
			}
		}
		return null;
	}
	
	/**
	 * 检查参数是否为空且是否为数值（整型或浮点）型类型(notNumericKey指定的key不接受数值型检测)
	 * 
	 * @param params
	 *            需要检查的参数键值对
	 * @param notNumericKey
	 *            map中不需要检查是否是数值（整型或浮点）类型的key
	 * @return null表示参数全部合法，否则将返回包含错误提示的JSONObject对象
	 */
	public static JSONObject checkParamspage(Map<String , Object> map , String ... notNumericKey) {
		APIResponseUtil result = null;
		StringBuffer msg_paramIsNull = new StringBuffer("参数: ");
		StringBuffer msg_notInteger = new StringBuffer(",");
		StringBuffer msg_Integer = new StringBuffer(",");
		if(map != null && map.size() > 0) {
			Iterator<String> iter = map.keySet().iterator();
			List<String> notIntegerKeyList = Arrays.asList(notNumericKey);
			String regex_Integer = "^(\\-)?\\d+$";
			String regex = "^(\\-)?\\d+(\\.?\\d+)?$";
			String key = "";
			Object value = null;
			boolean matches = false;
			while (iter.hasNext()) {
				key = iter.next();
				value = map.get(key);
				if(value == null) {
					msg_paramIsNull.append(key + "、");
				} else {
					if(!notIntegerKeyList.contains(key)) {// 需要检查是参数类型是否为数值（整型或浮点）类型
						if(key.toUpperCase().equals("pageNo".toUpperCase()) || key.toUpperCase().equals("pageSize".toUpperCase())) {
							matches = Pattern.matches(regex_Integer , value.toString());
							if(!matches) {
								msg_Integer.append(key + "、");
								continue;
							}
						}
						matches = Pattern.matches(regex , value.toString());
						if(!matches) {
							msg_notInteger.append(key + "、");
						}
					}
				}
			}
			msg_paramIsNull.append("$");
			msg_notInteger.append("$");
			msg_Integer.append("$");
			if(msg_paramIsNull.toString().contains("、$") || msg_notInteger.toString().contains("、$") || msg_Integer.toString().contains("、$")) {
				msg_notInteger = new StringBuffer(msg_notInteger.toString().replaceFirst(",\\$" , ""));
				msg_Integer = new StringBuffer(msg_Integer.toString().replaceFirst(",\\$" , ""));
				String resultMsg = msg_paramIsNull.toString().replaceFirst("、\\$" , "是必须的") + msg_Integer.toString().replaceFirst("、\\$" , "必须为整数") + msg_notInteger.toString().replaceFirst("、\\$" , "必须为数值型") + "!";
				result = new APIResponseUtil();
				result.setResultCode("0");
				result.setResultMsg(resultMsg.replaceFirst("\\$," , ""));
				result.setPageNo(0);
				result.setPageSize(0);
				result.setTotal(0);
				return result.converToJson();
			}
		}
		return null;
	}
	
}

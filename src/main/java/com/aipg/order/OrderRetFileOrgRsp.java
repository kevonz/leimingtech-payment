/*通联支付网络服务股份有限公司版权所有
 *未经授权严禁查看传抄
 *
 *作者:Vinci
 *日期:2012-6-26 上午11:44:43  
 */
package com.aipg.order;

//import javax.xml.bind.annotation.XmlRootElement;

import sun.util.logging.resources.logging;

/**
 * 
 * @author: Vinci
 * @time: 2012-6-26 上午11:44:43   
 * @modifylog:
 */
//@XmlRootElement(name = "OrderRetFileOrgRsp")
public class OrderRetFileOrgRsp {
	private String trxcod;
	private String payinst;
	private String entinst;
	private String timestamp;
	private String rspcode;
	private String rspmsg;
	private String settday;
	private String content;
	private String mac;
	public String buildXMLforSign() {
		StringBuilder sb = new StringBuilder();
		sb.append(trxcod).append("|");
		sb.append(payinst).append("|");
		sb.append(entinst).append("|");
		sb.append(timestamp).append("|");
		sb.append(rspcode).append("|");
		sb.append(settday).append("|");
		sb.append(content);
		return sb.toString();
	}
	public String getTrxcod() {
		return trxcod;
	}
	public void setTrxcod(String trxcod) {
		this.trxcod = trxcod;
	}
	public String getPayinst() {
		return payinst;
	}
	public void setPayinst(String payinst) {
		this.payinst = payinst;
	}
	public String getEntinst() {
		return entinst;
	}
	public void setEntinst(String entinst) {
		this.entinst = entinst;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getRspcode() {
		return rspcode;
	}
	public void setRspcode(String rspcode) {
		this.rspcode = rspcode;
	}
	public String getRspmsg() {
		return rspmsg;
	}
	public void setRspmsg(String rspmsg) {
		this.rspmsg = rspmsg;
	}
	public String getSettday() {
		return settday;
	}
	public void setSettday(String settday) {
		this.settday = settday;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
}

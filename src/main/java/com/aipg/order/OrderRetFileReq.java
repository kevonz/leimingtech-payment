/*通联支付网络服务股份有限公司版权所有
 *未经授权严禁查看传抄
 *
 *作者:Vinci
 *日期:2012-6-26 上午11:44:34  
 */
package com.aipg.order;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author: Vinci
 * @time: 2012-6-26 上午11:44:34   
 * @modifylog:
 */
//@XmlRootElement(name = "OrderRetFileReq")
public class OrderRetFileReq {
	private String trxcod;
	private String payinst;
	private String entinst;
	private String username;
	private String pwd;
	private String timestamp;
	private String settday;
	private String onlysucc;
	private String mac;
	public String buildXMLforSign() {
		StringBuilder sb = new StringBuilder();
		sb.append(trxcod).append("|");
		sb.append(payinst).append("|");
		sb.append(entinst).append("|");
		sb.append(username).append("|");
		sb.append(pwd).append("|");
		sb.append(timestamp).append("|");
		sb.append(settday).append("|");
		sb.append(onlysucc);
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSettday() {
		return settday;
	}
	public void setSettday(String settday) {
		this.settday = settday;
	}
	public String getOnlysucc() {
		return onlysucc;
	}
	public void setOnlysucc(String onlysucc) {
		this.onlysucc = onlysucc;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
}

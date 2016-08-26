/**
 *项目：xmlInterf
 *通联支付网络有限公司
 * 作者：张广海
 * 日期：Jan 2, 2013
 * 功能说明：交易demo测试参数
 */
package com.allinpay.xmltrans.pojo;

import java.io.File;

import com.leimingtech.core.common.SpringContextUtil;
import com.leimingtech.extend.module.payment.module.allinpay.pc.sdk.SDKConfig;

/**
 */
public class TranxCon {

	
	/**TOTAL_SUM
	 * XML交易参数
	 */
	public   String acctName = "张国光";
	public   String acctNo = "6225882516636351";
	public   String amount = "100000";//交易金额
	public   String bankcode = "0105";//银行代码
	public   String cerPath= "/usr/local/tomcat7/webapps/leimingtech-admin/allinpay/TLCert.cer";
	public   String merchantId = "200290000014408";
	public   String password = "`12qwe";
	//商户证书信息
	public   String pfxPassword = "111111";
	public   String pfxPath= "/usr/local/tomcat7/webapps/leimingtech-admin/allinpay/20029000001440804.p12";
	public   String sum = "200000";//交易总金额
	public   String tel = "13434245847";
	public   String tltcerPath= "/usr/local/tomcat7/webapps/leimingtech-admin/allinpay/allinpay-pds.cer";
	public   String userName = "20029000001440804"; 
	
	public SDKConfig config = SDKConfig.getConfig();
	public TranxCon(){
	    String relapath=SpringContextUtil.getResourceRootRealPath()+File.separator+"";
	    config.loadPropertiesFromPath(relapath);// 从classpath加载acp_sdk.properties文件
	}
	public String getAcctName() {
		return acctName;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public String getAmount() {
		return amount;
	}
	public String getBankcode() {
		return bankcode;
	}
	public String getCerPath() {
		return config.getValidateCertDir()+cerPath;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public String getPassword() {
		return password;
	}
	public String getPfxPassword() {
		return pfxPassword;
	}
	public String getPfxPath() {
		return config.getValidateCertDir()+pfxPath;
	}
	public String getSum() {
		return sum;
	}
	public String getTel() {
		return tel;
	}
	public String getTltcerPath() {
		return config.getValidateCertDir()+tltcerPath;
	}
	public String getUserName() {
		return userName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public void setCerPath(String cerPath) {
		this.cerPath = cerPath;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPfxPassword(String pfxPassword) {
		this.pfxPassword = pfxPassword;
	}
	public void setPfxPath(String pfxPath) {
		this.pfxPath = pfxPath;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setTltcerPath(String tltcerPath) {
		this.tltcerPath = tltcerPath;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
 
	
	
}

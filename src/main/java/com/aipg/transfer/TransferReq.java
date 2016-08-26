package com.aipg.transfer;

/**
 * 实时转账、快速转账、标准转账 请求
 * 
 * @author luolc
 * @version 1.0
 */
public class TransferReq {

	private String BUSINESS_CODE;// 业务代码
	private String MERCHANT_ID;// 商户号
	private String AMOUNT;// 转账金额
	private String FEE;// 转账手续费
	private String CUST_USERID;// 用户自定义号
	private String REMARK;// 备注
	// 以D_开头的是转出方信息，以P_开头的为转入方信息
	private String D_BANK_CODE;// 银行代码
	private String D_BANK_NAME;// 开户行名称
	private String D_UNION_BANK;// 支付行号
	private String D_ACCOUNT_TYPE;// 账号类型
	private String D_ACCOUNT_NO;// 账号
	private String D_ACCOUNT_NAME;// 账号名
	private String D_ACCOUNT_PROP;// 账号属性
	private String D_ID_TYPE;// 开户证件类型
	private String D_ID;// 证件号
	private String D_TEL;// 手机号/小灵通
	private String P_BANK_CODE;
	private String P_BANK_NAME;
	private String P_UNION_BANK;
	private String P_ACCOUNT_TYPE;
	private String P_ACCOUNT_NO;
	private String P_ACCOUNT_NAME;
	private String P_ACCOUNT_PROP;
	private String P_ID_TYPE;
	private String P_ID;
	private String P_TEL;

	public String getBUSINESS_CODE() {
		return BUSINESS_CODE;
	}

	public void setBUSINESS_CODE(String bUSINESS_CODE) {
		BUSINESS_CODE = bUSINESS_CODE;
	}

	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}

	public void setMERCHANT_ID(String mERCHANT_ID) {
		MERCHANT_ID = mERCHANT_ID;
	}

	public String getAMOUNT() {
		return AMOUNT;
	}

	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}

	public String getFEE() {
		return FEE;
	}

	public void setFEE(String fEE) {
		FEE = fEE;
	}

	public String getCUST_USERID() {
		return CUST_USERID;
	}

	public void setCUST_USERID(String cUST_USERID) {
		CUST_USERID = cUST_USERID;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}

	public String getD_BANK_CODE() {
		return D_BANK_CODE;
	}

	public void setD_BANK_CODE(String d_BANK_CODE) {
		D_BANK_CODE = d_BANK_CODE;
	}

	public String getD_BANK_NAME() {
		return D_BANK_NAME;
	}

	public void setD_BANK_NAME(String d_BANK_NAME) {
		D_BANK_NAME = d_BANK_NAME;
	}

	public String getD_UNION_BANK() {
		return D_UNION_BANK;
	}

	public void setD_UNION_BANK(String d_UNION_BANK) {
		D_UNION_BANK = d_UNION_BANK;
	}

	public String getD_ACCOUNT_TYPE() {
		return D_ACCOUNT_TYPE;
	}

	public void setD_ACCOUNT_TYPE(String d_ACCOUNT_TYPE) {
		D_ACCOUNT_TYPE = d_ACCOUNT_TYPE;
	}

	public String getD_ACCOUNT_NO() {
		return D_ACCOUNT_NO;
	}

	public void setD_ACCOUNT_NO(String d_ACCOUNT_NO) {
		D_ACCOUNT_NO = d_ACCOUNT_NO;
	}

	public String getD_ACCOUNT_NAME() {
		return D_ACCOUNT_NAME;
	}

	public void setD_ACCOUNT_NAME(String d_ACCOUNT_NAME) {
		D_ACCOUNT_NAME = d_ACCOUNT_NAME;
	}

	public String getD_ACCOUNT_PROP() {
		return D_ACCOUNT_PROP;
	}

	public void setD_ACCOUNT_PROP(String d_ACCOUNT_PROP) {
		D_ACCOUNT_PROP = d_ACCOUNT_PROP;
	}

	public String getD_ID_TYPE() {
		return D_ID_TYPE;
	}

	public void setD_ID_TYPE(String d_ID_TYPE) {
		D_ID_TYPE = d_ID_TYPE;
	}

	public String getD_ID() {
		return D_ID;
	}

	public void setD_ID(String d_ID) {
		D_ID = d_ID;
	}

	public String getD_TEL() {
		return D_TEL;
	}

	public void setD_TEL(String d_TEL) {
		D_TEL = d_TEL;
	}

	public String getP_BANK_CODE() {
		return P_BANK_CODE;
	}

	public void setP_BANK_CODE(String p_BANK_CODE) {
		P_BANK_CODE = p_BANK_CODE;
	}

	public String getP_BANK_NAME() {
		return P_BANK_NAME;
	}

	public void setP_BANK_NAME(String p_BANK_NAME) {
		P_BANK_NAME = p_BANK_NAME;
	}

	public String getP_UNION_BANK() {
		return P_UNION_BANK;
	}

	public void setP_UNION_BANK(String p_UNION_BANK) {
		P_UNION_BANK = p_UNION_BANK;
	}

	public String getP_ACCOUNT_TYPE() {
		return P_ACCOUNT_TYPE;
	}

	public void setP_ACCOUNT_TYPE(String p_ACCOUNT_TYPE) {
		P_ACCOUNT_TYPE = p_ACCOUNT_TYPE;
	}

	public String getP_ACCOUNT_NO() {
		return P_ACCOUNT_NO;
	}

	public void setP_ACCOUNT_NO(String p_ACCOUNT_NO) {
		P_ACCOUNT_NO = p_ACCOUNT_NO;
	}

	public String getP_ACCOUNT_NAME() {
		return P_ACCOUNT_NAME;
	}

	public void setP_ACCOUNT_NAME(String p_ACCOUNT_NAME) {
		P_ACCOUNT_NAME = p_ACCOUNT_NAME;
	}

	public String getP_ACCOUNT_PROP() {
		return P_ACCOUNT_PROP;
	}

	public void setP_ACCOUNT_PROP(String p_ACCOUNT_PROP) {
		P_ACCOUNT_PROP = p_ACCOUNT_PROP;
	}

	public String getP_ID_TYPE() {
		return P_ID_TYPE;
	}

	public void setP_ID_TYPE(String p_ID_TYPE) {
		P_ID_TYPE = p_ID_TYPE;
	}

	public String getP_ID() {
		return P_ID;
	}

	public void setP_ID(String p_ID) {
		P_ID = p_ID;
	}

	public String getP_TEL() {
		return P_TEL;
	}

	public void setP_TEL(String p_TEL) {
		P_TEL = p_TEL;
	}

}

package com.aipg.acctvalid;
/**
 * 批量验证报文明细
 * @author DON
 *
 */
public class VbDetail {
	private String SN;      //记录序号
	private String BINDID;      //	协议ID
	private String RELATID;      //关联ID
	private String BANK_CODE;      //银行代码
	private String OPTYPE;      //操作类型
	private String ACCOUNT_TYPE;      //账号类型
	private String ACCOUNT_NO;      //账号
	private String ACCOUNT_NAME;      //账号名
	private String ACCOUNT_PROP;      //账号属性
	private String ID_TYPE;      //开户证件类型
	private String ID;      //证件号
	private String RELATEDCARD;      //关联卡号
	private String TEL;   //手机号/小灵通
	private String MERREM;	//商户保留
	private String REMARK;	//备注

	public String getTEL() {
		return TEL;
	}
	public void setTEL(String tEL) {
		TEL = tEL;
	}
	public String getMERREM() {
		return MERREM;
	}
	public void setMERREM(String mERREM) {
		MERREM = mERREM;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
	public String getBINDID() {
		return BINDID;
	}
	public void setBINDID(String bINDID) {
		BINDID = bINDID;
	}
	public String getRELATID() {
		return RELATID;
	}
	public void setRELATID(String rELATID) {
		RELATID = rELATID;
	}
	public String getBANK_CODE() {
		return BANK_CODE;
	}
	public void setBANK_CODE(String bANKCODE) {
		BANK_CODE = bANKCODE;
	}
	public String getOPTYPE() {
		return OPTYPE;
	}
	public void setOPTYPE(String oPTYPE) {
		OPTYPE = oPTYPE;
	}
	public String getACCOUNT_TYPE() {
		return ACCOUNT_TYPE;
	}
	public void setACCOUNT_TYPE(String aCCOUNTTYPE) {
		ACCOUNT_TYPE = aCCOUNTTYPE;
	}
	public String getACCOUNT_NO() {
		return ACCOUNT_NO;
	}
	public void setACCOUNT_NO(String aCCOUNTNO) {
		ACCOUNT_NO = aCCOUNTNO;
	}
	public String getACCOUNT_NAME() {
		return ACCOUNT_NAME;
	}
	public void setACCOUNT_NAME(String aCCOUNTNAME) {
		ACCOUNT_NAME = aCCOUNTNAME;
	}
	public String getACCOUNT_PROP() {
		return ACCOUNT_PROP;
	}
	public void setACCOUNT_PROP(String aCCOUNTPROP) {
		ACCOUNT_PROP = aCCOUNTPROP;
	}
	public String getID_TYPE() {
		return ID_TYPE;
	}
	public void setID_TYPE(String iDTYPE) {
		ID_TYPE = iDTYPE;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getRELATEDCARD() {
		return RELATEDCARD;
	}
	public void setRELATEDCARD(String rELATEDCARD) {
		RELATEDCARD = rELATEDCARD;
	}
	
	
	

}

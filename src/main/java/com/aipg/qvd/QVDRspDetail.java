package com.aipg.qvd;
/**
 * 验证明细查询响应报文细则
 * @author DON
 *
 */
public class QVDRspDetail {
	private String BATCHID;	//交易批次号
	private String SN;	//记录序号
	private String BINDID;	//协议ID
	private String RELATID;	//关联ID
	private String SUBMITTIME;	//提交时间
	private String ACCOUNT_NO;	//账号
	private String ACCOUNT_NAME;	//账号名
	private String RELATEDCARD;	//关联卡号
	private String MERREM;	//商户保留字段
	private String REMARK;	//备注
	private String RET_CODE;	//返回码
	private String ERR_MSG;	//错误文本
	public String getBATCHID() {
		return BATCHID;
	}
	public void setBATCHID(String bATCHID) {
		BATCHID = bATCHID;
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
	public String getSUBMITTIME() {
		return SUBMITTIME;
	}
	public void setSUBMITTIME(String sUBMITTIME) {
		SUBMITTIME = sUBMITTIME;
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
	public String getRELATEDCARD() {
		return RELATEDCARD;
	}
	public void setRELATEDCARD(String rELATEDCARD) {
		RELATEDCARD = rELATEDCARD;
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
	public String getRET_CODE() {
		return RET_CODE;
	}
	public void setRET_CODE(String rETCODE) {
		RET_CODE = rETCODE;
	}
	public String getERR_MSG() {
		return ERR_MSG;
	}
	public void setERR_MSG(String eRRMSG) {
		ERR_MSG = eRRMSG;
	}
	
	

}

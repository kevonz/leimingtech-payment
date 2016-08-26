package com.aipg.qvd;
/**
 * 验证明细查询请求报文
 * @author DON
 *
 */
public class QVDReq {
	private String PGTAG;	//页码标识
	private String MERCHANT_ID;  //	商户代码
	private String QUERY_SN;	//要查询的交易流水
	private String STATUS;	//状态
	private String STARTTIME;	//开始时间
	private String ENDTIME;	//结束时间
	public String getPGTAG() {
		return PGTAG;
	}
	public void setPGTAG(String pGTAG) {
		PGTAG = pGTAG;
	}
	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANTID) {
		MERCHANT_ID = mERCHANTID;
	}
	public String getQUERY_SN() {
		return QUERY_SN;
	}
	public void setQUERY_SN(String qUERYSN) {
		QUERY_SN = qUERYSN;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getSTARTTIME() {
		return STARTTIME;
	}
	public void setSTARTTIME(String sTARTTIME) {
		STARTTIME = sTARTTIME;
	}
	public String getENDTIME() {
		return ENDTIME;
	}
	public void setENDTIME(String eNDTIME) {
		ENDTIME = eNDTIME;
	}

	
	
}

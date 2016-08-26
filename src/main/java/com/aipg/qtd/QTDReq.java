package com.aipg.qtd;

public class QTDReq {
	private String PGTAG;
	private String MERCHANT_ID;
	private String QUERY_SN;
	private String STATUS;
	private String TYPE;
	private String STARTTIME;
	private String ENDTIME;
	
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
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
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

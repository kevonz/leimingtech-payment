package com.aipg.etdtlquery;

public class EtDtl {

	private String SN;	//递增序列
	private String ACCTNO;	//账户号	C(30)	商户的通联帐户
	private String VOUCHERNO;	//凭证号	C(30)	通联凭证号
	private String CRDR;	//借贷	N(1)	1.借2.贷
	private String AMOUNT;	//金额	N(30)	单位分
	private String TRXID;	//交易流水	C(40)	如收付交易时为文件名
	private String PAYCODE;	//交易代码	C(8)	交易代码，见附录
	private String POSTDATE;	//记账时间	C(14)	yyyyMMddhhmmss
	private String MEMO;	//备注	C(1024)	
	
	
	
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
	public String getACCTNO() {
		return ACCTNO;
	}
	public void setACCTNO(String aCCTNO) {
		ACCTNO = aCCTNO;
	}
	public String getVOUCHERNO() {
		return VOUCHERNO;
	}
	public void setVOUCHERNO(String vOUCHERNO) {
		VOUCHERNO = vOUCHERNO;
	}
	public String getCRDR() {
		return CRDR;
	}
	public void setCRDR(String cRDR) {
		CRDR = cRDR;
	}
	public String getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public String getTRXID() {
		return TRXID;
	}
	public void setTRXID(String tRXID) {
		TRXID = tRXID;
	}
	public String getPAYCODE() {
		return PAYCODE;
	}
	public void setPAYCODE(String pAYCODE) {
		PAYCODE = pAYCODE;
	}
	public String getPOSTDATE() {
		return POSTDATE;
	}
	public void setPOSTDATE(String pOSTDATE) {
		POSTDATE = pOSTDATE;
	}
	public String getMEMO() {
		return MEMO;
	}
	public void setMEMO(String mEMO) {
		MEMO = mEMO;
	}
	
	
	

	
	
}

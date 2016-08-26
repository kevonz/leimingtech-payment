package com.aipg.agrmqx;

public class XQSDetail {
	private String CONTRACTNO;		//合同号
	private String AGREEMENTNO;		//协议号
	private String MEMID;			//商户客户ID
	private String ACCT;			//客户账号
	private String ACCTNAME;		//客户名
	private String MAXSINGLEAMT;	//单笔限额
	private String DAYMAXSUCCCNT;	//日成功交易笔数限制
	private String DAYMAXSUCCAMT;	//日成功交易金额限制
	private String MONMAXSUCCCNT;	//月成功交易笔数限制
	private String MONMAXSUCCAMT;	//月成功交易金额限制
	private String AGRDEADLINE;		//交易期限以时间表示
	private String ERRMSG;			//错误原因
	private String STATUS;			//状态
	
	public String getCONTRACTNO() {
		return CONTRACTNO;
	}
	public void setCONTRACTNO(String cONTRACTNO) {
		CONTRACTNO = cONTRACTNO;
	}
	public String getAGREEMENTNO() {
		return AGREEMENTNO;
	}
	public void setAGREEMENTNO(String aGREEMENTNO) {
		AGREEMENTNO = aGREEMENTNO;
	}
	public String getMEMID() {
		return MEMID;
	}
	public void setMEMID(String mEMID) {
		MEMID = mEMID;
	}
	public String getACCT() {
		return ACCT;
	}
	public void setACCT(String aCCT) {
		ACCT = aCCT;
	}
	public String getACCTNAME() {
		return ACCTNAME;
	}
	public void setACCTNAME(String aCCTNAME) {
		ACCTNAME = aCCTNAME;
	}
	public String getMAXSINGLEAMT() {
		return MAXSINGLEAMT;
	}
	public void setMAXSINGLEAMT(String mAXSINGLEAMT) {
		MAXSINGLEAMT = mAXSINGLEAMT;
	}
	public String getDAYMAXSUCCCNT() {
		return DAYMAXSUCCCNT;
	}
	public void setDAYMAXSUCCCNT(String dAYMAXSUCCCNT) {
		DAYMAXSUCCCNT = dAYMAXSUCCCNT;
	}
	public String getDAYMAXSUCCAMT() {
		return DAYMAXSUCCAMT;
	}
	public void setDAYMAXSUCCAMT(String dAYMAXSUCCAMT) {
		DAYMAXSUCCAMT = dAYMAXSUCCAMT;
	}
	public String getMONMAXSUCCCNT() {
		return MONMAXSUCCCNT;
	}
	public void setMONMAXSUCCCNT(String mONMAXSUCCCNT) {
		MONMAXSUCCCNT = mONMAXSUCCCNT;
	}
	public String getMONMAXSUCCAMT() {
		return MONMAXSUCCAMT;
	}
	public void setMONMAXSUCCAMT(String mONMAXSUCCAMT) {
		MONMAXSUCCAMT = mONMAXSUCCAMT;
	}
	public String getAGRDEADLINE() {
		return AGRDEADLINE;
	}
	public void setAGRDEADLINE(String aGRDEADLINE) {
		AGRDEADLINE = aGRDEADLINE;
	}
	public String getERRMSG() {
		return ERRMSG;
	}
	public void setERRMSG(String eRRMSG) {
		ERRMSG = eRRMSG;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	
	
	
	

}

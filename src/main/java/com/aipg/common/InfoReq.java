package com.aipg.common;

public class InfoReq {
	private String TRX_CODE = "";
	private String VERSION = "";
	private String DATA_TYPE = "";
	private String LEVEL = "";
	private String MERCHANT_ID;
	private String USER_NAME = "";
	private String USER_PASS = "";
	private String REQ_SN = "";
	private String REQTIME;
	private String SIGNED_MSG = "";

	public String getDATA_TYPE() {
		return DATA_TYPE;
	}

	public void setDATA_TYPE(String data_type) {
		DATA_TYPE = data_type;
	}

	public String getLEVEL() {
		return LEVEL;
	}

	public void setLEVEL(String level) {
		LEVEL = level;
	}

	public String getUSER_NAME() {
		return USER_NAME;
	}

	public void setUSER_NAME(String user_name) {
		USER_NAME = user_name;
	}

	public String getUSER_PASS() {
		return USER_PASS;
	}

	public void setUSER_PASS(String user_pass) {
		USER_PASS = user_pass;
	}

	public String getREQ_SN() {
		return REQ_SN;
	}

	public void setREQ_SN(String req_sn) {
		REQ_SN = req_sn;
	}

	public String getSIGNED_MSG() {
		return SIGNED_MSG;
	}

	public void setSIGNED_MSG(String signed_msg) {
		SIGNED_MSG = signed_msg;
	}

	public String getTRX_CODE() {
		return TRX_CODE;
	}

	public void setTRX_CODE(String trx_code) {
		TRX_CODE = trx_code;
	}

	public String getVERSION() {
		return VERSION;
	}

	public void setVERSION(String version) {
		VERSION = version;
	}

	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}

	public void setMERCHANT_ID(String mERCHANTID) {
		MERCHANT_ID = mERCHANTID;
	}

	public String getREQTIME() {
		return REQTIME;
	}

	public void setREQTIME(String rEQTIME) {
		REQTIME = rEQTIME;
	}
}

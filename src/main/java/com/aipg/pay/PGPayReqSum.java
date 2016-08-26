package com.aipg.pay;

public class PGPayReqSum
{
	private String BUSINESS_CODE="";
	private String MERCHANT_ID="";
	private String SUBMIT_TIME="";
	private String TOTAL_ITEM="";
	private String TOTAL_SUM="";
	private String SETTDAY="";
	public String getSETTDAY()
	{
		return SETTDAY;
	}
	public void setSETTDAY(String sETTDAY)
	{
		SETTDAY = sETTDAY;
	}	
	public String getBUSINESS_CODE() {
		return BUSINESS_CODE;
	}
	public void setBUSINESS_CODE(String business_code) {
		BUSINESS_CODE = business_code;
	}
	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String merchant_id) {
		MERCHANT_ID = merchant_id;
	}
	public String getSUBMIT_TIME() {
		return SUBMIT_TIME;
	}
	public void setSUBMIT_TIME(String submit_time) {
		SUBMIT_TIME = submit_time;
	}
	public String getTOTAL_ITEM() {
		return TOTAL_ITEM;
	}
	public void setTOTAL_ITEM(String total_item) {
		TOTAL_ITEM = total_item;
	}
	public String getTOTAL_SUM() {
		return TOTAL_SUM;
	}
	public void setTOTAL_SUM(String total_sum) {
		TOTAL_SUM = total_sum;
	}
}

package com.aipg.agrmsync;

import java.util.ArrayList;
import java.util.List;

public class SignInfoSync {
	private int TOTAL;	        //总客户数
	private String CONTRACTNO;	//合同号
	private String MERCHANT_ID;	//商户代码
	private List<SignInfoDetail> details = new ArrayList<SignInfoDetail>();
	

	public List<SignInfoDetail> getDetails() {
		return details;
	}
	public void setDetails(List<SignInfoDetail> details) {
		this.details = details;
	}
	public int getTOTAL() {
		return TOTAL;
	}
	public void setTOTAL(int tOTAL) {
		TOTAL = tOTAL;
	}
	
	public String getCONTRACTNO() {
		return CONTRACTNO;
	}
	public void setCONTRACTNO(String cONTRACTNO) {
		CONTRACTNO = cONTRACTNO;
	}
	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANTID) {
		MERCHANT_ID = mERCHANTID;
	}
	
	public void addDetail(SignInfoDetail detail) {
		details.add(detail);
	}
	
}

package com.aipg.syncex;

import java.util.ArrayList;
import java.util.List;

import com.aipg.synreq.SyncDetail;

public class SyncReqEx {
	private int TOTAL;
	private String CONTRACTNO;
	private String MERCHANT_ID;
	private List details = new ArrayList();

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

	public void setTOTAL(int TOTAL) {
		this.TOTAL = TOTAL;
	}

	public int getTOTAL() {
		return this.TOTAL;
	}

	public void setDetails(List details) {
		this.details = details;
	}

	public List getDetails() {
		return this.details;
	}

	public void addDetail(SyncReqExDetail detail) {
		details.add(detail);
	}
}


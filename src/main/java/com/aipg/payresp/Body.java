package com.aipg.payresp;

import java.util.ArrayList;
import java.util.List;

public class Body {
	
	private List details = new ArrayList();
	public List getDetails() {
		return details;
	}
	public void setDetails(List details) {
		this.details = details;
	}
	
	public void addDetail(Ret_Detail detail) {
		details.add(detail);
	}
}

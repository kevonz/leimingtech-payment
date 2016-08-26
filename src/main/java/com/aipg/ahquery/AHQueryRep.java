package com.aipg.ahquery;

import java.util.ArrayList;
import java.util.List;

import com.aipg.etquery.EtNode;

public class AHQueryRep {
	private String ACCTNO;
	private List details = new ArrayList();
	
	public String getACCTNO() {
		return ACCTNO;
	}
	public void setACCTNO(String aCCTNO) {
		ACCTNO = aCCTNO;
	}
	public List getDetails() {
		return details;
	}
	public void setDetails(List details) {
		this.details = details;
	}
	public void addDtl(BalNode dtl)
	{
		if(details==null) details=new ArrayList();
		details.add(dtl);
	}
}

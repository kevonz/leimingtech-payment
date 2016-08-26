package com.aipg.etquery;

import java.util.ArrayList;
import java.util.List;

import com.aipg.qtd.QTDRspDetail;

public class EtQueryRep {
	private String PGTAG;
	private List details = new ArrayList();

	public String getPGTAG() {
		return PGTAG;
	}
	public void setPGTAG(String pGTAG) {
		PGTAG = pGTAG;
	}
	public List getDetails() {
		return details;
	}
	public void setDetails(List details) {
		this.details = details;
	}
	public void addDtl(EtNode dtl)
	{
		if(details==null) details=new ArrayList();
		details.add(dtl);
	}
}

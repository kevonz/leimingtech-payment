package com.aipg.transquery;

import java.util.ArrayList;
import java.util.List;

//@SuppressWarnings("unchecked")
public class QTransRsp
{
	private List details;
	public List getDetails()
	{
		return details;
	}

	public void setDetails(List details)
	{
		this.details = details;
	}
	public void addDtl(QTDetail dtl)
	{
		if(details==null) details=new ArrayList();
		details.add(dtl);
	}
}

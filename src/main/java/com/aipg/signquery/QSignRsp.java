package com.aipg.signquery;

import java.util.ArrayList;
import java.util.List;

//@SuppressWarnings("unchecked")
public class QSignRsp
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
	public void addDtl(QSignDetail dtl)
	{
		if(details==null) details=new ArrayList();
		details.add(dtl);
	}
}

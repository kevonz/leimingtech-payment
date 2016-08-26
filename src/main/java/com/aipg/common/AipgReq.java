package com.aipg.common;

import java.util.ArrayList;
import java.util.List;


//@SuppressWarnings("unchecked")
public class AipgReq
{
	private InfoReq INFO;
	private List trxData;
	public InfoReq getINFO()
	{
		return INFO;
	}
	public void setINFO(InfoReq iNFO)
	{
		INFO = iNFO;
	}
	public List getTrxData()
	{
		return trxData;
	}

	public void setTrxData(List trxData)
	{
		this.trxData = trxData;
	}
	public void addTrx(Object o)
	{
		if(o==null) return ;
		if(trxData==null) trxData=new ArrayList();
		trxData.add(o);
	}
	public Object findObj(Class clzx)
	{
		if(trxData==null) return null;
		for(Object ox:trxData)
		{
			if(clzx.isInstance(ox)) return ox;
		}
		return trxObj();
	}
	public Object trxObj()
	{
		if(trxData==null) return null;
		if(!trxData.isEmpty()) return trxData.iterator().next();
		return null;
	}
}

package com.aipg.queryresp;



import com.aipg.common.InfoReq;
import com.aipg.common.XStreamEx;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Process {
	
	public XStream getXStream( ) {
		XStream xstream = new XStreamEx(new DomDriver());
		xstream.alias("AIPG", AIPG.class);
		xstream.alias("INFO", InfoReq.class);
		xstream.alias("RET_DETAIL", Ret_Detail.class);
 		xstream.aliasField("RET_DETAILS", Body.class, "details");
		return xstream;
	}
	
	public AIPG parseXML(String strData) {		
		return (AIPG)getXStream().fromXML(strData);
	}
	
	public String formXML(AIPG obj) {
		return getXStream().toXML(obj);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

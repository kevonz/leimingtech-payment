package com.aipg.test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import com.aipg.common.AipgReq;
import com.aipg.common.InfoReq;
import com.aipg.common.XSUtil;
import com.aipg.common.XStreamEx;
import com.aipg.rtreq.Trans;
import com.aipg.synreq.Sync;
import com.aipg.synreq.SyncDetail;
import com.aipg.transquery.TransQueryReq;
import com.thoughtworks.xstream.XStream;
public class TestMain
{
	//protected static Logger log=Logger.getLogger(TestMain.class);
	private URLConnection createRequest(String strUrl, String strMethod) throws Exception
	{
		URL url = new URL(strUrl);
		URLConnection conn = url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		if (conn instanceof HttpsURLConnection)
		{
			HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
			httpsConn.setRequestMethod(strMethod);
			//httpsConn.setSSLSocketFactory(SSLUtil.getInstance().getSSLSocketFactory());
			//httpsConn.setHostnameVerifier(simpleVerifier);
		}
		else if (conn instanceof HttpURLConnection)
		{
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setRequestMethod(strMethod);
		}
		return conn;
	}
	public String send(String url,String xml) throws Exception
	{

		byte[] postData;
		postData = xml.getBytes("GBK");
		URLConnection request = null;
		request = createRequest(url, "POST");

		request.setRequestProperty("Content-type", "application/tlt-notify");
		request.setRequestProperty("Content-length", String.valueOf(postData.length));
		request.setRequestProperty("Keep-alive", "false");

		OutputStream reqStream = request.getOutputStream();
		reqStream.write(postData);
		reqStream.close();

		ByteArrayOutputStream ms = null;
		String respText;

		InputStream resStream = request.getInputStream();
		ms = new ByteArrayOutputStream();
		byte[] buf = new byte[4096];
		int count;
		while ((count = resStream.read(buf, 0, buf.length)) > 0)
		{
			ms.write(buf, 0, count);
		}
		resStream.close();
		respText = new String(ms.toByteArray(), "GBK");
		return respText;
	}
	public static String signMsg(String strData, String pathPfx, String pass)
	{
		String strRnt = "";
		// 签名
		Crypt crypt = new Crypt();
		// 固定取我司的公钥
		String strMsg = strData.replaceAll("<SIGNED_MSG></SIGNED_MSG>", "");
		//log.debug("签名字符串为\n[" + strMsg + "]");
		if (crypt.SignMsg(strMsg, pathPfx, pass))
		{
			String signedMsg = crypt.getLastSignMsg();
			strRnt = strData.replaceAll("<SIGNED_MSG></SIGNED_MSG>", "<SIGNED_MSG>" + signedMsg + "</SIGNED_MSG>");
			//log.debug(signedMsg);
		}
		else
		{
			//log.error(crypt.getLastErrMsg());
			strRnt = strData;
		}
		return strRnt;
	}
	private InfoReq makeReq(String trxcod)
	{
		InfoReq info=new InfoReq();
		info.setTRX_CODE(trxcod);
		info.setREQ_SN(String.valueOf(System.currentTimeMillis()));
		info.setUSER_NAME(userName);
		info.setUSER_PASS(password);
		info.setLEVEL("5");
		info.setDATA_TYPE("2");
		info.setVERSION("03");
		return info;
	}
	private static String buildXml(Object o)
	{
		XStream xs=new XStreamEx();
		XSUtil.initXStream(xs, true);
		String xml = xs.toXML(o);
		xml=xml.replaceAll("__", "_");
		return xml;
	}
	public String testDown() throws Exception
	{
		AipgReq ar=new AipgReq();
		InfoReq ir=makeReq("200002");
		TransQueryReq dr=new TransQueryReq();
		ar.setINFO(ir);
		ar.addTrx(dr);
		dr.setSTATUS(2);
		dr.setSTART_DAY("20100515");
		dr.setEND_DAY("20100515");
		return buildXml(ar);
	}
	public String testSign()
	{
		AipgReq ar=new AipgReq();
		InfoReq ir=makeReq("210001");
		Sync sync=new Sync();
		SyncDetail syncdtl;
		syncdtl=new SyncDetail();
		syncdtl.setACCT("012345678901238");
		syncdtl.setMOBILE("13800138000");
		syncdtl.setAGREEMENTNO("124345111111");
		sync.addDetail(syncdtl);
		//syncdtl=new SyncDetail();
		//syncdtl.setACCT("0123456789012348");
		//syncdtl.setMOBILE("13800138000");
		//sync.addDetail(syncdtl);

		ar.setINFO(ir);
		ar.addTrx(sync);
		return buildXml(ar);
	}
	public String testQuery()
	{
		AipgReq aipgReq=new AipgReq();
		InfoReq info=makeReq("200004");
		aipgReq.setINFO(info);
		TransQueryReq dr=new TransQueryReq();
		aipgReq.addTrx(dr);
		dr.setSTATUS(2);
		dr.setSTART_DAY("20100515");
		dr.setEND_DAY("20100515");
		return buildXml(aipgReq);
	}
	public String testRTTrx()
	{
		AipgReq aipg=new AipgReq();
		Trans trans=new Trans();
		InfoReq info=makeReq("100011");
		aipg.setINFO(info);
		aipg.addTrx(trans);

		trans.setMERCHANT_ID(merchantId);
		trans.setAMOUNT("10000");
		trans.setBANK_CODE("102");
		trans.setACCOUNT_NAME("testing");
		trans.setACCOUNT_NO("0123456789012345");
		trans.setBUSINESS_CODE("10600");
		return buildXml(aipg);
	}
	public void test() throws Exception
	{
		cerPath = System.getProperty("user.dir") + "\\config\\allinpay-pds.cer";
		pfxPath = System.getProperty("user.dir") + "\\config\\1345200001test.p12";
		String xml=testSign();
		//xml="<?xml version=\"1.0\" encoding=\"GBK\"?><AIPG><INFO><TRX_CODE>100011</TRX_CODE><VERSION>03</VERSION><DATA_TYPE>2</DATA_TYPE><LEVEL>5</LEVEL><USER_NAME>134520000102</USER_NAME><USER_PASS>111111</USER_PASS><REQ_SN>134520000136020200220100525000340</REQ_SN><SIGNED_MSG>1f642b35796a40b8727b0f3ecc6deab3dfdb1dab46ffcb9ddba5861c48b0020f83aee8fb9b2a0527d7f8b4d40171666c30db280482b1d6972b2c5c79214416c7bcc19b31b3ec068c045066db770741ac8b83930b891ad13466e9c8ff9f70783aaa4df384f0a33b783ee2eccca45ad2f0ad920106ddc0a29a24ddf0c9cc892201627b300f0dd77d71a20c523d998176ecf42f584898ecf8d8ebf8b7bfe66bd75ce9a8c9b4b12b6975fa8396d642da45ee83ee8fbbeb26d6bb29272f94ebb2e9bdb84a1e577c32c75664228288fd0a4b2b574319fd39fe71faf64ffd95eb002f11e401f747d0759ca79bd8d06171994cdcec505452ce854dfaa822dbc203f7d278</SIGNED_MSG></INFO><TRANS><BUSINESS_CODE>14902</BUSINESS_CODE><MERCHANT_ID>1345200001</MERCHANT_ID><SUBMIT_TIME>20100527173200</SUBMIT_TIME><TERM_CODE/><TRACK2/><TRACK3/><PINCODE/><E_USER_CODE/><VALIDATE/><CVV2/><BANK_CODE>105</BANK_CODE><ACCOUNT_TYPE>00</ACCOUNT_TYPE><ACCOUNT_NO>6227123456780000999</ACCOUNT_NO><ACCOUNT_NAME>test</ACCOUNT_NAME><ACCOUNT_PROP>0</ACCOUNT_PROP><AMOUNT>5000</AMOUNT><CURRENCY/><ID_TYPE/><ID/><TEL/><CUST_USERID/><REMARK/></TRANS></AIPG>";
		xml=signMsg(xml,pfxPath,pfxPassword);
		/*xml="<AIPG>\n"
		+"  <INFO>\n"
		+"    <TRX_CODE>210003</TRX_CODE>\n"
		+"    <VERSION>03</VERSION>\n"
		+"    <DATA_TYPE>2</DATA_TYPE>\n"
		+"    <REQ_SN>1279012679572</REQ_SN>\n"
		+"    <SIGNED_MSG>7e4f16a5d7850d798d52463a226c637bf518f932b2bd1507e093a1bff728ea4c962d0f96f663f53dd0991ade0d624cdfe1f9f047dff16465887585008b833772089d3629a5f1c17f11c054376764130d62bb9477f39c5e4d456e887316744813fd000f43932f13d53d90415d796c492018bea025ad44b56be3e2f8c684f6331a2a34a6538c0b7de2b4b80ee2d0b91e743759eebb01854ef87f7eb2cd24aa82081dc77e934c9d267304c59b887d9f1c2d337c1cf80620dbb719f92832e1cca877a51f360a4d1cd55bc54affa57ee1952000322649acfe1ba37ed144f40696f246e4e938d36d241d4db8be83b368b26e03ea26e3a110742727425c37b7444b6ac5</SIGNED_MSG>\n"
		+"  </INFO>\n"
		+"  <NSIGNREQ>\n"
		+"    <QSDETAIL>\n"
		+"      <AGREEMENTNO>A25506</AGREEMENTNO>\n"
		+"      <CONTRACTNO>0</CONTRACTNO>\n"
		+"      <ACCT>6222024301012469605</ACCT>\n"
		+"      <STATUS>2</STATUS>\n"
		+"    </QSDETAIL>\n"
		+"  </NSIGNREQ>\n"
		+"</AIPG>\n";
		*/
		long t=System.currentTimeMillis();
		String resp=send("https://113.108.182.5:8443/aipg/ProcessServlet",xml);
		//String resp=send("http://202.102.53.140:8079/tLSignNotify.do",xml);
		t-=System.currentTimeMillis();
		//log.debug(resp);
		//log.debug(-t);
		//log.debug("-----------end----------");
	}
	public static void main(String[] args)
	{
		TestMain test=new TestMain();
		try
		{
			test.test();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			//log.error("error",ex);
		}
	}
	private String userName = "134520000102";
	private String password = "111111";
	private String merchantId = "1345200001";
	private String pfxPassword = "1345200001@aip";
	private String pfxPath;
	private String cerPath;
}

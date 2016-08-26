package com.allinpay;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.Provider;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.allinpay.security.CryptInf;
import com.allinpay.security.CryptNoRestrict;
import com.allinpay.security.CryptUnderRestrict;



public class XmlTools
{
	private static Provider prvd=null;
	private static final SSLHandler simpleVerifier=new SSLHandler();
	private static SSLSocketFactory sslFactory;
	private static URLConnection createRequest(String strUrl, String strMethod) throws Exception
	{
		URL url = new URL(strUrl);
		URLConnection conn = url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		if (conn instanceof HttpsURLConnection)
		{
			HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
			httpsConn.setRequestMethod(strMethod);
			httpsConn.setSSLSocketFactory(XmlTools.getSSLSF());
			httpsConn.setHostnameVerifier(XmlTools.getVerifier());
		}
		else if (conn instanceof HttpURLConnection)
		{
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setRequestMethod(strMethod);
		}
		return conn;
	}
	private static void close(InputStream c)
	{
		try
		{
			if(c!=null) c.close();
		}
		catch(Exception ex)
		{
			
		}
	}
	private static void close(OutputStream c)
	{
		try
		{
			if(c!=null) c.close();
		}
		catch(Exception ex)
		{
			
		}
	}
	private static class SSLHandler implements X509TrustManager,HostnameVerifier
	{	
		private SSLHandler()
		{
		}
		
		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean verify(String arg0, SSLSession arg1)
		{
			return true;
		}
	}
	private static boolean verifySign(String strXML, String cerFile,Provider prv,boolean restrict,boolean isfront) throws Exception
	{
		if(!isfront){
			PaymentSign.initProvider();
			// 签名
			CryptInf crypt;
			if(restrict) crypt=new CryptUnderRestrict("GBK");
			else crypt=new CryptNoRestrict("GBK",prv);
			//
			File file = new File(cerFile);
			if (!file.exists()) throw new Exception("文件"+cerFile+"不存在");
			int iStart = strXML.indexOf("<SIGNED_MSG>");
			if(iStart==-1) throw new Exception("XML报文中不存在<SIGNED_MSG>");
			int end = strXML.indexOf("</SIGNED_MSG>");
			if(end==-1) throw new Exception("XML报文中不存在</SIGNED_MSG>");
			String signedMsg = strXML.substring(iStart + 12, end);
			String strMsg = strXML.substring(0, iStart) + strXML.substring(end + 13);
			System.out.println(strMsg);
			System.out.println(strMsg.length());
			System.out.println(signedMsg.toLowerCase());
			return crypt.VerifyMsg(signedMsg.toLowerCase(), strMsg,cerFile);
		}else{
			return true;
		}
		
	}
	private static String signPlain(String strData, String pathPfx, String pass,Provider prv,boolean restrict) throws Exception
	{
		PaymentSign.initProvider();
		CryptInf crypt;
		if(restrict) crypt=new CryptUnderRestrict("GBK");
		else crypt=new CryptNoRestrict("GBK",prv);
		String strRnt = "";
		if (crypt.SignMsg(strData, pathPfx, pass))
		{
			String signedMsg = crypt.getLastSignMsg();
			strRnt = signedMsg;
		}
		else
		{
			throw new Exception("签名失败");
		}
		return strRnt;		
	}
	private static String signMsg(String strData, String pathPfx, String pass,Provider prv,boolean restrict) throws Exception
	{
		final String IDD_STR="<SIGNED_MSG></SIGNED_MSG>";
		String strMsg = strData.replaceAll(IDD_STR, "");
		String signedMsg = signPlain(strMsg, pathPfx, pass,prv,restrict);
		String strRnt = strData.replaceAll(IDD_STR, "<SIGNED_MSG>" + signedMsg + "</SIGNED_MSG>");
		return strRnt;
	}
	public static HostnameVerifier getVerifier()
	{
		return simpleVerifier;
	}
	public static synchronized SSLSocketFactory getSSLSF() throws Exception
	{
		if(sslFactory!=null) return sslFactory; 
		SSLContext sc = prvd==null?SSLContext.getInstance("SSLv3"):SSLContext.getInstance("SSLv3");
		sc.init(null, new TrustManager[]{simpleVerifier}, null);
		sslFactory = sc.getSocketFactory();
		return sslFactory;
	}
	public static void initProvider(Provider p)
	{
		prvd=p;
	}	
	public static String buildXml(Object o,boolean isreq)
	{
		XStreamIg xs=new XStreamIg();
		XSUtilEx.initXStream(xs, isreq);
		String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?>"+xs.toXML(o);
		xml=xml.replaceAll("__", "_");
		return xml;
	}
	public static Object parseXml(String xml,boolean isreq)
	{
		XStreamIg xs=new XStreamIg();
		XSUtilEx.initXStream(xs, isreq);
		return xs.fromXML(xml);
	}
	public static String send(String url,String xml) throws Exception
	{
		OutputStream reqStream=null;
		InputStream resStream =null;
		URLConnection request = null;
		String respText=null;
		byte[] postData;
		try
		{
			postData = xml.getBytes("GBK");
			request = createRequest(url, "POST");
	
			request.setRequestProperty("Content-type", "application/tlt-notify");
			request.setRequestProperty("Content-length", String.valueOf(postData.length));
			request.setRequestProperty("Keep-alive", "false");
	
			reqStream = request.getOutputStream();
			reqStream.write(postData);
			reqStream.close();
	
			ByteArrayOutputStream ms = null;	
			resStream = request.getInputStream();
			ms = new ByteArrayOutputStream();
			byte[] buf = new byte[4096];
			int count;
			while ((count = resStream.read(buf, 0, buf.length)) > 0)
			{
				ms.write(buf, 0, count);
			}
			resStream.close();
			respText = new String(ms.toByteArray(), "GBK");
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			close(reqStream);
			close(resStream);
		}
		return respText;
	}
	public static boolean verifySign(String strXML, String cerFile,boolean restrict,boolean isfront) throws Exception
	{
		return verifySign(strXML, cerFile, prvd, restrict,isfront);
	}
	public static String signPlain(String strData, String pathPfx, String pass,boolean restrict) throws Exception
	{
		return signPlain(strData, pathPfx, pass, prvd, restrict);
	}
	public static String signMsg(String strData, String pathPfx, String pass,boolean restrict) throws Exception
	{
		return signMsg(strData, pathPfx, pass, prvd, restrict);
	}
	public static boolean verifySign(String strXML, String cerFile,Provider prv) throws Exception
	{
		return verifySign(strXML, cerFile, prv,false,false);
	}
	public static String signPlain(String strData, String pathPfx, String pass,Provider prv) throws Exception
	{
		return signPlain(strData, pathPfx, pass, prv,false);
	}
	public static String signMsg(String strData, String pathPfx, String pass,Provider prv) throws Exception
	{
		return signMsg(strData, pathPfx, pass, prv, false);
	}
	public static void main(String[] args) throws Exception
	{
		//设置安全提供者
		BouncyCastleProvider provider = new BouncyCastleProvider();
		initProvider(provider);
		String orgs="6222021001116245702|13301293658|9900|1406090801011639|0|0";
		String signmsg="74ae130d89bc346b2eda98e171aae73f554fca4211f6b5f6906a6668cd3742298bfc428453356b91b1e654b567c12c743e1ba4fa1e333d835835336586f2b1db6888a9176060c099653e34fe31dcd72e23e9aa613f904e75904f07ef0a27e8b5560fbab0d2359f95b24150db865d7e8fac6edbb5abc9cc32793f0ef76b31a761f56ed7599444ce9e3aebd75564e677ebbba3bfad538a56cf974b1e948bc647bb3a33662f56785126835b1ef1e8abba4a2a3acbd5d838633c6b6f1cf2119a869eabda3ba6f1026d0c1fc94b39b2d41c6eb185f3f0b5bca9b505594009814ac3c8f1d52a73933c5ba6901f120b26596fa4d7c1e12f0032cee72e74b8a42bf274f5";
		// 签名
		String signMsg=signPlain(orgs,"config/allinpay-pds.pfx","allinpay-pds",null);
		System.out.println("签名信息:"+signMsg);
		// 验签
		CryptInf crypt;
		crypt=new CryptNoRestrict("GBK",null);
		boolean flag=crypt.VerifyMsg(signmsg, orgs,  "config/allinpay-pds.cer");
		if(flag){
			System.out.println("[验签结果]："+flag);
		}
	
	}
}

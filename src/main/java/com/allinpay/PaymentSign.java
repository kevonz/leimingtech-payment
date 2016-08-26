package com.allinpay;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.allinpay.security.CryptInf;
import com.allinpay.security.CryptNoRestrict;
import com.allinpay.security.CryptUnderRestrict;


public class PaymentSign
{
	public static String PRIVATE_PATH="";
	private StringBuffer digStr;
	private boolean restrict;
	public PaymentSign(boolean restrict)
	{
		this.restrict=restrict;
	}
	public static void initProvider()
	{
		//if(initProv) return ;
		BouncyCastleProvider p=new BouncyCastleProvider();
		Security.addProvider(p);
	}
	public PaymentSign addField(Object field)
	{
		return addField(field.toString());
	}
	public PaymentSign addField(String field)
	{
		if(digStr==null) digStr=new StringBuffer();
		if(digStr.length()>0) digStr.append("|");
		if(field==null) field="";
		digStr.append(field);
		return this;
	}
	public String makeMD5withKey(String passwd) throws Exception
	{
		String str=digStr.toString()+"|"+passwd;
		byte[] md5=EasySecure.md5digest(str.getBytes("UTF-8"));
		return EasySecure.toHex(md5);
	}
	public boolean verifyMD5withKey(String signmac,String passwd) throws Exception
	{
		String vsm=makeMD5withKey(passwd);
		if(vsm.equals(signmac))
		{
			throw new Exception("验证失败");
		}
		return true;
	}
	public String makeSHAwithKey(String passwd) throws Exception
	{
		String str=digStr.toString()+"|"+passwd;
		byte[] sha=EasySecure.digest("SHA-1",str.getBytes("UTF-8"));
		return EasySecure.toHex(sha);
	}
	public boolean verifySHAwithKey(String signmac,String passwd) throws Exception
	{
		String vsm=makeSHAwithKey(passwd);
		if(vsm.equals(signmac))
		{
			throw new Exception("验证失败");
		}
		return true;
	}
	public String signMsg(String certFile,String passwd) throws Exception
	{
		initProvider();
		CryptInf c;
		if(restrict) c=new CryptUnderRestrict("GBK");
		else c=new CryptNoRestrict("GBK");
		
		if(!c.SignMsg(digStr.toString(), certFile, passwd))
		{
			throw new Exception("签名失败");
		}
		return c.getLastSignMsg();
	}
	public boolean verify(String signField,String certFile) throws Exception
	{
		initProvider();
		CryptInf c;
		if(restrict) c=new CryptUnderRestrict("GBK");
		else c=new CryptNoRestrict("GBK");
		if(!c.VerifyMsg(signField, digStr.toString(), certFile))
		{
			throw new Exception("验签失败");
		}
		return true;
	}
	public String str()
	{
		return digStr.toString();
	}
	public static void main(String args[])
	{
		try
		{
			String pkgPath = "WebRoot/WEB-INF/1345200001test.pki";
			String pkgPassword = "1345200001@aip";
			PaymentSign ps=new PaymentSign(true);
			ps.digStr=new StringBuffer();
			ps.digStr.append("1345200001|2010041600000004|91300|HOAIR$ALLIN-HOB2B|");
			System.out.println(ps.signMsg(pkgPath, pkgPassword));
			String url=BouncyCastleProvider.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			System.out.println(url);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}

package com.allinpay.security;

public class CryptTest
{
	String plainText = "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
	String encryptedText = "";

	String certPath = "MERCHANT.cer";
	String pfxPath = "MERCHANT.pfx";
	String pkiPath = "WebRoot/WEB-INF/1345200001test.pki";
	String pfxPassWord = "1345200001@aip";

	public void test1() throws Exception
	{
		CryptUnderRestrict crypt = new CryptUnderRestrict("GBK");

		if (crypt.EncryptMsg(this.plainText, this.certPath))
		{
			this.encryptedText = crypt.getLastResult();
			System.out.println("encryptedText=" + this.encryptedText);
		}

		if (crypt.DecryptMsg(this.encryptedText, this.pfxPath, this.pfxPassWord))
			System.out.println("decyptedText=" + crypt.getLastResult());

		String signedText = null;
		if (crypt.SignMsg(this.plainText, this.pfxPath, this.pfxPassWord))
		{
			signedText = crypt.getLastResult();
			System.out.println("signedText=" + signedText);
		}

		if (crypt.VerifyMsg(signedText, this.plainText, this.certPath))
			System.out.println("verifyMsg=" + crypt.getLastResult());
	}

	public void test2() throws Exception
	{
		CryptUnderRestrict yac = new CryptUnderRestrict("GBK");

		if (yac.EncryptMsg(this.plainText, this.pkiPath))
		{
			this.encryptedText = yac.getLastResult();
			System.out.println("encryptedText=" + this.encryptedText);
		}

		if (yac.DecryptMsg(this.encryptedText, this.pkiPath, this.pfxPassWord))
			System.out.println("decyptedText=" + yac.getLastResult());

		String signedText = "";
		if (yac.SignMsg(this.plainText, this.pkiPath, this.pfxPassWord))
		{
			signedText = yac.getLastResult();
			System.out.println("signedText=" + signedText);
		}

		if (yac.VerifyMsg(signedText, this.plainText, this.pkiPath))
			System.out.println("verifyMsg - success");
	}

	public void test3() throws Exception
	{
		CryptUnderRestrict crypt = new CryptUnderRestrict("GBK");
		CryptUnderRestrict yac = new CryptUnderRestrict("GBK");

		if (crypt.EncryptMsg(this.plainText, this.certPath))
		{
			this.encryptedText = crypt.getLastResult();
			System.out.println("encryptedText=" + this.encryptedText);
		}

		if (yac.DecryptMsg(this.encryptedText, this.pkiPath, this.pfxPassWord))
			System.out.println("decyptedText=" + yac.getLastResult());

		if (yac.EncryptMsg(this.plainText, this.pkiPath))
		{
			this.encryptedText = yac.getLastResult();
			System.out.println("encryptedText=" + this.encryptedText);
		}

		if (crypt.DecryptMsg(this.encryptedText, this.pfxPath, this.pfxPassWord))
			System.out.println("decyptedText=" + crypt.getLastResult());

		String signedText = "";

		if (yac.SignMsg(this.plainText, this.pkiPath, this.pfxPassWord))
		{
			signedText = yac.getLastResult();
			System.out.println("signedText=" + signedText);
		}

		if (crypt.VerifyMsg(signedText, this.plainText, this.certPath))
			System.out.println("verifyMsg - success");

		if (crypt.SignMsg(this.plainText, this.pfxPath, this.pfxPassWord))
		{
			signedText = crypt.getLastResult();
			System.out.println("signedText=" + signedText);
		}

		if (yac.VerifyMsg(signedText, this.plainText, this.pkiPath))
			System.out.println("verifyMsg - success");
	}

	public static void main(String[] args)
	{
		CryptTest test = new CryptTest();
		try
		{
			test.test2();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
package com.allinpay.security.pki;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Enumeration;

public class ConvertToPki
{
	public static void convertPER(String certPath,String pkiPath) throws Exception
	{
		FileInputStream certfile = null;
		certfile = new FileInputStream(certPath);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		
		X509Certificate x509cert=null;
		try
		{
			x509cert = (X509Certificate) cf.generateCertificate(certfile);
		}
		catch(Exception ex)
		{
			if(certfile!=null) certfile.close();
			throw ex;
		}

		RSAPublicKey pubkey = (RSAPublicKey) x509cert.getPublicKey();
		pubkey.getModulus();
		RSAPublicKeyStructure rsapks=new RSAPublicKeyStructure(pubkey.getModulus(),pubkey.getPublicExponent());
		PkiStore store = new PkiStore();
		store.store(pkiPath,rsapks);
	}
	public static void convertPKCS12(String pfxPath,String pfxPassword,String pkgPath,String pkgPassword) throws Exception
	{
		KeyStore ks = KeyStore.getInstance("PKCS12");

		InputStream fiKeyFile = new FileInputStream(pfxPath);
		ks.load(fiKeyFile, pfxPassword.toCharArray());
		Enumeration myEnum = ks.aliases();
		String keyAlias = null;

		RSAPrivateCrtKey priKey = null;
		while (myEnum.hasMoreElements())
		{
			keyAlias = (String) myEnum.nextElement();

			if (ks.isKeyEntry(keyAlias))
			{
				priKey = (RSAPrivateCrtKey) ks.getKey(keyAlias, pfxPassword.toCharArray());
				break;
			}
		}

		BigInteger mod = priKey.getModulus();
		BigInteger pubExp = priKey.getPublicExponent();
		BigInteger priExp = priKey.getPrivateExponent();
		BigInteger p = priKey.getPrimeP();
		BigInteger q = priKey.getPrimeQ();
		BigInteger dP = priKey.getPrimeExponentP();
		BigInteger dQ = priKey.getPrimeExponentQ();
		BigInteger qInv = priKey.getCrtCoefficient();

		RSAPrivateCrtKeyStructure priKeyStru = new RSAPrivateCrtKeyStructure(mod, pubExp, priExp, p, q, dP, dQ, qInv);

		PkiStore store = new PkiStore();
		store.store(pkgPath, pkgPassword, priKeyStru);

		System.out.println(pkgPath + " is converted successfully.");		
	}
	public static void main(String[] args) throws Exception
	{
		try
		{
			//convertPER("allinpay-pds.cer","allinpay-pds.pki");
			//if(true) return ;
			String pfxPath = "WebRoot/WEB-INF/1345200001test.p12";
			String pfxPassword = "1345200001@aip";

			String pkgPath = "WebRoot/WEB-INF/1345200001test.pki";
			String pkgPassword = "1345200001@aip";

			if (args.length == 4)
			{
				pfxPath = args[0];
				pfxPassword = args[1];
				pkgPath = args[2];
				pkgPassword = args[3];
			}
			else
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

				System.out.print("pfx或p12文件:");
				pfxPath = reader.readLine();
				System.out.print("密码:");
				pfxPassword = reader.readLine();
				System.out.print("转换后的文件:");
				pkgPath = reader.readLine();
				System.out.print("转换后的密码:");
				pkgPassword = reader.readLine();
			}
			convertPKCS12(pfxPath, pfxPassword, pkgPath, pkgPassword);
		}
		catch (Exception e)
		{
			System.err.println("fail reason: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
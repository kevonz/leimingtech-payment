/**
 * 
 */
package com.aipg.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Enumeration;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author Administrator
 * 
 */
public class Crypt
{

	/**
	 * 构造函数
	 */
	public Crypt()
	{
		// Security.addProvider(new BouncyCastleProvider());
	}
	public Crypt(String encoding)
	{
		this.encoding=encoding;
		// Security.addProvider(new BouncyCastleProvider());
	}

	private String encoding = "GBK";

	/**
	 * 取出上次调用加密、解密、签名函数成功后的输出结果
	 */
	protected String lastResult;

	/**
	 * 取出上次调用任何函数失败后的失败原因
	 */
	protected String lastErrMsg;

	/**
	 * 返回上一次签名结果
	 */
	protected String lastSignMsg;

	/**
	 * 对字符串进行加密
	 * 
	 * @param TobeEncrypted
	 *            待加密的字符串
	 * @param CertFile
	 *            解密者公钥证书路径
	 * @return 加密成功返回true(从LastResult属性获取结果)，失败返回false(从LastErrMsg属性获取失败原因)
	 */
	public boolean EncryptMsg(final String TobeEncrypted, final String CertFile)
	{
		boolean result = false;
		FileInputStream certfile = null;
		try
		{
			certfile = new FileInputStream(CertFile);

			CertificateFactory cf = CertificateFactory.getInstance("X.509");

			X509Certificate x509cert = (X509Certificate) cf.generateCertificate(certfile);
			RSAPublicKey pubkey = (RSAPublicKey) x509cert.getPublicKey();
			BigInteger mod = pubkey.getModulus();

			int keylen = mod.bitLength() / 8;
			if (TobeEncrypted.length() > keylen - 11)
			{
				// 创建Cipher 对象（确定算法[RSA]、方式[NONE]和填充[PKCS1Padding]）
				Cipher pub = Cipher.getInstance("RSA/NONE/PKCS1Padding", "BC");
				pub.init(Cipher.WRAP_MODE, pubkey);

				// get a DES private key
				KeyGenerator kp = KeyGenerator.getInstance("DESEDE");
				kp.init(new SecureRandom());// SecureRandom提供加密的强随机数生成器 (RNG)
				SecretKey sk = kp.generateKey();
				// 将密钥包装
				byte wrappedKey[] = pub.wrap(sk);

				// 创建Cipher 对象（确定算法[DESEDE]、方式[OFB:输出反馈方式(Output Feedback
				// Mode)]和填充[NoPadding]）
				pub = Cipher.getInstance("DESEDE/OFB/NoPadding");
				pub.init(Cipher.ENCRYPT_MODE, sk);

				// int input = TobeEncrypted.length();
				// 按单部分操作加密或解密数据，或者结束一个多部分操作。数据被加密还是解密取决于此 cipher 的初始化方式
				byte encrypted[] = pub.doFinal(TobeEncrypted.getBytes(encoding));
				byte iv[] = pub.getIV();// 返回新缓冲区中的初始化向量 (IV)
				byte enc_ascii[] = new byte[encrypted.length * 2];
				byte iv_asc[] = new byte[iv.length * 2];
				byte prikey_asc[] = new byte[wrappedKey.length * 2];
				Hex2Ascii(encrypted.length, encrypted, enc_ascii);
				Hex2Ascii(iv.length, iv, iv_asc);
				Hex2Ascii(wrappedKey.length, wrappedKey, prikey_asc);
				this.lastResult = new String(iv_asc) + new String(prikey_asc) + new String(enc_ascii);
			}
			else
			{
				Cipher pub = Cipher.getInstance("RSA/NONE/PKCS1Padding", "BC");
				pub.init(Cipher.ENCRYPT_MODE, pubkey);
				byte encrypted[] = pub.doFinal(TobeEncrypted.getBytes(encoding));
				byte enc_ascii[] = new byte[encrypted.length * 2];
				Hex2Ascii(encrypted.length, encrypted, enc_ascii);
				this.lastResult = new String(enc_ascii);

			}
			result = true;
		}
		catch (CertificateException e)
		{
			e.printStackTrace();
			this.lastErrMsg = "Error Number:-10016, Error Description: ER_CERT_PARSE_ERROR（证书解析错误）";
			result = false;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			this.lastErrMsg = "Error Number:-10005, Error Description: ER_FIND_CERT_FAILED（找不到证书）";
			result = false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.lastErrMsg = "Error Number:-10022, Error Description: ER_ENCRYPT_ERROR（加密失败）" + e.toString();
			result = false;
		}
		finally
		{
			try
			{
				if (!certfile.equals(null))
				{
					certfile.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
				this.lastErrMsg = "Error Number:-10030, Error Description: ER_CLOSEFILE_ERROR（证书文件关闭失败）";
				result = false;
			}

		}
		return result;
	}

	/**
	 * 对加密后的密文进行解密
	 * 
	 * @param TobeDecrypted
	 *            需要解密的密文
	 * @param KeyFile
	 *            PFX证书文件路径
	 * @param PassWord
	 *            私钥保护密码
	 * @return 解密成功返回true(从LastResult属性获取结果)，失败返回false(从LastErrMsg属性获取失败原因)
	 */
	public boolean DecryptMsg(final String TobeDecrypted, final String KeyFile, final String PassWord)
	{
		boolean result = false;
		FileInputStream fiKeyFile = null;
		try
		{
			KeyStore ks = KeyStore.getInstance("PKCS12");
			// ks.load(new FileInputStream(KeyFile), PassWord.toCharArray());
			fiKeyFile = new FileInputStream(KeyFile);
			ks.load(fiKeyFile, PassWord.toCharArray());
			Enumeration myEnum = ks.aliases();
			String keyAlias = null;
			RSAPrivateCrtKey prikey = null;
			// keyAlias = (String) myEnum.nextElement();
			/* IBM JDK必须使用While循环取最后一个别名，才能得到个人私钥别名 */
			while (myEnum.hasMoreElements())
			{
				keyAlias = (String) myEnum.nextElement();
				// System.out.println("keyAlias==" + keyAlias);
				if (ks.isKeyEntry(keyAlias))
				{
					prikey = (RSAPrivateCrtKey) ks.getKey(keyAlias, PassWord.toCharArray());
					break;
				}
			}
			if (prikey == null)
			{
				this.lastErrMsg = "Error Number:-10015, Error Description: ER_PRIKEY_CANNOT_FOUND（没有找到匹配私钥）";
				result = false;
			}
			else
			{
				// RSAPrivateCrtKey prikey = (RSAPrivateCrtKey)
				// ks.getKey(keyAlias,
				// PassWord.toCharArray());
				BigInteger mod = prikey.getModulus();
				int keylen = mod.bitLength() / 8;
				if (TobeDecrypted.length() > keylen * 2)
				{
					byte iv_asc[] = TobeDecrypted.substring(0, 16).getBytes(encoding);
					byte prikey_asc[] = TobeDecrypted.substring(iv_asc.length, iv_asc.length + keylen * 2).getBytes(encoding);
					byte enc_ascii[] = TobeDecrypted.substring(iv_asc.length + keylen * 2).getBytes();

					/*
					 * b_decin.read(iv_asc, 0, 16); b_decin.read(prikey_asc, 0,
					 * 256); b_decin.read(enc_ascii, 0, in_len - 16 - 256);
					 */
					byte iv[] = new byte[8];
					byte unwrappedkey[] = new byte[prikey_asc.length / 2];
					byte decrypted[] = new byte[enc_ascii.length / 2];

					Ascii2Hex(iv_asc.length, iv_asc, iv);
					Ascii2Hex(prikey_asc.length, prikey_asc, unwrappedkey);
					Ascii2Hex(enc_ascii.length, enc_ascii, decrypted);

					Cipher pri = Cipher.getInstance("RSA/NONE/PKCS1Padding", "BC");
					pri.init(Cipher.UNWRAP_MODE, prikey);
					Key unwrappedKey = pri.unwrap(unwrappedkey, "DESEDE", Cipher.SECRET_KEY);
					// 此类指定一个初始化向量 (IV)。使用 IV 的例子是反馈模式中的密码，如，CBC 模式中的 DES 和使用
					// OAEP
					// 编码操作的 RSA 密码
					IvParameterSpec ivspec = new IvParameterSpec(iv);
					pri = Cipher.getInstance("DESEDE/OFB/NoPadding");
					pri.init(Cipher.DECRYPT_MODE, unwrappedKey, ivspec);
					// byte decryptout[] = ;

					this.lastResult = new String(pri.doFinal(decrypted));

				}
				else
				{
					Cipher pri = Cipher.getInstance("RSA/NONE/PKCS1Padding", "BC");
					pri.init(Cipher.DECRYPT_MODE, prikey);
					byte enc_ascii[] = TobeDecrypted.getBytes(encoding);
					byte decrypted[] = new byte[enc_ascii.length / 2];
					Ascii2Hex(enc_ascii.length, enc_ascii, decrypted);
					byte decryptout[] = pri.doFinal(decrypted);

					this.lastResult = new String(decryptout);

				}
				result = true;
			}

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			this.lastErrMsg = "Error Number:-10005, Error Description: ER_FIND_CERT_FAILED（找不到证书）";
			result = false;
		}
		catch (UnrecoverableKeyException e)
		{
			e.printStackTrace();
			this.lastErrMsg = "Error Number:-10015, Error Description: ER_PRIKEY_CANNOT_FOUND（没有找到匹配私钥）";
			result = false;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			this.lastErrMsg = "Error Number:-10015, Error Description: ER_PRIKEY_CANNOT_FOUND（没有找到匹配私钥）";
			result = false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.lastErrMsg = "Error Number:-10023, Error Description: ER_DECRYPT_ERROR（解密失败）";
			result = false;
		}
		finally
		{
			try
			{
				if (!fiKeyFile.equals(null))
				{
					fiKeyFile.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				this.lastErrMsg = "Error Number:-10030, Error Description: ER_CLOSEFILE_ERROR（证书文件关闭失败）";
				result = false;
			}
		}
		return result;
	}

	/**
	 * 对字符串进行签名
	 * 
	 * @param TobeSigned
	 *            需要进行签名的字符串
	 * @param KeyFile
	 *            PFX证书文件路径
	 * @param PassWord
	 *            私钥保护密码
	 * @return 签名成功返回true(从LastResult属性获取结果)，失败返回false(从LastErrMsg属性获取失败原因)
	 */
	public boolean SignMsg(final String TobeSigned, final String KeyFile, final String PassWord)
	{
		boolean result = false;
		FileInputStream fiKeyFile = null;
		try
		{
			this.lastSignMsg = "";
			KeyStore ks = KeyStore.getInstance("PKCS12");
			// ks.load(new FileInputStream(KeyFile), PassWord.toCharArray());
			fiKeyFile = new FileInputStream(KeyFile);
			ks.load(fiKeyFile, PassWord.toCharArray());
			Enumeration myEnum = ks.aliases();
			String keyAlias = null;
			RSAPrivateCrtKey prikey = null;
			// keyAlias = (String) myEnum.nextElement();
			/* IBM JDK必须使用While循环取最后一个别名，才能得到个人私钥别名 */
			while (myEnum.hasMoreElements())
			{
				keyAlias = (String) myEnum.nextElement();
				// System.out.println("keyAlias==" + keyAlias);
				if (ks.isKeyEntry(keyAlias))
				{
					prikey = (RSAPrivateCrtKey) ks.getKey(keyAlias, PassWord.toCharArray());
					break;
				}
			}
			if (prikey == null)
			{
				this.lastErrMsg = "Error Number:-10015, Error Description: ER_PRIKEY_CANNOT_FOUND（没有找到匹配私钥）";
				result = false;
			}
			else
			{
				Signature sign = Signature.getInstance("SHA1withRSA");
				sign.initSign(prikey);
				sign.update(TobeSigned.getBytes(encoding));
				byte signed[] = sign.sign();
				byte sign_asc[] = new byte[signed.length * 2];
				Hex2Ascii(signed.length, signed, sign_asc);
				this.lastResult = new String(sign_asc);
				this.lastSignMsg = this.lastResult;
				result = true;
			}

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			this.lastErrMsg = "Error Number:-10005, Error Description: ER_FIND_CERT_FAILED（找不到证书）";
			result = false;
		}
		catch (UnrecoverableKeyException e)
		{
			e.printStackTrace();
			this.lastErrMsg = "Error Number:-10015, Error Description: ER_PRIKEY_CANNOT_FOUND（没有找到匹配私钥） | Exception:" + e.getMessage();
			result = false;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			this.lastErrMsg = "Error Number:-10015, Error Description: ER_PRIKEY_CANNOT_FOUND（没有找到匹配私钥） | Exception:" + e.getMessage();
			result = false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.lastErrMsg = "Error Number:-10020, Error Description: ER_SIGN_ERROR（签名失败）" + e.toString() + "| Exception:" + e.getMessage();
			result = false;
		}
		finally
		{
			try
			{
				if (!fiKeyFile.equals(null))
				{
					fiKeyFile.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				this.lastErrMsg = "Error Number:-10030, Error Description: ER_CLOSEFILE_ERROR（证书文件关闭失败）";
				result = false;
			}
		}
		return result;
	}

	/**
	 * 验证签名
	 * 
	 * @param TobeVerified
	 *            待验证签名的密文
	 * @param PlainText
	 *            待验证签名的明文
	 * @param CertFile
	 *            签名者公钥证书
	 * @return 验证成功返回true，失败返回false(从LastErrMsg属性获取失败原因)
	 */
	public boolean VerifyMsg(final String TobeVerified, final String PlainText, final String CertFile)
	{
		boolean result = false;
		FileInputStream certfile = null;
		try
		{
			certfile = new FileInputStream(CertFile);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate x509cert = (X509Certificate) cf.generateCertificate(certfile);
			RSAPublicKey pubkey = (RSAPublicKey) x509cert.getPublicKey();
			Signature verify = Signature.getInstance("SHA1withRSA");
			verify.initVerify(pubkey);
			byte signeddata[] = new byte[TobeVerified.length() / 2];
			Ascii2Hex(TobeVerified.length(), TobeVerified.getBytes(encoding), signeddata);
			verify.update(PlainText.getBytes(encoding));
			if (verify.verify(signeddata))
			{
				result = true;
			}
			else
			{
				this.lastErrMsg = "Error Number:-10021, Error Description:ER_VERIFY_ERROR（验签失败）";
				result = false;
			}

		}
		catch (CertificateException e)
		{
			e.printStackTrace();
			this.lastErrMsg = "Error Number:-10016, Error Description: ER_CERT_PARSE_ERROR（证书解析错误）";
			result = false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.lastErrMsg = "Error Number:-10021, Error Description: ER_VERIFY_ERROR（验签失败）";
			result = false;
		}
		finally
		{
			try
			{
				if (certfile!=null)
				{
					certfile.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
				this.lastErrMsg = "Error Number:-10021, Error Description: ER_VERIFY_ERROR（验签失败）";
				result = false;
			}

		}
		return result;
	}

	/**
	 * 返回上次调用加密、解密、签名函数成功后的输出结果
	 * 
	 * @return 返回上次调用加密、解密、签名函数成功后的输出结果
	 */
	public String getLastResult()
	{
		return this.lastResult;
	}

	/**
	 * 返回上次调用任何函数失败后的失败原因
	 * 
	 * @return 返回上次调用任何函数失败后的失败原因
	 */
	public String getLastErrMsg()
	{
		return this.lastErrMsg;
	}

	/**
	 * 返回上一次签名结果
	 * 
	 * @return 签名结果
	 */
	public String getLastSignMsg()
	{
		return this.lastSignMsg;
	}

	/**
	 * 将十六进制数据转换成ASCII字符串
	 * 
	 * @param len
	 *            十六进制数据长度
	 * @param data_in
	 *            待转换的十六进制数据
	 * @param data_out
	 *            已转换的ASCII字符串
	 */
	private static void Hex2Ascii(int len, byte data_in[], byte data_out[])
	{
		byte temp1[] = new byte[1];
		byte temp2[] = new byte[1];
		for (int i = 0, j = 0; i < len; i++)
		{
			temp1[0] = data_in[i];
			temp1[0] = (byte) (temp1[0] >>> 4);
			temp1[0] = (byte) (temp1[0] & 0x0f);
			temp2[0] = data_in[i];
			temp2[0] = (byte) (temp2[0] & 0x0f);
			if (temp1[0] >= 0x00 && temp1[0] <= 0x09)
			{
				(data_out[j]) = (byte) (temp1[0] + '0');
			}
			else if (temp1[0] >= 0x0a && temp1[0] <= 0x0f)
			{
				(data_out[j]) = (byte) (temp1[0] + 0x57);
			}

			if (temp2[0] >= 0x00 && temp2[0] <= 0x09)
			{
				(data_out[j + 1]) = (byte) (temp2[0] + '0');
			}
			else if (temp2[0] >= 0x0a && temp2[0] <= 0x0f)
			{
				(data_out[j + 1]) = (byte) (temp2[0] + 0x57);
			}
			j += 2;
		}
	}

	/**
	 * 将ASCII字符串转换成十六进制数据
	 * 
	 * @param len
	 *            ASCII字符串长度
	 * @param data_in
	 *            待转换的ASCII字符串
	 * @param data_out
	 *            已转换的十六进制数据
	 */
	private static void Ascii2Hex(int len, byte data_in[], byte data_out[])
	{
		byte temp1[] = new byte[1];
		byte temp2[] = new byte[1];
		for (int i = 0, j = 0; i < len; j++)
		{
			temp1[0] = data_in[i];
			temp2[0] = data_in[i + 1];
			if (temp1[0] >= '0' && temp1[0] <= '9')
			{
				temp1[0] -= '0';
				temp1[0] = (byte) (temp1[0] << 4);

				temp1[0] = (byte) (temp1[0] & 0xf0);

			}
			else if (temp1[0] >= 'a' && temp1[0] <= 'f')
			{
				temp1[0] -= 0x57;
				temp1[0] = (byte) (temp1[0] << 4);
				temp1[0] = (byte) (temp1[0] & 0xf0);
			}

			if (temp2[0] >= '0' && temp2[0] <= '9')
			{
				temp2[0] -= '0';

				temp2[0] = (byte) (temp2[0] & 0x0f);

			}
			else if (temp2[0] >= 'a' && temp2[0] <= 'f')
			{
				temp2[0] -= 0x57;

				temp2[0] = (byte) (temp2[0] & 0x0f);
			}
			data_out[j] = (byte) (temp1[0] | temp2[0]);

			i += 2;
		}

	}

	protected String replaceAll(String strURL, String strAugs)
	{

		// JDK1.3中String类没有replaceAll的方法
		/** ********************************************************** */
		int start = 0;
		int end = 0;
		String temp = new String();
		while (start < strURL.length())
		{
			end = strURL.indexOf(" ", start);
			if (end != -1)
			{
				temp = temp.concat(strURL.substring(start, end).concat("%20"));
				if ((start = end + 1) >= strURL.length())
				{
					strURL = temp;
					break;
				}

			}
			else if (end == -1)
			{
				if (start == 0)
					break;
				if (start < strURL.length())
				{
					temp = temp.concat(strURL.substring(start, strURL.length()));
					strURL = temp;
					break;
				}
			}

		}

		temp = "";
		start = end = 0;

		while (start < strAugs.length())
		{
			end = strAugs.indexOf(" ", start);
			if (end != -1)
			{
				temp = temp.concat(strAugs.substring(start, end).concat("%20"));
				if ((start = end + 1) >= strAugs.length())
				{
					strAugs = temp;
					break;
				}

			}
			else if (end == -1)
			{
				if (start == 0)
					break;
				if (start < strAugs.length())
				{
					temp = temp.concat(strAugs.substring(start, strAugs.length()));
					strAugs = temp;
					break;
				}
			}

		}

		/** **************************************************************** */
		return strAugs;
	}

}

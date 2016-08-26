package com.allinpay.security.pki;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.modes.OFBBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class PkiStore
{
	private byte[] iv = { 49, 50, 51, 52, 53, 54, 55, 56 };

	public void store(String targetFile, String password, RSAPrivateCrtKeyStructure priKeyStru) throws Exception
	{
		byte[] key = getKey(password.getBytes());

		File file = new File(targetFile);
		if (file.exists())
		{
			throw new Exception("file already exists: " + targetFile);
		}

		RSAPublicKeyStructure pubKeyStru = new RSAPublicKeyStructure(priKeyStru.getModulus(), priKeyStru.getPubExp());

		ByteArrayOutputStream baOut = new ByteArrayOutputStream();
		ObjectOutputStream oOut = new ObjectOutputStream(baOut);
		oOut.writeObject(priKeyStru);
		oOut.flush();
		byte[] plain = baOut.toByteArray();
		byte[] encrypted = encrypt(plain, key, this.iv);
		ByteArray ba = new ByteArray(encrypted);
		try
		{
			oOut = new ObjectOutputStream(new FileOutputStream(file));
			oOut.writeObject(pubKeyStru);
			oOut.writeObject(ba);
			oOut.flush();
		}
		catch (Exception e)
		{
			try
			{
				if (oOut != null)
					oOut.close();
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
			if ((file != null) && (file.exists()))
			{
				file.delete();
			}
			throw e;
		}
		finally
		{
			try
			{
				if (oOut != null)
					oOut.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public RSAPrivateCrtKeyStructure load(String targetFile, String password) throws Exception
	{
		byte[] key = getKey(password.getBytes());

		RSAPrivateCrtKeyStructure priKeyStru = null;

		File file = new File(targetFile);
		if (!(file.exists()))
		{
			throw new Exception("file doesn't exist: " + targetFile);
		}

		ObjectInputStream oIn = null;
		ByteArray ba = null;
		try
		{
			oIn = new ObjectInputStream(new FileInputStream(file));
			oIn.readObject();
			ba = (ByteArray) oIn.readObject();
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			try
			{
				if (oIn != null)
					oIn.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		byte[] encrypted = ba.getBytes();
		byte[] decrypted = decrypt(encrypted, key, this.iv);

		oIn = new ObjectInputStream(new ByteArrayInputStream(decrypted));
		priKeyStru = (RSAPrivateCrtKeyStructure) oIn.readObject();

		return priKeyStru;
	}
	public void store(String targetFile, RSAPublicKeyStructure pubKeyStru) throws Exception
	{
		ObjectOutputStream out = null;
		FileOutputStream fout=new FileOutputStream(targetFile);
		try
		{
			out = new ObjectOutputStream(fout);
			out.writeObject(pubKeyStru);
		}
		finally
		{
			fout.close();
			out.close();
		}
	}
	public RSAPublicKeyStructure load(String targetFile) throws Exception
	{
		RSAPublicKeyStructure pubKeyStru = null;

		File file = new File(targetFile);
		if (!(file.exists()))
		{
			throw new Exception("file doesn't exist: " + targetFile);
		}

		ObjectInputStream oIn = null;
		try
		{
			oIn = new ObjectInputStream(new FileInputStream(file));
			pubKeyStru = (RSAPublicKeyStructure) oIn.readObject();
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			try
			{
				if (oIn != null)
					oIn.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return pubKeyStru;
	}

	private byte[] encrypt(byte[] plain, byte[] key, byte[] iv) throws Exception
	{
		DESedeEngine desede = new DESedeEngine();
		BufferedBlockCipher bufferedBlockCipher = new BufferedBlockCipher(new OFBBlockCipher(desede, 8 * desede.getBlockSize()));

		byte[] encrypted = new byte[plain.length];
		CipherParameters desedeParams = new ParametersWithIV(new KeyParameter(key), iv);
		bufferedBlockCipher.init(true, desedeParams);
		int i = bufferedBlockCipher.processBytes(plain, 0, plain.length, encrypted, 0);
		bufferedBlockCipher.doFinal(encrypted, i);

		return encrypted;
	}

	private byte[] decrypt(byte[] encrypted, byte[] key, byte[] iv) throws Exception
	{
		DESedeEngine desede = new DESedeEngine();
		BufferedBlockCipher bufferedBlockCipher = new BufferedBlockCipher(new OFBBlockCipher(desede, 8 * desede.getBlockSize()));

		byte[] decrypted = new byte[encrypted.length];
		CipherParameters desedeParams = new ParametersWithIV(new KeyParameter(key), iv);
		bufferedBlockCipher.init(true, desedeParams);
		int i = bufferedBlockCipher.processBytes(encrypted, 0, encrypted.length, decrypted, 0);
		bufferedBlockCipher.doFinal(decrypted, i);

		return decrypted;
	}

	private byte[] getKey(byte[] src)
	{
		byte[] key = new byte[24];

		MD5Digest digest = new MD5Digest();
		digest.update(src, 0, src.length);
		digest.doFinal(key, 0);

		Arrays.fill(key, 16, 24, (byte) 0);

		return key;
	}
}
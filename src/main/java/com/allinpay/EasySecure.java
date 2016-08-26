package com.allinpay;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class EasySecure
{
	static public String rightPad(String str,int num,int ch)
	{
		StringBuffer sb=new StringBuffer(str);
		int count=num-sb.length();
		for(int i=0;i<count;++i) sb.append((char)ch);
		return sb.toString();
	}
	static public String right(String str,int num)
	{
		int len=str.length();
		len-=num;
		if(len>=0) str=str.substring(len);
		return str;
	}
	// 对PIN数据进行转换:根据文档的格式2进行：“0”+len+补F
	static public String pin(String userkey)
	{
		int len=userkey.length();
		return rightPad("0"+String.valueOf(len)+userkey,16,'F');
	}
	// 主帐号格式：P1  ---  P4     “0000”   P5  ---  P16     主帐号最右12位（不含校验位）
	static public String pan(String acct)
	{
		return "0000"+right(acct, 13).substring(0,12);
	}
	static public byte[] mixPin(String acct,String userkey)
	{
		byte[] mp;
		String pan,pin;
		pan=pan(acct);
		pin=pin(userkey);
		mp=xor(fromHex(pan),fromHex(pin));
		return mp;
	}
	static public byte[] xor(byte[] x1,byte[] x2)
	{
		int minLen=x1.length;
		if(minLen>x2.length) minLen=x2.length;
		byte[] ret=new byte[minLen];
		for(int i=0;i<minLen;++i)
		{
			ret[i]=x1[i];
			ret[i]^=x2[i];
		}
		return ret;
	}
	static public byte[] translate(byte[] data,Key fromK,Key toKey,String alm) throws Exception
	{
		return encrypt(toKey, decrypt(fromK, data, alm), alm);
	}
	/**
	 * 摘要
	 * @param 需要摘要的数据
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] digest(String alm,byte[] data) throws NoSuchAlgorithmException
	{
        MessageDigest md5 = MessageDigest.getInstance(alm);
        md5.update(data);
        return md5.digest();		
	}
	/**
	 * MD5摘要
	 * @param 需要摘要的数据
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] md5digest(byte[] data) throws NoSuchAlgorithmException
	{
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return md5.digest();		
	}
	/**
	 * DES加密
	 * @param key 密钥
	 * @param data 需要加密的数据
	 * @return 加密后的数据
	 */
	public static byte[] encrypt(Key key,byte[] data,String alm) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		Cipher cipher = Cipher.getInstance(alm);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(data);
	}
	/**
	 * DES解密
	 * @param key 密钥
	 * @param data 需要解密的数据
	 * @return
	 */
	public static byte[] decrypt(Key key,byte[] data,String alm) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		Cipher cipher = Cipher.getInstance(alm);
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(data);
	}
	/**
	 * 辅助函数，把二进制变成16进制字符串
	 */
	public static String toHex(byte[] bb)
	{
		int i;
		if(bb==null) return "<null>";
		StringBuffer sb = new StringBuffer(128);
		for (i = 0; i < bb.length; ++i)
		{
			sb.append(Character.toUpperCase(Character.forDigit((bb[i] >> 4) & 0xF, 16)));
			sb.append(Character.toUpperCase(Character.forDigit(bb[i] & 0xF, 16)));
		}
		return sb.substring(0);
	}
	/**
	 * 辅助函数，把16进制字符串变成二进制
	 */
	public static byte[] fromHex(String s)
	{
		int i, len;
		byte[] br;
		len = s.length() / 2;
		br = new byte[len];
		for (i = 0; i < len; ++i)
		{
			br[i] = (byte) ((Character.digit(s.charAt(i * 2), 16) << 4)&0xF0 | (Character
					.digit(s.charAt(i * 2 + 1), 16))&0xF);
		}
		return br;
	}
	/**
	 * 从二进制(一定要8字节)生成密钥对象
	 */
	public static Key evalKeyHex(String hex,String alm)
	{
		return evalKey(fromHex(hex),alm);
	}
	/**
	 * 从二进制(一定要8字节)生成密钥对象
	 */
	public static Key evalKey(byte [] keyByte,String alm)
	{
		if(alm.equals("DESede")&&keyByte.length==16)
		{
			byte[] key24=new byte[24];
			System.arraycopy(keyByte, 0,key24,0,16);
			System.arraycopy(keyByte, 0,key24,16,8);
			keyByte=key24;
		}
		SecretKeySpec deskey = new SecretKeySpec(keyByte, alm);
		return deskey;
	}
	/**
	 * 从字符串生成密钥对象
	 */
	public static Key evalKey(String strKey,String alm) throws NoSuchAlgorithmException
	{
		KeyGenerator _generator = KeyGenerator.getInstance(alm);
		_generator.init(new SecureRandom(strKey.getBytes()));
		return _generator.generateKey();
	}
	/**
	 * 演示MD5摘要和三种不同形式的同一密钥加密
	 * @throws Exception
	 */
	public static void testShow() throws Exception
	{
		String dataStr="12345678";
		String keyStr="abcdefg";            //字符串密钥
		String keyHex="DCB6DF5EFD83C440";   //16进制字符串密钥
		byte keyBy[]={-36, -74, -33, 94, -3, -125, -60, 64}; //二进制密钥
		byte dataBy[]=dataStr.getBytes("UTF-8");
		byte md5[],des1[],des2[],des3[];
		Key  k1,k2,k3;
		k1=evalKey(keyStr,"DES");
		k2=evalKey(keyBy,"DES");
		k3=evalKey(fromHex(keyHex),"DES");
		
		des1=encrypt(k1, dataBy,ALM_NOPAD);
		des2=encrypt(k2, dataBy,ALM_NOPAD);
		des3=encrypt(k3, dataBy,ALM_NOPAD);
		md5=md5digest(dataBy);
		
		//System.out.println("Key="+Arrays.toString(k1.getEncoded()));
		System.out.println("KeyHex="+toHex(keyBy));
		System.out.println("PlainText="+dataStr);
		System.out.println("---------------------------------------");
		
		System.out.println("MD5="+toHex(md5));
		System.out.println("DES1="+toHex(des1));
		System.out.println("DES2="+toHex(des2));
		System.out.println("DES3="+toHex(des3));
	}
	public static void main(String[] args) throws Exception
	{
		digest("SHA-1", "12321321".getBytes());
		//testShow();
	}
	public final static String ALM_NOPAD="DES/ECB/NoPadding";
	public final static String ALM_PAD="DES";
	private final static String KEY_ALM="DES";
}


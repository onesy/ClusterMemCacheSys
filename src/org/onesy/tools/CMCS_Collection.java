package org.onesy.tools;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CMCS_Collection {

	/**
	 * toString() 将会被调用
	 * @param src
	 * @return
	 */
	public static <T> byte[] calculateMD5(T src){
		String SRCStr = src.toString();
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		md5.update(SRCStr.getBytes());
		byte[] result = md5.digest();
		return result;
	}
	/**
	 * 将byte数组转化为BigInteger
	 * @param bytes
	 * @return
	 */
	public static BigInteger byteToBigInteger(byte[] bytes){
		return new BigInteger(bytes);
	}
	/**
	 * 以BigInteger的形式取得一个对象的MD5码的绝对值
	 * 对象<T> magic必须实现了toString()方法
	 * @param magic
	 * @return
	 */
	public static <T> BigInteger getMD5AbsBigInteger(T magic) {
		return byteToBigInteger(calculateMD5(magic)).abs();
	}
}

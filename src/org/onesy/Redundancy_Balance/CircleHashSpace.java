package org.onesy.Redundancy_Balance;

import java.math.BigInteger;
import java.util.ArrayList;

import org.onesy.tools.Collections;

public class CircleHashSpace {

	private static CircleHashSpace circleHashSpace = null;

	private static ArrayList<String> MagicArray = null;

	private CircleHashSpace() {
		CircleHashSpace.setMagicArray(new ArrayList<String>());
	}

	private CircleHashSpace(ArrayList<String> magicArray) {
		CircleHashSpace.setMagicArray(magicArray);
	}

	public synchronized static CircleHashSpace getInstance() {
		if (CircleHashSpace.circleHashSpace == null)
			CircleHashSpace.circleHashSpace = new CircleHashSpace();
		return CircleHashSpace.circleHashSpace;
	}

	public synchronized static CircleHashSpace getInstance(
			ArrayList<String> magicArray) {
		if (CircleHashSpace.circleHashSpace == null
				|| CircleHashSpace.circleHashSpace
						.equals(new CircleHashSpace()))
			CircleHashSpace.circleHashSpace = new CircleHashSpace(magicArray);
		return CircleHashSpace.circleHashSpace;
	}

	private static void checkInitial() {
		if (MagicArray == null) {
			MagicArray = new ArrayList<String>();
		}
	}

	public static ArrayList<String> getMagicArray() {
		checkInitial();
		return MagicArray;
	}

	public static void setMagicArray(ArrayList<String> magicArray) {
		MagicArray = Sort(magicArray);
	}

	public static void setMagicArray(String[] magicArray) {
		MagicArray = Sort(magicArray);
	}

	/**
	 * 随便写个插入吧，别整复杂了，时间不多，时间以后再来整,反正节点也不多。。。对吧？ 添加传入的数组，并且排序
	 * 
	 * @param magicArray
	 * @return
	 * @return
	 */
	private static ArrayList<String> Sort(ArrayList<String> magicArray) {
		for (String magic : magicArray) {
			add(magic);
		}
		return MagicArray;
	}

	private static ArrayList<String> Sort(String[] magicArray) {
		for (String magic : magicArray) {
			add(magic);
		}
		return MagicArray;
	}

	protected static void add(String magic) {
		checkInitial();
		if (MagicArray.size() == 0) {
			Collections.getMD5AbsBigInteger(magic);
			MagicArray.add(0, Collections.getMD5AbsBigInteger(magic).toString());
			return;
		}
		for (int i = 0; i < MagicArray.size(); i++) {
			if (Collections.getMD5AbsBigInteger(magic)
					.compareTo(new BigInteger(MagicArray.get(i))) < 0) {
				MagicArray.add(i, Collections.getMD5AbsBigInteger(magic).toString());
				return;
			}
		}
		MagicArray.add(Collections.getMD5AbsBigInteger(magic).toString());
	}

	/**
	 * @overload
	 * @param magic
	 */
	protected static void add(int magic) {
		add(magic + "");
	}

	protected static void add(BigInteger magic) {
		add(magic.toString());
	}

	protected static void add(Integer magic) {
		add(magic.toString());
	}

	protected static boolean del(String magic) {
		checkInitial();
		return MagicArray.remove(magic);
	}

	protected static boolean del(Integer magic) {
		return del(magic.toString());
	}

	protected static boolean del(BigInteger magic) {
		return del(magic.toString());
	}

	protected static boolean del(int magic) {
		return del(magic + "");
	}

	/**
	 * 需要更改为的magic必须不存在 这个函数不会容忍存在相同的magic key
	 * 
	 * @param srcMagic
	 * @param targetMagic
	 * @return
	 */
	protected static boolean modify(String srcMagic, String targetMagic) {
		checkInitial();
		if (!MagicArray.contains(targetMagic) && MagicArray.remove(srcMagic)) {
			add(targetMagic);
			return true;
		} else {
			return false;
		}
	}

	protected static <T> ArrayList<Boolean> modify(ArrayList<T> srcMagics,
			ArrayList<T> targetMagics) {
		ArrayList<Boolean> result = new ArrayList<Boolean>();
		for (int i = 0; i < srcMagics.size(); i++) {
			result.add(
					i,
					modify(srcMagics.get(i).toString(), targetMagics.get(i)
							.toString()));
		}
		return result;

	}

	protected static <T> String Search4Magic(T userMagic) {
		for (int i = 0; i < MagicArray.size(); i++) {
			if (new BigInteger(Collections.calculateMD5(userMagic))
					.abs().compareTo(new BigInteger(MagicArray.get(i)).abs()) <= 0) {
				return MagicArray.get(i);
			}
		}
		return MagicArray.get(0);
	}

	/*
	 * public static BigInteger calculateMD5(String magic) { String SRCStr =
	 * magic; MessageDigest md5 = null; try { md5 =
	 * MessageDigest.getInstance("MD5"); } catch (NoSuchAlgorithmException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); }
	 * md5.update(SRCStr.getBytes()); byte[] result = md5.digest(); BigInteger
	 * bd = new BigInteger(result); return bd; }
	 */

}

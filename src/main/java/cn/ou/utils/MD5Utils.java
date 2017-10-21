package cn.ou.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5算法加密
 * @author Administrator
 *
 */
public class MD5Utils { 
	/**
	 * 使用MD5的算法进行加密
	 * @param plainText ：传入需要加密的字符串
	 * @return 返回一个加密后的字符串
	 */
	public static String md5(String plainText){
		if(plainText != null){
			//存放哈希值结果的byte数组
			byte[] secretBytes = null;
			try {
				//getInstance("md5"):返回实现指定摘要算法的 MessageDigest 对象
				//digest(byte[] ..)使用指定的 byte 数组对摘要进行最后更新，然后完成摘要计算
				secretBytes = MessageDigest.getInstance("MD5").digest(plainText.getBytes());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				throw new RuntimeException("没有md5这个算法！");
			}
			String md5Code = new BigInteger(1, secretBytes).toString(16);
			for (int i = 0; i < 32 - md5Code.length(); i++) {
				md5Code = "0" + md5Code;
			}
			return md5Code;
		}else {
			return null;
		}
	}

}

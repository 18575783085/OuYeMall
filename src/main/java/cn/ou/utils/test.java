package cn.ou.utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * 测试两种加密方法
 * @author Administrator
 *
 */
public class test {
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		//输入密码
		String password = "123";
		
		//把密码字符串放进加密方法
		String encryptedPwd = MDUtil.getEncryptedPwd(password);
		
		//输入确认密码
		String password2 = "123";
		
		//进行密码匹配
		boolean validPassword = MDUtil.validPassword(password2, encryptedPwd);
		
		//使用老师的加密算法
		String md5 = MD5Utils.md5(password);
		System.out.println("老师提供的md5算法："+md5);
		System.out.println("自己找到的md5算法："+encryptedPwd);
		System.out.println(encryptedPwd.length());
		System.out.println(validPassword);
		
	}
}

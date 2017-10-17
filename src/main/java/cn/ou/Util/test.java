package cn.ou.Util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * 测试加密
 * @author Administrator
 *
 */
public class test {
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String password = "123456";
		
		//把密码字符串放进加密方法
		String encryptedPwd = MDUtil.getEncryptedPwd(password);
		
		System.out.println(encryptedPwd);
		String password2 = "123123";
		
		boolean validPassword = MDUtil.validPassword(password2, encryptedPwd);
		System.out.println(encryptedPwd.length());
		System.out.println(validPassword);
		
	}
}

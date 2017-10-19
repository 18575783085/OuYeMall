package cn.ou.factory;

import cn.ou.dao.UserDao;
import cn.ou.utils.PropUntils;

/**
 * Dao的UserDao工厂类
 * @author Administrator
 *
 */
public class UserDaoFactory {
	private UserDaoFactory(){}
	
	/**
	 * 获取Dao的工厂类对象
	 * 单例模式 --- 懒汉式
	 * @return 每调用获取工厂类方法就创建Dao工厂类
	 */
	public static UserDaoFactory getDaoFactory(){
		return new UserDaoFactory();
	}
	
	/**
	 * 通过getInstance方法来获取配置文件对应的参数的类字节码文件
	 * @return 类字节码文件--->UserDaoImpl Java文件
	 */
	public UserDao getInstance(){
		//获取配置文件中对应的value值参数
		String value = PropUntils.getProperty("UserDao");
		try {
			//通过加载类字节文件来获取该对象的实例
			Class clz = Class.forName(value);
			
			return (UserDao) clz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
}

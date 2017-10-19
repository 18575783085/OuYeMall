package cn.ou.factory;

import java.util.Properties;

import cn.ou.service.UserService;
import cn.ou.utils.PropUntils;

/**
 * 业务层的UserService工厂类
 * @author Administrator
 *
 */
public class UserServiceFactory {
	private UserServiceFactory(){}
	/**
	 * 获取业务层的工厂类对象
	 * 单例模式---懒汉式
	 * @return 每调用获取工厂类方法就创建业务层工厂实例
	 */
	public static UserServiceFactory getServiceFactory(){
		return new UserServiceFactory();
	}
	
	/**
	 * 通过getInstance方法来获取配置文件对应的参数的类字节码文件
	 * @return 类字节码文件 -----> UserServiceImpl Java文件
	 */
	public UserService getInstance(){
		//缺点：每次调用getInstance()都会加载一次config.properties文件，效率太低。
		//创建属性文件的工具类，不管调用几次，应该只加载一次配置文件
		/*Properties p = new Properties();
		p.load(inStream);
		return null;*/
		
		//获取userService --> cn.ou.service.impl.UserServiceImpl
		//获取配置文件中对应的value值参数
		String className = PropUntils.getProperty("UserService");
		
		try {
			//通过加载类字节文件来获取该对象的实例
			Class clz = Class.forName(className);
			
			return (UserService)clz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
}

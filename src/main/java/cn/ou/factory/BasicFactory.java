package cn.ou.factory;

import cn.ou.Util.PropUntils;

/**
 * 通用工厂类
 * @author Administrator
 *
 */
public class BasicFactory {
	//私有化构造方法
	private BasicFactory(){}
	
	/**
	 * 调用获取通用工厂类的实例
	 * @return 返回一个通用工厂类实例
	 */
	public static BasicFactory getBasicFactory(){
		return new BasicFactory();
	}
	
	/**
	 * 调用加载properties配置文件中获取相应的key对应的value值
	 * @param key : 要获取对应的value值
	 * @return 通过获取对应的value值参数来返回
	 * 修改数据类型--->泛型
	 * T:UserDao
	 * Class<T>:UserDao.class
	 */
	public  <T> T getInstance(Class<T> intfClz){
		//获取配置文件中对应的参数
		//String value = PropUntils.getProperty(key);
		/*
		 * UserDao.class -> "UserDao"
		 * getName() -> "cn.ou.dao.UserDaoImpl"
		 * getSimpleName() -> "UserDao"
		 */
		String intfName = intfClz.getSimpleName();
		
		//intfName:UserDao
		//获取UserDao在config.properties文件中对应实现类的包名.类名
		String value = PropUntils.getProperty(intfName);
		
		try {
			//通过加载类字节文件来获取该对象的实例
			Class clz = Class.forName(value);
			//clz创建对象
			return (T)clz.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
 			e.printStackTrace();
		}
		return null;
	}
}

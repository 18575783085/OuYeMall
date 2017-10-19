package cn.ou.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 加载并获取properties配置文件
 * @author Administrator
 *
 */
public class PropUntils {
	/**
	 * 创建properties类对象
	 */
	private static Properties p = new Properties();
	
	//将构造方法私有化（单例模式）
	private PropUntils(){}
	
	static{
		//获取properties配置所在web项目的classes文件夹路径
		//获取该文件的完整路径名称（路径+文件名称+后缀名称）
		//TODO 为啥这个路径就能读取：PropUntils.class.getClassLoader().getResourceAsStream("config.properties")
		/*
		 * 注意：tomcat的安装路径尽量不要有中文或者空格，否则不能使用下面的获取路径方法
		 * String path = PropUntils.class.getClassLoader().getResource("config.properties").getPath();
		 */
		try {
			//加载并获取配置文件
			//p.load(new FileInputStream(path));
			p.load(PropUntils.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取properties文件的对象
	 * @return 配置文件对象
	 */
	public static Properties getProp(){
		return p;
	}

	/**
	 * 获取properties配置文件中内容
	 * @param key ： 配置文件中的key值
	 * @return 对应key的value值
	 */
	public static String getProperty(String key){
		return p.getProperty(key);
	}
}

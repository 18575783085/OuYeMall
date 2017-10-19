package cn.ou.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 实现类--->返回结果集
 * @author Administrator
 *
 * @param <T>
 */
public class BeanHandler<T> implements ResultSetHandler<T> {
	private Class<T> clz;
	
	public BeanHandler(Class<T> clz){
		this.clz = clz;
	}
	
	//rs --> T t
	public T handler(ResultSet rs) throws SQLException {
		try{
			
			//1.声明对象
			T t = null;
			//3.判断rs中是否有数据
			if(rs.next()){//有数据
				//4.创建对象
				t = clz.newInstance();
				
				//5.为 t 对象属性赋值（值来至于rs）
				//获取字段的值：t.setXxx(rs.getXxx("属性的名称"))
				//5.1.获取clz对应的BeanInfo对象(BeanInfo本身不能实例化，只能通过"别人"来调用创建)
				BeanInfo bInfo = Introspector.getBeanInfo(clz);
				
				//5.2.获取clz对应类的所有的属性方法对应的PropertyDescriptor数组
				PropertyDescriptor[] pds = bInfo.getPropertyDescriptors();
				
				//5.3.遍历数组，为t的每一个属性复制
				for (int i = 0; i < pds.length; i++) {
					PropertyDescriptor pd = pds[i];
					
					//5.4.获取属性的名称eg："username"
					String name = pd.getName();
					
					//5.5.获取属性对应的setXxx()方法
					Method setMethod = pd.getWriteMethod();
					
					//5.6.执行set方法
					//username(User类属性名)--->username(user表中字段名)
					//例如：setUserName(rs.getString("Username"))
					
					try{
						setMethod.invoke(t, rs.getObject(name));
					} catch (SQLException e) {
						/*
						 * 这个捕捉异常是因为当这个类没有继承父类时，默认继承Object类，并顺带获取了class属性
						 * 所以当出现异常捕捉后继续让程序往下走
						 */
						continue;
					}
				}
			}
			//2.返回对象
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

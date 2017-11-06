package cn.ou.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 模拟实现DBUtils中的query方法查询多条语句
 * @author Administrator
 *	
 *	List<User> list = query(sql,new BeanListHandler<User>(User.class))
 *	
 *	测试方法请移步到--->DaoUtils
 */
public class BeanListHandler<T> implements ResultSetHandler<List<T>>{

	private Class<T> clz;
	
	public BeanListHandler(Class<T> clz){
		this.clz = clz;
	}
	@Override
	public List<T> handler(ResultSet rs) throws SQLException {
		List<T> list = new ArrayList<T>();
		
		try {
			while(rs.next()){
				//创建对象
				T t = clz.newInstance();
				BeanInfo bInfo =Introspector.getBeanInfo(clz);
				PropertyDescriptor[] pds = bInfo.getPropertyDescriptors();
				for (int i = 0; i < pds.length; i++) {
					PropertyDescriptor pd = pds[i];
					//获取属性名称
					String name = pd.getName();
					//获取set方法
					Method setMethod = pd.getWriteMethod();
					try {
						Object obj = null;
						/*
						 * 由于BigDecimal和Integer类都是Number类子类，所以他们两个既不能自动类型也不能强制类型转换。
						 */
						if(pd.getPropertyType() == int.class){
							obj = rs.getInt(name);
						}else {
							obj = rs.getObject(name);
						}
						//执行set方法
						setMethod.invoke(t, obj);
					
					} catch (SQLException e) {
						continue;
					}
				}
				//添加到集合中
				list.add(t);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}

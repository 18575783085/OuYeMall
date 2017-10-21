package cn.ou.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 模拟Dbutils中的ResultSetHandler接口的底层
 * @author Administrator 
 *
 */
public interface ResultSetHandler<T> {
	//功能：把 rs 中的数据 -> T t 或者 List<T>list
	T handler(ResultSet rs) throws SQLException;
}

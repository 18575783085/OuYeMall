package cn.ou.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 数据库工具类
 * @author Administrator
 *
 */
public class JDBCUtils {
	private JDBCUtils(){}
	
	private static ComboPooledDataSource cpds = new ComboPooledDataSource();
	
	
	/**
	 * 获取连接池
	 * @return 获取数据库连接
	 * @throws SQLException  获取连接失败
	 */
	public static Connection getConnection() throws SQLException{
		return cpds.getConnection();
	}
	
	/**
	 * 关闭资源
	 * @param conn 归还数据库连接
	 * @param statement 关闭传输器
	 * @param rs 关闭返回结果集
	 */
	public static void close(Connection conn,Statement statement,ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				rs = null;
			}
		}
		
		if(statement != null){
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				statement = null;
			}
		}
		
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				conn = null;
			}
		}
	}
}

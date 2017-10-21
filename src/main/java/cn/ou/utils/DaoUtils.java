package cn.ou.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import cn.ou.entity.User;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 数据库工具类 
 * @author Administrator
 *
 */
public class DaoUtils {
	private DaoUtils(){}
	
	private static ComboPooledDataSource cpds = new ComboPooledDataSource();
	
	/**
	 * 获取连接池
	 * @return
	 */
	public static ComboPooledDataSource getPool(){
		return cpds;
	}
	
	/**
	 * 获取连接池连接
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
	
	/* --------------------------我是分割线----------------------------*/
	/**
	 * 模仿创建实现DBUtils的query和update方法
	 */
	
	/**
	 * 添加、修改、删除都调用该方法
	 * @param sql 数据库语句
	 * @param params 为占位符赋值的参数（对象数组）
	 * @return 影响的行数
	 * @throws SQLException
	 */
	public static int update(String sql,Object... params) throws SQLException{
		
		//1.声明数据连接相关对象
		Connection conn = null;
		PreparedStatement ps = null;
		
		try{
			//2.获取数据库连接
			conn = getConnection();
			
			//3.创建预编译传输器，预编译sql语句，并返回ps对象
			ps = conn.prepareStatement(sql);
			
			//4.为占位符赋值
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i+1, params[i]);
			}
			/*for (int i = 1; i < params.length; i++) {
				ps.setObject(i, params[i-1]);
			}*/
			
			//5.执行操作（添加/修改/删除）,并返回影响的行数
			int executeUpdateRow = ps.executeUpdate();
			
			//6.有影响行数表示（添加/修改/删除）成功，反之则失败
			return executeUpdateRow;
		} catch (SQLException e) {
			//7.继续抛出异常
			throw e;
		} finally{
			//8.关闭数据库连接相关对象
			DaoUtils.close(conn, ps, null);
		}
	}
	
	/**
	 * 查询方法(即可以查一条数据，也可以查多条数据)
	 * @param sql ：sql语句
	 * @param rsh ：查一条数据 new BeanHandler<T>(T.class)
	 * @param params : 查询条件的查询
	 * @return 查询一条返回T t；查多条返回List<T>
	 * @throws SQLException 
	 */
	public static <T>T query(String sql,ResultSetHandler<T>rsh,Object... params) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			//1.获取数据库连接
			conn = getConnection();
			
			//2.预编译sql语句并返回ps
			ps = conn.prepareStatement(sql);
			
			//3.为占位符赋值
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i+1, params[i]);
			}
			
			//4.执行查询操作，并返回结果集
			rs = ps.executeQuery();
			
			//5. rs --> T t 或 List<T>（有可能返回一条数据或者多条数据）
			//调用BeanHandler实现类中的handler方法
			return rsh.handler(rs);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally{
			//6.关闭数据库相关连接
			DaoUtils.close(conn, ps, rs);
		}
	}
	
	public static void main(String[] args) {
		//测试查询多条数据
		String sql = "select * from user";
		try {
			List<User> list = query(sql, new BeanListHandler<User>(User.class));
			for(User user:list){
				//获取rs结果集参数
				int id = user.getId();
				String username = user.getUsername();
				String password = user.getPassword();
				String nickname = user.getNickname();
				String email = user.getEmail();
				String phone = user.getPhone();
				System.out.println(username+","+password+","+nickname+","+email+","+phone);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

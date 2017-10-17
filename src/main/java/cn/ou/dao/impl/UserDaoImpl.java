package cn.ou.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.ou.Util.DaoUtils;
import cn.ou.dao.UserDao;
import cn.ou.entity.User;
/**
 * Dao的实现类
 * 主要负责执行SQL的CURD指令
 * @author Administrator
 *
 */
public class UserDaoImpl implements UserDao {

	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	/**
	 * 用户登录
	 */
	public User login(String username, String password) {
		
		try {
			//1.获取数据库连接
			conn = DaoUtils.getConnection();
			//2.编写sql语句
			String sql = "select * from user where username=? and password=?";
			//3.预编译sql。并返回ps
			ps = conn.prepareStatement(sql);
			//4.为占位符赋值
			ps.setString(1, username);
			ps.setString(2, password);
			//5.执行查询操作，并返回结果集
			rs = ps.executeQuery();
			
			//6.判断结果集是否存在数据
			if(rs.next()){//7.该用户存在
				//创建用户实例对象
				User user = new User();
				//获取用户信息
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setNickname(rs.getString("nickname"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				
				//返回user对象
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally{
			//9.关闭数据库对象
			DaoUtils.close(conn, ps, rs);
		}
		
		//8.不存在返回null
		return null;
	}

	/**
	 * 判断用户是否存在
	 */
	public boolean unIsExist(String username) {
		try {
			//1.获取数据库连接
			conn = DaoUtils.getConnection();
			
			//2.创建sql语句
			String sql = "select * from user where username=?";
			
			//3.创建传输器，预编译sql，并返回rs结果
			ps = conn.prepareStatement(sql);
			
			//4.为占位符赋值
			ps.setString(1, username);
			
			//5.执行查询操作，并返回结果集
			rs = ps.executeQuery();
			
			//6.判断结果集是否存在数据,并返回结果
			//存在则true
			return rs.next();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtils.close(conn, ps, rs);
		}
		return false;
	}

}

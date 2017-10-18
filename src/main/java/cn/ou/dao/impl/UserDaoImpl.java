package cn.ou.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

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
			/*//1.获取数据库连接
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
				return user;*/
			
			
			/*
			 *TODO 版本1：导入dbutils-jar包，使用dbutils接口进行修改简化CURDsql语句
			 */
			//1.创建QueryRunner对象
			QueryRunner qr = new QueryRunner(DaoUtils.getPool());
			
			//2.编写sql语句
			String sql = "select * from user where username=? and password=?";
			
			//3.执行查询操作，并返回User类对象
			return qr.query(sql, new BeanHandler<User>(User.class),username,password);
			
			/*
			 * TODO 版本2：使用自己开发的接口实现类（重点理解dbutils接口的底层实现原理）
			 */
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		//8.不存在返回null
		return null;
	}

	/**
	 * 判断用户是否存在
	 */
	public boolean unIsExist(String username) {
		try {
			/*//1.获取数据库连接
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
			return rs.next();*/
			
			//1.创建QueryRunner对象
			QueryRunner qr = new QueryRunner(DaoUtils.getPool());
			//2.编写sql语句
			String sql = "select * from user where username=?";
			//3.查询返回结果，并处理结果
			return qr.query(sql, new BeanHandler<User>(User.class), username) != null;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return false;
	}

	/**
	 * 用户注册
	 * 添加一条数据
	 */
	public int regist(User user) {
		try {
			/*//1.获取数据库连接
			conn = DaoUtils.getConnection();
			//2.创建sql语句
			String sql = "insert into user" +
					"(username,password,nickname,email,phone) " +
					"values" +
					"(?,?,?,?,?)";
			//3.创建传输器，预编译sql
			ps = conn.prepareStatement(sql);
			//4.为占位赋值
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getNickname());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getPhone());
			//5.执行查询操作，并返回结果集
			int row = ps.executeUpdate();
			//6.返回执行结果，有行数则为插入成功，反之则失败
			return row;*/
			
			//1.创建QueryRunner对象
			QueryRunner qr = new QueryRunner(DaoUtils.getPool());
			
			//2.编写sql语句
			String sql = "insert into user"
					+ "(username,password,nickname,email,phone)"
					+ "values "
					+ "(?,?,?,?,?)";
			
			//3.查询返回执行结果，有行数则为插入成功，反之则失败
			return qr.update(sql, user.getUsername(),user.getPassword(),user.getNickname(),user.getEmail(),user.getPhone());
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

}

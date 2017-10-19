package cn.ou.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.ou.dao.UserDao;
import cn.ou.entity.User;
import cn.ou.utils.DaoUtils;
//import cn.ou.Util.BeanHandler;
/**
 * Dao的实现类
 * 主要负责执行SQL的CURD指令
 * @author Administrator
 */
public class UserDaoImpl implements UserDao {

	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	/**
	 *	现在出现一个问题:太多重复执行sql语句的步骤，从而造成给人感觉沉冗繁琐
	 *	解决问题：用一些简单方法来对这些sql代码进行封装，增强代码的阅读性
	 *	版本1：使用commons-dbutils(jar包)提供的一些接口来对Dao实现类中CURD进行封装
	 *	版本2：使用自己开发的接口实现类(重点理解dbutils的query和update底层原理)
	 */
	
	
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
				return user;
			}*/
			
			/*版本1*/
			//1.创建QueryRunner对象--->获取连接池对象
			QueryRunner qr = new QueryRunner(DaoUtils.getPool());
			//2.编写sql语句
			String sql = "select * from user where username=? and password=?";
			//3.调用dbutils中的查询方法(query())
			//我的理解：query(参数1，参数2，参数3)--->参数1:传入sql语句--->参数2:ResultSetHandler接口里面的子接口方法--->参数3:为占位符赋值
			User query = qr.query(sql, new BeanHandler<User>(User.class),username,password);
			//4.存在则返回一个用户对象
			return query;
			
			/*版本2*/
			/*//1.编写sql语句
			String sql = "select * from username where username=? and password=?";
			//2.调用query方法来执行sql语句，并返回结果
			User query = DaoUtils.query(sql, new BeanHandler<User>(User.class), username,password);
			//3.存在则返回一个用户对象
			return query;*/
			
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
			
			/*版本1*/
			//1.创建QueryRunner对象---->获取连接池对象
			QueryRunner qr = new QueryRunner(DaoUtils.getPool());
			//2.编写sql语句
			String sql = "select * from user where username=?";
			//3.执行sql语句，并返回结果
			User query = qr.query(sql, new BeanHandler<User>(User.class), username);
			//4.有结果表示该用户已存在----->反之返回不存在
			return query != null;
			
			/*版本2*/
			/*//1.编写sql语句
			String sql = "select * from user where username=?";
			//2.执行sql语句，并返回结果
			User query = DaoUtils.query(sql, new BeanHandler<User>(User.class), username);
			//3.有结果表示该用户已存在---->反之返回不存在
			return query != null;*/
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		//不存在
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
			//4.为占位符赋值
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getNickname());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getPhone());
			//5.执行查询操作，并返回结果集
			int row = ps.executeUpdate();
			//6.返回执行结果，有行数则为插入成功，反之则失败
			return row;*/
			
			/*版本1*/
			//1.创建QueryRunner对象--->获取连接池对象
			QueryRunner qr = new QueryRunner(DaoUtils.getPool());
			//2.编写sql语句
			String sql = "insert into user " +
					"(username,password,nickname,email,phone)" +
					"values" +
					"(?,?,?,?,?)";
			//3.执行sql语句并返回结果
			//update(参数1，参数2)--->参数1：传入sql语句--->参数2：为占位符赋值
			int updateRow = qr.update(sql, user.getUsername(),user.getPassword(),user.getNickname(),user.getEmail(),user.getPhone());
			//4.返回结果(影响行数)--->有影响行数表示插入成功-->反之则插入失败
			return updateRow;
			
			/*版本2*/
			/*//1.编写sql语句
			String sql = "insert into user " +
					"(username,password,nickname,email,phone)" +
					"values" +
					"(?,?,?,?,?)";
			//2.执行sql语句并返回结果
			int updateRow = DaoUtils.update(sql, user.getUsername(),user.getPassword(),user.getNickname(),user.getEmail(),user.getPhone());
			
			//3.返回结果(影响行数)--->有影响行数表示插入成功-->反之则插入失败
			return updateRow;*/
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} 
	}

	/*--------------------------------------------------------------*/
	
	/**
	 * 检查手机号码是否存在
	 */
	public boolean checkPhone(String phone) {
		/*使用版本1*/
		try {
			//1.创建QuerRunner对象--->获取数据库对象
			QueryRunner qr = new QueryRunner(DaoUtils.getPool());
			
			//2.编写sql语句
			String sql = "select * from user where phone=?";
			
			//3.执行sql语句，并返回结果集
			User query = qr.query(sql, new BeanHandler<User>(User.class),phone);
			
			//4.如果结果集不等于null则为存在，
			return query != null;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//不存在
		return false;
		
	}

}

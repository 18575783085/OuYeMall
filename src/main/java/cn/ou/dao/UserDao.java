package cn.ou.dao;

import cn.ou.entity.User;

/**
 * 用户Dao接口
 * 数据库语句
 * @author Administrator
 *
 */
public interface UserDao {

	/**
	 * 根据用户名和密码查询对应的用户信息，
	 * 存在返回对应的用户信息，反之返回null
	 * @param username : 用户名
	 * @param password ： 密码
	 * @return 存在则返回User类对象，反之返回null
	 */
	public User login(String username, String password);
	
	/**
	 * 判断用户名是否存在
	 * @param username 用户名
	 * @return true：表示用户已存在	false：不存在
	 */
	public boolean unIsExist(String username);

	/**
	 * 向数据库的user表中添加一条用户信息
	 * @param user : 封装了用户信息user类对象
	 * @return	影响的行数
	 */
	public int regist(User user) ;

	/*------------------------------------------------------*/
	
	/**
	 * 检查用户输入的手机号码是否存在
	 * @param phone ： 用户输入的手机号码
	 * @return true：表示已存在；false：表示不存在
	 */
	public boolean checkPhone(String phone);

	

}

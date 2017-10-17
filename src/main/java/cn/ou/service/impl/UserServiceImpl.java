package cn.ou.service.impl;

import cn.ou.dao.UserDao;
import cn.ou.dao.impl.UserDaoImpl;
import cn.ou.entity.User;
import cn.ou.service.UserService;
/**
 * 业务层实现类
 * @author Administrator
 *
 */
public class UserServiceImpl implements UserService {
	
	private UserDao userDao = new UserDaoImpl();
	/**
	 * 用户登录的业务逻辑
	 */
	public User login(String username, String password) {
		//通过调用dao接口的用户登录方法来返回结果给servlet
		return userDao.login(username,password);
	}
	
	/**
	 * 通过ajax来调用servlet进行判断用户是否存在的业务逻辑
	 */
	public boolean unisExist(String username) {
		//通过调用dao接口的判断用户是否存在方法来返回结果给servlet
		return userDao.unIsExist(username);
	}

}

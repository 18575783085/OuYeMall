package cn.ou.service.impl;

import cn.ou.dao.UserDao;
import cn.ou.dao.impl.UserDaoImpl;
import cn.ou.entity.User;
import cn.ou.exception.MsgException;
import cn.ou.factory.BasicFactory;
import cn.ou.factory.UserDaoFactory;
import cn.ou.service.UserService;
/**
 * 业务层实现类
 * @author Administrator
 *
 */
public class UserServiceImpl implements UserService {
	
	//private UserDao userDao = new UserDaoImpl();
	//解耦 ----> 通过对类字节码的反射获取该对象的实例
	//private UserDao userDao = UserDaoFactory.getDaoFactory().getInstance();
	//进一步的解耦
	//private UserDao userDao = (UserDao) BasicFactory.getBasicFactory().getInstance(UserDao.class);
	//加上泛型
	private UserDao userDao = BasicFactory.getBasicFactory().getInstance(UserDao.class);
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

	/**
	 * 用户注册的业务逻辑
	 */
	public boolean regist(User user) throws MsgException {
		if(userDao.unIsExist(user.getUsername())){
			throw new MsgException("用户名已经存在！");
		}else {
			int row = userDao.regist(user);
			return row > 0;
		}
	}

}

package cn.ou.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import cn.ou.entity.User;
import cn.ou.factory.BasicFactory;
import cn.ou.service.UserService;
import cn.ou.service.impl.UserServiceImpl;
import cn.ou.utils.DaoUtils;
import cn.ou.utils.MD5Utils;
import cn.ou.utils.MDUtil;

/**
 * 处理用户的登录请求
 * @author Administrator
 * 序号解释：
 * x.x.x.x
 * 登陆步骤.判断用户名是否存在.判断用户密码是否匹配.判断用户是否勾选记住用户名
 *
 */
public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*//1.处理请求参数乱码
		request.setCharacterEncoding("utf-8");*/ 
		
		//2.获取登录信息
		/*String username = request.getParameter("username");
		String password = request.getParameter("password");*/
		String remname = request.getParameter("remname");
		
		//2.接收参数
		//2.1获取登录用户信息
		User user = new User();
		
		try {
			BeanUtils.populate(user, request.getParameterMap());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		
		//3.登录用户（根据用户名和密码查询用户）
		/*由于用户的密码加密存放到数据，所以先查询该用户的密码，
		*然后进行解密，再判断输入的密码是否跟解密后的匹配
		*匹配成功进行下一步操作
		*TODO(为了跟上课堂老师的案例进度，如需解密效果请移步到CopyofLoginServlet)
		*/
		/*创建业务层对象*/
		//A1.业务层接口调用业务层的实现类
		//UserService userService = new UserServiceImpl();
		//解耦--->UserService工厂类
		//UserService userService = UserServiceFactory.getServiceFactory().getServiceFactory();
		//解耦--->工厂模式(用通用工厂类)
		//UserService userService = (UserService) BasicFactory.getBasicFactory().getInstance(UserService.class);
		//解耦--->泛型(用通用工厂类)
		UserService userService = BasicFactory.getBasicFactory().getInstance(UserService.class);
		
		//A2.调用业务层方法
		//由于注册时使用了MD5加密，所以登录时，先将用户输入的密码进行加密，再跟数据库的密码进行匹配
		//password = MD5Utils.md5(password);
		user = userService.login(user.getUsername(),user.getPassword());
		
		//A3.判断登陆是否成功
		if(user == null){
			//A4.登陆失败，提示用户或密码错误--->login.jsp
			request.setAttribute("msg", "用户名或密码错误！");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}else {
			//TODO 30天自动登录
			//判断是否勾选30天自动登录
			if("true".equals(request.getParameter("autologin"))){//勾选
				//创建Cookie对象,把用户名和密码存进Cookie
				Cookie autoLoginCK = new Cookie("autologin", 
						URLEncoder.encode(user.getUsername()+","+user.getPassword(),"UTF-8"));
				/*
				 * Cookie autoLoginCK = new Cookie("autologin", username+","+password);
				 * 异常：java.lang.IllegalArgumentException: Control character in cookie value or attribute.
				 */
				
				//设置最大存活时间
				autoLoginCK.setMaxAge(3600*60*60);//30天
				//设置路径
				autoLoginCK.setPath("/");
				//添加Cookie
				response.addCookie(autoLoginCK);
				
				//下一步---->编写一个过滤器类autologin
			}
			
			
			
			//3.2.1.判断是否需要记住用户名(判断复选按钮的选择属性)
			if("true".equals(remname)){//3.2.3.1.如果勾选记住用户名
				
				//3.2.1.1.将用户名进行url编码之后再存入Cookie中
				Cookie cookie = new Cookie("remname", URLEncoder.encode(user.getUsername(), "utf-8"));
				
				//3.2.1.2.设置Cookie的最大存活时间
				cookie.setMaxAge(3600 * 24 * 30);//30天
				
				//3.2.1.3.设置Cookie的路径
				cookie.setPath(request.getContextPath()+"/");
				
				//3.2.1.4.将Cookie添加到响应中
				response.addCookie(cookie);
				
			}else {
				//3.2.2.取消记住用户名（删除Cookie）----->不勾选
				Cookie cookie = new Cookie("remname", "");
				//3.2.2.1.立即删除Cookie!
				cookie.setMaxAge(0);
				cookie.setPath(request.getContextPath()+"/");
				response.addCookie(cookie);
			}
			
			//3.2.3.进行登录操作（将用户信息保存进session中）
			//TODO --- 登录操作
			//创建session对象，向session保存当前用户的用户名
			HttpSession session = request.getSession();
			
			//将用户名和密码(user对象类的用户信息)保存进session中
			session.setAttribute("user", user);
			//session.setAttribute("password", password);
			
			
			
			//3.2.4.登录成功，跳转页面
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
			
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}

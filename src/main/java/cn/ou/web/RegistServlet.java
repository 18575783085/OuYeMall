package cn.ou.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.ou.dao.UserDao;
import cn.ou.entity.User;
import cn.ou.exception.MsgException;
import cn.ou.factory.BasicFactory;
import cn.ou.factory.UserServiceFactory;
import cn.ou.service.UserService;
import cn.ou.service.impl.UserServiceImpl;
import cn.ou.utils.DaoUtils;
import cn.ou.utils.MDUtil;
import cn.ou.utils.WebUtils;

/**
 * 注册按钮触发的后台操作
 * @author Administrator
 *
 */
public class RegistServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*//1.解决乱码
		//1.1解决请求参数乱码（post提交）
		request.setCharacterEncoding("utf-8");
		//1.2解决响应正文乱码
		response.setContentType("text/html;charset=utf-8");*/
		
		//2.获取用户参数(用户的注册信息)
		User user = new User();
		/*
		 * key		value
		 * String	String[]
		 * username	{"abc"}
		 */
		//页面中表单项 name 属性的值一定要和User类中属性名一样
		try {
			BeanUtils.populate(user, request.getParameterMap());
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}
		//3.数据校验（将信息响应到前端）
		//调用user中的非空校验方法，来抛出自定义异常提示信息
		try {
			user.check();
		} catch (MsgException e) {
			//跳转到注册页面
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
		}
		
		//3.5.判断手机获取短信验证码跟api接口产生的随机码是否一样
		//TODO  略：可以直接通过前端光标移走，Ajax获取调用后台数据来判断短信验证码是否匹配
		
		
		//3.6.校验图片验证码
		//TODO 
		//3.6.1.获取保存在session的图片验证码
		String code = (String) request.getSession().getAttribute("code");
		
		//3.6.2.判断验证码是否匹配
		if(!user.getValistr().toLowerCase().equals(code.toLowerCase())){
			request.setAttribute("msg", "验证码不正确！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		
		//3.7.判断“点击注册用户”时，手机号码发送短信后跟点击注册时，该手机号码是否前后一致
		//3.7.1.获取session中发送短信后的手机号码（AjaxCheckSmsServlet）
		String smsphone = (String) request.getSession().getAttribute("smsphone");
		
		//3.7.2.进行判断
		if(!smsphone.equals(user.getPhone())){//手机号码前后不一致
			request.setAttribute("msg", "× 该手机号码不是原来的号码，请重新获取验证码！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		
		//3.8.对用户输入的密码参数进行MD5加密再保存到数据库
		//TODO 加密出现的小问题：数据库表设计中的密码字符大小设置了100，结果存储不了，改为varchar(200)
		//TODO(为了跟上课堂老师的案例进度，如需加密效果请移步到CopyofRegistServlet)
		/*String MdPassword = "";
		try {
			//3.8.1.获取密码参数，把密码放进加密方法里面
			MdPassword = MDUtil.getEncryptedPwd(password);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}*/
		
		
		
		//3.9.判断是否重复提交
		// 1.从隐藏域中获取token
		String token1 = request.getParameter("token");
		// 2.从session中获取token
		Object tkObj = request.getSession().getAttribute("token");

		if (tkObj != null && token1.equals((String) tkObj)) {
			// 第一次添加,从session中清除token
			request.getSession().getAttribute("token");

		} else {// 非第一次添加
			throw new RuntimeException("请不要重复提交");

		}
		
		//4.创建业务层对象
		//UserService userService = new UserServiceImpl();
		//解耦 ----> UserService工厂类 ---> 通过对类字节码的反射获取该对象的实例
		//UserService userService = UserServiceFactory.getServiceFactory().getInstance();
		//解耦---->当需要更多工厂类时，就要不停地去创建多个相似的工厂类，那干嘛不直接创建一个通用的工厂类？
		//(用通用工厂类)
		//UserService userService = (UserService) BasicFactory.getBasicFactory().getInstance(UserService.class);
		/*
		 * 进一步的解耦引出两个问题
		 * 1、由于参数是个字符串，写错了没有提示
		 * TODO 2、每次还需要强制类型转换
		 * 解耦 --->用泛型(通用工厂类)来修饰数据类型
		 */
		UserService userService = BasicFactory.getBasicFactory().getInstance(UserService.class);
		
		
		
		//4.1.调用业务注册方法
		try {
			//TODO 对升级后的注册页面（MVC模式）进行解耦（接口+配置文件+工厂）+利用DBUtil框架简化Dao的Sql语句
			boolean result = userService.regist(user);
			//4.2.注册成功
			if(result){
				//5.提示用户注册成功，3秒之后跳转到主页
				response.getWriter().write("<h1 style='color:red;text-align:center'>" +
						"恭喜您注册成功, 3秒之后将会跳转到主页...</h1>");
				response.setHeader("Refresh", "3;url="+request.getContextPath()+"/index.jsp");
			
			}else{//注册失败
				response.getWriter().write(
						"<h1 style='color:red;text-align:center'>" +
						"系统错误，请重新注册...</h1>");
				response.setHeader("Refresh", "3;url="
						+request.getContextPath()+"/regist.jsp");
			}
		} catch (MsgException e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/regist.jsp").
				forward(request, response);
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

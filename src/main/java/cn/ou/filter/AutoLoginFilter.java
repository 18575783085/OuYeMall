package cn.ou.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import cn.ou.entity.User;
import cn.ou.factory.BasicFactory;
import cn.ou.service.UserService;

public class AutoLoginFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//1.判断当前是否登录，只有未登录才处理自动登录
		//1.1、强制类型转换-->因为ServletRequest没有获取cookie的方法
		HttpServletRequest request2 = (HttpServletRequest) request;
		
		//1.2、获取session中登录的用户信息
		Object un = request2.getSession().getAttribute("user");
		
		//1.3、判断是否为未登录
		if(un == null){//为空则未登录
			//2、获取所有的Cookie
			Cookie[] cks = request2.getCookies();
			
			//3、cks不为null时，遍历cks
			if(cks != null){
				//4、遍历cks，找自动登录的cookie
				Cookie ck = null;
				for (int i = 0; i < cks.length; i++) {
					//通过cookie的名字来判断是否勾选‘30天自动登录’
					if("autologin".equals(cks[i].getName())){
						//把找到的cookie赋值给ck变量
						ck = cks[i];
						break;
					}
				}
				
				//5、判断是否存在自动登录的Cookie(autologin)
				if(ck != null){//不为空
					//6、获取用户名和密码(cookie的值)，并进行URL反向处理
					String cookieValue = URLDecoder.decode(ck.getValue(), "UTF-8");
					/*
					 * String cookieValue = ck.getValue();
					 * 异常：java.lang.IllegalArgumentException: Control character in cookie value or attribute.
					 */
					
					//7、把获取的cookie值进行拆分
					String[] arrCookieValue = cookieValue.split(",");
					String username = arrCookieValue[0];//用户名
					String password = arrCookieValue[1];//密码
					
					//8、创建业务层对象
					UserService userService = BasicFactory.getBasicFactory().getInstance(UserService.class);
					
					//9、调用用户登录的方法
					User user = userService.login(username, password);
					
					//10、判断用户名和密码，正确实现登录
					if(user != null){
						//不为空，则表示用户存在
						//把用户设置存进到session域中
						request2.getSession().setAttribute("user", user);
						
					}
				}
				
			}
			
		}
		//放行
		chain.doFilter(request, response);
		
	}

	public void destroy() {
	}

}

package cn.ou.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * ServletContext监听器-----通过初始化时，获取web应用路径，返回去前台
 * @author Administrator  
 *
 */
public class MyServletContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		//appPath    request.getContextPath()
		String path = sce.getServletContext().getContextPath();
		sce.getServletContext().setAttribute("appPath", path);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		sce.getServletContext().removeAttribute("appPath");
	}

}

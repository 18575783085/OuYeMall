package cn.ou.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
/**
 * 解决全站乱码-----过滤器
 * 1、直接统一设置字符集，而不需要每一个servlet都单独设置字符集解码
 * @author Administrator
 *
 */
public class EncodeFilter implements Filter {
	/**
	 * 字符集
	 */
	private String encode;
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		//从web.xml配置文件获取初始化参数
		encode = config.getInitParameter("encode");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//设置响应编码格式
		response.setContentType("text/html;charset="+encode);
		//创建装饰后的对象
		MyRequest req = new MyRequest((HttpServletRequest)request);
		
		//放行
		chain.doFilter(request, response);
	}
	class MyRequest extends HttpServletRequestWrapper{
		//被装饰的对象
		private HttpServletRequest request;
		
		private Map<String, String[]>map = null;
		
		public MyRequest(HttpServletRequest request) {
			super(request);
			this.request = request;
		}
		public Map<String, String[]> getParameterMap(){
			if(map == null){
				//判断提交请求的方式是GET、POST、还是其它的五种
				if("POST".equals(request.getMethod())){
					try {
						//处理乱码
						request.setCharacterEncoding(encode);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					map = request.getParameterMap();
					return map;
					
				}else if ("GET".equals(request.getMethod())) {
					//调用request获取form表单提交的所有输入框的值
					map = request.getParameterMap();
					//遍历处理每一个表单项中的乱码
					for(Map.Entry<String, String[]> entry:map.entrySet()){
						//获取value的值
						String values[] = entry.getValue();
						//遍历出数组中的乱码
						if(values != null){
							for (int i = 0; i < values.length; i++) {
								try {
									values[i] = new String(values[i].getBytes("ISO8859-1"),encode);
								} catch (UnsupportedEncodingException e) {
									e.printStackTrace();
								}
							}
						}
					}
					return map;
				}else {
					//其它五种提交方法，爱咋滴咋滴
					map = request.getParameterMap();
					return map;
				}
			}else {
				return map;
			}
		}
		
		public String[] getParameterValues(String name){
			return getParameterMap().get(name);
		}
		
		public String getParameter(String name){
			String values[] = getParameterValues(name);
			return values != null ? values[0] : "";
		}
		
	}
	
	
	@Override
	public void destroy() {
	
	}

}

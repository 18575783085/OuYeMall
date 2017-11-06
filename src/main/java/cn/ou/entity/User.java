package cn.ou.entity;

import cn.ou.exception.MsgException;
import cn.ou.utils.WebUtils;

/** 
 * 注册页面的主要参数的对象实体类
 * @author Administrator
 *
 */
public class User { 
	/**
	 * 注册用户序号(唯一)
	 */
	private int id;
	/**
	 * 用户名(唯一)
	 */
	private String username;
	/**
	 * 用户密码
	 */
	private String password;
	/**
	 * 用户的昵称
	 */
	private String nickname;
	/**
	 * 用户的邮箱(唯一)
	 */
	private String email;
	/**
	 * 用户的手机号码(唯一)
	 */
	private String phonenumber;
	/**
	 * 用户权限角色
	 */
	private String role;
	
	//参数改造
	/**
	 * 确认密码
	 */
	private String password2;
	/**
	 * 短信验证码
	 */
	private String smsvalistr;
	/**
	 * 图片验证码
	 */
	private String valistr;
	
	/**
	 * 非空和数据校验方法
	 * @throws MsgException 
	 */
	public void check() throws MsgException{
		//非空校验
		//3.1.判断参数是否为空，如果为空，请求转发回到注册页面
		if(WebUtils.isNull(username)){
			//如果用户名为空，跳转回注册页面提示用户“用户名不能为空！”
			throw new MsgException( "用户名不能为空！");
		}
		if(WebUtils.isNull(password)){
			//如果用户名为空，跳转回注册页面提示用户“用户名不能为空！”
			throw new MsgException( "密码不能为空！");
		}
		if(WebUtils.isNull(password2)){
			//如果用户名为空，跳转回注册页面提示用户“用户名不能为空！”
			throw new MsgException( "确认密码不能为空！");
		}
		if(WebUtils.isNull(nickname)){
			//如果用户名为空，跳转回注册页面提示用户“用户名不能为空！”
			throw new MsgException( "呢称不能为空！");
		}
		if(WebUtils.isNull(email)){
			//如果用户名为空，跳转回注册页面提示用户“用户名不能为空！”
			throw new MsgException( "邮箱不能为空！");
		}
		if(WebUtils.isNull(phonenumber)){
			//如果用户名为空，跳转回注册页面提示用户“用户名不能为空！”
			throw new MsgException( "手机号码不能为空！");
		}
		if(WebUtils.isNull(smsvalistr)){
			//如果用户名为空，跳转回注册页面提示用户“用户名不能为空！”
			throw new MsgException( "短信验证码不能为空！");
		}
		if(WebUtils.isNull(valistr)){
			//如果用户名为空，跳转回注册页面提示用户“用户名不能为空！”
			throw new MsgException( "验证码不能为空！");
		}
		
		//3.2.判断两次密码是否一致
		if(!password.equals(password2)){
			throw new MsgException("两次密码不一致！");
		}
		
		//3.3.判断邮箱格式是否正确
		//邮箱正则：^\\w+@\\w+(\\.[a-z]+)+$
		if(!email.matches("^\\w+@\\w+(\\.[a-z]+)+$")){
			throw new MsgException("邮箱格式不正确！");
		}
		
		//3.4判断验证码是否一致（根据 手机 获取的 验证码，再进行 判断）
		//3.4.1.判断手机号码是否是11位
		if(!phonenumber.matches("^1[3|5|8][0-9]{9}$")){
			throw new MsgException("手机号码格式不正确！");
		}
	}
	
	
	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public String getSmsvalistr() {
		return smsvalistr;
	}
	public void setSmsvalistr(String smsvalistr) {
		this.smsvalistr = smsvalistr;
	}
	public String getValistr() {
		return valistr;
	}
	public void setValistr(String valistr) {
		this.valistr = valistr;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phonenumber;
	}
	public void setPhone(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	
}

package cn.ou.exception;
/**
 * 自定义异常：用来提示用户输入参数的警告信息
 * @author Administrator
 *
 */
public class MsgException extends Exception {
	public MsgException(){ 
		super();
	}
	
	public MsgException(String msg){
		super(msg);
	}
	
}

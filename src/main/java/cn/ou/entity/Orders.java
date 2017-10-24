package cn.ou.entity;

import java.util.Date;

/**
 * 订单实体类
 * @author Administrator
 *
 */
public class Orders {
	/**
	 * 订单id
	 */
	private String id;
	/**
	 * 订单金额
	 */
	private double money;
	/**
	 * 收货人信息
	 */
	private String receiverinfo;
	/**
	 * 支付状态----0：表示未支付；1：表示已支付
	 */
	private int paystate;
	/**
	 * 订单添加的时间
	 */
	private Date ordertime;
	/**
	 * 用户id
	 */
	private int user_id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getReceiverinfo() {
		return receiverinfo;
	}
	public void setReceiverinfo(String receiverinfo) {
		this.receiverinfo = receiverinfo;
	}
	public int getPaystate() {
		return paystate;
	}
	public void setPaystate(int paystate) {
		this.paystate = paystate;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	
	
}

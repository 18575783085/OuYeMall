package cn.ou.entity;

import java.util.Map;

/**
 * 订单信息实体类
 * 为了显示该订单中所有商品的详细信息
 * @author Administrator
 *
 */
public class OrderInfo {
	/**
	 * 保存订单表中一条订单信息
	 */
	private Orders orders;
	/**
	 * 保存orderitem表中的多条信息
	 */
	private Map<Product, Integer> map;
	
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	public Map<Product, Integer> getMap() {
		return map;
	}
	public void setMap(Map<Product, Integer> map) {
		this.map = map;
	}

}

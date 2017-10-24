package cn.ou.entity;
/**
 * 订单列表实体类
 * @author Administrator
 *
 */
public class OrderItem {
	/**
	 * 订单id
	 */
	private String order_id;
	/**
	 * 商品id
	 */
	private String product_id;
	/**
	 * 订单商品数量
	 */
	private int buynum;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public int getBuynum() {
		return buynum;
	}
	public void setBuynum(int buynum) {
		this.buynum = buynum;
	}

	

}

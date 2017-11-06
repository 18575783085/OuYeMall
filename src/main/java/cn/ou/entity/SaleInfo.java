package cn.ou.entity;
/**
 * 销售榜单（信息）实体类
 * @author OYE
 *
 */
public class SaleInfo {
	/**
	 * 商品id
	 */
	private String prod_id;
	/**
	 * 商品名称
	 */
	private String prod_name;
	/**
	 * 销售数量
	 */
	private int sale_num;
	
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public int getSale_num() {
		return sale_num;
	}
	public void setSale_num(int sale_num) {
		this.sale_num = sale_num;
	}
	
	

}

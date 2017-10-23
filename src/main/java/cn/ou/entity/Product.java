package cn.ou.entity;

import java.io.Serializable;

/**
 * 商品参数
 * @author Administrator
 *
 */
public class Product implements Serializable {
	/**
	 * 商品id
	 */
	private String id;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 商品价格
	 */
	private Double price;
	/**
	 * 商品分类
	 */
	private String category;
	/**
	 * 图片地址
	 */
	private String imgurl;
	/**
	 * 库存数量
	 */
	private int pnum;
	/**
	 * 商品描述
	 */
	private String description;
	
	
	/**
	 * 重写hashCode值:只要id相同，hashCode的值肯定相同
	 */
	public int hashCode() {
		return id == null ? 0:id.hashCode();
	}
	
	/**
	 * 重写equals方法
	 */
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(obj == null){
			 return false;
		}
		
		//判断obj是否为Product
		if(!(obj instanceof Product)){
			//obj不是Product类的对象
			return false;
		}
		
		//说明obj是Product类创建的对象
		Product other = (Product) obj;
		if(id != null && id.equals(other.getId())){
			//说明两个对象id相同
			return true;
		}
		return false;
		
	}
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public int getPnum() {
		return pnum;
	}
	public void setPnum(int pnum) {
		this.pnum = pnum;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
	
}

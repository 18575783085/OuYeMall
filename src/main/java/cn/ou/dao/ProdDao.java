package cn.ou.dao;

import java.util.List;

import cn.ou.entity.Product;

/**
 * 商品Dao层接口
 * 实现数据库语句
 * @author Administrator
 *
 */
public interface ProdDao {
	
	/**
	 * 查询全部商品
	 * @return 全部商品对应的集合对象
	 */
	public List<Product> findAll();

}

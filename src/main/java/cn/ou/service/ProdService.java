package cn.ou.service;

import java.util.List;

import cn.ou.entity.Product;
/**
 * 商品业务层管理接口
 * @author Administrator
 *
 */
public interface ProdService {

	/**
	 * 查询全部商品
	 * @return 全部商品对应的集合对象
	 */
	public List<Product> findAll();

}

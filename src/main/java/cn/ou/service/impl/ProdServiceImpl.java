package cn.ou.service.impl;

import java.util.List;

import cn.ou.dao.ProdDao;
import cn.ou.entity.Product;
import cn.ou.factory.BasicFactory;
import cn.ou.service.ProdService;
/**
 * 商品业务层管理实现
 * @author Administrator
 *
 */
public class ProdServiceImpl implements ProdService {
	
	//创建商品Dao对象
	private ProdDao prodDao = BasicFactory.getBasicFactory().getInstance(ProdDao.class);
	/**
	 * 调用dao的查询商品方法
	 */
	public List<Product> findAll() {
		return prodDao.findAll();
	}

}

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
	
	/**
	 * 调用dao的更新商品库存数据方法
	 */
	public boolean changePnum(String id, int pnum) {
		//1.调用dao层的方法，返回影响的行数
		int row = prodDao.changePnum(id,pnum);
		//2.根据row返回修改成功还是失败
		//1：成功 0：失败
		return row ==1;
		/*
		 * 相等于：
		 * 	if(row == 1){
		 * 	    return true;
		 * }else{
		 * 		return false;
		 * }
		 */
	}

	/**
	 * 调用dao的删除商品的方法
	 */
	public boolean deleteProdById(String id) {
		//获取影响行数
		int row = prodDao.deleteProdById(id);
		
		//根据row返回删除成功还是失败
		return row == 1;
	}

	/**
	 * 调用dao的按条件查询商品的方法
	 */
	public List<Product> findAllById(String name, String cate, Double min, Double max) {
		
		return prodDao.findAllById(name,cate,min,max);
	}

	/**
	 * 调用dao的根据商品id查询对应商品的详细信息的方法
	 */
	public Product findProdById(String id) {
		return prodDao.findProdById(id);
	}

}

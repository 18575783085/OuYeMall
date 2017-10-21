package cn.ou.dao.impl;

import java.sql.SQLException;
import java.util.List;

import cn.ou.dao.ProdDao;
import cn.ou.entity.Product;
import cn.ou.utils.BeanListHandler;
import cn.ou.utils.DaoUtils;
/**
 * 商品Dao层实现类
 * 执行数据库语句
 * @author Administrator
 *
 */
public class ProdDaoImpl implements ProdDao {

	/**
	 * 查询全部商品的业务逻辑
	 */
	public List<Product> findAll() {
		//1.声明集合对象，用来保存查询出全部商品
		List<Product> list = null;
		
		//2.编写sql语句
		String sql = "select * from products";
		
		try {
			//3.执行查询，返回结果
			list = DaoUtils.query(sql, new BeanListHandler<Product>(Product.class));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//4.返回查询结果
		return list;
	}

}

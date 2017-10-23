package cn.ou.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.ou.dao.ProdDao;
import cn.ou.entity.Product;
import cn.ou.utils.BeanHandler;
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

	/**
	 * 修改商品库存数据的业务逻辑
	 */
	public int changePnum(String id, int pnum) {
		//1.编写sql语句
		String sql = "update products "
				+ "set pnum=? "
				+ "where id=?";
		try {
			//2.执行更新修改
			int row = DaoUtils.update(sql, pnum,id);
			
			//3.返回影响行数
			return row;
		} catch (SQLException e) {
			e.printStackTrace();
			//失败则返回0
			return 0;
		}
		
	}

	
	/**
	 * 删除单个商品的业务逻辑
	 */
	public int deleteProdById(String id) {
		//1.编写sql语句
		String sql = "delete from products where id=?";
		
		try {
			//2.执行删除语句
			int row = DaoUtils.update(sql, id);
			
			//3.返回影响行数
			return row;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	/**
	 * 根据条件查询商品的业务逻辑
	 */
	public List<Product> findAllById(String name, String cate, Double min, Double max) {
		//1.编写sql语句
		String sql = "select * from products where name like ?"+"and category like ?";
		
		//2.根据min和max动态拼写sql，并执行查询操作
		BeanListHandler<Product> blh = new BeanListHandler<Product>(Product.class);
		
		try {
			if(min == null && max == null){
				//当不填写最小值和最大值
				return DaoUtils.query(sql, blh, "%"+name+"%","%"+cate+"%");
				
			}else if (min != null && max == null) {
				//只填写了最小值
				sql += "and price >=?";
				return DaoUtils.query(sql, blh, "%"+name+"%","%"+cate+"%",min);
						
			}else if (min == null && max != null) {
				//只填写了最大值
				sql += "and price <=?";
				return DaoUtils.query(sql, blh, "%"+name+"%","%"+cate+"%",max);
				
			}else {
				//两个都填写了
				sql += "and price >=? and price <=?";
				return DaoUtils.query(sql, blh, "%"+name+"%","%"+cate+"%",min,max);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Product>();
		}
	}

	/**
	 * 根据商品id查询对应商品的详细信息的业务逻辑
	 */
	public Product findProdById(String id) {
		//1.编写sql语句
		String sql = "select * from products where id=?";
		
		try {
			//2.执行sql语句，获取商品对象
			Product prod = DaoUtils.query(sql, new BeanHandler<Product>(Product.class), id);
			
			//3.返回对应商品的结果
			return prod;
		} catch (SQLException e) {
			e.printStackTrace();
			//4.查无此信息
			return null;
		}
	}

}

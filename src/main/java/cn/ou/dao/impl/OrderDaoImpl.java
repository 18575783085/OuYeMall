package cn.ou.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import cn.ou.dao.OrderDao;
import cn.ou.entity.OrderItem;
import cn.ou.entity.Orders;
import cn.ou.utils.DaoUtils;
/**
 * 订单Dao层实现类
 * 执行关于订单的数据库业务逻辑
 * @author Administrator
 *
 */
public class OrderDaoImpl implements OrderDao {

	/**
	 * 添加订单信息的业务逻辑
	 */
	public void addOrder(Connection conn, Orders orders) {
		//1.编写sql语句
		String sql = "insert into orders"
				+ "(id,money,paystate,ordertime,receiverinfo,user_id)"
				+ "values"
				+ "(?,?,?,?,?,?)";
		
		try {
			//2.执行sql语句
			DaoUtils.update(conn, sql,orders.getId(),orders.getMoney(),orders.getPaystate(),orders.getOrdertime(),orders.getReceiverinfo(),orders.getUser_id());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 添加订单列表信息的业务逻辑
	 */
	public void addOrderItem(Connection conn, OrderItem item) {
		//1.编写sql语句
		String sql = "insert into orderitem"
				+ "(order_id,product_id,buynum)"
				+ "values"
				+ "(?,?,?)";
		
		try {
			//2.执行sql语句
			DaoUtils.update(conn, sql, item.getOrder_id(),item.getProduct_id(),item.getBuynum());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

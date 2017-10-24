package cn.ou.dao;

import java.sql.Connection;

import cn.ou.entity.OrderItem;
import cn.ou.entity.Orders;

/**
 * 订单Dao层接口
 * 关于订单的数据库语句
 * @author Administrator
 *
 */
public interface OrderDao {

	/**
	 * 向orders表中添加一条订单信息的数据
	 * @param conn ：数据库连接对象
	 * @param orders ：封装了订单相关信息的对象
	 */
	void addOrder(Connection conn, Orders orders);

	/**
	 * 向orderItem表中添加一条订单项目信息的数据
	 * @param conn ：数据库连接对象
	 * @param item ：封装了订单项目相关信息的对象
	 */
	void addOrderItem(Connection conn, OrderItem item);

}

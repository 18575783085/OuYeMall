package cn.ou.dao;

import java.sql.Connection;
import java.util.List;

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

	/**
	 * 根据用户id从orders（一张订单）表查询userId对应的全部订单
	 * @param userId：用户的id
	 * @return 对应全部订单
	 */
	List<Orders> findOrdersByUid(int userId);

	/**
	 * 根据用户id从orderItem(属于自己的订单列表)表中查询userId对应的全部订单项目
	 * @param userId：用户id
	 * @return userId对应的全部订单项目
	 */
	List<OrderItem> findOrderItemsByUid(int userId);

	/**
	 * 根据订单的id查询订单的详细信息（事务版）
	 * @param conn ：数据库连接对象
	 * @param oid ：订单的id
	 * @return 对应的订单详细信息
	 */
	Orders findOrderByOid(Connection conn, String oid);

	/**
	 * 根据订单id查询对应的订单下的全部订单项
	 * @param conn ：数据库连接对象
	 * @param oid ：订单的id
	 * @return 对应订单下的全部订单项
	 */
	List<OrderItem> findOrderItemsByOid(Connection conn, String oid);

	/**
	 * 根据订单id从orderitem（订单列表）表中删除对应订单下的全部订单项目
	 * @param conn ：数据库连接对象
	 * @param oid ：订单id
	 */
	void deleteOrderItemsByOid(Connection conn, String oid);
	
	/**
	 * 根据订单id从orders表删除对应的订单信息
	 * @param conn ：数据库连接对象
	 * @param oid ：订单id
	 */
	void deleteOrderByOid(Connection conn, String oid);

	/**
	 * 根据订单id从orders表中查询对应的订单信息
	 * @param p2_Order 订单id
	 * @return 返回订单信息
	 */
	Orders findOrderByOid(String p2_Order);

	/**
	 * 修改订单的支付状态
	 * @param oid：订单id
	 * @param paystate：修改后的订单状态
	 */
	void changePaystate(String oid, int paystate);




}

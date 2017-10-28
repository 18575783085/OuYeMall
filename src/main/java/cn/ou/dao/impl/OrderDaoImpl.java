package cn.ou.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.ou.dao.OrderDao;
import cn.ou.entity.OrderItem;
import cn.ou.entity.Orders;
import cn.ou.utils.BeanHandler;
import cn.ou.utils.BeanListHandler;
import cn.ou.utils.DaoUtils;
/**
 * 订单Dao层实现类
 * 执行关于订单的数据库业务逻辑
 * @author Administrator
 *
 */
public class OrderDaoImpl implements OrderDao {

	/**
	 * 添加订单信息的业务逻辑（事务版）
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
	 * 添加订单列表信息的业务逻辑（事务版）
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

	/**
	 * 查询属于该用户的所有订单信息的业务逻辑
	 */
	public List<Orders> findOrdersByUid(int userId) {
		//1、编译sql语句
		String sql = "select * from orders where user_id=?";
		
		try {
			//2、执行sql语句，获取订单信息（1条或多条）
			List<Orders> orders = DaoUtils.query(sql, new BeanListHandler<Orders>(Orders.class), userId);
			
			//3.返回订单信息
			return orders;
		} catch (SQLException e) {
			e.printStackTrace();
			//为了防止调用该方法的位置出现空指针异常
			return new ArrayList<Orders>();
		}
	}

	/**
	 * 根据用户id查询属于该用户自己的订单列表的业务逻辑
	 */
	public List<OrderItem> findOrderItemsByUid(int userId) {
		//1、编写sql语句
		String sql = "select orderitem.* from " +
				"orders,orderitem " +
				"where  orders.id = orderitem.order_id " +
				"and orders.user_id=?";
		try {
			//2.执行sql语句
			List<OrderItem> orderItems = DaoUtils.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), userId);
			
			//3.返回订单列表集合
			return orderItems;
		} catch (SQLException e) {
			e.printStackTrace();
			//为了防止调用该方法的位置出现控制控制异常
			return new ArrayList<OrderItem>();
		}
		
	}

	/**
	 * 根据订单id查询该订单的详细信息的业务逻辑（事务版）
	 */
	public Orders findOrderByOid(Connection conn, String oid) {
		//1、编写sql语句
		String sql = "select * from orders where id=?";
		
		try {
			//2、执行sql语句,获取查询结果
			Orders orders = DaoUtils.query(conn, sql, new BeanHandler<Orders>(Orders.class), oid);
			//3、返回查询结果
			return orders;
		} catch (SQLException e) {
			e.printStackTrace();
			
			//3、如果查询不到，则返回空
			return null;
		}
		
		
	}

	/**
	 * 根据订单id查询该订单的列表项的业务逻辑（事务版）
	 */
	public List<OrderItem> findOrderItemsByOid(Connection conn, String oid) {
		//1、编写sql语句
		String sql = "select * from orderitem where id=?";
		
		try {
			//2、执行sql语句，获取查询结果
			List<OrderItem> orderItems = DaoUtils.query(conn, sql, new BeanListHandler<OrderItem>(OrderItem.class), oid);
			
			//3、返回查询订单列表结果
			return orderItems;
		} catch (SQLException e) {
			e.printStackTrace();
			//3、如果查询不到，则返回一个空集合
			return new ArrayList<OrderItem>();
		}
	}

	/**
	 * 根据订单id删除该订单所在的列表项的业务逻辑（事务版）
	 */
	public void deleteOrderItemsByOid(Connection conn, String oid) {
		//1、编写sql语句
		String sql = "delete from orderitem where id=?";
		
		try {
			//2、执行sql语句，获取影响行数
			DaoUtils.update(conn, sql, oid);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 根据订单id删除订单的业务逻辑（事务版）
	 */
	public void deleteOrderByOid(Connection conn, String oid) {
		//1、编写sql语句
		String sql = "delete from orders where id=?";
		
		try {
			//2、执行sql语句
			DaoUtils.update(conn, sql, oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据订单id查询订单信息的业务逻辑（非事务版）
	 */
	public Orders findOrderByOid(String p2_Order) {
		//1.编写sql语句
		String sql = "select * from orders where id=?";
		
		try {
			//2.执行sql语句，获取结果对象
			Orders orders = DaoUtils.query(sql, new BeanHandler<Orders>(Orders.class), p2_Order);
			
			//3.返回结果对象
			return orders;
		} catch (SQLException e) {
			e.printStackTrace();
			//3.查不到则返回null
			return null;
		}
	}

	/**
	 * 根据订单id修改订单的支付状态的业务逻辑
	 */
	public void changePaystate(String oid, int paystate) {
		//1.编写sql语句
		String sql = "update orders "
				+ "set paystate=? "
				+ "where id=?";
		
		try {
			//2.执行sql语句
			DaoUtils.update(sql, paystate,oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

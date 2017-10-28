package cn.ou.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ou.dao.OrderDao;
import cn.ou.dao.ProdDao;
import cn.ou.dao.UserDao;
import cn.ou.entity.OrderInfo;
import cn.ou.entity.OrderItem;
import cn.ou.entity.Orders;
import cn.ou.entity.Product;
import cn.ou.exception.MsgException;
import cn.ou.factory.BasicFactory;
import cn.ou.service.OrderService;
import cn.ou.utils.DaoUtils;
/**
 * 订单业务层实现类
 * @author Administrator
 *
 */
public class OrderServiceImpl implements OrderService {
	//创建ProdDao对象
	private ProdDao prodDao = BasicFactory.getBasicFactory().getInstance(ProdDao.class);
	//创建OrderDao对象
	private OrderDao orderDao = BasicFactory.getBasicFactory().getInstance(OrderDao.class);
	//声明Connection对象
	private Connection conn = null;
	
	
	/**
	 * 利用事务来进行添加订单信息（事务版）
	 */
	public void addOrder(Orders orders, List<OrderItem> oiList)
			throws MsgException {
		try {
			//1.获取数据库连接，开启事务
			//1.1获取数据库连接
			conn = DaoUtils.getConnection();
			
			//1.2开启事务
			conn.setAutoCommit(false);
			
			//2.Dao层接口--=---向orders表中添加一条数据的方法
			orderDao.addOrder(conn,orders);
			
			//3.遍历oiList，逐一进行操作
			for(OrderItem item:oiList){
				//4.判断库存是否存在，不足抛出自定义异常
				//4.1根据订单列表中的商品id在商品表查询该商品的详细信息
				Product product = prodDao.findProdById(conn,item.getProduct_id());
				
				//4.2判断库存是否充足
				if(product.getPnum() < item.getBuynum()){
					//如果商品数量少于订单中商品的数量
					throw new MsgException("库存不足，id："+product.getId()+",name："+product.getName()+",pnum："+product.getPnum());
					
				}else {
					//5.如果商品充足，修改商品的库存---等于商品原库存 减去 购买商品的数量
					prodDao.changePnum(conn, product.getId(),product.getPnum()-item.getBuynum());
				}
				
				//6.向orderitem表中添加一条信息
				orderDao.addOrderItem(conn,item);
				
			}
			
			//7.提交事务
			conn.commit();
			
		} catch (MsgException me) {
			try {
				//7.回滚事务
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			//继续抛出异常
			throw me;
			
		} catch (SQLException e) {
			e.printStackTrace();
			//7.回滚事务
			try {
				conn.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			
		}finally{
			//8.关闭数据库连接
			DaoUtils.close(conn, null, null);
		}
		
		

	}

	/**
	 * 利用用户id来查找属于自己的全部订单信息
	 */
	public List<OrderInfo> findOrderInfosByUid(int userId) {
		//1、定义集合对象保存结果集
		List<OrderInfo> resultList = new ArrayList<OrderInfo>();
		
		//2.从orders（订单）表查询userId对应全部订单
		List<Orders> orderList = orderDao.findOrdersByUid(userId);
		
		//3.从orderitem（订单列表）表查询userId(用户id)对应全部订单项（找出属于该用户id的那张订单列表）
		List<OrderItem> itemList = orderDao.findOrderItemsByUid(userId);
		
		//4.遍历orderList----->先遍历订单（有多少张订单）--->然后遍历商品信息和购买商品的数量
		for(Orders orders:orderList){
			//5.创建OrderInfo对象(订单信息)
			OrderInfo info = new OrderInfo();
			
			//6、为info的属性赋值（order_id,product_id,buynum）
			//6.1、为属性orders赋值
			info.setOrders(orders);
			
			//6.2、为属性map赋值（商品信息，商品数量）
			//6.2.1、创建Map<Product,Integer> cartMap
			Map<Product, Integer> cartMap = new HashMap<Product, Integer>();
			
			//6.2.2、遍历itemList（该订单里面的所有商品信息）
			for(OrderItem item:itemList){
				if(item.getOrder_id().equals(orders.getId())){//判断订单里面的订单id是否等于用户生成订单的订单id
					//找出属于该订单里的该商品id的商品信息
					Product product = prodDao.findProdById(item.getProduct_id());
					
					//购物车添加该商品和购买该商品的数量
					cartMap.put(product, item.getBuynum());
				}
			}
			
			//6.2.3将map保存到info(订单信息)的map属性
			info.setMap(cartMap);
			
			//7、将info添加到resultList中
			resultList.add(info);
			
		}
		
		return resultList;
	}

	/**
	 * 根据订单id来进行删除该订单
	 */
	public void deleteOrderByOid(String oid) throws MsgException {
		//1、获取数据库连接并开启事务
		Connection conn = null;
		try {
			//1.1、获取数据库连接
			conn = DaoUtils.getConnection();
			
			//1.2、设置取消自动提交事务
			conn.setAutoCommit(false);
			
			//2只有未支付的订单才能够删除
			//2.1、根据订单id查询订单信息
			Orders orders = orderDao.findOrderByOid(conn,oid);
			
			//2.2、如果该订单已经被其他删除
			//（模拟场景：两个客户端同时登录同一个账号，但其中A客户端的订单列表页面还没有刷新，B客户端进行对这张订单进行了删除操作，A客户端也同样对这订单进行删除操作，这时候应该显示该订单已删除而不是抛出异常！）
			if(orders == null){
				throw new MsgException("<font style='color:red;size:20px'>该订单不存在！不需要再次删除！！</font>");
			}
			
			//2.3、如果不是未支付的订单抛出自定义异常
			if(orders.getPaystate() != 0){
				throw new  MsgException("<font style='color:red;size:20px'>只有未支付的订单才能够删除！！！</font>");
			}
			
			//3、根据订单查询该订单下所有的订单项
			List<OrderItem> itemList = orderDao.findOrderItemsByOid(conn,oid);
			
			//4、遍历itemList,还原每一个商品的库存
			for(OrderItem item:itemList){
				//根据商品的id，把购买的数量还原给库存
				prodDao.updatePnum(conn,item.getProduct_id(),item.getBuynum());
			}
			
			//5、根据订单id(oid)删除orderitem(订单列表)表中的所有订单项
			orderDao.deleteOrderItemsByOid(conn,oid);
			
			//6、根据订单id(oid)删除orders表中一条记录
			orderDao.deleteOrderByOid(conn,oid);
			
			//7、提交事务
			conn.commit();
		} catch (MsgException me) {//当遇到自定义异常或者系统检测的异常，我们都要对数据库的数据进行回滚
			//7、回滚事务
			try {
				conn.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			//将自定义异常继续抛给调用该方法处
			throw me;
		}catch (SQLException e) {
			e.printStackTrace();
			//7、回滚事务
			try {
				conn.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			
		} finally{
			//8、关闭数据库连接对象
			DaoUtils.close(conn, null, null);
		}
		
	}

	/**
	 * 根据订单id查询该订单的详细信息(非事务版)
	 */
	public Orders findOrderByOid(String p2_Order) {
		return orderDao.findOrderByOid(p2_Order);
	}

	/**
	 * 根据订单id修改订单信息中的支付状态
	 */
	public void changePaystate(String oid, int paystate) {
		orderDao.changePaystate(oid,paystate);
	}

}

package cn.ou.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import cn.ou.dao.OrderDao;
import cn.ou.dao.ProdDao;
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
	Connection conn = null;
	
	
	/**
	 * 利用事务来进行添加订单信息
	 */
	public void addOrder(Orders orders, List<OrderItem> oiList)
			throws MsgException {
		try {
			//1.获取数据库连接，开启事务
			//1.1获取数据库连接
			conn = DaoUtils.getConnection();
			
			//1.2开启事务
			conn.setAutoCommit(false);
			
			//2.向orders表中添加一条数据的方法
			orderDao.addOrder(conn,orders);
			
			//3.遍历oiList，逐一进行操作
			for(OrderItem item:oiList){
				//4.判断库存是否存在，不足抛出自定义异常
				//4.1根据商品id查询商品的详细信息
				Product product = prodDao.findProdById(conn,item.getProduct_id());
				
				//4.2判断库存是否充足
				if(product.getPnum() < item.getBuynum()){
					//如果商品数量少于订单中商品的数量
					throw new MsgException("库存不足，id："+product.getId()+",name："+product.getName()+",pnum："+product.getPnum());
					
				}else {
					//5.如果商品充足，修改商品的库存
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

}

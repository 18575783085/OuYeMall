package cn.ou.service;

import java.util.List;

import cn.ou.entity.OrderInfo;
import cn.ou.entity.OrderItem;
import cn.ou.entity.Orders;
import cn.ou.entity.SaleInfo;
import cn.ou.exception.MsgException;

/**
 * 订单业务层接口
 * @author Administrator
 *
 */
public interface OrderService {
	/**
	 * 添加订单相关信息
	 * （向orders添加一条，向orderitem表中添加oiList.size()条数据，修改Products表对应商品的库存）
	 * @param orders ：封装了订单信息的对象
	 * @param oiList ：封装了订单项相关信息对象的集合对象
	 * @throws MsgException : 遇到添加的商品库存不足抛出异常
	 */
	public void addOrder(Orders orders, List<OrderItem> oiList) throws MsgException;

	/**
	 * 根据用户id查询对应用户的全部订单信息
	 * @param userId ： 用户id
	 * @return 对应用户的全部订单信息
	 */
	public List<OrderInfo> findOrderInfosByUid(int userId);

	/**
	 * 根据订单id删除该订单相关信息，并还原该订单购买的所有商品的库存
	 * @param id：订单id
	 * @throws MsgException：删除非“未支付”的订单时抛出异常
	 */
	public void deleteOrderByOid(String id) throws MsgException;

	/**
	 * 根据订单id查询对应的订单信息
	 * @param p2_Order 订单id
	 * @return 返回一个订单对象
	 */
	public Orders findOrderByOid(String p2_Order);

	/**
	 * 修改订单的支付状态 0：表示未支付；1表示：已支付
	 * @param oid：订单id
	 * @param paystate：修改后的支付状态
	 */
	public void changePaystate(String oid, int paystate);

	/**
	 * 查询全部的销售榜单列表
	 * @return 全部的销售榜单
	 */
	public List<SaleInfo> findSaleInfos();

}

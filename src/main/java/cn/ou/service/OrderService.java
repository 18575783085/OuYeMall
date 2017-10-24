package cn.ou.service;

import java.util.List;

import cn.ou.entity.OrderItem;
import cn.ou.entity.Orders;
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

}

package com.icss.biz;

import com.icss.dao.TicketDao;
import com.icss.entity.Ticket;
import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;

import com.icss.dao.UserDao;
import com.icss.dto.JMSDto;
import com.icss.entity.Order;
import com.icss.util.Log;
import com.icss.util.RedisUtil;

import java.util.List;

public class UserBiz {
	/**
	 * 购票的方法.
	 *   1. 更新车票信息.
	 *   2. 写入Redis的新订单集合中.
	 * @param jd
	 * @throws Exception
	 */
	public void buyTicket(JMSDto jd){		
		String seatno = updateTicketState(jd);             //出票 
		Order order = new Order();
		order.setSeatno(seatno);
		order.setOrderno(jd.getOrderno());
		order.setUname(jd.getUname());  
		addOrderMap(order); 							   // 写入redis新订单集合中
	}

	/**
	 * 退票逻辑
	 *  1. 获取所有票的信息
	 *  2. 退掉每张票
	 * @param orders
	 * @throws Exception
	 */
	public void refoundTicket(List<String> orders) throws Exception {
		TicketDao td = new TicketDao();
		List<Ticket> tickets = td.getTicketsByOrder(orders);
		for(Ticket t:tickets){
			td.refoundTicket(t);
		}

	}
	
	/**
	 * 出票  (如果出票失败，seatno为null)
	 * @param jd
	 * @return
	 * @throws Exception
	 */
	private  String updateTicketState(JMSDto jd){	
		
		String seatno = null;
		
		UserDao userDao = new UserDao();		
		try{
			Thread.sleep(2000);                     										//延时模拟
			seatno = userDao.updateTicketState(jd); //更新票的信息
		}catch(Exception e){
			e.printStackTrace();
			Log.logger.error(e.getMessage());			
		}finally{
			userDao.closeConnection();
		}
		
		return seatno;
		
	}
	
	/**
	 * 向redis中添加新订单.
	 * @param order
	 */
	private void addOrderMap(Order order) {
		Jedis jedis = null;
		
		try{
			System.out.println("订单写入redis:" + order.getOrderno() + " ---- " + order.getSeatno() + " ------ " + order.getUname());
			//无论成功,不成功还是出现了异常都需要发送订单数据到redis中,根据作为号是否为null进行判断
			jedis = RedisUtil.getJedis();
			ObjectMapper mapper = new ObjectMapper();  
			String orderJson = mapper.writeValueAsString(order);
			jedis.hset("new_orderMap", order.getOrderno(), orderJson);  //未被处理的新订单的集合.
		}catch(Exception e){
			e.printStackTrace();
			Log.logger.error(e.getMessage());
		}finally{
			if(jedis != null){
				jedis.close();
			}
		}
	}

}

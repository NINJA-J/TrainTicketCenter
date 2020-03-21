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
	 * ��Ʊ�ķ���.
	 *   1. ���³�Ʊ��Ϣ.
	 *   2. д��Redis���¶���������.
	 * @param jd
	 * @throws Exception
	 */
	public void buyTicket(JMSDto jd){		
		String seatno = updateTicketState(jd);             //��Ʊ 
		Order order = new Order();
		order.setSeatno(seatno);
		order.setOrderno(jd.getOrderno());
		order.setUname(jd.getUname());  
		addOrderMap(order); 							   // д��redis�¶���������
	}

	/**
	 * ��Ʊ�߼�
	 *  1. ��ȡ����Ʊ����Ϣ
	 *  2. �˵�ÿ��Ʊ
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
	 * ��Ʊ  (�����Ʊʧ�ܣ�seatnoΪnull)
	 * @param jd
	 * @return
	 * @throws Exception
	 */
	private  String updateTicketState(JMSDto jd){	
		
		String seatno = null;
		
		UserDao userDao = new UserDao();		
		try{
			Thread.sleep(2000);                     										//��ʱģ��
			seatno = userDao.updateTicketState(jd); //����Ʊ����Ϣ
		}catch(Exception e){
			e.printStackTrace();
			Log.logger.error(e.getMessage());			
		}finally{
			userDao.closeConnection();
		}
		
		return seatno;
		
	}
	
	/**
	 * ��redis������¶���.
	 * @param order
	 */
	private void addOrderMap(Order order) {
		Jedis jedis = null;
		
		try{
			System.out.println("����д��redis:" + order.getOrderno() + " ---- " + order.getSeatno() + " ------ " + order.getUname());
			//���۳ɹ�,���ɹ����ǳ������쳣����Ҫ���Ͷ������ݵ�redis��,������Ϊ���Ƿ�Ϊnull�����ж�
			jedis = RedisUtil.getJedis();
			ObjectMapper mapper = new ObjectMapper();  
			String orderJson = mapper.writeValueAsString(order);
			jedis.hset("new_orderMap", order.getOrderno(), orderJson);  //δ��������¶����ļ���.
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

package com.icss.Service;

import com.icss.Dao.*;
import com.icss.Dao.old.*;
import com.icss.Entity.*;
import com.icss.Entity.old.Order;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.codehaus.jackson.map.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import com.icss.dto.JMSDto;
import com.icss.util.Log;
import com.icss.util.RedisUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicketService {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	public static int GenerateCover( int s, int e){
		int t = 0;
		for(int i = s; i < e; i++)
			t = t * 2 + 1;
		for(int i = 0; i < s; i++)
			t *= 2;

		return t;
	}

	/**
	 * ��Ʊ�ķ���.
	 *   1. ���³�Ʊ��Ϣ.
	 *   2. д��Redis���¶���������.
	 * @param ticket Ԥ�ڹ���ĳ�Ʊ��Ϣ
	 */
	public boolean buyTicket(Ticket ticket){
		String seatno = issueTicket(ticket);             //��Ʊ

		Order order = new Order();
		order.setSeatno(ticket.getSeat());
		order.setOrderno(ticket.getId().toString());
		order.setUname(ticket.getOwner());
		addOrderMap(order); 							   // д��redis�¶���������

		return seatno != null;
	}

//	/**
//	 * ��Ʊ�߼�
//	 *  1. ��ȡ����Ʊ����Ϣ
//	 *  2. �˵�ÿ��Ʊ
//	 * @param orders
//	 * @throws Exception
//	 */
//	public void refoundTicket(List<String> orders) throws Exception {
//		TicketDao td = new TicketDao();
////		List<Ticket> tickets = td.getTicketsByOrder(orders);
////		for(Ticket t:tickets){
////			td.refoundTicket(t);
////		}
//	}

	/**
	 * ��Ʊ
	 * @param tickets Ҫ�˵�Ʊ���б�
	 * @return �Ƿ���Ʊ�ɹ�
	 */
	public boolean cancelTickets(List<Ticket> tickets) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		TicketMapper ticketMapper = sqlSession.getMapper(TicketMapper.class);
		for( Ticket t: tickets) {
			boolean succ = false;
			for( int i = 0; i < 3 && !succ; i++)
				succ = ( ticketMapper.cancelTicket(t) > 0 );
			if( !succ ){
				sqlSession.rollback();
				return false;
			}
		}
		sqlSession.commit();
		return true;
	}
	
//	/**
//	 * ��Ʊ  (�����Ʊʧ�ܣ�seatnoΪnull)
//	 * @param jd
//	 * @return
//	 * @throws Exception
//	 */
//	private  String updateTicketState(JMSDto jd){
//
//		String seatno = null;
//
//		UserDao userDao = new UserDao();
//		try{
//			Thread.sleep(2000);                     										//��ʱģ��
//			seatno = userDao.updateTicketState(jd); //����Ʊ����Ϣ
//		}catch(Exception e){
//			e.printStackTrace();
//			Log.logger.error(e.getMessage());
//		}finally{
//			userDao.closeConnection();
//		}
//
//		return seatno;
//	}

	/**
	 * ��Ʊ���������пɹ���ĳ�Ʊ���ɹ�������
	 * @param ticket ��������ĳ�Ʊ����Ϣ
	 * @return
	 */
	public String issueTicket(Ticket ticket){

		String seatNo = null;

//		Thread.sleep(2000);                     										//��ʱģ��
		int occupation = GenerateCover(ticket.getDeparture(), ticket.getArrival());

		System.out.println("sStation ind = " + ticket.getDeparture() + ", eStation ind = " + ticket.getArrival());
		List<Seat> seats = getValidSeats(ticket.getScheduleKey(), occupation);
		if( seats.size() == 0 ){
			System.out.println("no seats available");
			return null;
		}

		for( Seat seat: seats){
			if( addTicket(seat, ticket) ) {
				seatNo = ticket.getId().toString();
				break;
			}
		}

		return seatNo;
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

	// Basic Services

	public List<Seat> getValidSeats(ScheduleKey scheduleKey, int occupation){
		Map<String, Object> cond = new HashMap<String, Object>();
		cond.put("train", scheduleKey.getTrain());
		cond.put("schedule", scheduleKey.getId());
		cond.put("occupation", occupation);

		SqlSession sqlSession = sqlSessionFactory.openSession();
		SeatMapper seatMapper = sqlSession.getMapper(SeatMapper.class);
		return seatMapper.getValidSeats(cond);
	}

	public boolean addTicket(Seat seat, Ticket ticket){
		Map<String, Object> cond = new HashMap<String, Object>();
		cond.put("train", seat.getTrain());
		cond.put("seat", seat.getSeat());
		cond.put("schedule", seat.getSchedule());
		cond.put("occupation", GenerateCover(ticket.getDeparture(), ticket.getArrival()));

		SqlSession sqlSession = sqlSessionFactory.openSession();
		TicketMapper ticketMapper = sqlSession.getMapper(TicketMapper.class);
		SeatMapper seatMapper = sqlSession.getMapper(SeatMapper.class);
		if( seatMapper.getSeatByKey(seat) == null )
			return false;

		ticket.setId(null);
		ticket.setSeat(seat.getSeat());
		if( ticketMapper.insertSelective(ticket) == 0 )
			return false;
		sqlSession.commit();
		return true;
	}

	// Main Function

	public static void alterticket(Ticket ticket){
		ticket.setSeat("another seat");
	}

	public static void main(String[] args) {
		Ticket ticket = new Ticket();
		ticket.setSeat("a seat");

		System.out.println("Seat before = " + ticket.getSeat());
		alterticket(ticket);
		System.out.println("Seat after  = " + ticket.getSeat());
	}

}

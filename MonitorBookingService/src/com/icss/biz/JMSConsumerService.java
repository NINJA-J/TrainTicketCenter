package com.icss.biz;


import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.codehaus.jackson.map.ObjectMapper;
import com.icss.dto.JMSDto;
import com.icss.util.JMSConnectionFactory;
import com.icss.util.Log;

public class JMSConsumerService {

	public void start(){
		
		Connection connection = null; 						// ����
		Session session; 									// �Ự ���ܻ��߷�����Ϣ���߳�
		Destination destination; 							// ��Ϣ��Ŀ�ĵ�
		MessageConsumer messageConsumer; 					// ��Ϣ��������			

		try {
			connection = JMSConnectionFactory.getConnection(); 			// ͨ�����ӹ�����ȡ����
			connection.start(); 										// ��������
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE); 
			destination=session.createQueue("buyTicketQueue"); 			// �������ӵ���Ϣ����
			messageConsumer = session.createConsumer(destination); 		// ������Ϣ������
			messageConsumer.setMessageListener(new Listener()); 		// ע����Ϣ����
			System.out.println("������ʼ����");
		} catch (Exception e) {
			Log.logger.error(e.getMessage());
			e.printStackTrace();
		}
		//connection���ܹر�
	}

}

/**
 * ��Ϣ����
 * 
 * @author Administrator
 * 
 */
class Listener implements MessageListener {	
	
	public void onMessage(Message message) {		
		System.out.println("���յ���Ϣ��" + message.toString());
		UserBiz userBiz = new UserBiz();
		if(message instanceof TextMessage){			
			TextMessage tm = (TextMessage)message;			
			JMSDto jd = null;
			if(message != null){
				ObjectMapper mapper = new ObjectMapper(); 
				try {
					jd = mapper.readValue(tm.getText(),JMSDto.class);  		//��jsonת����JMSDto����
					userBiz.buyTicket(jd);									//��Ʊ��д����
				} catch(Exception e){
					Log.logger.error(e.getMessage());
					e.printStackTrace();
				}				
			}
		}		
	}
}
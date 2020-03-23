package com.icss.start;

import com.icss.Entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.Serializable;

@Service(value = "startConsumerService")
public class StartConsumer {

	static {
		System.out.println("Consumer Service Started...");
	}

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	@Qualifier("destination")
	private Destination destination;

	public void sendTextMessage(String msg){
		System.out.println("Send Message: " + msg);
		jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			}
		});
	}

	public void sendObjectMessage(Serializable o){
		System.out.println("Send Message: " + o);
		jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(o);
			}
		});
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		StartConsumer consumer = (StartConsumer) context.getBean("startConsumerService");
		consumer.sendTextMessage("a message");

		Ticket ticket = new Ticket();
		ticket.setId(5);
		ticket.setTrain("DB007");
		ticket.setSeat("123");
		ticket.setDeparture(0);
		ticket.setArrival(2);
		consumer.sendObjectMessage(ticket);
		//		JMSConsumerService consumer = context.getBean("");
//		consumer.start();
	}
}

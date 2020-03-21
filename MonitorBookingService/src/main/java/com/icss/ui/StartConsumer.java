package com.icss.ui;

import com.icss.Service.JMSConsumerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartConsumer {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/Config/applicationContext.xml");
		JMSConsumerService consumer = new JMSConsumerService();
		consumer.start();
	}
}

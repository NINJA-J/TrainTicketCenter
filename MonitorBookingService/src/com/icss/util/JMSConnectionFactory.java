package com.icss.util;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;


public class JMSConnectionFactory {
	private static final String USERNAME=ActiveMQConnection.DEFAULT_USER; 			// Ĭ�ϵ������û���  
    private static final String PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD; 		// Ĭ�ϵ���������  	
	private static ConnectionFactory connectionFactory;
	  
	static{		  
	  connectionFactory=new ActiveMQConnectionFactory(USERNAME, 
              PASSWORD,  "tcp://172.18.6.155:61616?connectionTimeout=3000");
	}
	
	public static Connection getConnection() throws Exception{
		Connection connection = null;
		
		if(connectionFactory != null){
			connection = connectionFactory.createConnection(); 
		}
		
		return connection;
	}
		
}

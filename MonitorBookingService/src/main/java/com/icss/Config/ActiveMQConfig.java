package com.icss.Config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Connection;
import javax.jms.JMSException;
import java.util.*;

@Configuration
public class ActiveMQConfig {

    @Autowired
    @Qualifier("dbProperties")
    private Properties dbProperties;

    @Autowired
    private ActiveMQConnectionFactory activeMQConnectionFactory;

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    @Autowired
    private ActiveMQQueue activeMQQueue;

    @Bean(name = "activeMQConFactory")
    public ActiveMQConnectionFactory activeMQConnectionFactory(){
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(dbProperties.getProperty("activemq.host"));
        factory.setUserName(dbProperties.getProperty("activemq.username"));
        factory.setPassword(dbProperties.getProperty("activemq.password"));

        //ActiveMQ有序列化白名单，需要全放开或者指定白名单
        factory.setTrustAllPackages(true);
//        factory.setTrustedPackages(Arrays.asList(new String[]{"com.icss.Entity"}));

        return factory;
    }

    @Bean(name = "cachedConnFactory")
    public CachingConnectionFactory cachingConnectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setTargetConnectionFactory(activeMQConnectionFactory);
        factory.setSessionCacheSize(Integer.parseInt(
                dbProperties.getProperty("activemq.cacheSize")));

        return factory;
    }

    @Bean(name = "jmsConnection")
    public Connection jmsConnection(ActiveMQConnectionFactory activeMQConnectionFactory)
            throws JMSException {
        return activeMQConnectionFactory.createConnection();
    }

    @Bean(name = "destination")
    public ActiveMQQueue activeMQQueue(){
        ActiveMQQueue queue = new ActiveMQQueue(
                dbProperties.getProperty("activemq.order.consumer"));

        return queue;
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate jms = new JmsTemplate();
        jms.setConnectionFactory(cachingConnectionFactory);
        jms.setDefaultDestination(activeMQQueue);

        return jms;
    }
}

package com.icss.Service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

@Component(value = "ticketMessageListener")
public class Listener implements MessageListener {

    @Autowired
    private TicketService ticketService;

    @SneakyThrows
    public void onMessage(Message message) {
        System.out.println("接收到消息：" + message.toString());
//        return;
        if(message instanceof TextMessage){
            TextMessage tm = (TextMessage)message;
            System.out.println("----textMessage:   " + tm.getText());
//            JMSDto jd = null;
//            if(message != null){
//                ObjectMapper mapper = new ObjectMapper();
//                try {
//                    jd = mapper.readValue(tm.getText(),JMSDto.class);  		//把json转换成JMSDto对象
//                    Ticket ticket = new Ticket();
//                    ticketService.buyTicket(ticket);									//出票，写订单
//                } catch(Exception e){
//                    Log.logger.error(e.getMessage());
//                    e.printStackTrace();
//                }
//            }
        } else if( message instanceof ObjectMessage){
            ObjectMessage objectMessage = (ObjectMessage) message;
            System.out.println("----objectMessage: " + objectMessage.getObject());
        }
    }
}

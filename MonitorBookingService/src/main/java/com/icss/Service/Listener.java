package com.icss.Service;

import com.icss.Entity.Ticket;
import com.icss.dto.JMSDto;
import com.icss.util.Log;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component(value = "ticketMessageListener")
public class Listener implements MessageListener {

    public void onMessage(Message message) {
        System.out.println("接收到消息：" + message.toString());
        TicketService ticketService = new TicketService();
        if(message instanceof TextMessage){
            TextMessage tm = (TextMessage)message;
            JMSDto jd = null;
            if(message != null){
                ObjectMapper mapper = new ObjectMapper();
                try {
                    jd = mapper.readValue(tm.getText(),JMSDto.class);  		//把json转换成JMSDto对象
                    Ticket ticket = new Ticket();
                    ticketService.buyTicket(ticket);									//出票，写订单
                } catch(Exception e){
                    Log.logger.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}

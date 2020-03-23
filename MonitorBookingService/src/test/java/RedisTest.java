import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icss.Entity.Ticket;
import org.apache.camel.component.mock.TimeClause;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;


    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testRedisValue() throws IOException {
        redisTemplate.opsForValue().set("num1", 1);
        redisTemplate.opsForValue().set("num2", 2);
        Ticket ticket = new Ticket();
        ticket.setId(5);
        ticket.setTrain("a train");
        ticket.setSeat("seat");
        ticket.setDeparture(0);
        ticket.setArrival(2);
        redisTemplate.opsForValue().set("ticket", ticket);
        System.out.println("testRedisValue value set");

        System.out.println("get val: num1 = " + redisTemplate.opsForValue().get("num1"));
        System.out.println("get val: num2 = " + redisTemplate.opsForValue().get("num2"));
        System.out.println("get val: ticket = " + redisTemplate.opsForValue().get("ticket") + "--");
        assertEquals(redisTemplate.opsForValue().get("num1"), 1);
        assertEquals(redisTemplate.opsForValue().get("num2"), 2);
        assertEquals(redisTemplate.opsForValue().get("ticket"), ticket);
    }
}

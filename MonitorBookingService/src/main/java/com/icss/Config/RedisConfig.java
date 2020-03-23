package com.icss.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

@Configuration
public class RedisConfig {

    @Autowired
    @Qualifier("dbProperties")
    private Properties dbProperties;

    @Autowired
    @Qualifier("jedisPoolConfig")
    private JedisPoolConfig jedisPoolConfig;

    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(Integer.parseInt(
                dbProperties.getProperty("redis.pool.maxIdle")));
        jedisPoolConfig.setMaxTotal(Integer.parseInt(
                dbProperties.getProperty("redis.pool.maxTotal")));
        jedisPoolConfig.setMaxWaitMillis(Long.parseLong(
                dbProperties.getProperty("redis.pool.maxWaitMillis")));
        jedisPoolConfig.setTestOnBorrow(Boolean.parseBoolean(
                dbProperties.getProperty("redis.pool.testOnBorrow")));

        return jedisPoolConfig;
    }

    @Bean
    public JedisPool getJedisPool(){
        return new JedisPool(
                jedisPoolConfig,
                dbProperties.getProperty("redis.host"),
                Integer.parseInt(dbProperties.getProperty("redis.port")));
    }

    @Bean(name = "jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(
                dbProperties.getProperty("redis.host"));
        redisStandaloneConfiguration.setDatabase(Integer.parseInt(
                dbProperties.getProperty("redis.database")));
//        redisStandaloneConfiguration.setPassword(
//                dbProperties.getProperty("redis.password"));
        redisStandaloneConfiguration.setPort(
                Integer.parseInt(dbProperties.getProperty("redis.port")));

        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate redisTemplate(
            @Qualifier("jedisConnectionFactory") RedisConnectionFactory redisConnectionFactory){
        RedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        RedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // 定义RedisTemplate，并设置连接工程
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();

        // key 的序列化采用 StringRedisSerializer
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        // value 值的序列化采用 GenericJackson2JsonRedisSerializer
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);

        // 设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setEnableTransactionSupport(Boolean.parseBoolean(
                dbProperties.getProperty("redis.enableTransactionSupport")));

        return redisTemplate;
    }
}

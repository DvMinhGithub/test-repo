package com.example.demo.config;

//import io.lettuce.core.*;
//import io.lettuce.core.api.StatefulRedisConnection;
//import io.lettuce.core.api.sync.RedisCommands;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPooled;

@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Value("${redis.password}")
    private String redisPassword;

    @Value("${redis.username}")
    private String redisUsername;

    @Value("${redis.database}")
    private int redisDatabase;

    @Value("${redis.isAuth}")
    private boolean redisAuth;


    //Config for lettuce
//    private RedisURI redisURI() {
//        RedisURI redisURI = new RedisURI();
//        redisURI.setHost(redisHost);
//        redisURI.setPort(redisPort);
//        redisURI.setCredentialsProvider(new StaticCredentialsProvider(redisUsername, redisPassword.toCharArray()));
//        redisURI.setDatabase(redisDatabase);
//        return redisURI;
//    }

//    @Bean
//    public RedisCommands<String, String> redisCommands() {
//        RedisClient redisClient = RedisClient.create(redisURI());
//        StatefulRedisConnection<String, String> redisConnection = redisClient.connect();
//        return redisConnection.sync();
//    }


    //Config for jedis
    @Bean
    public Jedis jedis() {
        try (JedisPool jedisPool = redisAuth ? new JedisPool(redisHost, redisPort, redisUsername, redisPassword) : new JedisPool(redisHost, redisPort)) {
            Jedis jedis = jedisPool.getResource();
            jedis.select(redisDatabase);
            return jedis;
        }
    }

}
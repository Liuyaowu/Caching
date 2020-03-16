package com.mobei.caching.config;

import com.mobei.caching.bean.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    //过期时间
    private Duration timeToLive = Duration.ofDays(1);

    /**
     * 必须要指定这个缓存key和value的序列化方式，否则对象存储在Redis中是以二进制的形式，
     * 就是说在Redis中看到的不是json字符串
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(timeToLive)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                .disableCachingNullValues();

        RedisCacheManager redisCacheManager = RedisCacheManager
                .builder(connectionFactory)
                .cacheDefaults(configuration)
                .transactionAware()//支持事务
                .build();

        return redisCacheManager;
    }

    @Bean
    public RedisTemplate<String, Employee> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Employee> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        template.setKeySerializer(keySerializer());
        template.setHashKeySerializer(keySerializer());

        template.setValueSerializer(valueSerializer());
        template.setHashValueSerializer(valueSerializer());

        return template;
    }

    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    private RedisSerializer<Object> valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

}

package com.sky.config;

import com.sky.json.JacksonObjectMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    JacksonObjectMapper jacksonObjectMapper = new JacksonObjectMapper();
    GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(jacksonObjectMapper);

    // 对 Redis 缓存的各项属性进行配置
    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10)) // 设置默认过期时间
                .disableCachingNullValues() // 不缓存 null 值，避免缓存穿透
                // 把缓存键序列化为字符串格式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 采用 Jackson JSON 格式对缓存值进行序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));
    }
}

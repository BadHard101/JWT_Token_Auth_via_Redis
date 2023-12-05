package com.example.studentsretake.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
//храним данные авторизации пользователей в нереляционной базе данных редис.
//конфигурация бд редис
@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Bean
    public JedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("redis"); // Конфигурация для подключения к Redis серверу
        configuration.setPort(6379);
        return new JedisConnectionFactory(configuration); // Создание фабрики подключения Jedis
    }

    @Bean
    public RedisTemplate<String, Object> template() {
        RedisTemplate<String, Object> template = new RedisTemplate<>(); // Создание объекта RedisTemplate

        template.setConnectionFactory(connectionFactory()); // Установка фабрики подключения

        // Установка сериализаторов для ключей и значений
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());

        template.setEnableTransactionSupport(true); // Включение поддержки транзакций

        template.afterPropertiesSet(); // Завершение настройки шаблона Redis

        return template; // Возвращаем готовый шаблон Redis
    }
}

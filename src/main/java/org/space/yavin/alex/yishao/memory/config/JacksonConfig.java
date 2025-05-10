package org.space.yavin.alex.yishao.memory.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Jackson配置类
 * 用于配置JSON序列化时使用下划线命名法（snake_case）
 */
@Configuration
public class JacksonConfig {

    /**
     * 配置ObjectMapper使用下划线命名法
     * 这将使所有Controller返回的JSON字段从驼峰命名法转换为下划线命名法
     * 例如：userName -> user_name
     */
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    @Bean
    public ObjectMapper objectMapper() {
        // 创建JavaTimeModule以处理Java 8日期时间类型
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        
        // 配置LocalDateTime序列化器，使用指定的日期时间格式
        LocalDateTimeSerializer localDateTimeSerializer = 
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
        javaTimeModule.addSerializer(LocalDateTime.class, localDateTimeSerializer);
        
        return Jackson2ObjectMapperBuilder.json()
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .modules(javaTimeModule)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // 禁用将日期写为时间戳数组
                .build();
    }
}
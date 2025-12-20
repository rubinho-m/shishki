package com.rubinho.shishki.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Collections;

@Configuration
public class FeignConfiguration {
    @Bean
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder(
                new SpringEncoder(() -> new HttpMessageConverters(
                        Collections.singletonList(new MappingJackson2HttpMessageConverter())
                ))
        );
    }
}

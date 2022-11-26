package dev.byli.orderstatus.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DigitalSignatureConfig {

    @Bean
    public DigitalSignature digitalSignature(){
        return new DigitalSignature();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


}

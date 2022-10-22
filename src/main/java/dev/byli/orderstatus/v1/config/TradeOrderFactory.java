package dev.byli.orderstatus.v1.config;

import dev.byli.orderstatus.v1.component.MockTradeOrderStatus;
import dev.byli.orderstatus.v1.component.TradeOrderStatus;
import dev.byli.orderstatus.v1.component.BinanceTradeOrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class TradeOrderFactory {
    @Value("${order.status.test}")
    private Boolean isTest;

    @Bean
    public TradeOrderStatus getBean(){
        if(isTest){
            log.info("Test :D !");
            return new MockTradeOrderStatus();
        }else{
            log.info("BE CAREFUL ->>> PRODUCTION MODE !!!");
            return new BinanceTradeOrderStatus();
        }
    }
}

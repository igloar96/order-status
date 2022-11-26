package dev.byli.orderstatus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableFeignClients
public class OrderRoutingApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderRoutingApplication.class, args);
	}

}

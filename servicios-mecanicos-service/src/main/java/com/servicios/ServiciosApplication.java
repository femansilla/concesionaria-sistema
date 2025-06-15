package main.java.com.servicios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiciosApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiciosApplication.class, args);
    }
}
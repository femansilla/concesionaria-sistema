package main.java.com.concesionaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ConcesionariaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConcesionariaApplication.class, args);
    }
}
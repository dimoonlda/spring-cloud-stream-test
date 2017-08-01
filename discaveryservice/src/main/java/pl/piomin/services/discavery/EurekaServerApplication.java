package pl.piomin.services.discavery;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;

/**
 * Created by admin on 01.07.2017.
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    protected org.slf4j.Logger logger = LoggerFactory.getLogger(EurekaServerApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

    @Bean
    public String test() {
        logger.error("created test2.");
        return "";
    }
}

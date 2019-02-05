package io.github.radd.mgrtestserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MgrTestServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MgrTestServerApplication.class, args);
    }

}


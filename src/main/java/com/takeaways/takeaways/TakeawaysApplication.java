package com.takeaways.takeaways;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
//使得Filter生效
@ServletComponentScan
@SpringBootApplication
public class TakeawaysApplication {

    public static void main(String[] args) {
        SpringApplication.run(TakeawaysApplication.class, args);
        log.info("Takeaways Application Started");
    }

}

package com.test.testwebmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class TestWebManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestWebManagerApplication.class, args);
    }

}

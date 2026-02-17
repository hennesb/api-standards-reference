package com.example.myapi;

import org.springframework.boot.SpringApplication;

public class TestMyapiApplication {

    public static void main(String[] args) {
        SpringApplication.from(MyapiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}

package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * [起動オプション] -Dspring.profiles.active=[staging, production]
 */
@SpringBootApplication
public class Application {

  public static void main(String[] arguments) {
    SpringApplication.run(Application.class, arguments);
  }
}

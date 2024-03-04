package com.gsdd.dw2;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableCaching
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "DW2 API", version = "2.0",
    description = "REST with Spring-Boot & PostgreSQL",
    contact = @Contact(email = "alex.galvis.sistemas@gmail.com")))
public class Dw2Application {

  public static final String BASE_PACKAGE = "com.gsdd.dw2.";

  public static void main(String[] args) {
    SpringApplication.run(Dw2Application.class, args);
  }
}

package com.gsdd.dw2.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.gsdd.dw2.Dw2Application;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket productApi() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage(Dw2Application.BASE_PACKAGE + "controller"))
        .paths(PathSelectors.regex("/v1.*")).build().apiInfo(apiInfo());
  }

  private Contact getContact() {
    return new Contact("A. Galvis", null, "alex.galvis.sistemas@gmail.com");
  }

  private ApiInfo apiInfo() {
    return new ApiInfo("DW2 SQL REST", "REST con Spring-Boot & H2", "0.0.1.SNAPSHOT", null,
        getContact(), null, null, Collections.emptyList());
  }

}

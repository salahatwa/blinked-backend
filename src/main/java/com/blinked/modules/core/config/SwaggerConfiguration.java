package com.blinked.modules.core.config;

import static io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER;
import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfiguration {
  @Bean
  public OpenAPI springShopOpenAPI() {
    return new OpenAPI()
      .info(new Info()
        .title("Spring boot API example")
        .description("A complete user registry, with access permissions,JWT token, integration and unit tests, usingthe RESTful API pattern.")
        .version("v5.0.0")
        .license(new License()
          .name("GNU General Public License v3.0")
          .url("https://github.com/Throyer/springboot-api-rest-example/blob/master/LICENSE"))
        .contact(new Contact()
          .email("throyer.dev@gmail.com")
          .name("Renato Henrique")
          .url("https://github.com/Throyer")))
      .addSecurityItem(new SecurityRequirement()
    	        .addList("JWT"))
    	      .components(new Components()
    	        .addSecuritySchemes("JWT", new SecurityScheme()
    	          .name("JWT")
    	          .type(SecurityScheme.Type.HTTP)
    	          .scheme("bearer")
    	          .bearerFormat("JWT")));
    
//      .components(new Components()
//    		  .addSecuritySchemes("Sample JAT",
//                      new SecurityScheme()
//                              .type(SecurityScheme.Type.HTTP)
//                              .scheme("bearer")
//                              .bearerFormat("JWT")));
  }
}

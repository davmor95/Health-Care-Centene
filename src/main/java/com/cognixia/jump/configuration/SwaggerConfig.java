package com.cognixia.jump.configuration;

//import static com.google.common.collect.Lists.newArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/*
 * On Postman, do a get request on: http://localhost:8080/v2/api-docs
 * 
 * On Browser, go to URL: http://localhost:8080/swagger-ui.html
 */

/**
 * Configuration for Swagger Documentation for the Restaurant Reviews API.
 * @author David Morales
 * @version v1 (08/29/2020)
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	/**
	 * Selects all APIs that have the class annotation RestController and includes the API's info.
	 * @author David Morales
	 * @return Docket - the Restaurant Reviews API's Documentation
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.paths(PathSelectors.any())
				.build()
				.apiInfo( apiDetails() )
		/*
		 * .useDefaultResponseMessages(false) .globalResponseMessage(RequestMethod.GET,
		 * newArrayList( new ResponseMessageBuilder() .code(500) .message("500 message")
		 * .responseModel(new ModelRef("Error")) .build(), new ResponseMessageBuilder()
		 * .code(403) .message("Forbidden!") .build()))
		 */;
	}
	/**
	 * Creates the Restaurant Reviews API's base info.
	 * @author David Morales
	 * @return AppInfo - the Restaurant Reviews API's base info 
	 */
	private ApiInfo apiDetails() {
		
		return new ApiInfo(
				"Enrollee HealthCare API",
				"API for en enrollee database for a HealthCare system.",
				"1.0", 
				"http://localhost:8080", 
				new Contact("Project Team", "http://localhost:8080/login", 
						""), 
				"API License", 
				"https://github.com/davmor95",
				Collections.emptyList());
	}

}

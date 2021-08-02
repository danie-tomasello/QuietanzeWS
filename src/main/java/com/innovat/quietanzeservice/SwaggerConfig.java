package com.innovat.quietanzeservice;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig 
{
	/*
	 * URL Swagger: http://localhost:5051/swagger-ui.html
	 * Docs: https://swagger.io/docs/
	 */

	@Bean
	public Docket api()
	{
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.innovat.quietanzeservice.controller"))
					.paths(regex("/.*"))
				.build()
				.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() 
	{
		return new ApiInfoBuilder()
				.title("QUIETANZE SERVICE API")
                .description("Spring Boot REST API per l'archivio delle quietanze a seguito di pagamenti F24")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("Daniele Tomasello"," ", "daniele.tomasello@innovatsrl.it"))
                .build();
	}
}


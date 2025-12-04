package com.kdw.wb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowCredentials(true)
		.allowedMethods("*")
//		.allowedOrigins("http://localhost:5500",
//				"http://127.0.0.1:5500",
//				"http://localhost:5173",
//				"http://127.0.0.1:5173",
//				"http://confront:5173")
		.allowedOriginPatterns("*")
		.allowedHeaders("*");
	}
}

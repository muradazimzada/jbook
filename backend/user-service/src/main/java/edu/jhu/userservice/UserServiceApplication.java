package edu.jhu.userservice;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(@NotNull CorsRegistry registry) {
//				// Allow all endpoints for the frontend origin
//				registry.addMapping("/**").allowedOrigins("http://localhost:8005")
//						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//						.allowedHeaders("Authorization", "Content-Type", "X-API-KEY")
//						.exposedHeaders("Authorization")
//						.maxAge(3600)
//						.allowCredentials(true);  // Allow credentials (if necessary)
//			}
//		};
//	}
}

package bakery.employeeTaskManager;

import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jayway.jsonpath.internal.Path;

@SpringBootApplication
public class EmployeeTaskManagerApplication {
	
	// Main method - entry point of the Spring Boot application
	public static void main(String[] args) {
		SpringApplication.run(EmployeeTaskManagerApplication.class, args);
	}
	
	// Method to configure resource handlers
	@Configuration
	public class WebConfig implements WebMvcConfigurer {

	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/**")
	                .addResourceLocations("classpath:/static/")
	                .setCachePeriod(0); // No caching for static resources
	    }
	}

}

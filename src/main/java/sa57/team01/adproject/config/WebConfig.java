package sa57.team01.adproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.path}") // Define this property in your application.properties
    private String uploadDir;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Adjust the mapping pattern as needed
                .allowedOrigins("http://localhost:3000", "10.0.2.2:80")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Assuming you store files in 'uploadDir' on your filesystem
        // And you want them to be accessible via '/uploads/**' URL path
        String resourceLocation = "file:///" + uploadDir.replace("\\", "/"); // windows fix
        String macLocation = "file:" + uploadDir.replace("\\", "/"); // mac fix
        registry.addResourceHandler("/uploads/**").addResourceLocations(resourceLocation);

        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

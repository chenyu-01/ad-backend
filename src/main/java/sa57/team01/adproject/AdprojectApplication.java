package sa57.team01.adproject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sa57.team01.adproject.repositories.*;
import sa57.team01.adproject.services.PropertyService;

import java.nio.file.Path;


@SpringBootApplication
public class AdprojectApplication {

    @Value("${upload.path}")
    private String uploadDir;

    public static void main(String[] args) {
        SpringApplication.run(AdprojectApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {


            // clean external upload folder images
            Path path = Path.of(uploadDir);
            if (path.toFile().exists()) {
                for (java.io.File file : path.toFile().listFiles()) {
                    // if file is a image file
                    if (file.getName().endsWith(".png") || file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg") || file.getName().endsWith(".gif")) {
                        file.delete();
                    }
                }
            }







        };
    }
}


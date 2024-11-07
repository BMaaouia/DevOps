package tn.esprit.spring.kaddem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class KaddemApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(KaddemApplication.class);
        Environment env = app.run(args).getEnvironment();
        System.out.println("Application started on port: " + env.getProperty("server.port"));
    }


}

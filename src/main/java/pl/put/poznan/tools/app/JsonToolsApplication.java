package pl.put.poznan.tools.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"pl.put.poznan.tools.rest"})
public class JsonToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsonToolsApplication.class, args);
    }
}

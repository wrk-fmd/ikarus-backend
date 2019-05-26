package at.wrk.fmd.ikarusbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class IkarusBackendApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
        return builder.sources(IkarusBackendApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(IkarusBackendApplication.class, args);
    }

}
package org.example.migrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MigratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(MigratorApplication.class, args);
    }
}

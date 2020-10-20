package com.example.demoNeo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableNeo4jRepositories
@EnableSwagger2
public class DemoNeo4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoNeo4jApplication.class, args);
    }

}

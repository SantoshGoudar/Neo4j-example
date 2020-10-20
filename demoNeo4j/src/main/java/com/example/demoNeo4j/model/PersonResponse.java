package com.example.demoNeo4j.model;

import java.util.List;
import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

@Data
@QueryResult
public class PersonResponse {
    private Long id;
    private String name;
    private int age;
    private City livesAt;
    private List<Person> friends;
}

package com.example.demoNeo4j.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
@NoArgsConstructor
@Data
public class City {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

}

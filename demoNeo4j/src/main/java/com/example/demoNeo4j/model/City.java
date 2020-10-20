package com.example.demoNeo4j.model;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
@NoArgsConstructor
@Data
@JsonIgnoreProperties("persons")
public class City {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

}

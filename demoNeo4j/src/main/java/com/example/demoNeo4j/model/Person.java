package com.example.demoNeo4j.model;


import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@Data
@NodeEntity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int age;
    @Relationship(type = "STAYS_AT")
    private City livesAt;
    @Relationship(type = "FRIEND_OF")
    private List<FriendOf> friends;

}

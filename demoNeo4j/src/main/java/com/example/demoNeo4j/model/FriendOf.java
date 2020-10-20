package com.example.demoNeo4j.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;


@Data
@NoArgsConstructor
@RelationshipEntity(type = "FRIEND_OF")
@JsonIgnoreProperties("startNode")
public class FriendOf {
    private int fromYears;

    @StartNode
    @EqualsAndHashCode.Exclude
    private Person startNode;

    @EndNode
    @EqualsAndHashCode.Exclude
    private Person endNode;
}


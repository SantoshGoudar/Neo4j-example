package com.example.demoNeo4j.repository;

import java.util.List;
import com.example.demoNeo4j.model.Person;
import com.example.demoNeo4j.model.PersonResponse;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("MATCH (alex:Person)-[:FRIEND_OF]-(afriends:Person),(semen:Person)-[:FRIEND_OF]-(sFriends:Person) WHERE " + "alex.name=$fName AND semen.name=$nName AND (alex)-[:FRIEND_OF]-(sFriends) return sFriends")
    public List<Person> getMutualFriendsBetween(String fName, String nName);

    @Query("MATCH (alex:Person)-[:FRIEND_OF]-(afriends:Person),(afriends:Person)-[:FRIEND_OF]-(sFriends:Person) WHERE" + " alex.name=$fName AND NOT (alex)-[]-(sFriends) return sFriends")
    public List<Person> getFriendSuggestion(String fName);

    @Query("MATCH (pr:Person)-[:FRIEND_OF]-(fr:Person),(pr)-[st:STAYS_AT]->(c:City) where ID(pr)=$id return ID(pr) " + "as id, pr" + ".name" + " as name,pr.age as age, Collect" + "(fr) as friends, c as livesAt")
    public PersonResponse getPersonWithAllFriends(Long id);
}

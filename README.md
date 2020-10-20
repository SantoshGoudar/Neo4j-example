# Neo4j-example

## GraphDB ## 
 GraphDB is a database concept where they save the data that we have in graph topology.
That means they store data as nodes and edges which connect each node.  Node can also have properties and edges can also have properties.
This concept eases in finding relationship between nodes using edges, also traversing from node to node is easy.
Using this concept there are many databases. In this example we will see **Neo4j**.

There are mainly three building blocks of a neo4j - Node, Relationship, Properties. Let us Consider a simple example to better understand it like social media.
Here there will be entity Person he can have many friends, each person lives in a City, lets just consider this scenarios.

**RDBMS.** 
If we want to design this in RDBMS way - we can have three tables
1. Person - with name(String), age(Int), id, city_id
2. City - with name(String), id
3. Friends - with id, id, fromYears(int)

![Screenshot](Diagram.png)

if one person is friend of another we store that in Friends table with id of both persons and when retreiving we can use join query to identify all the friends of a person.
This is fairly simple when we have two tables, but when we have huge business case this join queries become complex and time consuming. In cases like this graphdb helps us.
In graphdb this relationship is saved exactly how we draw that in a graph topology using links(pointers) between two entities relationship.

**Neo4J:**  Below is the first set of things that we want to save with persons and friendship between them.

![Screenshot](graph.png)

Now next thing is when we add city - we can add city as a seperate node and create relationship between each person and city where he recides.
Or you can even add a property called "residesAt" in person node for saving which city he stays in, but with second approach we will loose the ability to easily navigate to people staying at the same city. So lets use the first approach where we will save City as a node and add a relationship between person and city (this is a normalized form we do same thing in RDBMS by having new table for City and using foriegn key in Person table).

So when we consider City as another node this is how the graph looks

![Screenshot](city.png)

So to achieve this there are these constructs in Neo4J.
**Node** - is the main entity - which can have properties. For example  Person - Alex -  has name, age, leaves in some city. He has friends Bob, Semen, 
**Relationship** - is how each node is related to each other.  For Example  Alex is friend of Bob, Semen. Alex Follows Semen on social media, Bob and Semen leaves in City Boston.
 Even relationship can have properties like - Friend Of -relationship can have  from how many years.

## Cypher ##
Cypher is a Neo4j's query langauage used to create, insert, update, retrieve data from neo4j. It follows pattern matching approach to find what you nodes, relationships from the DB you want to retrieve.

Here i'm using docker desktop to run neo4j on my system, you can also download and run  community edition of neo4j on desktops for handson.

1. First step is to setup docker for desktop on your systems. use the below  link they have step by step to get it installed and run it.

https://docs.docker.com/desktop/

2. Then i use the below command to download and run container for Neo4j Community edition on my docker for desktop.

`docker run --restart always --publish=7474:7474 --publish=7687:7687 --volume=$HOME/neo4j/data:/data neo4j:4.1.1`

Once the container is up and running.  Go to http://localhost:7474/  from the browser, from there you can run your cypher queries and do all operations.


We use below query to create all the nodes and relationships between those nodes. Here i'm creating directed relations, note that we cannot create relations without direction. Eventhough you create relation with direction, while querying you can traverse in both direction and you can get all nodes you need.

```

CREATE (alex:Person{name:"Alex",age:24})
CREATE (Keenu:Person{name:"Keenu",age:24})
CREATE(bob:Person{name:"Bob",age:26})
CREATE(semen:Person{name:"Semen",age:25})
CREATE(lucy:Person{name:"Lucy",age:25})
CREATE(james:Person{name:"James",age:42})
CREATE
(alex)-[:FRIEND_OF{fromYears:2}]->(semen),
(alex)-[:FRIEND_OF{fromYears:2}]->(Keenu),
(semen)-[:FRIEND_OF{fromYears:2}]->(Keenu),
(semen)-[:FRIEND_OF{fromYears:2}]->(bob),
(alex)-[:FRIEND_OF{fromYears:3}]->(bob),
(bob)-[:FRIEND_OF{fromYears:5}]->(lucy),
(lucy)-[:FRIEND_OF{fromYears:8}]->(james)
CREATE (boston:City{name:"Boston"})
CREATE (la:City{name:"Los Angeles"})
CREATE (ny:City{name:"NewYork"})
CREATE
(alex)-[:STAYS_AT]->(boston),
(bob)-[:STAYS_AT]->(la),
(semen)-[:STAYS_AT]->(la),
(lucy)-[:STAYS_AT]->(ny),
(james)-[:STAYS_AT]->(ny)

```
Once you create these data. Run below query to get the result back or to see complete data.

`MATCH (n) RETURN n`


This will return you the data exactly as below in graph or you can even see tabalur form.

![Screenshot](city.png)

Now let's see  query for finding mutual friends between "Alex" and "Semen"

`MATCH (alex:Person{name:"Alex"})-[:FRIEND_OF]-(afriends:Person),(semen:Person{name:"Semen"})-[:FRIEND_OF]-(sFriends:Person)
WHERE (alex)-[:FRIEND_OF]-(sFriends) return sFriends`

It returns Keenu and Bob.

Now let's see query for finding friend recommendation for "Alex", here i'm searching for friends of alex's friends who are not already in alex's friend list.

`MATCH (alex:Person{name:"Alex"})-[:FRIEND_OF]-(afriends:Person),(afriends:Person)-[:FRIEND_OF]-(sFriends:Person) WHERE NOT (alex)-[]-(sFriends)
return sFriends`

it returns  'lucy'

## OGM (Object Graph Mapper) ##
 As we have ORM tools for mapping between object models of Java and database entities. We have OGM - object graph mapping library for mapping between POJO models and graph models i.e nodes and relations. It has Session, Transaction, Transaction management and all suppport what we expect from such a library.
This uses neo4j - java drivers to connect to the database.

## Spring Data Neo4j (SDN) ##
we have spring data support for neo4j as well. Under the hood it uses OGM -and reduces all boiler plate code required for session creation, transaction handling etc.
And comes with all the support like @Query support, finder based on method name, sorting, paging etc.

I have implemented the above usecase through Spring boot and neo4j starter.

Below are my entity models 
```
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

@NodeEntity
@NoArgsConstructor
@Data
public class City {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

}

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

```
As you can see @NodeEntity is used to mark "Person" and "City" node. @Relationship - is used to mark relationship.  @RelationshipEntity is used to mark a relationship node to save properties of relationship. It also needs @StartNode and @EndNode to mark start and end of relationship entity. Here i have marked startNode as ignored for Json serialization or deserialization to avoid infinite recursion. Otherwise Person has reference to FriendOf and FriendOf has reference to Person - it will become endless loop.
In the annotation @Relationship - if you dont specify direction - it takes default as outgoing. So when we get or Load a person - we get only friends with out going direction. But if we want to get all friends - we need to set direction as "Undirected", but if we make that even "endNode" in relationship will cause infinite recursion while deserializing.  So Instead of that i have written a special query to get all the friends as below and using @QueryResult i'm getting my desired result.

Below is the repository method for the same
```
@Query("MATCH (pr:Person)-[:FRIEND_OF]-(fr:Person),(pr)-[st:STAYS_AT]->(c:City) where ID(pr)=$id return ID(pr) as id, pr.name as name,pr.age as age, Collect(fr) as friends, c as livesAt")
public PersonResponse getPersonWithAllFriends(Long id);
 
```

You can run the application and go to 

http://localhost:8082/swagger-ui.html#/

And try get all Persons, get mutual Friends or get friend Suggestion.




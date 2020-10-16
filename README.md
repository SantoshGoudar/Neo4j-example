# Neo4j-example

GraphDB - is a database concept where they save the data that we have in graph topology.
That means they store data as nodes and edges which connect each node.  Node can also have properties and edges can also have properties.
This concept eases in finding relationship between nodes using edges, also traversing from node to node is easy.
Using this concept there are many databases. In this example we will see neo4j.

There are mainly three building blocks of a neo4j - Node, Relationship, Properties. Let us Consider a simple example to better understand it like social media.
Here there will be Person that can have many friends,each person can follow certain person, each person lives in a City, lets just consider this scenarios.

Node - is the main entity - which can have properties. For example  Person - Alex -  has name, age, leaves in some city. He has friends Bob, Semen, 
Relationship - is how each node is related to each other.  For Example  Alex is friend of Bob, Semen. Alex Follows Semen on social media, Bob and Semen leaves in City Boston.
 Even relationship can have properties like - Friend Of -relationship can have  from how many years.

Below is the first set of things that we want to save with persons and friendsship between them.

https://github.com/SantoshGoudar/Neo4j-example/blob/main/graph.png

package com.example.demoNeo4j.controller;


import java.util.List;
import com.example.demoNeo4j.model.BulkFriendRequest;
import com.example.demoNeo4j.model.Person;
import com.example.demoNeo4j.model.PersonResponse;
import com.example.demoNeo4j.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("persons")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/get/{id}")
    public Person getPerson(@PathVariable Long id) {
        Person person = personService.getPerson(id);
        return person;
    }

    @GetMapping("/getWithAllFriends/{id}")
    public PersonResponse getPersonWithAllFriends(@PathVariable Long id) {
        PersonResponse person = personService.getPersonWithAllFriends(id);
        return person;
    }

    @GetMapping("/all")
    public Iterable<Person> getPersons() {
        Iterable<Person> all = personService.getPersons();
        return all;
    }

    @GetMapping("/mutualFriends")
    public List<Person> getMutualFriendsBetween(String fName, String nName) {
        return personService.getMutualFriendsBetween(fName, nName);
    }


    @GetMapping("/suggestion")
    public List<Person> getFriendSuggestion(String fName) {
        return personService.getFriendSuggestion(fName);
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @PostMapping("/addFriends")
    public Person addFriends(@RequestBody BulkFriendRequest bulkFriendRequest) {
        return personService.addFriends(bulkFriendRequest);
    }
}

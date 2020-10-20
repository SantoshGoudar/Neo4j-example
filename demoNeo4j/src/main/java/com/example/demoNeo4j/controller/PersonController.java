package com.example.demoNeo4j.controller;


import java.util.List;
import com.example.demoNeo4j.model.Person;
import com.example.demoNeo4j.model.PersonResponse;
import com.example.demoNeo4j.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("persons")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/get/{id}")
    public Person getPerson(@PathVariable Long id) {
        Person person = personRepository.findById(id).orElse(null);
        return person;
    }

    @GetMapping("/getWithAllFriends/{id}")
    public PersonResponse getPersonWithAllFriends(@PathVariable Long id) {
        PersonResponse person = personRepository.getPersonWithAllFriends(id);
        return person;
    }

    @GetMapping("/all")
    public Iterable<Person> getPersons() {
        Iterable<Person> all = personRepository.findAll();
        return all;
    }

    @GetMapping("/mutualFriends")
    public List<Person> getMutualFriendsBetween(String fName, String nName) {
        return personRepository.getMutualFriendsBetween(fName, nName);
    }


    @GetMapping("/suggestion")
    public List<Person> getFriendSuggestion(String fName) {
        return personRepository.getFriendSuggestion(fName);
    }
}

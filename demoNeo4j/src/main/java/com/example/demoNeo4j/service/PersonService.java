package com.example.demoNeo4j.service;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.example.demoNeo4j.model.BulkFriendRequest;
import com.example.demoNeo4j.model.FriendOf;
import com.example.demoNeo4j.model.Person;
import com.example.demoNeo4j.model.PersonResponse;
import com.example.demoNeo4j.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.context.PersistentEntities;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    public Person getPerson(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public PersonResponse getPersonWithAllFriends(Long id) {
        PersonResponse person = personRepository.getPersonWithAllFriends(id);
        return person;
    }

    public Iterable<Person> getPersons() {
        Iterable<Person> all = personRepository.findAll();
        return all;
    }

    public List<Person> getMutualFriendsBetween(String fName, String nName) {
        return personRepository.getMutualFriendsBetween(fName, nName);
    }


    public List<Person> getFriendSuggestion(String fName) {
        return personRepository.getFriendSuggestion(fName);
    }

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    @Transactional
    public Person addFriends(BulkFriendRequest friendRequest) {
        ArrayList<Long> iterable = Lists.newArrayList(friendRequest.getTargetIds());
        iterable.add(friendRequest.getSourceId());
        Iterable<Person> allById = personRepository.findAllById(iterable);
        Person sourcePerson=null;
        for (Iterator<Person> p=allById.iterator();p.hasNext();) {
            Person next = p.next();
            if (next.getId().equals(friendRequest.getSourceId())) {
                sourcePerson = next;
                p.remove();
                break;
            }
        }
        Person source=sourcePerson;
        List<FriendOf> friends = new ArrayList<>();
        allById.forEach(person -> {
            FriendOf friend = new FriendOf();
            friend.setStartNode(source);
            friend.setEndNode(person);
            friend.setFromYears(0);
            friends.add(friend);
        });
        source.setFriends(friends);
        return personRepository.save(source);
    }
}

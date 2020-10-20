package com.example.demoNeo4j.repository;

import com.example.demoNeo4j.model.City;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {
}

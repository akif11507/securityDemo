package com.example.securitydemo.repo;

import com.example.securitydemo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

//@RepositoryRestController
//@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends JpaRepository<Person, Long> {

//    List<Person> findByLastName(@Param("name") String name);

}


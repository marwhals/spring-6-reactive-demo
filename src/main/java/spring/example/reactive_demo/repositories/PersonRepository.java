package spring.example.reactive_demo.repositories;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.example.reactive_demo.domain.Person;

public interface PersonRepository {

    Mono<Person> getById(Integer id);

    Flux<Person> findAll();

}

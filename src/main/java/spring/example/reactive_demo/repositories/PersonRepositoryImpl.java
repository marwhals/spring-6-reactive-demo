package spring.example.reactive_demo.repositories;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.example.reactive_demo.domain.Person;

public class PersonRepositoryImpl implements PersonRepository {

    Person micheal = Person.builder().id(1).firstName("Micheal").lastName("Weston").build();
    Person micheal1 = Person.builder().id(1).firstName("Micheal").lastName("Weston1").build();
    Person micheal2 = Person.builder().id(1).firstName("Bob").lastName("Weston2").build();

    @Override
    public Mono<Person> getById(Integer id) {
        return Mono.just(micheal);
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(micheal, micheal1, micheal2);
    }
}

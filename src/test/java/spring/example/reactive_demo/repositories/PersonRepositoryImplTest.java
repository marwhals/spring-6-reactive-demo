package spring.example.reactive_demo.repositories;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import spring.example.reactive_demo.domain.Person;

class PersonRepositoryImplTest {

    PersonRepositoryImpl personRepository = new PersonRepositoryImpl();

    @Test
    void testMonoByIdBlock() {
        Mono<Person> personMono = personRepository.getById(1);

        Person person = personMono.block(); //Blocking.....not good

        System.out.println(person.toString());

    }

    @Test
    void testGetByIdSubscriber() {
        Mono<Person> personMono = personRepository.getById(1);
        //Non blocking
        personMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void testMapOperation() {
        Mono<Person> personMono = personRepository.getById(1);

        personMono.map(person -> { //😱😱😱😱 Back from the dead 🤣?
            return person.getFirstName();
        }).subscribe(firstName -> {
            System.out.println(firstName);
        });


    }


}
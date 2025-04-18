package spring.example.reactive_demo.repositories;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.example.reactive_demo.domain.Person;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        //😱😱😱😱 Back from the dead 🤣?
        personMono.map(Person::getFirstName).subscribe(System.out::println);

        //Equivalent but more verbose
//        personMono.map(person -> { //😱😱😱😱 Back from the dead 🤣?
//            return person.getFirstName();
//        }).subscribe(firstName -> {
//            System.out.println(firstName);
//        });
    }

    @Test
    void testFluxBlockFirst() {
        Flux<Person> personFlux = personRepository.findAll();

        Person person = personFlux.blockFirst();

        System.out.println(person.toString());
    }

    @Test
    void testFluxSubscriber() {
        Flux<Person> personFlux = personRepository.findAll();

        personFlux.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void testFluxMap() {
        Flux<Person> personFlux = personRepository.findAll();

        personFlux.map(Person::getFirstName).subscribe(System.out::println);
    }

    @Test
    void testFluxToList() {
        Flux<Person> personFlux = personRepository.findAll();

        Mono<List<Person>> listMono = personFlux.collectList();

        listMono.subscribe(list -> {
            list.forEach(person -> System.out.println(person.toString()));
        });

    }

    @Test
    void testFilterOnName() {
        personRepository.findAll()
                .filter(person -> person.getFirstName().equals("Micheal"))
                .subscribe(person -> System.out.println(person.toString()));
    }

    @Test
    void testGetById() {
        Mono<Person> bobMono = personRepository.findAll().filter(person -> person.getFirstName().equals("Bob"))
                .next();
        //Need subscribe for the "back pressure" of a subscriber asking for some data from server
        bobMono.subscribe(person -> System.out.println(person.toString()));
    }

    @Test
    void testFindPersonByIdNotFound() {
        Flux<Person> personFlux = personRepository.findAll();

        final Integer id = 8;

        Mono<Person> personMono = personFlux.filter(person -> person.getId() == id).single()
                .doOnError(throwable -> {
                    System.out.println("Error in flux");
                    System.out.println("Throwable message" + throwable.getMessage());
                });

        // This is required, no back pressure then no exception will be thrown
        personMono.subscribe(person -> {
            System.out.println(person.toString());
        }, throwable -> {
            System.out.println("An error has occurred");
            System.out.println(throwable.getMessage());
        });
    }

    @Test
    void testGetByIdFound() {
        Mono<Person> personMono = personRepository.getById(2);

        assertTrue(personMono.hasElement().block());
    }

    @Test
    void testGetByIdNotFound() {
        Mono<Person> personMono = personRepository.getById(6);

        assertFalse(personMono.hasElement().block());
    }



}
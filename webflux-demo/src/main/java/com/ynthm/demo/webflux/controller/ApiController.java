package com.ynthm.demo.webflux.controller;

import com.ynthm.demo.webflux.entity.Person;
import com.ynthm.demo.webflux.repository.PersonRepository;
import com.ynthm.demo.webflux.service.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.domain.Sort.Order.desc;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@RestController
public class ApiController {

  @Autowired private PersonRepository personRepository;

  @Autowired private R2dbcEntityTemplate template;

  @GetMapping("/hello")
  public Flux<Person> handle() {
    Flux<Person> all =
        template
            .select(Person.class)
            .matching(query(where("name").is("Ethan").and("age").in(18)).sort(by(desc("id"))))
            .all();
    return all;
  }

  @PostMapping("/persons")
  public Mono<Person> save(@RequestBody Person person) {
    // return personRepository.save(person);
    return template.insert(Person.class).using(person);
  }

  @GetMapping("/persons")
  public Flux<Person> list() {
    return template.select(Query.empty(), Person.class);
  }

  @PutMapping("/persons")
  public void update(@RequestBody Person person) {
    template
        .update(Person.class)
        .inTable("person")
        .matching(query(where("id").is(person.getId())))
        .apply(Update.update("name", person.getName()).set("age", person.getAge()))
        .subscribe();
  }

  @DeleteMapping("/persons/{id}")
  public Mono<Integer> delete(Long id) {
    return template.delete(Person.class).matching(Query.query(Criteria.where("id").is(id))).all();
  }

  @GetMapping("test")
  public String get111() {
    Listener listener = new Listener();
    listener.doMany();

    return "1231";
  }

  @GetMapping("test/one")
  public String get2222() {
    Listener listener = new Listener();
    listener.doOne(1);

    return "1231";
  }
}

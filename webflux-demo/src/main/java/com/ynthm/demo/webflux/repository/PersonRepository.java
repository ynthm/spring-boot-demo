package com.ynthm.demo.webflux.repository;

import com.ynthm.demo.webflux.entity.Person;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Repository
public interface PersonRepository extends ReactiveCrudRepository<Person, Long> {

  Flux<Person> findByName(String name);

  @Modifying
  @Query("UPDATE person SET name = :name where id = :id")
  Mono<Integer> setFixedNameFor(String name, Long id);

  @Query("SELECT * FROM person WHERE name = :#{[0]}")
  Flux<Person> findByQueryWithExpression(String name);
}

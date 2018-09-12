package com.liyosi.springreactivestreams;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

/**
 * Created by liyosi on Sep, 2018
 */
@Slf4j
public class ReactiveExampleTests {

  Person michael = new Person("Michale", "Weston");
  Person fiona = new Person("fiona", "Glenna");
  Person sam = new Person("Sams", "Axe");
  Person jesse = new Person("Sesse", "Porter");


  @Test
  public void monoTests() throws Exception {
    // create mono object
    Mono<Person> personMono = Mono.just(michael);

    // get person object from mono publisher

    Person person = personMono.block();

    // output name
    log.info(person.sayName());
  }

  @Test
  public void monoTransform() throws Exception {
    // create person mono
    Mono<Person> personMono = Mono.just(fiona);

    // transform the mono
    PersonCommand command = personMono.map(person -> {
      return new PersonCommand(person);
    }).block();

    // output name
    log.info(command.sayName());

  }

  @Test(expected = NullPointerException.class)
  public void monoFilter() throws Exception {
    Mono<Person> personMono = Mono.just(sam);

    // filter

    Person samAxe = personMono
        .filter(person -> person.getFirstName().equalsIgnoreCase("foo"))
        .block();

    log.info(samAxe.sayName());
  }

  @Test
  public void fluxTest() throws Exception {
    Flux<Person> people = Flux.just(michael, fiona, sam, jesse);

    people.subscribe(person -> log.info(person.sayName()));
  }

  @Test
  public void fluxTestFilter() throws Exception {

    Flux<Person> people = Flux.just(michael, fiona, sam, jesse);

    people
        .filter(person -> person.getFirstName().contains("s"))
        .subscribe(person -> log.info(person.getFirstName()));
  }

  @Test
  public void fluxTestDelayNoOutput() throws Exception {
    Flux<Person> people = Flux.just(michael, fiona, sam, jesse);

    people.delayElements(Duration.ofSeconds(1))
        .subscribe(person -> log.info(person.sayName()));

  }

  @Test
  public void fluxTestDelay() throws Exception {
    CountDownLatch countDownLatch = new CountDownLatch(1);

    Flux<Person> people = Flux.just(michael, fiona, sam, jesse);

    people.delayElements(Duration.ofSeconds(1))
        .doOnComplete(countDownLatch::countDown)
        .subscribe(person -> log.info(person.sayName()));

    countDownLatch.await();
  }

  @Test
  public void fluxTestFilterDelay() throws Exception {

    CountDownLatch countDownLatch = new CountDownLatch(1);

    Flux<Person> people = Flux.just(michael, fiona, sam, jesse);

    people.delayElements(Duration.ofSeconds(1))
        .filter(person -> person.getFirstName().contains("i"))
        .doOnComplete(countDownLatch::countDown)
        .subscribe(person -> log.info(person.sayName()));

    countDownLatch.await();
  }
}

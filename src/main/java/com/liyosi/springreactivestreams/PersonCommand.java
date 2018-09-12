package com.liyosi.springreactivestreams;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by liyosi on Sep, 2018
 */
@Data
@AllArgsConstructor
public class PersonCommand {

  private String firstName;
  private String lastName;

  public PersonCommand(Person person) {
    this.firstName = person.getFirstName();
    this.lastName = person.getLastName();
  }

  public String sayName() {
    return "My name is " + firstName + " " + lastName;
  }
}

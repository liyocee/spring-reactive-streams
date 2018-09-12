package com.liyosi.springreactivestreams;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by liyosi on Sep, 2018
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

  private String firstName;
  private String lastName;

  public String sayName() {
    return "My name is " + firstName + " " + lastName;
  }
}

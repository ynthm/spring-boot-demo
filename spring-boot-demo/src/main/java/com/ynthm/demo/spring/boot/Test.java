package com.ynthm.demo.spring.boot;

import java.util.ArrayList;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class Test {
  public static void main(String[] args) {
    Class c1 = new ArrayList<String>().getClass();
    Class c2 = new ArrayList<Integer>().getClass();
    System.out.println(c1 == c2);
  }
}

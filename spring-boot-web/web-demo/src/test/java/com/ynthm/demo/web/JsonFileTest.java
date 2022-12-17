package com.ynthm.demo.web;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.params.ParameterizedTest;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class JsonFileTest {
  @ParameterizedTest
  @JsonFileSource(resources = "/jsons/test.json")
  void test1(ObjectNode objectNode) {
    System.out.println(objectNode.toString());
  }
}

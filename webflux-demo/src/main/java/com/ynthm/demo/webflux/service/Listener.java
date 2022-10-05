package com.ynthm.demo.webflux.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class Listener {
  public static Map<Integer, BaseService> map = new ConcurrentHashMap<>();

  static {
    map.put(1, new AService());
    map.put(2, new BService());
    map.put(3, new CService());
  }

  public void doOne(int number) {
    if (map.containsKey(number)) {
      map.get(number).doSomething(List.of("3", "5", "sb", "ynthm"));
    }
  }

  public void doMany() {
    List<String> printList = List.of("3", "5", "sb", "ynthm");
    List<BaseService> serviceList = List.of(new AService(), new BService(), new CService());

    serviceList.stream().parallel().forEach(service -> service.doSomething(printList));
  }
}

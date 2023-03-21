package com.ynthm.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ethan Wang
 */
@Data
public class Node implements Serializable {

  protected Long id;
  protected Long pid;
  protected String name;

  protected Integer sortNo;

  protected List<? extends Node> children = new ArrayList<>();

  public Node() {
  }

  public Node(Long id, Long pid, String name) {
    this.id = id;
    this.pid = pid;
    this.name = name;
  }

  public void copyFiled(Node source) {
    this.id = source.getId();
    this.pid = source.getPid();
  }
}

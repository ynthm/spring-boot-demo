package com.ynthm.common.util;

import com.ynthm.common.domain.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Ethan Wang
 */
public class TreeUtil {
  private TreeUtil() {}

  public static <T extends Node> List<T> buildTree(List<T> nodes) {
    Map<Long, List<T>> id2Nodes =
        nodes.stream()
            .filter(node -> node.getPid() != 0)
            .collect(
                Collectors.groupingBy(
                    Node::getPid,
                    Collectors.mapping(
                        Function.identity(),
                        Collectors.collectingAndThen(
                            Collectors.toList(),
                            e ->
                                e.stream()
                                    .sorted(Comparator.comparingInt(Node::getSortNo))
                                    .collect(Collectors.toList())))));
    nodes.forEach(node -> node.setChildren(id2Nodes.getOrDefault(node.getId(), new ArrayList<>())));
    return nodes.stream()
        .filter(node -> node.getPid() == 0)
        .sorted(Comparator.comparingInt(Node::getSortNo))
        .collect(Collectors.toList());
  }
}

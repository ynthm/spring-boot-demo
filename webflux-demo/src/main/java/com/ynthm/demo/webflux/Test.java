package com.ynthm.demo.webflux;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class Test {

  public static int ways(int total, int k) {
    int[][] t = new int[k + 1][total + 1];
    for (int i = 1; i <= k; i++) {
      for (int j = 1; j <= total; j++) {
        if (j < i) {
          t[i][j] = t[i - 1][j];
        } else if (i == j) {
          t[i][j] = 1 + t[i - 1][j];
        } else {
          t[i][j] = t[i - 1][j] + t[i][j - i];
        }
      }
    }
    return t[k][total];
  }

  public static void main(String[] args) {
    System.out.println(ways(8, 2));
  }
}

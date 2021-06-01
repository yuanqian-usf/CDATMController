package com.circulardollar.cdatm.state;

import java.util.Comparator;

public class StateComparator implements Comparator<Integer> {

  @Override
  public int compare(Integer weight1, Integer weight2) {
    return ((weight2 < weight1? weight2 + 5 : weight2) - weight1) % 5;
  }
}

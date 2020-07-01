package com.yan.movielens.recommender.structure;

import java.util.Map;
import java.util.Set;

public interface Vector {
    int size();

    double get(int index);

    void set(int index, double value);

    boolean contains(int index);

    void remove(int index);

    void clear();

    Set<Integer> indexSet();

    Map<Integer,Double> returnMap();
}
